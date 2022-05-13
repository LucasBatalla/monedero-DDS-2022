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
  private List<Movimiento> movimientos = new ArrayList<>();

  public Cuenta(double montoInicial) {
    saldo = montoInicial;
  }

  public void depositar(double cantidad) {
    if (cantidad <= 0) {
      throw new MontoNegativoException(cantidad + ": el monto a ingresar debe ser un valor positivo");
    }

    if (movimientos.stream().filter(movimiento -> movimiento.getTipoMovimiento() == TiposDeMovimientos.DEPOSITO).count() >= 3) {
      throw new MaximaCantidadDepositosException("Ya excedio los " + 3 + " depositos diarios");
    }

    new Movimiento(LocalDate.now(), cantidad, new Deposito()).agregarMovimientoA(this);
    saldo += cantidad;
  }

  public void extraer(double cantidad) {
    if (cantidad <= 0) {
      throw new MontoNegativoException(cantidad + ": el monto a ingresar debe ser un valor positivo");
    }
    if (saldo - cantidad < 0) {
      throw new SaldoMenorException("No puede sacar mas de " + saldo + " $");
    }
    double montoExtraidoHoy = getMontoExtraidoA(LocalDate.now());
    double limite = 1000 - montoExtraidoHoy;
    if (cantidad > limite) {
      throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + 1000
          + " diarios, límite: " + limite);
    }
    new Movimiento(LocalDate.now(), cantidad, new Extraccion()).agregarMovimientoA(this);
    saldo -= cantidad;
  }

  public void agregarMovimiento(LocalDate fecha, double cuanto) {
    movimientos.add(new Movimiento(fecha, cuanto, new Deposito()));
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return movimientos.stream()
        .filter(movimiento -> movimiento.getTipoMovimiento() == TiposDeMovimientos.EXTRACCION && movimiento.getFecha().equals(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

}
