package utn.frc.bda.serviciopruebas.web.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utn.frc.bda.serviciopruebas.web.api.dto.NotificacionPruebasVehiculoDTO;
import utn.frc.bda.serviciopruebas.web.service.NotificacionService;

import java.util.List;

@RestController
@RequestMapping("/notificacion")
public class NotificacionAPI {

    private NotificacionService notificacionService;

    @Autowired
    public NotificacionAPI(NotificacionService notificacionService){
        this.notificacionService = notificacionService;
    }

    @GetMapping("/obtener-prubas")
    public ResponseEntity<List<NotificacionPruebasVehiculoDTO>> obtenerPruebas(){
        return new ResponseEntity<>(notificacionService.obtenerNotificacionesPruebas(), HttpStatus.OK);
    }

}
