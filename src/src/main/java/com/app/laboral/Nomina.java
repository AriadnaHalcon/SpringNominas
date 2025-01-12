package com.app.laboral;

public class Nomina {
	private static final int[] SUELDO_BASE = {50000, 70000, 90000, 110000, 130000, 150000, 170000, 190000, 210000, 230000};

    public int sueldo(Empleado emp) {
        int sueldo = SUELDO_BASE[emp.getCategoria() - 1] + (5000 * emp.getAnyos());
        return sueldo;
    }

    public Nomina() {}
}

