package ar.com.proyecto.down.seguros.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.proyecto.down.seguros.model.Pagos;
import ar.com.proyecto.down.seguros.model.Poliza;
import ar.com.proyecto.down.seguros.dto.PagoDTO;
import ar.com.proyecto.down.seguros.repository.PagosRepository;
import ar.com.proyecto.down.seguros.repository.PolizaRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PagosService {

    @Autowired
    private PagosRepository pagosRepo;

    @Autowired
    private PolizaRepository polizaRepo;

    public Pagos guardarPago(Pagos pago) {
        return pagosRepo.save(pago);
    }

    public Pagos guardarPago(PagoDTO pagoDto) {
        Poliza poliza = polizaRepo.findById(pagoDto.getIdPoliza())
                .orElseThrow(() -> new RuntimeException("Poliza no encontrada con ID: " + pagoDto.getIdPoliza()));

        Pagos pago = new Pagos();
        pago.setMonto(pagoDto.getMonto());
        pago.setFecha(pagoDto.getFecha());
        pago.setEstado(pagoDto.getEstado());
        pago.setMetodoPago(pagoDto.getMetodoPago());
        pago.setPoliza(poliza);

        return pagosRepo.save(pago);
    }

    public Pagos traerPagoId(Long id) {
        return pagosRepo.findById(id).orElse(null);
    }

    public boolean eliminarPago(Long id) {
        try {
            pagosRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Pagos editarPago(Long idOriginal, Double nuevoImporte, String nuevoMetodoPago) {
        Pagos pagoExistente = pagosRepo.findById(idOriginal).orElse(null);
        if (pagoExistente != null) {
            pagoExistente.setMonto(nuevoImporte);
            pagoExistente.setMetodoPago(nuevoMetodoPago);
            return pagosRepo.save(pagoExistente);
        }
        return null;
    }

    public List<Pagos> obtenerPagos() {
        return pagosRepo.findAll();
    }

    public List<Pagos> traerporFechaPago(LocalDate fechaPago) {
        return pagosRepo.findByFecha(fechaPago);
    }

}
