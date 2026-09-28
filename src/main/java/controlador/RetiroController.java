/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 *
 * @author Dell
 */ 


import modelo.ModeloRetiro;

public class RetiroController {
    private ModeloRetiro modelo;

    public RetiroController(ModeloRetiro modelo) {
        this.modelo = modelo;
    }

    public void onMontoSeleccionado(double monto) {
        modelo.seleccionarMonto(monto);
    }

    public void onSolicitarRetiro() {
        modelo.procesarRetiro();
    }
}