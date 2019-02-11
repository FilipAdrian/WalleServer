package com.walle.project.server.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.walle.project.server.controller.CustomDateTimeDeserialize;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.text.SimpleDateFormat;

@Entity
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "data")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = CustomDateTimeDeserialize.class)
    private Date data;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "quantity")
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private Users users;
    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;
    @Column(name = "amount")
    private BigDecimal amount;

    public Sales() {
    }

    public Sales(String data, Double price, Integer quantity,
                 Users users, Product product, Client client, Double amount) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd");

        try {
            java.util.Date date = simpleDateFormat.parse (data);
            java.sql.Date sqlData = new java.sql.Date (date.getTime ( ));
            this.data = sqlData;
        } catch (Exception e) {
            e.getLocalizedMessage ( );
            e.getMessage ( );
        }
        this.price = new BigDecimal (price);
        this.quantity = quantity;
        this.users = users;
        this.product = product;
        this.client = client;
        this.amount = new BigDecimal (amount);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Date getData() {
        return data;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public void setData(Date data) {
        this.data = data;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    @Override
    public String toString() {
        return "Sales{" +
                "id=" + id +
                ", data=" + data +
                ", price=" + price +
                ", quantity=" + quantity +
                ", users=" + users +
                ", product=" + product +
                ", client=" + client +
                ", amount=" + amount +
                '}';
    }
}
