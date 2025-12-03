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

import ar.com.proyecto.down.seguros.model.Cliente;
import ar.com.proyecto.down.seguros.service.ClienteService;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> obtenerClientes() {
        return clienteService.obtenerClientes();
    }

    @GetMapping("/{id}")
    public Cliente traerCliente(@PathVariable Long id) {
        return clienteService.traerCliente(id);
    }

    @PostMapping("/guardar")
    public ResponseEntity<Map<String, Object>> guardarCliente(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteService.guardarCliente(cliente);
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Usuario creado correctamente");
        response.put("cliente", nuevoCliente);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/eliminar/{id}")
    public boolean eliminarCliente(@PathVariable Long id) {
        return clienteService.eliminarCliente(id);
    }

    @PutMapping("/editar/{id}")
    public Cliente editarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        return clienteService.editarCliente(id, cliente.getNombre(), cliente.getEmail());
    }
}
