package ar.com.proyecto.down.seguros.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.proyecto.down.seguros.model.TipoPoliza;
import ar.com.proyecto.down.seguros.repository.TipoPolizaRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TipoPolizaService {

    @Autowired
    private TipoPolizaRepository tipoRepo;

    public TipoPoliza guardarTipoPoliza(TipoPoliza tipoPoliza) {
        return tipoRepo.save(tipoPoliza);
    }

    public TipoPoliza traerTipoPoliza(Long id) {
        return tipoRepo.findById(id).orElse(null);
    }

    public boolean eliminarTipoPoliza(Long id) {
        try {
            tipoRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public TipoPoliza editarTipoPoliza(Long idOriginal, String nuevoNombre, double nuevoPrecioBase) {
        TipoPoliza tipoExistente = tipoRepo.findById(idOriginal).orElse(null);
        if (tipoExistente != null) {
            tipoExistente.setNombre(nuevoNombre);
            tipoExistente.setPreciobase(nuevoPrecioBase);
            return tipoRepo.save(tipoExistente);
        }
        return null;
    }

    public List<TipoPoliza> obtenerTipoPoliza() {
        return tipoRepo.findAll();
    }

    public Optional<TipoPoliza> buscarPorNombre(String nombre) {
        return tipoRepo.findByNombre(nombre);
    }

}
