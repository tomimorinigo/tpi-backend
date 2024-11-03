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
    private VehiculoService vehiculoService;
    private NotificacionService notificacionService;

    @Autowired
    public PosicionService(PosicionRepository posicionRepository, AgenciaService agenciaService, VehiculoService vehiculoService, NotificacionService notificacionService){
        this.posicionRepository = posicionRepository;
        this.agenciaService = agenciaService;
        this.vehiculoService = vehiculoService;
        this.notificacionService = notificacionService;
    }

    // Endpoint 4 -> recibir posicion y evaluar si esta dentro de zonas peligrosas o fuera de radio
    public void recibirPosicion(PosicionDTO posicion) {
        // Evaluar si el vehiculo se encuentra en una prueba
        if (vehiculoService.recibirVehiculo(posicion.getIdVehiculo())) {
            // Si el vehiculo se encuentra en una prueba, evaluar si está dentro de los límites establecidos
            if (agenciaService.validarPosicionEnRadioAgencia(posicion)) {
                // Si está dentro de los límites establecidos, debemos comprobar las zonas peligrosas
                if (agenciaService.validarPosicionEnZonasPeligrosas(posicion)) {
                    // TODO: Enviar notificación al empleado
                    //notificacionService.enviarNotificacionIncidencia(posicion);

                    // TODO: Cambiarle al interesado su atributo de restringido
                    //
                    System.out.println("El vehiculo esta en zona peligrosa");
                }

                System.out.println("El vehiculo esta dentro de radio y fuera de zonas peligrosas");
            } else {
                System.out.println("El vehiculo esta fuera de radio");
                //notificacionService.enviarNotificacionIncidencia(posicion);
            }
        } else {
           System.out.println("El vehiculo no se encuentra en una prueba");
        }
    }
}
