package TransportesYIYO.seguimiento.models.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "camiones")
public class Camiones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String marca;

    @Column
    @NotBlank
    private String patente;

    @OneToMany(mappedBy = "camion")
    @JsonManagedReference("camion-pedidos")
    private List<Pedidos> pedidos = new ArrayList<>();

    //CONSTRUCTORES

    public Camiones() {
    }

    public Camiones(Long id) {
        this.id = id;
    }


    //GETTER/SETTER
    @JsonManagedReference
    public List<Pedidos> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedidos> pedidos) {
        this.pedidos = pedidos;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
