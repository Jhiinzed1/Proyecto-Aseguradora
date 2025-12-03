package ar.com.proyecto.down.seguros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.proyecto.down.seguros.model.BienAsegurado;
import ar.com.proyecto.down.seguros.model.TipoPoliza;

@Repository
public interface BienAseguradoRepository extends JpaRepository<BienAsegurado, Long> {

    List<BienAsegurado> findByValor(double valor);

    List<BienAsegurado> findByTipoPoliza(TipoPoliza tipoPoliza);

}
