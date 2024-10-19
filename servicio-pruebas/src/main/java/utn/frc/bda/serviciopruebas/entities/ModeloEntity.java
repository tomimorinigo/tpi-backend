package utn.frc.bda.serviciopruebas.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity @Table(name="modelos")
@Getter @Setter @ToString @NoArgsConstructor
public class ModeloEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Long id;

    @Column(name="descripcion", nullable=false)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name="id_marca")
    private MarcaEntity marca;

    @OneToMany(mappedBy="modelo")
    private Set<VehiculoEntity> vehiculos;

    public void addVehiculo(VehiculoEntity vehiculo){
        if(vehiculo == null){

        }
        vehiculo.setModelo(this);
    }


}
