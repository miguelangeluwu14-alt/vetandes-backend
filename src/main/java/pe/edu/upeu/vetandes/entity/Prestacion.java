package pe.edu.upeu.vetandes.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;

@Entity
@Table(name="prestacion")
public class Prestacion {
 @Id
 @GeneratedValue(strategy=GenerationType.IDENTITY)
 private Long id;
 @Column(name="codigo")
 private String codigo;
 @Column(name="nombre")
 private String nombre;
 @Column(name="precio")
 private BigDecimal precio;
 @Column(name="especie_aplicable")
 private String especieAplicable;
 @Column(name="activo")
 private Integer activo;
 public Long getId() { return id; }
 public void setId(Long value) { this.id = value; }
 public String getCodigo() { return codigo; }
 public void setCodigo(String value) { this.codigo = value; }
 public String getNombre() { return nombre; }
 public void setNombre(String value) { this.nombre = value; }
 public BigDecimal getPrecio() { return precio; }
 public void setPrecio(BigDecimal value) { this.precio = value; }
 public String getEspecieAplicable() { return especieAplicable; }
 public void setEspecieAplicable(String value) { this.especieAplicable = value; }
 public Integer getActivo() { return activo; }
 public void setActivo(Integer value) { this.activo = value; }
}
