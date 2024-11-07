package utn.frc.bda.servicioposicion.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import utn.frc.bda.servicioposicion.web.api.dto.VehiculoDTO;

@Service
public class VehiculoService {

    // URL: http://localhost:8080/vehiculo/esta-en-prueba
    @Value("${vehiculo.service.url}")
    private String vehiculoServiceUrl;

    public boolean estaEnPruebaVehiculo(Integer idVehiculo){
        RestTemplate restTemplate = new RestTemplate();
        Boolean result = restTemplate.getForObject(vehiculoServiceUrl + "esta-en-prueba?idVehiculo=" + idVehiculo, Boolean.class);
        return Boolean.TRUE.equals(result);
    }

    public VehiculoDTO  obtenerVehiculo(Integer idVehiculo){
        RestTemplate restTemplate = new RestTemplate();
        VehiculoDTO vehiculoDTO = restTemplate.getForObject(vehiculoServiceUrl + "obtener-vehiculo?idVehiculo=" + idVehiculo, VehiculoDTO.class);
        return vehiculoDTO;
    }

}
