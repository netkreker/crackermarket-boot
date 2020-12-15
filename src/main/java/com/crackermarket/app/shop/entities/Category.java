package com.crackermarket.app.shop.entities;

import com.crackermarket.app.core.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CATEGORY")
public class Category extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "PARENT_CATEGORY")
    private Category parentCategory;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Parameter> parameters = new ArrayList<>();

    public void addParameter(Parameter parameter) {
        parameters.add(parameter);
    }

    public void removeParameter(Parameter parameter) {
        parameters.remove(parameter);
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameter(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    @Override
    public String toString() {
        return "Category { " +
                " name= " + getName() +
                " parentCategory= " + getParentCategory() +
                " id= " + getId() +
                " }";
    }
}
