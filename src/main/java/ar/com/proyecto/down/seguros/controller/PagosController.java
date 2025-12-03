package ar.com.proyecto.down.seguros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.proyecto.down.seguros.dto.PagoDTO;
import ar.com.proyecto.down.seguros.model.Pagos;
import ar.com.proyecto.down.seguros.service.PagosService;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/pagos")
public class PagosController {

    @Autowired
    private PagosService pagosService;

    @GetMapping("/todos")
    public List<Pagos> obtenerPagos() {
        return pagosService.obtenerPagos();
    }

    @GetMapping("/{id}")
    public Pagos traerPago(@PathVariable Long id) {
        return pagosService.traerPagoId(id);
    }

    @PostMapping("/guardar")
    public ResponseEntity<Map<String, Object>> guardarPago(@RequestBody PagoDTO pagoDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            Pagos nuevoPago = pagosService.guardarPago(pagoDto);
            response.put("mensaje", "Pago registrado correctamente");
            response.put("pago", nuevoPago);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("mensaje", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public boolean eliminarPago(@PathVariable Long id) {
        return pagosService.eliminarPago(id);
    }

    @PutMapping("/editar/{id}")
    public Pagos editarPago(@PathVariable Long id, @RequestBody Pagos pago) {
        return pagosService.editarPago(id, pago.getMonto(), pago.getMetodoPago());
    }
}
