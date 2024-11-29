package com.app.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.laboral.Empleado;
import com.app.laboral.Nomina;
import com.app.services.EmpleadoService;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {
    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    // Mostrar todos los empleados
    @GetMapping
    public String listarEmpleados(Model model) {
        model.addAttribute("empleados", empleadoService.obtenerTodos());
        return "empleados/listar";
    }
    
    @GetMapping("/consultar-sueldo")
    public String consultarSueldoForm(Model model) {
        model.addAttribute("dni", "");
        return "empleados/consultarSueldo";
    }

    // Mostrar el sueldo al consultar el DNI
    @PostMapping("/consultar-sueldo")
    public String consultarSueldo(@RequestParam String dni, Model model) {
        Optional<Empleado> empleado = empleadoService.buscarPorDni(dni);
        if (empleado.isPresent()) {
            Nomina nomina = new Nomina();
            int sueldo = nomina.sueldo(empleado.get());
            model.addAttribute("empleado", empleado.get());
            model.addAttribute("sueldo", sueldo);
            return "empleados/mostrarSueldo";
        } else {
            model.addAttribute("error", "Empleado no encontrado");
            return "empleados/consultarSueldo";
        }
    }
    
    @GetMapping("/editar")
    public String editarEmpleadoForm(Model model) {
        model.addAttribute("dni", "");
        return "empleados/editar";
    }

    // Buscar y mostrar empleados para editar
    @PostMapping("/editar")
    public String editarEmpleado(@RequestParam String dni, Model model) {
        Optional<Empleado> empleado = empleadoService.buscarPorDni(dni);
        if (empleado.isPresent()) {
            model.addAttribute("empleado", empleado.get());
            return "empleados/formularioEditar";
        } else {
            model.addAttribute("error", "Empleado no encontrado");
            return "empleados/editar";
        }
    }

    // Guardar empleado modificado
    @PostMapping("/guardar")
    public String guardarEmpleadoModificado(@ModelAttribute Empleado empleado) {
        empleadoService.guardar(empleado);
        return "redirect:/empleados";
    }
}
