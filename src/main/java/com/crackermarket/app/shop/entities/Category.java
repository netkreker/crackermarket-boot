package com.crackermarket.app.shop.entities;

import com.crackermarket.app.core.BaseEntity;
import com.crackermarket.app.shop.repository.CategoryDAO;
import com.crackermarket.app.shop.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "CATEGORY")
public class Category extends BaseEntity {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PARENT_CATEGORY")
    private Category parentCategory;

    @ManyToMany
    private Set<Parameter> parameters = new HashSet<>();

    public void addParameter(Parameter parameter) {
        parameters.add(parameter);
    }

    public void removeParameter(Parameter parameter) {
        parameters.remove(parameter);
    }

    public Set<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(Set<Parameter> parameters) {
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
