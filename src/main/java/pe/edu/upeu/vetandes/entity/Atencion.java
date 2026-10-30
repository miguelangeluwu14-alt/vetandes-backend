package pe.edu.upeu.vetandes.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;

@Entity
@Table(name="atencion")
public class Atencion {
 @Id
 @GeneratedValue(strategy=GenerationType.IDENTITY)
 private Long id;
 @Column(name="numero")
 private String numero;
 @Column(name="fecha")
 private LocalDateTime fecha;
 @ManyToOne(fetch=FetchType.LAZY,optional=false)
 @JoinColumn(name="mascota_id",nullable=false)
 private Mascota mascota;
 @Column(name="peso")
 private BigDecimal peso;
 @Column(name="diagnostico")
 private String diagnostico;
 @Column(name="estado")
 private String estado;
 @Column(name="subtotal")
 private BigDecimal subtotal;
 @Column(name="descuento")
 private BigDecimal descuento;
 @Column(name="total")
 private BigDecimal total;
 @Column(name="usuario_registro")
 private String usuarioRegistro;
 @Column(name="motivo_anulacion")
 private String motivoAnulacion;
 @OneToMany(mappedBy="atencion",fetch=FetchType.LAZY)
 private List<DetalleAtencion> detalles = new ArrayList<>();
 public Long getId() { return id; }
 public void setId(Long value) { this.id = value; }
 public String getNumero() { return numero; }
 public void setNumero(String value) { this.numero = value; }
 public LocalDateTime getFecha() { return fecha; }
 public void setFecha(LocalDateTime value) { this.fecha = value; }
 public Mascota getMascota() { return mascota; }
 public void setMascota(Mascota value) { this.mascota = value; }
 public BigDecimal getPeso() { return peso; }
 public void setPeso(BigDecimal value) { this.peso = value; }
 public String getDiagnostico() { return diagnostico; }
 public void setDiagnostico(String value) { this.diagnostico = value; }
 public String getEstado() { return estado; }
 public void setEstado(String value) { this.estado = value; }
 public BigDecimal getSubtotal() { return subtotal; }
 public void setSubtotal(BigDecimal value) { this.subtotal = value; }
 public BigDecimal getDescuento() { return descuento; }
 public void setDescuento(BigDecimal value) { this.descuento = value; }
 public BigDecimal getTotal() { return total; }
 public void setTotal(BigDecimal value) { this.total = value; }
 public String getUsuarioRegistro() { return usuarioRegistro; }
 public void setUsuarioRegistro(String value) { this.usuarioRegistro = value; }
 public String getMotivoAnulacion() { return motivoAnulacion; }
 public void setMotivoAnulacion(String value) { this.motivoAnulacion = value; }
 public List<DetalleAtencion> getDetalles() { return detalles; }
}
