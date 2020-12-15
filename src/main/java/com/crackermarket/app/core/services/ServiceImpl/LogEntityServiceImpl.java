package com.crackermarket.app.core.services.ServiceImpl;

import com.crackermarket.app.core.services.LogEntityService;
import com.crackermarket.app.core.LogEntity;
import com.crackermarket.app.core.repository.LogEntityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LogEntityServiceImpl implements LogEntityService {

    private LogEntityDAO logEntityDAO;

    @Autowired
    public LogEntityServiceImpl(LogEntityDAO logDao) {
        this.logEntityDAO = logDao;
    }

    public LogEntityServiceImpl() {

    }

    @Override
    public void save(LogEntity log) {
        logEntityDAO.save(log);
    }

    @Override
    public LogEntity findById(String id) {
        return logEntityDAO.findById(UUID.fromString(id));
    }

    @Override
    public List<LogEntity> findAll() {
        return logEntityDAO.findAll();
    }

    @Override
    public void delete(String id) {
        logEntityDAO.delete(logEntityDAO.findById(UUID.fromString(id)));
    }

    @Override
    public void update(LogEntity log) {
        logEntityDAO.update(log);
    }
}
