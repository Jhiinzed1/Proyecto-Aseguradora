package ar.com.proyecto.down.seguros.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.proyecto.down.seguros.dto.PolizaDTO;
import ar.com.proyecto.down.seguros.dto.PolizaDTO;
import ar.com.proyecto.down.seguros.model.BienAsegurado;
import ar.com.proyecto.down.seguros.model.Cliente;
import ar.com.proyecto.down.seguros.model.Poliza;
import ar.com.proyecto.down.seguros.model.TipoPoliza;
import jakarta.transaction.Transactional;
import ar.com.proyecto.down.seguros.repository.PolizaRepository;

@Service
@Transactional
public class PolizaService {

    @Autowired
    private PolizaRepository polizaRepository;

    @Autowired
    private ar.com.proyecto.down.seguros.repository.ClienteRepository clienteRepository;
    @Autowired
    private ar.com.proyecto.down.seguros.repository.TipoPolizaRepository tipoPolizaRepository;
    @Autowired
    private ar.com.proyecto.down.seguros.repository.BienAseguradoRepository bienAseguradoRepository;

    public Poliza guardarPoliza(Poliza poliza) {

        if (poliza.getCliente() != null) {
            if (poliza.getCliente().getId() != null) {
                poliza.setCliente(clienteRepository.findById(poliza.getCliente().getId())
                        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                                org.springframework.http.HttpStatus.NOT_FOUND,
                                "Error: No se encontró el Cliente con ID " + poliza.getCliente().getId())));
            } else {
                poliza.setCliente(clienteRepository.save(poliza.getCliente()));
            }
        }

        if (poliza.getTipoPoliza() != null) {
            if (poliza.getTipoPoliza().getId() != null) {
                poliza.setTipoPoliza(tipoPolizaRepository.findById(poliza.getTipoPoliza().getId())
                        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                                org.springframework.http.HttpStatus.NOT_FOUND,
                                "Error: No se encontró el Tipo de Póliza con ID " + poliza.getTipoPoliza().getId())));
            } else {
                poliza.setTipoPoliza(tipoPolizaRepository.save(poliza.getTipoPoliza()));
            }
        }

        if (poliza.getBienAsegurado() != null) {
            if (poliza.getBienAsegurado().getId() != null) {
                poliza.setBienAsegurado(bienAseguradoRepository.findById(poliza.getBienAsegurado().getId())
                        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                                org.springframework.http.HttpStatus.NOT_FOUND,
                                "Error: No se encontró el Bien Asegurado con ID "
                                        + poliza.getBienAsegurado().getId())));
            } else {

                if (poliza.getBienAsegurado().getTipoPoliza() != null) {
                    if (poliza.getBienAsegurado().getTipoPoliza().getId() != null) {
                        poliza.getBienAsegurado()
                                .setTipoPoliza(tipoPolizaRepository
                                        .findById(poliza.getBienAsegurado().getTipoPoliza().getId())
                                        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                                                org.springframework.http.HttpStatus.NOT_FOUND,
                                                "Error: No se encontró el Tipo de Póliza (en Bien) con ID "
                                                        + poliza.getBienAsegurado().getTipoPoliza().getId())));
                    } else {
                        poliza.getBienAsegurado()
                                .setTipoPoliza(tipoPolizaRepository.save(poliza.getBienAsegurado().getTipoPoliza()));
                    }
                }
                poliza.setBienAsegurado(bienAseguradoRepository.save(poliza.getBienAsegurado()));
            }
        }
        return polizaRepository.save(poliza);
    }

    public Poliza guardarPoliza(PolizaDTO polizaDTO) {
        Poliza poliza = new Poliza();
        if (polizaDTO.getNumeroPoliza() == null || polizaDTO.getNumeroPoliza().isEmpty()) {
            poliza.setNumeroPoliza("POL-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        } else {
            poliza.setNumeroPoliza(polizaDTO.getNumeroPoliza());
        }

        actualizarDatosPolizaDesdeDTO(poliza, polizaDTO);

        return polizaRepository.save(poliza);
    }

    public Poliza editarPoliza(Long idOriginal, PolizaDTO polizaDTO) {
        Poliza poliza = traerPoliza(idOriginal);

        if (poliza != null) {
            actualizarDatosPolizaDesdeDTO(poliza, polizaDTO);
            return polizaRepository.save(poliza);
        }
        return null;
    }

    private void actualizarDatosPolizaDesdeDTO(Poliza poliza, PolizaDTO polizaDTO) {
        // Actualizar Fechas
        poliza.setFechaInicio(polizaDTO.getFechaInicio());
        poliza.setFechaFin(polizaDTO.getFechaFin());

        // Lógica de Cliente (Buscar por ID, por Nombre, o Crear Nuevo)
        Cliente cliente = null;
        if (polizaDTO.getIdCliente() != null) {
            cliente = clienteRepository.findById(polizaDTO.getIdCliente())
                    .orElseThrow(
                            () -> new RuntimeException("Cliente no encontrado con ID: " + polizaDTO.getIdCliente()));
        } else if (polizaDTO.getNombreCliente() != null && !polizaDTO.getNombreCliente().isEmpty()) {
            java.util.Optional<Cliente> existing = clienteRepository.findByNombre(polizaDTO.getNombreCliente());
            if (existing.isPresent()) {
                cliente = existing.get();
            } else {
                cliente = new Cliente();
                cliente.setNombre(polizaDTO.getNombreCliente());
                cliente.setEmail(polizaDTO.getNombreCliente().toLowerCase().replace(" ", ".") + "."
                        + java.util.UUID.randomUUID().toString().substring(0, 4) + "@example.com");
                cliente = clienteRepository.save(cliente);
            }
        } else {
            if (poliza.getCliente() == null) {
                throw new RuntimeException("Debe proporcionar un ID de Cliente o un Nombre.");
            }
            cliente = poliza.getCliente();
        }
        if (cliente != null) {
            poliza.setCliente(cliente);
        }

        if (polizaDTO.getIdTipoPoliza() != null) {
            TipoPoliza tipoPoliza = tipoPolizaRepository.findById(polizaDTO.getIdTipoPoliza())
                    .orElseThrow(() -> new RuntimeException(
                            "Tipo de Poliza no encontrado con ID: " + polizaDTO.getIdTipoPoliza()));
            poliza.setTipoPoliza(tipoPoliza);
        }

        BienAsegurado bienAsegurado = null;
        if (polizaDTO.getIdBienAsegurado() != null) {
            bienAsegurado = bienAseguradoRepository.findById(polizaDTO.getIdBienAsegurado())
                    .orElseThrow(() -> new RuntimeException(
                            "Bien Asegurado no encontrado con ID: " + polizaDTO.getIdBienAsegurado()));
        } else if (polizaDTO.getDescripcionBien() != null && !polizaDTO.getDescripcionBien().isEmpty()) {
            bienAsegurado = new BienAsegurado();
            bienAsegurado.setDescripcion(polizaDTO.getDescripcionBien());
            bienAsegurado.setValor(polizaDTO.getValorBien() != null ? polizaDTO.getValorBien() : 0.0);
            if (poliza.getTipoPoliza() != null) {
                bienAsegurado.setTipoPoliza(poliza.getTipoPoliza());
            }
            bienAsegurado = bienAseguradoRepository.save(bienAsegurado);
        } else {
            if (poliza.getBienAsegurado() == null) {
                throw new RuntimeException("Debe proporcionar un ID de Bien o una Descripción.");
            }
            bienAsegurado = poliza.getBienAsegurado();
        }

        if (bienAsegurado != null) {
            poliza.setBienAsegurado(bienAsegurado);
        }
    }

    public Optional<Poliza> traerPolizaPorNumero(String numeroPoliza) {
        return polizaRepository.findByNumeroPoliza(numeroPoliza);
    }

    public List<Poliza> buscarPorRangoVencimiento(LocalDate fechaInicio, LocalDate fechaFin) {
        return polizaRepository.findByFechaFinBetween(fechaInicio, fechaFin);
    }

    public List<Poliza> buscarPolizasPorCliente(Cliente cliente) {
        return polizaRepository.findByCliente(cliente);
    }

    public List<Poliza> buscarPolizasPorClienteId(Long idCliente) {
        return polizaRepository.findByClienteId(idCliente);
    }

    public List<Poliza> ObtenerPolizas() {
        return polizaRepository.findAll();
    }

    public boolean eliminarPoliza(Long id) {
        try {
            polizaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Poliza traerPoliza(Long id) {
        return polizaRepository.findById(id).orElse(null);
    }
}
