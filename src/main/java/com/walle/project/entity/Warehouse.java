package com.walle.project.entity;

import javax.persistence.*;

@Entity
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column
    private String phone;

    @ManyToOne
    @JoinColumn(name = "id_country")
    private Country country;

    public Warehouse(){}
    public Warehouse(String name,Country country,String address,String phone){
        this.name = name;
        this.address = address;
        this.country = country;
        this.phone = phone;

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public Country getCountry() {
        return country;
    }
}
