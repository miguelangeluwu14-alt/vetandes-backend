package pe.edu.upeu.vetandes.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;

@Entity
@Table(name="mascota")
public class Mascota {
 @Id
 @GeneratedValue(strategy=GenerationType.IDENTITY)
 private Long id;
 @Column(name="nombre")
 private String nombre;
 @Column(name="especie")
 private String especie;
 @Column(name="raza")
 private String raza;
 @Column(name="fecha_nacimiento")
 private LocalDate fechaNacimiento;
 @org.hibernate.annotations.JdbcTypeCode(org.hibernate.type.SqlTypes.CHAR)
 @Column(name="sexo",columnDefinition="CHAR(1)",length=1)
 private String sexo;
 @ManyToOne(fetch=FetchType.LAZY,optional=false)
 @JoinColumn(name="propietario_id",nullable=false)
 private Propietario propietario;
 public Long getId() { return id; }
 public void setId(Long value) { this.id = value; }
 public String getNombre() { return nombre; }
 public void setNombre(String value) { this.nombre = value; }
 public String getEspecie() { return especie; }
 public void setEspecie(String value) { this.especie = value; }
 public String getRaza() { return raza; }
 public void setRaza(String value) { this.raza = value; }
 public LocalDate getFechaNacimiento() { return fechaNacimiento; }
 public void setFechaNacimiento(LocalDate value) { this.fechaNacimiento = value; }
 public String getSexo() { return sexo; }
 public void setSexo(String value) { this.sexo = value; }
 public Propietario getPropietario() { return propietario; }
 public void setPropietario(Propietario value) { this.propietario = value; }
}
