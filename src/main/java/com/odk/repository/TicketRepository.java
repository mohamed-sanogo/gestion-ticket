package com.odk.repository;

import com.odk.entity.Ticket;
import com.odk.enums.TypePriorite;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByPriorite(TypePriorite type);
}
