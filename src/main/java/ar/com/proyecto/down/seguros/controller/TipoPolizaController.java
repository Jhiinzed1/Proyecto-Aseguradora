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

import ar.com.proyecto.down.seguros.model.TipoPoliza;
import ar.com.proyecto.down.seguros.service.TipoPolizaService;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/tipopolizas")
public class TipoPolizaController {

    @Autowired
    private TipoPolizaService tipoPolizaService;

    @GetMapping
    public List<TipoPoliza> obtenerTiposPoliza() {
        return tipoPolizaService.obtenerTipoPoliza();
    }

    @GetMapping("/{id}")
    public TipoPoliza traerTipoPoliza(@PathVariable Long id) {
        return tipoPolizaService.traerTipoPoliza(id);
    }

    @PostMapping("/guardar")
    public ResponseEntity<Map<String, Object>> guardarTipoPoliza(@RequestBody TipoPoliza tipoPoliza) {
        TipoPoliza nuevoTipo = tipoPolizaService.guardarTipoPoliza(tipoPoliza);
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Tipo de Póliza creado correctamente");
        response.put("tipoPoliza", nuevoTipo);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/eliminar/{id}")
    public boolean eliminarTipoPoliza(@PathVariable Long id) {
        return tipoPolizaService.eliminarTipoPoliza(id);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<TipoPoliza> editarTipoPoliza(@PathVariable Long id, @RequestBody TipoPoliza tipoPoliza) {
        TipoPoliza editada = tipoPolizaService.editarTipoPoliza(id, tipoPoliza.getNombre(), tipoPoliza.getPreciobase());
        if (editada != null) {
            return ResponseEntity.ok(editada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
