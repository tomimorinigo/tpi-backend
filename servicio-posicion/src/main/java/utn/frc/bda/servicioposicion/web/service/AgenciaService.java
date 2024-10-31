package utn.frc.bda.servicioposicion.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import utn.frc.bda.servicioposicion.mapa.AgenciaConfig;

@Service
public class AgenciaService {

    @Value("${agencia.service.url}")
    private String agenciaServiceUrl;

    private AgenciaConfig agenciaConfig;

    public AgenciaConfig getAgenciaConfig(){
        if (agenciaConfig == null){
            RestTemplate restTemplate = new RestTemplate();
            agenciaConfig = restTemplate.getForObject(agenciaServiceUrl, AgenciaConfig.class);
            agenciaConfig = new AgenciaConfig();
        }

        return agenciaConfig;
    }

}
