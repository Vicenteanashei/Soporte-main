package Perfulandia.Soporte.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
@Schema(description = "Ticket de soporte")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del ticket", example = "1")
    private Long id;

    @Column(nullable = false, length = 50)
    @Schema(description = "Título del ticket", example = "Problema con login")
    private String titulo;

    @Column(nullable = false, length = 1000)
    @Schema(description = "Descripción del ticket", example = "No puedo acceder con mi usuario.")
    private String descripcionTicket;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Estado del ticket", example = "ABIERTO")
    private EstadoTicket estadoTicket;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Categoría del ticket", example = "PERFUMES")
    private CategoriaTicket categoriaTicket;

    @Schema(description = "Usuario asignado", example = "juan.perez")
    private String asignadoA;

    @Column(nullable = false)
    
    
    
    private String creadoPor;
}