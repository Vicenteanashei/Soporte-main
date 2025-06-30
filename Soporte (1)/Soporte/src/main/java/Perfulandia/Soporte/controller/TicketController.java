package Perfulandia.Soporte.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<EntityModel<Ticket>> crearTicket(@RequestBody Ticket ticket) {
        try {
            Ticket nuevoTicket = ticketService.crearTicket(ticket);
            EntityModel<Ticket> resource = EntityModel.of(nuevoTicket,
                linkTo(methodOn(TicketController.class).obtenerTicketPorId(nuevoTicket.getId())).withSelfRel(),
                linkTo(methodOn(TicketController.class).obtenerTickets()).withRel("tickets"));

            return ResponseEntity
                .created(resource.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public CollectionModel<EntityModel<Ticket>> obtenerTickets() {
        List<EntityModel<Ticket>> tickets = ticketService.obtenerTodosLosTickets().stream()
            .map(ticket -> EntityModel.of(ticket,
                linkTo(methodOn(TicketController.class).obtenerTicketPorId(ticket.getId())).withSelfRel(),
                linkTo(methodOn(TicketController.class).obtenerTickets()).withRel("tickets")))
            .collect(Collectors.toList());

        return CollectionModel.of(tickets,
            linkTo(methodOn(TicketController.class).obtenerTickets()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Ticket>> obtenerTicketPorId(@PathVariable Long id) {
        return ticketService.obtenerTicketPorId(id)
            .map(ticket -> EntityModel.of(ticket,
                linkTo(methodOn(TicketController.class).obtenerTicketPorId(id)).withSelfRel(),
                linkTo(methodOn(TicketController.class).obtenerTickets()).withRel("tickets")))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Ticket>> actualizarTicket(@PathVariable Long id, @RequestBody Ticket ticketActualizado) {
        Ticket actualizado = ticketService.actualizarTicket(id, ticketActualizado);
        EntityModel<Ticket> resource = EntityModel.of(actualizado,
            linkTo(methodOn(TicketController.class).obtenerTicketPorId(id)).withSelfRel(),
            linkTo(methodOn(TicketController.class).obtenerTickets()).withRel("tickets"));

        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTicket(@PathVariable Long id) {
        ticketService.eliminarTicket(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/asignar")
    public ResponseEntity<EntityModel<Ticket>> asignarTicket(@PathVariable Long id, @RequestParam String usuarioId) {
        return Optional.ofNullable(ticketService.asignarTicket(id, usuarioId))
            .map(ticket -> EntityModel.of(ticket,
                linkTo(methodOn(TicketController.class).obtenerTicketPorId(id)).withSelfRel(),
                linkTo(methodOn(TicketController.class).obtenerTickets()).withRel("tickets"),
                linkTo(methodOn(TicketController.class).asignarTicket(id, usuarioId)).withRel("asignar")))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/categorizar")
    public ResponseEntity<EntityModel<Ticket>> categorizarTicket(@PathVariable Long id, @RequestParam CategoriaTicket categoria) {
        return Optional.ofNullable(ticketService.categorizarTicket(id, categoria))
            .map(ticket -> EntityModel.of(ticket,
                linkTo(methodOn(TicketController.class).obtenerTicketPorId(id)).withSelfRel(),
                linkTo(methodOn(TicketController.class).obtenerTickets()).withRel("tickets"),
                linkTo(methodOn(TicketController.class).categorizarTicket(id, categoria)).withRel("categorizar")))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}