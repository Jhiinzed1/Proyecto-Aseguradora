package ar.com.proyecto.down.seguros.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "polizas")
@Entity

public class Poliza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_poliza", nullable = false)
    private Long id;

    @Column(name = "numero_poliza", nullable = false)
    private String numeroPoliza;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_tipo_poliza", nullable = false)
    private TipoPoliza tipoPoliza;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bien_asegurado_id", nullable = false)
    private BienAsegurado bienAsegurado;

    @OneToMany(mappedBy = "poliza", cascade = CascadeType.ALL)
    private List<Pagos> pagos;
}
