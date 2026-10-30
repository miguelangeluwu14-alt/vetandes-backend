package pe.edu.upeu.vetandes.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;

@Entity
@Table(name="usuarios")
public class Usuario {
 @Id
 @GeneratedValue(strategy=GenerationType.IDENTITY)
 private Long id;
 @Column(name="username")
 private String username;
 @Column(name="password")
 private String password;
 @Column(name="nombrecompleto")
 private String nombreCompleto;
 @Column(name="activo")
 private Integer activo;
 @ManyToMany(fetch=FetchType.EAGER)
 @JoinTable(name="usuario_roles",joinColumns=@JoinColumn(name="usuario_id"),inverseJoinColumns=@JoinColumn(name="rol_id"))
 private Set<Rol> roles = new HashSet<>();
 public Long getId() { return id; }
 public void setId(Long value) { this.id = value; }
 public String getUsername() { return username; }
 public void setUsername(String value) { this.username = value; }
 public String getPassword() { return password; }
 public void setPassword(String value) { this.password = value; }
 public String getNombreCompleto() { return nombreCompleto; }
 public void setNombreCompleto(String value) { this.nombreCompleto = value; }
 public Integer getActivo() { return activo; }
 public void setActivo(Integer value) { this.activo = value; }
 public Set<Rol> getRoles() { return roles; }
 public void setRoles(Set<Rol> roles) { this.roles=roles; }
}
