package com.crackermarket.app.shop.services;

import com.crackermarket.app.shop.entities.ParameterValue;

import java.util.List;
import java.util.UUID;

public interface ParameterValueService {
    public void save(ParameterValue parameterValue);
    public ParameterValue findById(UUID id);
    public List<ParameterValue> findAll();
    public void delete(ParameterValue parameterValue);
}
