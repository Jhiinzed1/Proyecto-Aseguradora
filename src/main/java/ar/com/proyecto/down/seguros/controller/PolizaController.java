package ar.com.proyecto.down.seguros.controller;

import java.time.LocalDate;
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

import ar.com.proyecto.down.seguros.dto.PolizaDTO;
import ar.com.proyecto.down.seguros.model.Poliza;
import ar.com.proyecto.down.seguros.service.PolizaService;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/polizas")
public class PolizaController {

    @Autowired
    private PolizaService polizaService;

    @GetMapping("/todos")
    public List<Poliza> obtenerPolizas() {
        return polizaService.ObtenerPolizas();
    }

    @PostMapping("/guardar")
    public ResponseEntity<Map<String, Object>> guardarPoliza(@RequestBody PolizaDTO polizaDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            Poliza nuevaPoliza = polizaService.guardarPoliza(polizaDto);
            response.put("mensaje", "Póliza creada correctamente");
            response.put("poliza", nuevaPoliza);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("mensaje", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("mensaje", "Error al crear la póliza: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Poliza> traerPoliza(@PathVariable Long id) {
        Poliza poliza = polizaService.traerPoliza(id);
        if (poliza != null) {
            return ResponseEntity.ok(poliza);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscarPorVencimiento/{fechaInicio}/{fechaFin}")
    public List<Poliza> buscarPorRangoVencimiento(@PathVariable LocalDate fechaInicio,
            @PathVariable LocalDate fechaFin) {
        return polizaService.buscarPorRangoVencimiento(fechaInicio, fechaFin);
    }

    @GetMapping("/buscarPorCliente/{id}")
    public List<Poliza> buscarPorCliente(@PathVariable Long id) {
        return polizaService.buscarPolizasPorClienteId(id);
    }

    @DeleteMapping("/eliminar/{id}")
    public boolean eliminarPoliza(@PathVariable Long id) {
        return polizaService.eliminarPoliza(id);
    }

    @PutMapping("/editar/{idOriginal}")
    public ResponseEntity<Poliza> editarPoliza(@PathVariable Long idOriginal, @RequestBody PolizaDTO polizaDto) {
        Poliza editada = polizaService.editarPoliza(idOriginal, polizaDto);
        if (editada != null) {
            return ResponseEntity.ok(editada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
