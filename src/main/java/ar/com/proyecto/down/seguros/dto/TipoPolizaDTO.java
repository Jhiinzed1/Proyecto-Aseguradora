package ar.com.proyecto.down.seguros.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TipoPolizaDTO {
    private Long id;
    private String nombre;
    private Double preciobase;
}
