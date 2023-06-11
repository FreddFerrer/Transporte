package TransportesYIYO.seguimiento.models.entities;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedidos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer nroPedido;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Clientes cliente;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;

    @Column(nullable = false)
    @NotBlank(message = "El destino es obligatorio")
    private String destino;

    @Column(name = "fecha_salida")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date fechaSalida;

    @Column(name = "fecha_estimada")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date fechaEstimada;

    @Column(name = "entregado")
    private boolean entregado = false;

    @JsonManagedReference
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EstadoPedidos> estados = new ArrayList<>();


    @JsonBackReference("camion-pedidos")
    @ManyToOne
    @JoinColumn(name = "camion_id")
    private Camiones camion;

    public String getClienteNombre() {
        return cliente.getNombre() + " " + cliente.getApellido();
    }


    @PrePersist
    public void prePersist() {
        createAt = new Date();
    }

    public void agregarEstado(EstadoPedidos estado) {
        estados.add(estado);
        estado.setFecha(new Date());
        estado.setPedido(this);
    }


    //GETTER/SETTER


    public List<EstadoPedidos> getEstados() {
        return estados;
    }

    public void setEstados(List<EstadoPedidos> estados) {
        this.estados = estados;
    }

    @JsonBackReference
    public Camiones getCamion() {
        return camion;
    }

    public void setCamion(Camiones camion) {
        this.camion = camion;
    }


    public Integer getNroPedido() {
        return nroPedido;
    }

    @JsonBackReference
    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public void setNroPedido(Integer nroPedido) {
        this.nroPedido = nroPedido;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Date getFechaEstimada() {
        return fechaEstimada;
    }

    public void setFechaEstimada(Date fechaEstimada) {
        this.fechaEstimada = fechaEstimada;
    }

    public boolean isEntregado() {
        return entregado;
    }

    public void setEntregado(boolean entregado) {
        this.entregado = entregado;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Long getId() {
        return id;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getCamionId() {
        return camion != null ? camion.getId() : null;
    }

    public Long getClienteId() {
        return cliente.getId();
    }
}
