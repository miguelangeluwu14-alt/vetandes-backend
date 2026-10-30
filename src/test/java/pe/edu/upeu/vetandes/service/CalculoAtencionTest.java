package pe.edu.upeu.vetandes.service;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class CalculoAtencionTest {
 @Test void tresPrestacionesAplicanDiezPorCiento(){
  BigDecimal subtotal = new BigDecimal("115.00");
  assertEquals(new BigDecimal("11.50"),CalculoAtencion.descuento(subtotal,3));
  assertEquals(new BigDecimal("103.50"),subtotal.subtract(CalculoAtencion.descuento(subtotal,3)));
 }
 @Test void menosDeTresNoDescuentan(){
  assertEquals(new BigDecimal("0.00"),CalculoAtencion.descuento(new BigDecimal("85.00"),2));
 }
 @Test void redondeaDosDecimales(){assertEquals(new BigDecimal("1.01"),CalculoAtencion.redondear(new BigDecimal("1.005")));}
}
