package pe.edu.upeu.vetandes.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;

@Entity
@Table(name="propietario")
public class Propietario {
 @Id
 @GeneratedValue(strategy=GenerationType.IDENTITY)
 private Long id;
 @Column(name="dni")
 private String dni;
 @Column(name="nombres")
 private String nombres;
 @Column(name="telefono")
 private String telefono;
 public Long getId() { return id; }
 public void setId(Long value) { this.id = value; }
 public String getDni() { return dni; }
 public void setDni(String value) { this.dni = value; }
 public String getNombres() { return nombres; }
 public void setNombres(String value) { this.nombres = value; }
 public String getTelefono() { return telefono; }
 public void setTelefono(String value) { this.telefono = value; }
}
