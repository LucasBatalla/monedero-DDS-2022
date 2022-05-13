package dds.monedero.model;

import java.time.LocalDate;

public class Movimiento {
  private LocalDate fecha;
  private double monto;
  private TipoMovimiento tipo;

  private TiposDeMovimientos tipoMovimiento;

  public Movimiento(LocalDate fecha, double monto, TipoMovimiento tipo) {
    this.fecha = fecha;
    this.monto = monto;
    this.tipo = tipo;
  }

  public double getMonto() {
    return monto;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public TiposDeMovimientos getTipoMovimiento() {
    return tipo.getTipoDeMovimiento();
  }

  public void modificarSaldoCuenta(Cuenta cuenta) {
    cuenta.setSaldo(calcularValor(cuenta));
  }

  public double calcularValor(Cuenta cuenta) {
    return tipo.calcularValorMovimiento(cuenta, this);
  }
}
