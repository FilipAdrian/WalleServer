package com.walle.project.server.entity;

import javax.persistence.*;

@Entity
public class Manufacture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "id_country")
    private Country country;

    @Column(name = "address")
    private String address;

    public Manufacture() {};

    public Manufacture(String name , Country country , String address){
        this.name = name;
        this.country = country;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }

    public String getAddress() {
        return address;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
