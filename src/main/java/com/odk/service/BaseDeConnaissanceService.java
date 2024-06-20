package com.odk.service;

import com.odk.entity.BaseDeConnaissance;
import com.odk.repository.BaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseDeConnaissanceService {

    private final BaseRepository baseRepository;

    public BaseDeConnaissanceService(BaseRepository repository) {
        this.baseRepository = repository;
    }

    public BaseDeConnaissance createBase(BaseDeConnaissance base) {
        return baseRepository.save(base);
    }

    public List<BaseDeConnaissance> getAllBases() {
        return baseRepository.findAll();
    }

    public BaseDeConnaissance getBaseById(Integer id) {
        return baseRepository.findById(id).orElse(null);
    }

    public BaseDeConnaissance updateBase(Integer id, BaseDeConnaissance base) {
        if (baseRepository.existsById(id)) {
            base.setId(id);
            return baseRepository.save(base);
        }
        return null;
    }

    public void deleteBase(Integer id) {
        if (baseRepository.existsById(id)) {
            baseRepository.deleteById(id);
        }
    }
}
