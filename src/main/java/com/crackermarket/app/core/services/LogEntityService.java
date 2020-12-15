package com.crackermarket.app.core.services;

import com.crackermarket.app.core.LogEntity;

import java.util.List;

public interface LogEntityService {
    public void save(LogEntity log);
    public LogEntity findById(String id);
    public List<LogEntity> findAll();
    public void delete(String id);
    public void update(LogEntity log);
}
