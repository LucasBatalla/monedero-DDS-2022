package dds.monedero.model;

public interface TipoMovimiento {
  double calcularValorMovimiento(Cuenta cuenta, Movimiento movimiento);
  TiposDeMovimientos getTipoDeMovimiento();
}
