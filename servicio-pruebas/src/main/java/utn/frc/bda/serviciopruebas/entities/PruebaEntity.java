package utn.frc.bda.serviciopruebas.entities;

import lombok.*;
import jakarta.persistence.*;

@Entity @Table(name="pruebas")
@Getter @Setter @ToString @NoArgsConstructor
public class PruebaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Integer id;

    @Column(name="fecha_hora_inicio", nullable=false)
    private String fechaHoraInicio;

    @Column(name="fecha_hora_fin", nullable=false)
    private String fechaHoraFin;

    @Column(name="comentarios", nullable=false)
    private String comentarios;

    @ManyToOne
    @JoinColumn(name="id_interesado")
    private InteresadoEntity interesado;

    @ManyToOne
    @JoinColumn(name="id_empleado")
    private EmpleadoEntity empleado;

    @ManyToOne
    @JoinColumn(name="id_vehiculo")
    private VehiculoEntity vehiculo;

    public PruebaEntity(Integer id, String fechaHoraInicio, String fechaHoraFin, String comentarios){
        this.id = id;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.comentarios = comentarios;
    }
}
