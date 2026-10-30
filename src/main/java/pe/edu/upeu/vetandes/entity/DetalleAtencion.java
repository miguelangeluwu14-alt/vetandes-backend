package pe.edu.upeu.vetandes.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;

@Entity
@Table(name="detalle_atencion")
public class DetalleAtencion {
 @Id
 @GeneratedValue(strategy=GenerationType.IDENTITY)
 private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false)
 @JoinColumn(name="atencion_id",nullable=false)
 private Atencion atencion;
 @ManyToOne(fetch=FetchType.LAZY,optional=false)
 @JoinColumn(name="prestacion_id",nullable=false)
 private Prestacion prestacion;
 @Column(name="cantidad")
 private Integer cantidad;
 @Column(name="precio_unitario")
 private BigDecimal precioUnitario;
 @Column(name="subtotal")
 private BigDecimal subtotal;
 public Long getId() { return id; }
 public void setId(Long value) { this.id = value; }
 public Atencion getAtencion() { return atencion; }
 public void setAtencion(Atencion value) { this.atencion = value; }
 public Prestacion getPrestacion() { return prestacion; }
 public void setPrestacion(Prestacion value) { this.prestacion = value; }
 public Integer getCantidad() { return cantidad; }
 public void setCantidad(Integer value) { this.cantidad = value; }
 public BigDecimal getPrecioUnitario() { return precioUnitario; }
 public void setPrecioUnitario(BigDecimal value) { this.precioUnitario = value; }
 public BigDecimal getSubtotal() { return subtotal; }
 public void setSubtotal(BigDecimal value) { this.subtotal = value; }
}
