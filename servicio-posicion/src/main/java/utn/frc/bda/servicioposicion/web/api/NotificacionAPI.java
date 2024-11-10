package utn.frc.bda.servicioposicion.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frc.bda.servicioposicion.web.api.dto.NotificacionIncidenteDTO;
import utn.frc.bda.servicioposicion.web.api.dto.NotificacionPromocionDTO;
import utn.frc.bda.servicioposicion.web.service.NotificacionService;

import java.util.List;

@RestController
public class NotificacionAPI {

    private NotificacionService notificacionService;

    @Autowired
    public NotificacionAPI(NotificacionService notificacionService){
        this.notificacionService = notificacionService;
    }

    // Endpoint 5 -> notificacion de promociones
    @PostMapping("/notificacion/promocion")
    public ResponseEntity<String> notificarPromocion(@RequestBody  NotificacionPromocionDTO notificacion){
        System.out.println(notificacion);
        notificacionService.enviarNotificacionPromocion(notificacion);
        return new ResponseEntity<>("Notificacion de promocion enviada", HttpStatus.OK);
    }

    @GetMapping("/internal/notificacion/obtener-incidentes")
    public ResponseEntity<List<NotificacionIncidenteDTO>> obtenerIncidentes(){
        return new ResponseEntity<>(notificacionService.obtenerNotificacionesIncidentes(), HttpStatus.OK);
    }

}
