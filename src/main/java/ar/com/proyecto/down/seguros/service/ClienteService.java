package ar.com.proyecto.down.seguros.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.proyecto.down.seguros.model.Cliente;
import ar.com.proyecto.down.seguros.repository.ClienteRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepo;

    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepo.save(cliente);
    }

    public java.util.List<Cliente> obtenerClientes() {
        return clienteRepo.findAll();
    }

    public Cliente traerCliente(Long id) {
        return clienteRepo.findById(id).orElse(null);
    }

    public boolean eliminarCliente(Long id) {
        try {
            clienteRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Cliente editarCliente(Long idOriginal, String nuevoNombre, String nuevoEmail) {
        Cliente cliente = clienteRepo.findById(idOriginal).orElse(null);
        if (cliente != null) {
            cliente.setNombre(nuevoNombre);
            cliente.setEmail(nuevoEmail);
            return clienteRepo.save(cliente);
        }
        return null;
    }

    public Optional<Cliente> traerClienteporEmail(String email) {
        return clienteRepo.findByEmail(email);
    }

}
