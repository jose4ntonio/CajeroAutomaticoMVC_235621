/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author Dell
 */

import controlador.RetiroController;
import modelo.IModeloRetiroListener;
import modelo.ModeloRetiro;
import modelo.Recibo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PantallaRetiroView extends JFrame implements IModeloRetiroListener {

    private ModeloRetiro modelo;
    private RetiroController controlador;

    // Componentes del diagrama de clases
    private JPanel pnlMontos;
    private JPanel pnlInfoDerecho;
    private JButton btnRetirar;
    private JLabel lblEstadoCuenta;

    // Subpaneles para conmutar según el Storyboard
    private CardLayout cardLayoutDerecho;
    private JPanel cardPanel;
    private JLabel lblTitularVal;
    private JLabel lblSaldoActualVal;
    private JLabel lblRetiroVal;
    private JLabel lblComisionVal;
    private JLabel lblSaldoFinalVal;

    // Componentes del recibo
    private JLabel lblReciboDigitos;
    private JLabel lblReciboNombre;
    private JLabel lblReciboMonto;
    private JLabel lblReciboComision;

    // Botones de montos
    private JButton[] botonesMontos;

    public PantallaRetiroView(ModeloRetiro modelo, RetiroController controlador) {
        this.modelo = modelo;
        this.controlador = controlador;

        // Registro del observador en el modelo (Observer Pattern)
        this.modelo.agregarListener(this);

        initComponentes();
    }

    public void initComponentes() {
        setTitle("Cajero Automático - Retirar Efectivo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(245, 247, 250));

        // Encabezado
        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        pnlHeader.setBackground(new Color(26, 43, 76));
        JLabel lblHeader = new JLabel("CAJERO AUTOMÁTICO | RETIRO DE EFECTIVO");
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        pnlHeader.add(lblHeader);
        add(pnlHeader, BorderLayout.NORTH);

        // Panel Central dividido: Izquierda (Montos) y Derecha (Estado de cuenta / Recibo)
        JPanel pnlCentral = new JPanel(new GridLayout(1, 2, 20, 0));
        pnlCentral.setOpaque(false);
        pnlCentral.setBorder(new EmptyBorder(15, 20, 15, 20));

        // ==========================================
        // 1. ZONA IZQUIERDA: LISTA DE MONTOS (múltiplos de 200)
        // ==========================================
        pnlMontos = new JPanel(new BorderLayout(10, 10));
        pnlMontos.setOpaque(false);

        JLabel lblSelecMonto = new JLabel("Seleccione una cantidad disponible:");
        lblSelecMonto.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSelecMonto.setForeground(new Color(33, 37, 41));
        pnlMontos.add(lblSelecMonto, BorderLayout.NORTH);

        JPanel pnlGridBotones = new JPanel(new GridLayout(3, 2, 12, 12));
        pnlGridBotones.setOpaque(false);

        double[] montos = {200.0, 400.0, 600.0, 800.0, 1000.0, 1200.0};
        botonesMontos = new JButton[montos.length];

        for (int i = 0; i < montos.length; i++) {
            double monto = montos[i];
            JButton btn = new JButton("$ " + String.format("%.2f", monto));
            btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
            btn.setBackground(Color.WHITE);
            btn.setForeground(new Color(26, 43, 76));
            btn.setFocusPainted(false);
            btn.setBorder(new LineBorder(new Color(200, 205, 215), 2, true));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btn.addActionListener((ActionEvent e) -> {
                // Delegar al controlador (MVC)
                controlador.onMontoSeleccionado(monto);
            });

            botonesMontos[i] = btn;
            pnlGridBotones.add(btn);
        }
        pnlMontos.add(pnlGridBotones, BorderLayout.CENTER);
        pnlCentral.add(pnlMontos);

        // ==========================================
        // 2. ZONA DERECHA: PANEL REACTIVO (Storyboard: Estados 1, 2 y 3)
        // ==========================================
        pnlInfoDerecho = new JPanel(new BorderLayout(10, 10));
        pnlInfoDerecho.setBackground(Color.WHITE);
        pnlInfoDerecho.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(210, 215, 225), 1, true),
                new EmptyBorder(15, 15, 15, 15)
        ));

        lblEstadoCuenta = new JLabel("Información de la Operación", SwingConstants.CENTER);
        lblEstadoCuenta.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblEstadoCuenta.setForeground(new Color(26, 43, 76));
        pnlInfoDerecho.add(lblEstadoCuenta, BorderLayout.NORTH);

        cardLayoutDerecho = new CardLayout();
        cardPanel = new JPanel(cardLayoutDerecho);
        cardPanel.setOpaque(false);

        // Estado 1: Inicial / En espera de selección
        JPanel pnlEsperando = new JPanel(new GridBagLayout());
        pnlEsperando.setOpaque(false);
        JLabel lblEsperando = new JLabel("<html><center>Seleccione un monto de la lista<br>para previsualizar su estado de cuenta.</center></html>");
        lblEsperando.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblEsperando.setForeground(Color.GRAY);
        pnlEsperando.add(lblEsperando);

        // Estado 2: Pre-estado de cuenta con detalles de retiro
        JPanel pnlEstadoCuentaDetalle = new JPanel(new GridLayout(5, 2, 8, 8));
        pnlEstadoCuentaDetalle.setOpaque(false);
        pnlEstadoCuentaDetalle.setBorder(new EmptyBorder(20, 10, 20, 10));

        pnlEstadoCuentaDetalle.add(crearEtiquetaNegrita("Nombre del Titular:"));
        lblTitularVal = new JLabel("-");
        pnlEstadoCuentaDetalle.add(lblTitularVal);

        pnlEstadoCuentaDetalle.add(crearEtiquetaNegrita("Monto Actual:"));
        lblSaldoActualVal = new JLabel("-");
        pnlEstadoCuentaDetalle.add(lblSaldoActualVal);

        pnlEstadoCuentaDetalle.add(crearEtiquetaNegrita("Cantidad a Retirar:"));
        lblRetiroVal = new JLabel("-");
        lblRetiroVal.setForeground(new Color(180, 40, 40));
        pnlEstadoCuentaDetalle.add(lblRetiroVal);

        pnlEstadoCuentaDetalle.add(crearEtiquetaNegrita("Comisión de Retiro:"));
        lblComisionVal = new JLabel("-");
        lblComisionVal.setForeground(new Color(180, 40, 40));
        pnlEstadoCuentaDetalle.add(lblComisionVal);

        pnlEstadoCuentaDetalle.add(crearEtiquetaNegrita("Monto Final:"));
        lblSaldoFinalVal = new JLabel("-");
        lblSaldoFinalVal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSaldoFinalVal.setForeground(new Color(20, 120, 50));
        pnlEstadoCuentaDetalle.add(lblSaldoFinalVal);

        // Estado 3: Recibo de Retiro
        JPanel pnlReciboFinal = new JPanel(new GridLayout(4, 2, 8, 10));
        pnlReciboFinal.setOpaque(false);
        pnlReciboFinal.setBorder(new EmptyBorder(20, 10, 20, 10));

        pnlReciboFinal.add(crearEtiquetaNegrita("No. Cuenta:"));
        lblReciboDigitos = new JLabel("-");
        pnlReciboFinal.add(lblReciboDigitos);

        pnlReciboFinal.add(crearEtiquetaNegrita("Nombre del Cliente:"));
        lblReciboNombre = new JLabel("-");
        pnlReciboFinal.add(lblReciboNombre);

        pnlReciboFinal.add(crearEtiquetaNegrita("Cantidad Retirada:"));
        lblReciboMonto = new JLabel("-");
        lblReciboMonto.setFont(new Font("Segoe UI", Font.BOLD, 13));
        pnlReciboFinal.add(lblReciboMonto);

        pnlReciboFinal.add(crearEtiquetaNegrita("Cobro por Comisión:"));
        lblReciboComision = new JLabel("-");
        pnlReciboFinal.add(lblReciboComision);

        cardPanel.add(pnlEsperando, "ESPERA");
        cardPanel.add(pnlEstadoCuentaDetalle, "RESUMEN");
        cardPanel.add(pnlReciboFinal, "RECIBO");
        cardLayoutDerecho.show(cardPanel, "ESPERA");

        pnlInfoDerecho.add(cardPanel, BorderLayout.CENTER);

        // Botón Retirar Efectivo
        btnRetirar = new JButton("RETIRAR EFECTIVO");
        btnRetirar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnRetirar.setBackground(new Color(40, 167, 69));
        btnRetirar.setForeground(Color.WHITE);
        btnRetirar.setFocusPainted(false);
        btnRetirar.setEnabled(false); // Inicia deshabilitado hasta seleccionar monto
        btnRetirar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRetirar.setPreferredSize(new Dimension(200, 42));

        btnRetirar.addActionListener((ActionEvent e) -> {
            controlador.onSolicitarRetiro();
        });

        pnlInfoDerecho.add(btnRetirar, BorderLayout.SOUTH);
        pnlCentral.add(pnlInfoDerecho);

        add(pnlCentral, BorderLayout.CENTER);
    }

    private JLabel crearEtiquetaNegrita(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(new Color(60, 64, 67));
        return label;
    }

    // ==========================================
    // IMPLEMENTACIÓN DE IModeloRetiroListener (Observer)
    // ==========================================

    @Override
    public void onEstadoCuentaActualizado(String titular, double saldoActual, double montoRetiro, double comision, double saldoFinal) {
        lblTitularVal.setText(titular);
        lblSaldoActualVal.setText("$ " + String.format("%.2f", saldoActual));
        lblRetiroVal.setText("- $ " + String.format("%.2f", montoRetiro));
        lblComisionVal.setText("- $ " + String.format("%.2f", comision));
        lblSaldoFinalVal.setText("$ " + String.format("%.2f", saldoFinal));

        lblEstadoCuenta.setText("ESTADO DE CUENTA");
        cardLayoutDerecho.show(cardPanel, "RESUMEN");

        // Si el saldo final fuera menor a cero, advertir en el botón
        if (saldoFinal < 0) {
            btnRetirar.setEnabled(false);
            btnRetirar.setText("FONDOS INSUFICIENTES");
            btnRetirar.setBackground(Color.GRAY);
        } else {
            btnRetirar.setEnabled(true);
            btnRetirar.setText("RETIRAR EFECTIVO");
            btnRetirar.setBackground(new Color(40, 167, 69));
        }
    }

    @Override
    public void onRetiroExitoso(Recibo recibo) {
        lblReciboDigitos.setText(recibo.getUltimosDigitos());
        lblReciboNombre.setText(recibo.getNombreCliente());
        lblReciboMonto.setText("$ " + String.format("%.2f", recibo.getMontoRetirado()));
        lblReciboComision.setText("$ " + String.format("%.2f", recibo.getComisionCobrada()));

        lblEstadoCuenta.setText("RECIBO DE RETIRO");
        cardLayoutDerecho.show(cardPanel, "RECIBO");

        // Deshabilitar interacción una vez terminado el retiro
        btnRetirar.setEnabled(false);
        btnRetirar.setText("OPERACIÓN EXITOSA");
        btnRetirar.setBackground(new Color(26, 43, 76));

        for (JButton b : botonesMontos) {
            b.setEnabled(false);
        }

        JOptionPane.showMessageDialog(this, 
                "¡Retiro realizado con éxito!\nTome su dinero y su recibo impreso.", 
                "Cajero Automático", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void onError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error en Transacción", JOptionPane.ERROR_MESSAGE);
    }
}