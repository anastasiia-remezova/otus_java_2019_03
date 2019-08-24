package ru.otus.executor;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author sergey
 * created on 03.02.19.
 */
public class DbExecutorImpl<T> implements DbExecutor<T> {

    private final Connection connection;

    public DbExecutorImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public long insertRecord(String sql, List<String> params) throws SQLException {

        Savepoint savePoint = this.connection.setSavepoint("savePointName");

        try (PreparedStatement pst = connection.prepareStatement(sql)) {

            if (params != null) {
                for (int idx = 0; idx < params.size(); idx++) {
                    pst.setString(idx + 1, params.get(idx));
                }
            }
            int rowCount = pst.executeUpdate();

            this.connection.commit();
            System.out.println("inserted rowCount:" + rowCount + " sql: " + pst);
            return rowCount;
        } catch (SQLException ex) {
            this.connection.rollback(savePoint);
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    @Override
    public Optional<T> selectRecord(String sql, long id, Function<ResultSet, T> rsHandler) throws SQLException {
        try (PreparedStatement pst = this.connection.prepareStatement(sql)) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return Optional.ofNullable(rsHandler.apply(rs));
            }
        }
    }

    public Optional<T> selectRecord(long id, String tableName, String idName, Function<ResultSet, T> rsHandler) throws SQLException {
        try (PreparedStatement pst = this.connection.prepareStatement("select * from " + tableName + " where " + idName + " = ? ")) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return Optional.ofNullable(rsHandler.apply(rs));
            }
        }
    }

}