package domain;

import javax.persistence.*;

@Entity
@Table
public class PhoneDataSet {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String number;

}
