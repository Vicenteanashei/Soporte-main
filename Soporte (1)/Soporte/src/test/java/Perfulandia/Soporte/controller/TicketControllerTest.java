package Perfulandia.Soporte.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import Perfulandia.Soporte.model.CategoriaTicket;
import Perfulandia.Soporte.model.EstadoTicket;
import Perfulandia.Soporte.model.Ticket;
import Perfulandia.Soporte.service.TicketService;

@WebMvcTest(TicketController.class)
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @Autowired
    private ObjectMapper objectMapper;

    // Test para código 200 OK
    @Test
    void testObtenerTodosLosTickets_200OK() throws Exception {
        Ticket ticket1 = new Ticket();
        ticket1.setId(1L);
        Ticket ticket2 = new Ticket();
        ticket2.setId(2L);

        List<Ticket> tickets = Arrays.asList(ticket1, ticket2);
        when(ticketService.obtenerTodosLosTickets()).thenReturn(tickets);

        mockMvc.perform(get("/api/tickets"))
               .andExpect(status().isOk());
    }

    // Test para código 201 Created
    @Test
    void testCrearTicket_201Created() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setTitulo("Problema con login");
        ticket.setDescripcionTicket("No puedo iniciar sesión");
        ticket.setEstadoTicket(EstadoTicket.ABIERTO);
        ticket.setCreadoPor("usuario1");

        Ticket ticketGuardado = new Ticket();
        ticketGuardado.setId(1L);
        ticketGuardado.setTitulo(ticket.getTitulo());
        
        when(ticketService.crearTicket(any(Ticket.class))).thenReturn(ticketGuardado);

        mockMvc.perform(post("/api/tickets")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(ticket)))
               .andExpect(status().isCreated());
    }

    // Test para código 404 Not Found
    @Test
    void testObtenerTicketPorId_404NotFound() throws Exception {
        when(ticketService.obtenerTicketPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/tickets/1"))
               .andExpect(status().isNotFound());
    }

    // Test para código 500 Internal Server Error
    @Test
    void testCrearTicket_500InternalServerError() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setTitulo("Problema con login");
        
        when(ticketService.crearTicket(any(Ticket.class)))
            .thenThrow(new RuntimeException("Error inesperado en el servidor"));

        mockMvc.perform(post("/api/tickets")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(ticket)))
               .andExpect(status().isInternalServerError());
    }

    // Test adicional para 200 OK en actualización
    @Test
    void testActualizarTicket_200OK() throws Exception {
        Ticket ticketActualizado = new Ticket();
        ticketActualizado.setId(1L);
        ticketActualizado.setTitulo("Ticket actualizado");

        when(ticketService.actualizarTicket(eq(1L), any(Ticket.class))).thenReturn(ticketActualizado);

        mockMvc.perform(put("/api/tickets/1")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(ticketActualizado)))
               .andExpect(status().isOk());
    }

    // Test adicional para 204 No Content
    @Test
    void testEliminarTicket_204NoContent() throws Exception {
        doNothing().when(ticketService).eliminarTicket(1L);

        mockMvc.perform(delete("/api/tickets/1"))
               .andExpect(status().isNoContent());
    }

    // Test para 200 OK en asignación de ticket
    @Test
    void testAsignarTicket_200OK() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setAsignadoA("usuario2");

        when(ticketService.asignarTicket(1L, "usuario2")).thenReturn(ticket);

        mockMvc.perform(put("/api/tickets/1/asignar")
               .param("usuarioId", "usuario2"))
               .andExpect(status().isOk());
    }

    // Test para 200 OK en categorización de ticket
    @Test
    void testCategorizarTicket_200OK() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setCategoriaTicket(CategoriaTicket.PERFUMES);

        when(ticketService.categorizarTicket(1L, CategoriaTicket.PERFUMES)).thenReturn(ticket);

        mockMvc.perform(put("/api/tickets/1/categorizar")
               .param("categoria", "PERFUMES"))
               .andExpect(status().isOk());
    }
}