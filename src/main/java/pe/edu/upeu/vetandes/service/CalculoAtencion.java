package pe.edu.upeu.vetandes.service;

import java.math.*;

public final class CalculoAtencion {
 private CalculoAtencion() {}
 public static BigDecimal redondear(BigDecimal valor){return valor.setScale(2,RoundingMode.HALF_UP);}
 public static BigDecimal descuento(BigDecimal subtotal,int distintas){
  return distintas>=3 ? redondear(subtotal.multiply(new BigDecimal("0.10"))) : new BigDecimal("0.00");
 }
}
