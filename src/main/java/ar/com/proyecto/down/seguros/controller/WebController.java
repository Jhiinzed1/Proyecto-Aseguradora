package ar.com.proyecto.down.seguros.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ar.com.proyecto.down.seguros.model.TipoPoliza;
import ar.com.proyecto.down.seguros.service.TipoPolizaService;

import java.util.Collections;

@Controller
public class WebController {

    @Autowired
    private TipoPolizaService tipoPolizaService;

    @Autowired
    private ar.com.proyecto.down.seguros.service.PagosService pagosService;

    @Autowired
    private ar.com.proyecto.down.seguros.service.PolizaService polizaService;

    @GetMapping("/tipos-poliza")
    public String tiposPoliza(Model model) {
        model.addAttribute("nuevoTipoPoliza", new TipoPoliza());
        model.addAttribute("tiposPoliza", tipoPolizaService.obtenerTipoPoliza());
        return "vista-tipos-poliza";
    }

    @PostMapping("/tipos-poliza/guardar")
    public String guardarTipoPoliza(@ModelAttribute TipoPoliza tipoPoliza) {
        tipoPolizaService.guardarTipoPoliza(tipoPoliza);
        return "redirect:/tipos-poliza";
    }

    @GetMapping("/pagos")
    public String pagos(Model model) {
        model.addAttribute("nuevoPago", new ar.com.proyecto.down.seguros.dto.PagoDTO());
        model.addAttribute("pagos", pagosService.obtenerPagos());
        model.addAttribute("polizas", polizaService.ObtenerPolizas());
        return "vista-pagos";
    }

    @PostMapping("/pagos/registrar")
    public String guardarPago(@ModelAttribute ar.com.proyecto.down.seguros.dto.PagoDTO pagoDTO) {
        pagosService.guardarPago(pagoDTO);
        return "redirect:/pagos";
    }

}
