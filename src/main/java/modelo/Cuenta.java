/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Dell
 */

public class Cuenta {
    private String numeroCuenta;
    private String titular;
    private double saldo;

    public Cuenta(String numeroCuenta, String titular, double saldo) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = saldo;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public String getUltimosCuatroDigitos() {
        if (numeroCuenta != null && numeroCuenta.length() >= 4) {
            return "**** " + numeroCuenta.substring(numeroCuenta.length() - 4);
        }
        return "**** 0000";
    }

    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public boolean debitar(double montoTotal) {
        if (montoTotal <= saldo) {
            this.saldo -= montoTotal;
            return true;
        }
        return false;
    }
}
