package com.app.laboral;

import com.app.exceptions.DatosNoCorrectosException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "empleados")
public class Empleado extends Persona {

    @Column(nullable = false)
    private int categoria;

    @Column(nullable = false)
    private int anyos;

    public Empleado() {}

    public Empleado(String nombre, String dni, String sexo) {
        super(nombre, dni, sexo);
        this.categoria = 1;
        this.anyos = 0;
    }

    public Empleado(String nombre, String dni, String sexo, int categoria, int anyos) {
        super(nombre, dni, sexo);
        this.categoria = categoria;
        this.anyos = anyos;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) throws DatosNoCorrectosException {
        if (categoria < 1 || categoria > 10) {
            throw new DatosNoCorrectosException();
        }
        this.categoria = categoria;
    }

    public int getAnyos() {
        return anyos;
    }

    public void setAnyos(int anyos) {
        this.anyos = anyos;
    }

    public int getSueldo() {
        Nomina nomina = new Nomina();
        return nomina.sueldo(this);
    }

    public void incrAnyo() throws DatosNoCorrectosException {
        if (anyos < 0) {
            throw new DatosNoCorrectosException();
        }
        anyos++;
    }

    @Override
    public String toString() {
        return "Empleado [nombre=" + getNombre() + ", dni=" + getDni() + ", sexo=" + getSexo() + ", categoria=" + categoria + ", anyos=" + anyos + "]";
    }
}
