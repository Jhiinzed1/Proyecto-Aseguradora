package ar.com.proyecto.down.seguros.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.proyecto.down.seguros.model.Pagos;

@Repository
public interface PagosRepository extends JpaRepository<Pagos, Long> {

    List<Pagos> findByFecha(LocalDate fechaPago);
}
