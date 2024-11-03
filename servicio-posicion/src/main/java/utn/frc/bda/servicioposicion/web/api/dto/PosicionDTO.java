package utn.frc.bda.servicioposicion.web.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PosicionDTO {
    private double latitud;
    private double longitud;
    private Integer idVehiculo;

    public PosicionDTO(double latitud, double longitud, Integer idVehiculo){
        this.latitud = latitud;
        this.longitud = longitud;
        this.idVehiculo = idVehiculo;
    }
}
