package ar.com.proyecto.down.seguros.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.proyecto.down.seguros.model.Cliente;
import ar.com.proyecto.down.seguros.model.Poliza;

@Repository

public interface PolizaRepository extends JpaRepository<Poliza, Long> {

    public Optional<Poliza> findByNumeroPoliza(String numeroPoliza);

    public List<Poliza> findByFechaFinBetween(LocalDate fechaInicio, LocalDate fechaFin);

    public List<Poliza> findByCliente(Cliente cliente);

    public List<Poliza> findByClienteId(Long id);

}
