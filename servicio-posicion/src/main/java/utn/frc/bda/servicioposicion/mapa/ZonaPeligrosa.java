package utn.frc.bda.servicioposicion.mapa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ZonaPeligrosa {
    private Coordenadas noroeste;
    private Coordenadas sureste;

    // Comprobar si un punto está dentro de la zona peligrosa
    public boolean contienePunto(double lat, double lon) {
        return (lat <= noroeste.getLat() && lat >= sureste.getLat()) &&
                (lon >= noroeste.getLon() && lon <= sureste.getLon());
    }
}
