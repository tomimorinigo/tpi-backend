package utn.frc.bda.serviciopruebas.entities;

import lombok.*;
import jakarta.persistence.*;

@Entity @Table(name="pruebas")
@Getter @Setter @NoArgsConstructor
public class PruebaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Integer id;

    @Column(name="fecha_hora_inicio", nullable=false)
    private String fechaHoraInicio;

    @Column(name="fecha_hora_fin", nullable=true)
    private String fechaHoraFin;

    @Column(name="comentarios")
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

    public PruebaEntity(Integer id, String comentarios){
        this.id = id;
        this.comentarios = comentarios;
    }

    @Override
    public String toString() {
        return "PruebaEntity{" +
                "id=" + id +
                ", fechaHoraInicio='" + fechaHoraInicio + '\'' +
                ", fechaHoraFin='" + fechaHoraFin + '\'' +
                ", comentarios='" + comentarios + '\'' +
                '}';
    }
}
