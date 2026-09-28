/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Dell
 */

public class Recibo {
    private String ultimosDigitos;
    private String nombreCliente;
    private double montoRetirado;
    private double comisionCobrada;

    public Recibo(String ultimosDigitos, String nombreCliente, double montoRetirado, double comisionCobrada) {
        this.ultimosDigitos = ultimosDigitos;
        this.nombreCliente = nombreCliente;
        this.montoRetirado = montoRetirado;
        this.comisionCobrada = comisionCobrada;
    }

    public String getUltimosDigitos() {
        return ultimosDigitos;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public double getMontoRetirado() {
        return montoRetirado;
    }

    public double getComisionCobrada() {
        return comisionCobrada;
    }
}