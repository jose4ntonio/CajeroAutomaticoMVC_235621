/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Dell
 */

import java.util.ArrayList;
import java.util.List;

public class ModeloRetiro {
    private Cuenta cuenta;
    private double montoSeleccionado;
    private final double COMISION = 15.00;
    private List<IModeloRetiroListener> listeners;

    public ModeloRetiro(Cuenta cuenta) {
        this.cuenta = cuenta;
        this.montoSeleccionado = 0.0;
        this.listeners = new ArrayList<>();
    }

    public void agregarListener(IModeloRetiroListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removerListener(IModeloRetiroListener listener) {
        listeners.remove(listener);
    }

    public void seleccionarMonto(double monto) {
        this.montoSeleccionado = monto;
        double saldoActual = cuenta.getSaldo();
        double saldoFinal = saldoActual - (monto + COMISION);
        
        // Notificación Observer hacia los listeners (PantallaRetiroView)
        for (IModeloRetiroListener listener : listeners) {
            listener.onEstadoCuentaActualizado(
                cuenta.getTitular(),
                saldoActual,
                monto,
                COMISION,
                saldoFinal
            );
        }
    }

    public boolean procesarRetiro() {
        if (montoSeleccionado <= 0) {
            for (IModeloRetiroListener listener : listeners) {
                listener.onError("Debe seleccionar un monto para retirar.");
            }
            return false;
        }

        double totalADebitar = montoSeleccionado + COMISION;
        boolean exito = cuenta.debitar(totalADebitar);

        if (exito) {
            Recibo nuevoRecibo = new Recibo(
                cuenta.getUltimosCuatroDigitos(),
                cuenta.getTitular(),
                montoSeleccionado,
                COMISION
            );
            for (IModeloRetiroListener listener : listeners) {
                listener.onRetiroExitoso(nuevoRecibo);
            }
            return true;
        } else {
            for (IModeloRetiroListener listener : listeners) {
                listener.onError("Fondos insuficientes para cubrir el retiro y la comisión.");
            }
            return false;
        }
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public double getMontoSeleccionado() {
        return montoSeleccionado;
    }

    public double getComision() {
        return COMISION;
    }
}