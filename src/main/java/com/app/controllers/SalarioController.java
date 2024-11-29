package com.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.laboral.Empleado;
import com.app.services.EmpleadoService;

@Controller
public class SalarioController {

    private final EmpleadoService empleadoService;

    public SalarioController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping("/empleado/salario")
    public String formularioSalario() {
        return "formulario-salario";
    }

    @GetMapping("/empleado/salario/resultado")
    public String consultarSalario(@RequestParam String dni, Model model) {
        Empleado empleado = empleadoService.buscarPorDni(dni).orElse(null);
        if (empleado == null) {
            model.addAttribute("mensaje", "Empleado no encontrado");
            return "error";
        }
        int sueldo = empleado.getSueldo();
        model.addAttribute("empleado", empleado);
        model.addAttribute("sueldo", sueldo);
        return "resultado-salario"; 
    }
}
