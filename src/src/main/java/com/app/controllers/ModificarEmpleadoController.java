package com.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.laboral.Empleado;
import com.app.services.EmpleadoService;

@Controller
public class ModificarEmpleadoController {

    private final EmpleadoService empleadoService;

    public ModificarEmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping("/empleado/modificar")
    public String formularioModificar() {
        return "formulario-modificar"; // Nombre del archivo Thymeleaf formulario-modificar.html
    }

    @PostMapping("/empleado/modificar/resultado")
    public String modificarEmpleado(@RequestParam String dni, @RequestParam int categoria,
                                     @RequestParam int anyos, Model model) {
        Empleado empleado = empleadoService.buscarPorDni(dni).orElse(null);
        if (empleado == null) {
            model.addAttribute("mensaje", "Empleado no encontrado");
            return "error";
        }
        try {
            empleado.setCategoria(categoria);
            empleado.setAnyos(anyos);
            empleadoService.guardar(empleado);
            model.addAttribute("mensaje", "Empleado modificado exitosamente");
            return "resultado-modificar"; // Nombre del archivo Thymeleaf resultado-modificar.html
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al modificar el empleado: " + e.getMessage());
            return "error";
        }
    }
}