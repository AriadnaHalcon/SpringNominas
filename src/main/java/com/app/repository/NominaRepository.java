package com.app.repository;

import org.springframework.stereotype.Service;

import com.app.laboral.Empleado;
import com.app.laboral.Nomina;

@Service
public class NominaRepository {

    private final Nomina nomina = new Nomina();

    public int obtenerSueldo(Empleado empleado) {
        return nomina.sueldo(empleado);
    }
}
