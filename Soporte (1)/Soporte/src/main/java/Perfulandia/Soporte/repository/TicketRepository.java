package Perfulandia.Soporte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Perfulandia.Soporte.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{
    
}