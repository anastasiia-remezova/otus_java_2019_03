package domain;

import javax.persistence.*;

@Entity
@Table
public class AddressDataSet {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String street;

}
