package utn.frc.bda.serviciopruebas.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import utn.frc.bda.serviciopruebas.web.api.dto.NotificacionPruebasVehiculoDTO;

import java.util.List;

@Service
public class NotificacionService {

    @Value("${pruebas.service.url}")
    private String pruebasServiceUrl;
    private NotificacionIncidenciaRepository notificacionIncidenciaRepository;
    private NotificacionPromocionRepository notificacionPromocionRepository;
    private EmailService emailService;
    private VehiculoService vehiculoService;

    @Autowired
    public NotificacionService(NotificacionIncidenciaRepository notificacionIncidenciaRepository,
                               NotificacionPromocionRepository notificacionPromocionRepository,
                               EmailService emailService,
                               VehiculoService vehiculoService){
        this.notificacionIncidenciaRepository = notificacionIncidenciaRepository;
        this.notificacionPromocionRepository = notificacionPromocionRepository;
        this.emailService = emailService;
        this.vehiculoService = vehiculoService;
    }

    public List<NotificacionPruebasVehiculoDTO> obtenerNotificacionesPruebas(){
        return notificacionIncidenciaRepository.findAll().stream().map(notificacion -> {
            // Crear nuevo DTO
            return new NotificacionIncidenteDTO(notificacion.getId(),
                    notificacion.getIdVehiculo(), notificacion.getIdInteresado(), notificacion.getIdPrueba(),
                    notificacion.getIdEmpleado(), notificacion.getTipoIncidente(), notificacion.getFechaHora());
        }).toList();
    }
}
