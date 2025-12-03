package ar.com.proyecto.down.seguros.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.proyecto.down.seguros.model.TipoPoliza;

@Repository
public interface TipoPolizaRepository extends JpaRepository<TipoPoliza, Long> {

    Optional<TipoPoliza> findByNombre(String nombre);
}
