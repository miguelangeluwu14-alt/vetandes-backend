package pe.edu.upeu.vetandes.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;

@Entity
@Table(name="roles")
public class Rol {
 @Id
 @GeneratedValue(strategy=GenerationType.IDENTITY)
 private Long id;
 @Column(name="nombre")
 private String nombre;
 public Long getId() { return id; }
 public void setId(Long value) { this.id = value; }
 public String getNombre() { return nombre; }
 public void setNombre(String value) { this.nombre = value; }
}
