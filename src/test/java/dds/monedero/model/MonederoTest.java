package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MonederoTest {
  private Cuenta cuenta;

  @BeforeEach
  void init() {
    cuenta = new Cuenta(0);
  }

  @Test
  void elDepositoDeDineroAumentaElSaldoDeLaCuenta () {
    cuenta.depositar(1500);
    assertEquals(cuenta.getSaldo(), 1500);
  }

  @Test
  void laExtraccionDeDineroDisminuyeElSaldoDeLaCuenta () {
    cuenta.depositar(1500);
    assertEquals(cuenta.getSaldo(), 0);
  }
  @Test
  void noEsPosibleDepositarUnMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.depositar(-1500));
  }
  @Test
  void noEsPosibleRealizarMasDeTresDepositos() {
    assertThrows(MaximaCantidadDepositosException.class, () -> {
          cuenta.depositar(1500);
          cuenta.depositar(456);
          cuenta.depositar(1900);
          cuenta.depositar(245);
    });
  }

  @Test
  void noEsPosibleExtraerMasDineroQueElSaldoDisponible() {
    assertThrows(SaldoMenorException.class, () -> {
          cuenta.setSaldo(90);
          cuenta.extraer(1001);
    });
  }

  @Test
  public void noEsPosibleExtaerMasDineroQueElLimiteDeLaCuenta() {
    assertThrows(MaximoExtraccionDiarioException.class, () -> {
      cuenta.setSaldo(5000);
      cuenta.sacar(1001);
    });
  }

  @Test
  public void noEsPosibleExtraerUnMontoNegativo() {

    assertThrows(MontoNegativoException.class, () -> cuenta.sacar(-500));
  }

}