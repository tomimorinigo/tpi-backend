package utn.frc.bda.servicioposicion.mapa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AgenciaConfig {
    private Coordenadas coordenadasAgencia;
    private double radioAdmitidoKm;
    private List<ZonaPeligrosa> zonasRestringidas;
}
