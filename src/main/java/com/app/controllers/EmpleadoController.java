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
        return "empleados";  // Vista correcta 'empleados.html'
    }
    
    // Mostrar formulario para consultar sueldo
    @GetMapping("/salario")
    public String consultarSueldoForm(Model model) {
        model.addAttribute("dni", "");
        return "formulario-salario";  // Vista 'formulario-salario.html'
    }

    // Mostrar el sueldo al consultar el DNI
    @PostMapping("/salario/resultado")
    public String consultarSueldo(@RequestParam String dni, Model model) {
        Optional<Empleado> empleado = empleadoService.buscarPorDni(dni);
        if (empleado.isPresent()) {
            Nomina nomina = new Nomina();
            int sueldo = nomina.sueldo(empleado.get());
            model.addAttribute("empleado", empleado.get());
            model.addAttribute("sueldo", sueldo);
            return "resultado-salario";  // Vista 'resultado-salario.html'
        } else {
            model.addAttribute("error", "Empleado no encontrado");
            return "formulario-salario";  // Vista 'formulario-salario.html'
        }
    }
    
    // Mostrar formulario para editar empleado
    @GetMapping("/modificar")
    public String editarEmpleadoForm(Model model) {
        model.addAttribute("dni", "");
        return "formulario-modificar";  // Vista 'formulario-modificar.html'
    }

    // Buscar y mostrar empleado para editar
    @PostMapping("/modificar/resultado")
    public String editarEmpleado(@RequestParam String dni, Model model) {
        Optional<Empleado> empleado = empleadoService.buscarPorDni(dni);
        if (empleado.isPresent()) {
            model.addAttribute("empleado", empleado.get());
            return "formulario-modificar";  // Vista 'formulario-modificar.html'
        } else {
            model.addAttribute("error", "Empleado no encontrado");
            return "formulario-modificar";  // Vista 'formulario-modificar.html'
        }
    }

    // Guardar empleado modificado
    @PostMapping("/modificar/guardar")
    public String guardarEmpleadoModificado(@ModelAttribute Empleado empleado) {
        empleadoService.guardar(empleado);
        return "redirect:/empleados";  // Redirige a la lista de empleados
    }
}
