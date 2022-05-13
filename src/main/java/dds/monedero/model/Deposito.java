package dds.monedero.model;

public class Deposito implements TipoMovimiento {

  @Override
  public double calcularValorMovimiento(Cuenta cuenta, Movimiento movimiento) {
    return cuenta.getSaldo() + movimiento.getMonto();
  }

  @Override
  public TiposDeMovimientos getTipoDeMovimiento(){
    return TiposDeMovimientos.DEPOSITO;
  }
}
