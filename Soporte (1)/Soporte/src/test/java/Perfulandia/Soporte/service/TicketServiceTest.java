package Perfulandia.Soporte.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import Perfulandia.Soporte.model.CategoriaTicket;
import Perfulandia.Soporte.model.Ticket;
import Perfulandia.Soporte.repository.TicketRepository;

public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearTicket() {
        // Configurar
        Ticket ticket = new Ticket();
        ticket.setTitulo("Problema con login");
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        // Ejecutar
        Ticket resultado = ticketService.crearTicket(ticket);

        // Verificar
        assertNotNull(resultado);
        assertEquals("Problema con login", resultado.getTitulo());
        verify(ticketRepository).save(ticket);
    }

    @Test
    void testObtenerTodosLosTickets() {
        // Configurar
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        when(ticketRepository.findAll()).thenReturn(Arrays.asList(ticket1, ticket2));

        // Ejecutar
        List<Ticket> resultado = ticketService.obtenerTodosLosTickets();

        // Verificar
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(ticketRepository).findAll();
    }

    @Test
    void testObtenerTicketPorId() {
        // Configurar
        Long id = 1L;
        Ticket ticket = new Ticket();
        ticket.setId(id);
        when(ticketRepository.findById(id)).thenReturn(Optional.of(ticket));

        // Ejecutar
        Optional<Ticket> resultado = ticketService.obtenerTicketPorId(id);

        // Verificar
        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getId());
        verify(ticketRepository).findById(id);
    }

    @Test
    void testActualizarTicket() {
        // Configurar
        Long id = 1L;
        Ticket ticketExistente = new Ticket();
        ticketExistente.setId(id);
        ticketExistente.setTitulo("Título viejo");

        Ticket ticketActualizado = new Ticket();
        ticketActualizado.setTitulo("Título nuevo");

        when(ticketRepository.findById(id)).thenReturn(Optional.of(ticketExistente));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticketExistente);

        // Ejecutar
        Ticket resultado = ticketService.actualizarTicket(id, ticketActualizado);

        // Verificar
        assertNotNull(resultado);
        assertEquals("Título nuevo", resultado.getTitulo());
        verify(ticketRepository).findById(id);
        verify(ticketRepository).save(ticketExistente);
    }

    @Test
    void testEliminarTicket() {
        // Configurar
        Long id = 1L;
        doNothing().when(ticketRepository).deleteById(id);

        // Ejecutar
        ticketService.eliminarTicket(id);

        // Verificar
        verify(ticketRepository).deleteById(id);
    }

    @Test
    void testAsignarTicket() {
        // Configurar
        Long id = 1L;
        String usuarioId = "user123";
        Ticket ticket = new Ticket();
        ticket.setId(id);

        when(ticketRepository.findById(id)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        // Ejecutar
        Ticket resultado = ticketService.asignarTicket(id, usuarioId);

        // Verificar
        assertNotNull(resultado);
        assertEquals(usuarioId, resultado.getAsignadoA());
        verify(ticketRepository).findById(id);
        verify(ticketRepository).save(ticket);
    }

    @Test
    void testCategorizarTicket() {
        // Configurar
        Long id = 1L;
        CategoriaTicket categoria = CategoriaTicket.PERFUMES;
        Ticket ticket = new Ticket();
        ticket.setId(id);

        when(ticketRepository.findById(id)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        // Ejecutar
        Ticket resultado = ticketService.categorizarTicket(id, categoria);

        // Verificar
        assertNotNull(resultado);
        assertEquals(categoria, resultado.getCategoriaTicket());
        verify(ticketRepository).findById(id);
        verify(ticketRepository).save(ticket);
    }
}