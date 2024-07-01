package com.odk.service;

import com.odk.entity.BaseDeConnaissance;
import com.odk.entity.Ticket;
import com.odk.repository.BaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseDeConnaissanceService {

    private final BaseRepository baseDeConnaissanceRepository;

    public BaseDeConnaissanceService(BaseRepository baseDeConnaissanceRepository) {
        this.baseDeConnaissanceRepository = baseDeConnaissanceRepository;
    }

    public void addToBaseDeConnaissance(Ticket ticket, String reponse) {
        BaseDeConnaissance baseDeConnaissance = new BaseDeConnaissance();
        baseDeConnaissance.setTitre(ticket.getTitre());
        baseDeConnaissance.setDescription(ticket.getDescription());
        baseDeConnaissance.setReponse(reponse);
        baseDeConnaissanceRepository.save(baseDeConnaissance);
    }

    public BaseDeConnaissance createBase(BaseDeConnaissance base){
        return baseDeConnaissanceRepository.save(base);
    }

    public BaseDeConnaissance updateBase(BaseDeConnaissance base){
        return baseDeConnaissanceRepository.save(base);
    }

    public void deleteBase(Integer id){
        baseDeConnaissanceRepository.deleteById(id);
    }

    public List<BaseDeConnaissance> getAll() {
        return baseDeConnaissanceRepository.findAll();
    }
}
