package ar.com.proyecto.down.seguros.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BienAseguradoDTO {
    private String descripcion;
    private double valor;
    private Long idTipoPoliza;
}
