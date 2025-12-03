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

import ar.com.proyecto.down.seguros.dto.BienAseguradoDTO;
import ar.com.proyecto.down.seguros.model.BienAsegurado;
import ar.com.proyecto.down.seguros.service.BienAseguradoService;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/bienes")
public class BienAseguradoController {

    @Autowired
    private BienAseguradoService bienService;

    @GetMapping("/todos")
    public List<BienAsegurado> obtenerBienes() {
        return bienService.traerTodosLosBienesAsegurados();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BienAsegurado> traerBien(@PathVariable Long id) {
        BienAsegurado bien = bienService.traerBienAseguradoPorId(id);
        if (bien != null) {
            return ResponseEntity.ok(bien);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/guardar")
    public ResponseEntity<Map<String, Object>> guardarBien(@RequestBody BienAseguradoDTO bienDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            BienAsegurado nuevoBien = bienService.guardarBienAsegurado(bienDto);
            response.put("mensaje", "Bien Asegurado creado correctamente");
            response.put("bienAsegurado", nuevoBien);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("mensaje", "Error al crear el Bien Asegurado: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public boolean eliminarBien(@PathVariable Long id) {
        return bienService.eliminarBienAsegurado(id);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<BienAsegurado> editarBien(@PathVariable Long id, @RequestBody BienAsegurado bien) {
        BienAsegurado editado = bienService.editarBienAsegurado(id, bien);
        if (editado != null) {
            return ResponseEntity.ok(editado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
