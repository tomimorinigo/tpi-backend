package utn.frc.bda.servicioposicion.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name="Notificaciones_Incidencias")
@Getter @Setter @NoArgsConstructor
public class NotificacionIncidenciaEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="id_vehiculo")
    private Integer idVehiculo;

    @Column(name="id_interesado")
    private Integer idInteresado;

    @Column(name="id_prueba")
    private Integer idPrueba;

    @Column(name="id_empleado")
    private Integer idEmpleado;

    @Column(name="tipo_incidente")
    private String tipoIncidente;

    @Column(name="fecha_hora")
    private String fechaHora;

    public NotificacionIncidenciaEntity(Integer idVehiculo, Integer idInteresado, Integer idPrueba, Integer idEmpleado, String tipoIncidente, String fechaHora){
        this.idVehiculo = idVehiculo;
        this.idInteresado = idInteresado;
        this.idPrueba = idPrueba;
        this.idEmpleado = idEmpleado;
        this.tipoIncidente = tipoIncidente;
        this.fechaHora = fechaHora;
    }

}
