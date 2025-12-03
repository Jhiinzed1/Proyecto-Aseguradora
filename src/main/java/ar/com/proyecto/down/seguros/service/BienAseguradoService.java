package ar.com.proyecto.down.seguros.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.proyecto.down.seguros.model.BienAsegurado;
import ar.com.proyecto.down.seguros.model.TipoPoliza;
import ar.com.proyecto.down.seguros.dto.BienAseguradoDTO;
import ar.com.proyecto.down.seguros.repository.BienAseguradoRepository;
import ar.com.proyecto.down.seguros.repository.TipoPolizaRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class BienAseguradoService {

    @Autowired
    private BienAseguradoRepository bienRepo;

    @Autowired
    private TipoPolizaRepository tipoPolizaRepo;

    public BienAsegurado guardarBienAsegurado(BienAsegurado bienAsegurado) {
        return bienRepo.save(bienAsegurado);
    }

    public BienAsegurado guardarBienAsegurado(BienAseguradoDTO bienDto) {
        TipoPoliza tipoPoliza = tipoPolizaRepo.findById(bienDto.getIdTipoPoliza())
                .orElseThrow(() -> new RuntimeException(
                        "Tipo de Poliza no encontrado con ID: " + bienDto.getIdTipoPoliza()));

        BienAsegurado bien = new BienAsegurado();
        bien.setDescripcion(bienDto.getDescripcion());
        bien.setValor(bienDto.getValor());
        bien.setTipoPoliza(tipoPoliza);

        return bienRepo.save(bien);
    }

    public BienAsegurado traerBienAseguradoPorId(Long id) {
        return bienRepo.findById(id).orElse(null);
    }

    public List<BienAsegurado> traerTodosLosBienesAsegurados() {
        return bienRepo.findAll();
    }

    public boolean eliminarBienAsegurado(Long id) {
        try {
            bienRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public BienAsegurado editarBienAsegurado(Long idOriginal, BienAsegurado bienAsegurado) {
        BienAsegurado bienAseguradoOriginal = traerBienAseguradoPorId(idOriginal);
        if (bienAseguradoOriginal != null) {
            bienAseguradoOriginal.setDescripcion(bienAsegurado.getDescripcion());
            bienAseguradoOriginal.setValor(bienAsegurado.getValor());
            bienAseguradoOriginal.setTipoPoliza(bienAsegurado.getTipoPoliza());
            return bienRepo.save(bienAseguradoOriginal);
        }
        return null;
    }

    public List<BienAsegurado> TraerBienAseguradoPorValor(double valor) {
        return bienRepo.findByValor(valor);
    }

    public List<BienAsegurado> TraerBienAseguradoPorTipoPoliza(TipoPoliza tipoPoliza) {
        return bienRepo.findByTipoPoliza(tipoPoliza);
    }

}
