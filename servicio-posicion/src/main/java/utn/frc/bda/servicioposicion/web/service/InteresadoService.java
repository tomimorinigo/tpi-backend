package utn.frc.bda.servicioposicion.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InteresadoService {

    // URL: http://localhost:8081//interesado/restringir-interesado?idVehiculo=1
    @Value("${interesado.service.url}")
    private String interesadoServiceUrl;

    public void restringirInteresado(Integer idVehiculo){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(interesadoServiceUrl + "?idVehiculo=" + idVehiculo, null);
    }

}
