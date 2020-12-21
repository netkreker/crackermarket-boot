package com.crackermarket.app.user.entities;

import com.crackermarket.app.core.BaseEntity;
import com.crackermarket.app.user.enumerations.Rate;
import com.crackermarket.app.shop.entities.Product;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "FEEDBACK")
public class Feedback extends BaseEntity {

    @Column(name = "RATE")
    @Enumerated(EnumType.STRING)
    private Rate rate;

    @Column(name = "RECORD")
    private String record;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    public String getRate() {
        if (rate == Rate.GREAT)
            return "great";
        if (rate == Rate.GOOD)
            return "good";
        if (rate == Rate.SATISFACTORILY)
            return "satisfactorily";
        if (rate == Rate.BAD)
            return "bad";
        if (rate == Rate.VERY_BAD)
            return "very bad";
        return "none";
    }

    public void setRate(String rate) {
        switch (rate.toLowerCase()) {
            case "great":
                this.rate = Rate.GREAT;
                break;
            case "good":
                this.rate = Rate.GOOD;
                break;
            case "satisfactorily":
                this.rate = Rate.SATISFACTORILY;
                break;
            case "bad":
                this.rate = Rate.BAD;
                break;
            case "very bad":
                this.rate = Rate.VERY_BAD;
                break;
            default:
                this.rate = Rate.NONE;
                break;
        }
    }

    public String getRecord() { return record; }

    public void setRecord(String record) { this.record = record; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Product getProduct() { return product; }

    public void setProduct(Product product) { this.product = product; }

    @Override
    public String toString() {
        return "Feedback{" +
                "rate=" + rate.toString() +
                ", record='" + record + '\'' +
                ", user=" + user +
                ", product=" + product +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return rate == feedback.rate &&
                Objects.equals(record, feedback.record) &&
                Objects.equals(user, feedback.user) &&
                Objects.equals(product, feedback.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rate, record, user, product);
    }
}
