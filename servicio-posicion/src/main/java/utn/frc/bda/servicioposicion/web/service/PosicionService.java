package utn.frc.bda.servicioposicion.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frc.bda.servicioposicion.dal.PosicionRepository;
import utn.frc.bda.servicioposicion.mapa.AgenciaConfig;
import utn.frc.bda.servicioposicion.web.api.dto.PosicionDTO;

@Service
public class PosicionService {

    private PosicionRepository posicionRepository;
    private AgenciaService agenciaService;

    @Autowired
    public PosicionService(PosicionRepository posicionRepository, AgenciaService agenciaService){
        this.posicionRepository = posicionRepository;
        this.agenciaService = agenciaService;
    }

    // Endpoint 4 -> recibir posicion y evaluar si esta dentro de zonas peligrosas o fuera de radio
    public void recibirPosicion(PosicionDTO posicion){
        AgenciaConfig agenciaConfig = agenciaService.getAgenciaConfig();
        // TODO: Evaluar si esta dentro de zonas peligrosas o fuera de radio
    }

}
