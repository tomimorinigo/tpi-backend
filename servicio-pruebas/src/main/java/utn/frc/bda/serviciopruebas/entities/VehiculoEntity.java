package utn.frc.bda.serviciopruebas.entities;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Getter @Setter @NoArgsConstructor @ToString
@Entity
@Table(name="vehiculos")
public class VehiculoEntity {
    @Id @Column(name="id", nullable=false)
    private Integer id;

    @Basic @Column(name="patente")
    private String patente;

    @ManyToOne
    @JoinColumn(name="id_modelo")
    private ModeloEntity modelo;

    // Cambia la relación @OneToMany por una lista de IDs de posiciones
    @ElementCollection
    @Column(name = "id_posicion")
    private Set<Long> posicionIds = new HashSet<>(); // Almacenas solo los IDs de las posiciones


    @OneToMany(mappedBy="vehiculo")
    private Set<PruebaEntity> pruebas;

    public VehiculoEntity(String patente, ModeloEntity modelo){
        this.patente = patente;
        this.modelo = modelo;
        modelo.addVehiculo(this);
    }

    public void addPrueba(PruebaEntity prueba){
        if(pruebas == null){
            pruebas = new HashSet<>();
        }
        pruebas.add(prueba);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VehiculoEntity that = (VehiculoEntity) o;
        return id == that.id && Objects.equals(patente, that.patente);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + Objects.hashCode(patente);
        return result;
    }
}
