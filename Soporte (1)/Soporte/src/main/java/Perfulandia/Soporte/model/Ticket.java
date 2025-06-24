package Perfulandia.Soporte.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String titulo;
    @Column(nullable = false, length = 1000)
    private String descripcionTicket;
    @Enumerated(EnumType.STRING)
    private EstadoTicket estadoTicket;
    @Enumerated(EnumType.STRING)
    private CategoriaTicket categoriaTicket;
    private String asignadoA;
    @Column(nullable = false)
    private String creadoPor;
}