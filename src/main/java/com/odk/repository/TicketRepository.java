package com.odk.repository;

import com.odk.entity.Personne;
import com.odk.entity.Ticket;
import com.odk.enums.TypePriorite;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByPriorite(TypePriorite type);
    List<Ticket> findByApprenant(Personne id);
    @Query("SELECT t FROM Ticket t WHERE t.apprenant.id = :apprenantId")
    List<Ticket> findByApprenantId(@Param("apprenantId") Integer id);
}
