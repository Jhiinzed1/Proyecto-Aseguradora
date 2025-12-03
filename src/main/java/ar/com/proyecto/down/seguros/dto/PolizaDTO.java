package ar.com.proyecto.down.seguros.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PolizaDTO {
    private String numeroPoliza;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Long idCliente;
    private Long idTipoPoliza;
    private Long idBienAsegurado;
    private String nombreCliente;
    private String descripcionBien;
    private Double valorBien;
}
