package utn.frc.bda.servicioposicion.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="posiciones")
@Getter @Setter @ToString @NoArgsConstructor
public class PosicionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Long id;

    @Column(name="latitud", nullable=false)
    private Integer latitud;

    @Column(name="longitud", nullable=false)
    private Integer longitud;

    @Column(name="fecha", nullable=false)
    private String fecha;

    @Column(name = "id_vehiculo")
    private Long idVehiculo;

}
