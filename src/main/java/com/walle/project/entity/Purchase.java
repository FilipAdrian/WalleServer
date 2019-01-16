package com.walle.project.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "data")
    private Date data;
    @Column(name = "cost")
    private BigDecimal cost;
    @Column(name = "quantity")
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private Users users;
    @ManyToOne
    @JoinColumn (name = "id_product")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;
    @Column(name = "amount")
    private BigDecimal amount;

    public Purchase(){}

    public Purchase(String data, Double cost, Integer quantity,
                    Users users, Product product, Client client, Double amount) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd");

        try{
            Date date = simpleDateFormat.parse (data);
            java.sql.Date sqlData = new java.sql.Date (date.getTime ( ));
            this.data = sqlData;
        }
        catch (Exception e)
        {
            e.getLocalizedMessage ();
            e.getMessage ();
        }

        this.cost = new BigDecimal (cost);
        this.quantity = quantity;
        this.users = users;
        this.product = product;
        this.client = client;
        this.amount = new BigDecimal (cost);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
