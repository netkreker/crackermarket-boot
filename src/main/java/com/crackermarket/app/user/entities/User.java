package com.crackermarket.app.user.entities;

import com.crackermarket.app.core.BaseEntity;
import com.crackermarket.app.shop.entities.Category;
import com.crackermarket.app.shop.entities.Product;
import com.crackermarket.app.user.enumerations.LevelAccess;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "USERS")
public class User extends BaseEntity {

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "ACCESS_LEVEL")
    @Enumerated(EnumType.STRING)
    private LevelAccess access;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LevelAccess getAccess() {return access; }

    public void setAccess(LevelAccess access) {
        this.access = access;
    }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    ///////////////////////////////////////////////////////////////////////////////////

    // For sellers
    public Product createProduct(){
        return new Product();
    }

    public Boolean removeProduct(){
        return false;
    }

    // For customers
    public Feedback createFeedBack(){
        return new Feedback();
    }

    public Order createOrder(){
        return new Order();
    }

    // For admins
    public Category createCategory(){
        return new Category();
    }

    public Boolean deleteCategory(){
        return false;
    }

    public Boolean setProductNewCategory(){
        return false;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + '\'' +
                "name='" + getName() + '\'' +
                "userName='" + userName + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", access=" + access +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName) &&
                Objects.equals(password, user.password) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(email, user.email) &&
                Objects.equals(phoneNumber, user.phoneNumber) &&
                access == user.access;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password, surname, email, phoneNumber, access);
    }
}
