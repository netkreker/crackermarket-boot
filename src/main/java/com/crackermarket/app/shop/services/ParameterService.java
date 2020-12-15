package com.crackermarket.app.shop.services;

import com.crackermarket.app.shop.entities.Parameter;

import java.util.List;
import java.util.UUID;

public interface ParameterService {
    public void save(Parameter parameter);
    public Parameter findById(UUID id);
    public List<Parameter> findByName(String name);
    public List<Parameter> findAll();
    public void delete(Parameter parameter);
}
