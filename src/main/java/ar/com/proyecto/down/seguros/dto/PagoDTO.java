package ar.com.proyecto.down.seguros.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {
    private double monto;
    private LocalDate fecha;
    private String estado;
    private String metodoPago;
    private Long idPoliza;
}
