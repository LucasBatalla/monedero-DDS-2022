package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

public class Cuenta {

  private double saldo;
  private double limiteDeExtraccion = 1000;
  private double limiteDeDepositos = 3;
  private List<Movimiento> movimientos = new ArrayList<>();

  public Cuenta(double montoInicial) {
    saldo = montoInicial;
    validarMonto(montoInicial);
  }

  public void depositar(double cantidad) {
    validarMonto(cantidad);
    validarLimiteDepositos();
    Movimiento movimiento = new Movimiento(LocalDate.now(), cantidad, new Deposito())
    agregarMovimiento(movimiento);

  }

  public void extraer(double cantidad) {
    validarMonto(cantidad);
    validarExtraccion(cantidad);
    validarLimiteExtracciones(cantidad);
    Movimiento movimiento = new Movimiento(LocalDate.now(), cantidad, new Deposito());
    agregarMovimiento(movimiento);
  }

  public void agregarMovimiento(Movimiento movimiento) {
    movimientos.add(movimiento);
    movimiento.modificarSaldoCuenta(this);
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return movimientos.stream()
        .filter(movimiento -> movimiento.getTipoMovimiento() == TiposDeMovimientos.EXTRACCION && movimiento.getFecha().equals(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }
  public void validarMonto(double monto){
    if(monto < 0){
      throw new MontoNegativoException(monto + ": el monto a ingresar debe ser un valor positivo");
    }
  }

  public void validarExtraccion(double cantidad){
    if(saldo - cantidad < 0){
      throw new SaldoMenorException("No puede sacar mas de " + saldo + " $");
    }
  }

  public void validarLimiteExtracciones(double cantidad){
    double montoExtraidoHoy = getMontoExtraidoA(LocalDate.now());
    double limite = limiteDeExtraccion - montoExtraidoHoy;
    if (cantidad > limite) {
      throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + 1000
          + " diarios, límite: " + limite);
    }
  }
  public void validarLimiteDepositos(){
    if (movimientos.stream().filter(movimiento -> movimiento.getTipoMovimiento() == TiposDeMovimientos.DEPOSITO).count() >= 3) {
      throw new MaximaCantidadDepositosException("Ya excedio los " + 3 + " depositos diarios");
    }
  }
  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

  public List<Movimiento> getMovimientos(){
    return movimientos;
  }

}
