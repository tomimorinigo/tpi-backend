package utn.frc.bda.servicioposicion.web.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import utn.frc.bda.servicioposicion.entities.PosicionEntity;

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

    public PosicionEntity toEntity(){
        return new PosicionEntity(latitud, longitud, idVehiculo);
    }
}
