package ru.otus.domain;

import ru.otus.anotation.Id;

public class Account {

    @Id
    private long no;
    private String type;

    public Account(int no, String type, int rest) {
        this.no = no;
        this.type = type;
        this.rest = rest;
    }

    public long getNo() {
        return no;
    }

    public void setNo(long no) {
        this.no = no;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRest() {
        return rest;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }

    private int rest;

}
