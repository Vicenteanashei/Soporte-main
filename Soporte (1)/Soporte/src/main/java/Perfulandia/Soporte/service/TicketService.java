package Perfulandia.Soporte.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Perfulandia.Soporte.model.CategoriaTicket;
import Perfulandia.Soporte.model.Ticket;
import Perfulandia.Soporte.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class TicketService {
    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

    @Autowired
    private TicketRepository ticketRepository;

    public Ticket crearTicket(Ticket ticket) {
        logger.info("Intentando guardar ticket: {}", ticket);
        Ticket saved = ticketRepository.save(ticket);
        logger.info("Ticket guardado: {}", saved);
        return saved;
    }

    public List<Ticket> obtenerTodosLosTickets() {
        return ticketRepository.findAll();
    }

    public Optional<Ticket> obtenerTicketPorId(Long id) {
        return ticketRepository.findById(id);
    }

    public Ticket actualizarTicket(Long id, Ticket ticketActualizado) {
        return ticketRepository.findById(id).map(ticket -> {
            ticket.setTitulo(ticketActualizado.getTitulo());
            ticket.setDescripcionTicket(ticketActualizado.getDescripcionTicket());
            ticket.setEstadoTicket(ticketActualizado.getEstadoTicket());
            ticket.setCategoriaTicket(ticketActualizado.getCategoriaTicket());
            ticket.setAsignadoA(ticketActualizado.getAsignadoA());
            ticket.setCreadoPor(ticketActualizado.getCreadoPor());
            return ticketRepository.save(ticket);}).orElseGet(() -> {
                ticketActualizado.setId(id);
                return ticketRepository.save(ticketActualizado);});
    }

    public void eliminarTicket(Long id) {
        ticketRepository.deleteById(id);
    }

    public Ticket asignarTicket(Long id, String usuarioId) {
        return ticketRepository.findById(id).map(ticket -> {
            ticket.setAsignadoA(usuarioId);
            return ticketRepository.save(ticket);}).orElse(null);
    }

    public Ticket categorizarTicket(Long id, CategoriaTicket categoria) {
        return ticketRepository.findById(id).map(ticket -> {
            ticket.setCategoriaTicket(categoria);
            return ticketRepository.save(ticket);}).orElse(null);
    }

}