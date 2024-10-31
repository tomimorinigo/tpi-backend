package utn.frc.bda.servicioposicion.mapa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ZonaPeligrosa {
    private double latitud;
    private double longitud;
    private double radio;
}
