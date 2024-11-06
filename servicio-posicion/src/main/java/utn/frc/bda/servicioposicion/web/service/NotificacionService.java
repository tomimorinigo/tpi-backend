package utn.frc.bda.servicioposicion.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import utn.frc.bda.servicioposicion.dal.NotificacionIncidenciaRepository;
import utn.frc.bda.servicioposicion.dal.NotificacionPromocionRepository;
import utn.frc.bda.servicioposicion.entities.NotificacionIncidenciaEntity;
import utn.frc.bda.servicioposicion.web.api.dto.PruebaDTO;

import static utn.frc.bda.servicioposicion.utils.Utils.fechaActualFormatted;

@Service
public class NotificacionService {

    @Value("${pruebas.service.url}")
    private String pruebasServiceUrl;
    private NotificacionIncidenciaRepository notificacionIncidenciaRepository;
    private NotificacionPromocionRepository notificacionPromocionRepository;
    private EmailService emailService;

    @Autowired
    public NotificacionService(NotificacionIncidenciaRepository notificacionIncidenciaRepository,
                               NotificacionPromocionRepository notificacionPromocionRepository,
                               EmailService emailService){
        this.notificacionIncidenciaRepository = notificacionIncidenciaRepository;
        this.notificacionPromocionRepository = notificacionPromocionRepository;
        this.emailService = emailService;
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

            String mensajeHtml = emailService.createHtmlMensaje(prueba, notificacionIncidencia, tipoIncidente);
            // En el mail iria el mail del empleado pero por ahora lo mandamos a mi mail
            emailService.enviarEmailHtml("morinigotomas1@gmail.com", "Notificación de incidencia", mensajeHtml);

        } catch (Exception e) {
            System.err.println("Error al enviar notificación de incidencia: " + e.getMessage());
            throw e;
        }
    }

    // TODO: Crear notificacion de Promoción --> ENDPOINT 5
    public void enviarNotificacionPromocion(String idVehiculo){
        // TODO: Enviar notificación de promoción
        System.out.println("Enviando notificación de promoción");
    }

    // TODO: Generar notificaciones por whatsapp
    public void generarNotificacionesWhatsapp(){
        // TODO: Generar notificaciones por whatsapp
        System.out.println("Generando notificaciones por whatsapp");
    }
}
