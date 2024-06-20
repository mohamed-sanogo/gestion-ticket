package com.odk.service;

import com.odk.entity.BaseDeConnaissance;
import com.odk.repository.BaseRepository;
import org.springframework.stereotype.Service;

@Service
public class BaseDeConnaissanceService {

    private BaseRepository baseRepository;

    public BaseDeConnaissanceService(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }

    public void createbase(BaseDeConnaissance baseDeConnaissance){
        this.baseRepository.save(baseDeConnaissance);
    }

}
