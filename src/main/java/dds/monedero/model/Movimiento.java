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

  public void agregateA(Cuenta cuenta) {
    cuenta.setSaldo(calcularValor(cuenta));
    cuenta.agregarMovimiento(fecha, monto);
  }

  public double calcularValor(Cuenta cuenta) {
    return tipo.calcularValorMovimiento(cuenta, this);
  }
}
