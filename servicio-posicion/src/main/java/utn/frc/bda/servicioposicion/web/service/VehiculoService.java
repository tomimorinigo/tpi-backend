package utn.frc.bda.servicioposicion.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import utn.frc.bda.servicioposicion.mapa.AgenciaConfig;

@Service
public class VehiculoService {

    // TODO: PONER URL DE VEHICULO EN APPLICATION.PROPERTIES
    // URL: http://localhost:8080/vehiculo/recibir-vehiculo
    @Value("${vehiculo.service.url}")
    private String vehiculoServiceUrl;

    public boolean recibirVehiculo(Integer idVehiculo){
        RestTemplate restTemplate = new RestTemplate();
        Boolean result = restTemplate.getForObject(vehiculoServiceUrl + "?idVehiculo=" + idVehiculo, Boolean.class);
        return Boolean.TRUE.equals(result);
    }

}
