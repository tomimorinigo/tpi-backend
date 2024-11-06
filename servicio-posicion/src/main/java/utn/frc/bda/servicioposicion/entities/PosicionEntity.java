package utn.frc.bda.servicioposicion.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="posiciones")
@Getter @Setter @ToString @NoArgsConstructor
public class PosicionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Integer id;

    @Column(name="latitud", nullable=false)
    private double latitud;

    @Column(name="longitud", nullable=false)
    private double longitud;

    @Column(name="fecha_hora", nullable=false)
    private String fechaHora;

    @Column(name = "id_vehiculo")
    private Integer idVehiculo;

    public PosicionEntity(double latitud, double longitud, Integer idVehiculo){
        this.latitud = latitud;
        this.longitud = longitud;
        this.idVehiculo = idVehiculo;
    }
}
