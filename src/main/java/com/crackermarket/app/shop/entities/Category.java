package com.crackermarket.app.shop.entities;

import com.crackermarket.app.core.BaseEntity;
import com.crackermarket.app.shop.repository.CategoryDAO;
import com.crackermarket.app.shop.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "CATEGORY")
public class Category extends BaseEntity {

    @ManyToOne
//            (cascade = CascadeType.MERGE)
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

    public void copyParametersFromParentCategory(Category parentCategory) {
        if(parentCategory != null) {
            this.parameters.removeAll(parentCategory.getParameters());
            this.parameters.addAll(parentCategory.getParameters());
        }
//            for(Parameter p : this.parameters) {
//                for (Parameter p1 : parentCategory.parameters) {
//                    if (p.getId().equals(p1.getId())) {
//                        this.parameters.remove(p);
//                    }
//                }
//            }
//            for(Parameter p : parentCategory.parameters) {
//                this.parameters.add(p);
//            }

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
