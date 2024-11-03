package utn.frc.bda.servicioposicion.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import utn.frc.bda.servicioposicion.mapa.AgenciaConfig;
import utn.frc.bda.servicioposicion.web.api.dto.PosicionDTO;

@Service
public class AgenciaService {

    private final AgenciaConfig agenciaConfig;

    public AgenciaService(@Value("${agencia.service.url}") String agenciaServiceUrl) {
        // Obtener la configuración de la agencia
        RestTemplate restTemplate = new RestTemplate();

        try {
            this.agenciaConfig = restTemplate.getForObject(agenciaServiceUrl, AgenciaConfig.class);
            System.out.println("Configuración de agencia cargada exitosamente.");
        } catch (Exception e) {
            System.err.println("Error al obtener la configuración de agencia: " + e.getMessage());
            throw e;
        }
    }

    private double calcularDistancia(double lat1, double long1, double lat2, double long2) {
        // Calcular la distancia euclídea entre dos puntos
        return Math.sqrt(Math.pow(lat1 - lat2, 2) + Math.pow(long1 - long2, 2));
    }

    public boolean validarPosicionEnRadioAgencia(PosicionDTO posicion) {

        // Obtener la configuración de la agencia
        double agenciaLatitud = agenciaConfig.getCoordenadasAgencia().getLat();
        double agenciaLongitud = agenciaConfig.getCoordenadasAgencia().getLon();

        // Calcular la distancia euclídea entre la posición del vehículo y la agencia
        double distancia = calcularDistancia(agenciaLatitud, agenciaLongitud, posicion.getLatitud(), posicion.getLongitud());
        return distancia <= agenciaConfig.getRadioAdmitidoKm();
    }

    public boolean validarPosicionEnZonasPeligrosas(PosicionDTO posicion) {
        // Verificar si la posición está en alguna zona restringida
        // Devuelve true si la posicion está dentro de alguna zona restringida
        return agenciaConfig.getZonasRestringidas().stream()
                .anyMatch(zona -> zona.contienePunto(posicion.getLatitud(), posicion.getLongitud()));
    }
}