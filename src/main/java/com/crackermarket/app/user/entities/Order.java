package com.crackermarket.app.user.entities;

import com.crackermarket.app.core.BaseEntity;
import com.crackermarket.app.shop.entities.Product;
import com.crackermarket.app.user.entities.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ORDER")
public class Order extends BaseEntity {

    @Column(name = "TOTAL_PRICE")
    private Double total_price;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany
    @JoinTable(name = "ORDERS_PRODUCTS",
            joinColumns = {@JoinColumn(name = "order_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")})

    private Set<Product> products = new HashSet<>();

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public Boolean addProduct(){
        return true;
    }

    public Boolean removeProduct(){
        return true;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "user=" + user +
                ", total_price=" + total_price +
                '}';
    }
}
