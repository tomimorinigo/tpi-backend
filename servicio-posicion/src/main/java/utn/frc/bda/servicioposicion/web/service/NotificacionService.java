package utn.frc.bda.servicioposicion.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import utn.frc.bda.servicioposicion.dal.NotificacionIncidenciaRepository;
import utn.frc.bda.servicioposicion.dal.NotificacionPromocionRepository;
import utn.frc.bda.servicioposicion.entities.NotificacionIncidenciaEntity;
import utn.frc.bda.servicioposicion.entities.NotificacionPromocionEntity;
import utn.frc.bda.servicioposicion.web.api.dto.NotificacionIncidenteDTO;
import utn.frc.bda.servicioposicion.web.api.dto.NotificacionPromocionDTO;
import utn.frc.bda.servicioposicion.web.api.dto.PruebaDTO;
import utn.frc.bda.servicioposicion.web.api.dto.VehiculoDTO;

import java.util.List;

import static utn.frc.bda.servicioposicion.utils.Utils.fechaActualFormatted;

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

    // Crear notificacion de Incidencia
    public void enviarNotificacionIncidencia(Integer idVehiculo, String tipoIncidente){
        RestTemplate restTemplate = new RestTemplate();

        try {
            PruebaDTO prueba = restTemplate.getForObject(pruebasServiceUrl + "/prueba-actual?idVehiculo=" + idVehiculo, PruebaDTO.class);

            // Generar notificación de incidencia
            NotificacionIncidenciaEntity notificacionIncidencia = new NotificacionIncidenciaEntity(idVehiculo,
                    prueba.getInteresado().getId(), prueba.getId(), prueba.getEmpleado().getLegajo(),
                    tipoIncidente, fechaActualFormatted());

            // Persistir notificación de incidencia
            notificacionIncidenciaRepository.save(notificacionIncidencia);

            // Generar mail con la notificación de incidencia
            String mensajeHtml = emailService.createHtmlMensaje(prueba, notificacionIncidencia, tipoIncidente);
            // TODO: En el mail iria el mail del empleado pero por ahora lo mandamos a mi mail
            emailService.enviarEmailHtml("morinigotomas1@gmail.com", "Notificación de incidencia", mensajeHtml);

        } catch (Exception e) {
            System.err.println("Error al enviar notificación de incidencia: " + e.getMessage());
            throw e;
        }
    }

    // Crear notificacion de promoción
    public void enviarNotificacionPromocion(NotificacionPromocionDTO notificacion){

        NotificacionPromocionEntity notificacionPromocionEntity = notificacion.toEntity();
        // Persistimos notificación de promoción
        notificacionPromocionRepository.save(notificacionPromocionEntity);

        // Buscamos el vehiculo
        VehiculoDTO vehiculoDTO = vehiculoService.obtenerVehiculo(notificacionPromocionEntity.getIdVehiculo());

        // Enviamos mail con la notificación de promoción
        String mensajeHtml = emailService.createHtmlMensajePromocion(notificacionPromocionEntity, vehiculoDTO);

        // Enviamos un mail para cada destinatario
        notificacionPromocionEntity.getDestinatariosPromocion().forEach(destinatario -> {
            emailService.enviarEmailHtml(destinatario.getEmail(), "Notificación de promoción", mensajeHtml);
        });
    }

    public List<NotificacionIncidenteDTO> obtenerNotificacionesIncidentes(){
        return notificacionIncidenciaRepository.findAll().stream().map(notificacion -> {
            // Crear nuevo DTO
            return new NotificacionIncidenteDTO(notificacion.getId(),
                    notificacion.getIdVehiculo(), notificacion.getIdInteresado(), notificacion.getIdPrueba(),
                    notificacion.getIdEmpleado(), notificacion.getTipoIncidente(), notificacion.getFechaHora());
        }).toList();
    }

}
