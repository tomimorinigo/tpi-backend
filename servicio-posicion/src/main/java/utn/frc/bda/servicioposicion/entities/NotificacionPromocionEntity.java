package utn.frc.bda.servicioposicion.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name="Notificaciones_Promociones")
@Getter @Setter @NoArgsConstructor
public class NotificacionPromocionEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="id_vehiculo")
    private Integer idVehiculo;

    @Column(name="porcentaje_oferta")
    private double porcentajeOferta;

    @Column(name="fecha_hora")
    private String fechaHora;

    @Column(name="fecha_fin")
    private String fechaFin;

    public NotificacionPromocionEntity(Integer idVehiculo, double porcentajeOferta, String fechaHora, String fechaFin){
        this.idVehiculo = idVehiculo;
        this.porcentajeOferta = porcentajeOferta;
        this.fechaHora = fechaHora;
        this.fechaFin = fechaFin;
    }

}
