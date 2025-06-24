package Perfulandia.Soporte.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Perfulandia.Soporte.model.CategoriaTicket;
import Perfulandia.Soporte.model.Ticket;
import Perfulandia.Soporte.service.TicketService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<Ticket> crearTicket(@RequestBody Ticket ticket) {
        Ticket nuevo = ticketService.crearTicket(ticket);
        return ResponseEntity.ok(nuevo);
    }

    @GetMapping
    public List<Ticket> obtenerTickets() {
        return ticketService.obtenerTodosLosTickets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> obtenerTicketPorId(@PathVariable Long id) {
        Optional<Ticket> ticket = ticketService.obtenerTicketPorId(id);
        return ticket.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> actualizarTicket(@PathVariable Long id, @RequestBody Ticket ticketActualizado) {
        Ticket actualizado = ticketService.actualizarTicket(id, ticketActualizado);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTicket(@PathVariable Long id) {
        ticketService.eliminarTicket(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/asignar")
    public ResponseEntity<Ticket> asignarTicket(@PathVariable Long id, @RequestParam String usuarioId) {
        Ticket actualizado = ticketService.asignarTicket(id, usuarioId);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/categorizar")
        public ResponseEntity<Ticket> categorizarTicket(@PathVariable Long id, @RequestParam CategoriaTicket categoria) {
            Ticket actualizado = ticketService.categorizarTicket(id, categoria);
            return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }
}