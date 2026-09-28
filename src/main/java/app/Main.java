/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import controlador.RetiroController;
import modelo.Cuenta;
import modelo.ModeloRetiro;
import vista.PantallaRetiroView;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Dell
 */
public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            // 1. Inicializar la entidad de dominio con datos de prueba
            Cuenta cuentaInicial = new Cuenta("12345678901234", "Antonio González", 5000.00);

            // 2. Inicializar Modelo
            ModeloRetiro modelo = new ModeloRetiro(cuentaInicial);

            // 3. Inicializar Controlador con referencia al Modelo
            RetiroController controlador = new RetiroController(modelo);

            // 4. Inicializar Vista conectada a Modelo y Controlador (MVC Clásico)
            PantallaRetiroView vista = new PantallaRetiroView(modelo, controlador);

            // 5. Hacer visible la GUI
            vista.setVisible(true);
        }); 
       
    }
}
