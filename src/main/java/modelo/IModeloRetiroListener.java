package modelo;

public interface IModeloRetiroListener {
    void onEstadoCuentaActualizado(String titular, double saldoActual, double montoRetiro, double comision, double saldoFinal);
    void onRetiroExitoso(Recibo recibo);
    void onError(String mensaje);
}

