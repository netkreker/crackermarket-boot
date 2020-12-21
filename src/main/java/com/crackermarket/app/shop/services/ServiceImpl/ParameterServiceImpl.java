package com.crackermarket.app.shop.services.ServiceImpl;

import com.crackermarket.app.shop.entities.Parameter;
import com.crackermarket.app.shop.repository.ParameterDAO;
import com.crackermarket.app.shop.services.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParameterServiceImpl implements ParameterService {

    private ParameterDAO parameterDAO;

    @Autowired
    public ParameterServiceImpl(ParameterDAO parameterDAO) {
        this.parameterDAO = parameterDAO;
    }

    public ParameterServiceImpl() {

    }

    @Override
    public void save(Parameter parameter) {
        parameterDAO.save(parameter);
    }

    @Override
    public Parameter findById(String id) {
        return parameterDAO.findById(UUID.fromString(id));
    }

    @Override
    public List<Parameter> findByName(String name) {
        return parameterDAO.findByName(name);
    }

    @Override
    public List<Parameter> findAll() {
        return parameterDAO.findAll();
    }

    @Override
    public void delete(String id) {
        parameterDAO.delete(parameterDAO.findById(UUID.fromString(id)));
    }

    @Override
    public void update(Parameter parameter) {
        parameterDAO.update(parameter);
    }
}
