package Perfulandia.Soporte.model;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Ticket extends RepresentationModel<Ticket> {
    private Long id;
    private String titulo;
    private String descripcionTicket;
    
    @Enumerated(EnumType.STRING)
    private EstadoTicket estadoTicket;
    
    @Enumerated(EnumType.STRING)
    private CategoriaTicket categoriaTicket;
    
    private String asignadoA;
    private String creadoPor;
}