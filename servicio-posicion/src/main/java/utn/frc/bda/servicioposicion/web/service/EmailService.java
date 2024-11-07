package utn.frc.bda.servicioposicion.web.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;
import utn.frc.bda.servicioposicion.entities.NotificacionIncidenciaEntity;
import utn.frc.bda.servicioposicion.entities.NotificacionPromocionEntity;
import utn.frc.bda.servicioposicion.web.api.dto.PruebaDTO;
import utn.frc.bda.servicioposicion.web.api.dto.VehiculoDTO;

import static utn.frc.bda.servicioposicion.utils.Utils.fechaActualFormatted;

@Service
public class EmailService {

    private final JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    // Metodo para enviar email simple
    public void enviarEmailSimple(String para, String asunto, String contenido) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(para);
            message.setSubject(asunto);
            message.setText(contenido);

            emailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar email", e);
        }
    }

    // Metodo para enviar email con HTML
    public void enviarEmailHtml(String para, String asunto, String contenidoHtml) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(para);
            helper.setSubject(asunto);
            helper.setText(contenidoHtml, true);

            emailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar email HTML", e);
        }
    }

    public String createHtmlMensaje(PruebaDTO prueba, NotificacionIncidenciaEntity notificacionIncidencia, String tipoIncidente) {
        String mensajeHtml = String.format("""
                        <html>
                            <body>
                                <h1>Se ha generado una Incidencia del tipo: %s</h1>
                                <h3>Número de prueba: %s</h3>
                                <p>Vehiculo:</p>
                                  <ul>
                                    <li>Patente: %s</li>
                                    <li>Modelo: %s</li>
                                    <li>Marca: %s</li>
                                  </ul>
                                <p>Interesado:</p>
                                <ul>
                                    <li>Tipo documento: %s</li>
                                    <li>Documento: %s</li>
                                    <li>Nombre: %s</li>
                                    <li>Apellido: %s</li>
                                    <li>Nro. de Licencia: %s</li>
                                </ul>
                                <p>Empleado:</p>
                                <ul>
                                    <li>Nombre: %s</li>
                                    <li>Apellido: %s</li>
                                    <li>Telefono: %s</li>
                                </ul>
                                <br>
                                <p><b>Se ha generado la incidencia con numero</b>: %s</p>
                                <p><b>Fecha y hora de generación</b>: %s</p>
                            </body>
                        </html>
                        """, tipoIncidente, prueba.getId(), prueba.getVehiculo().getPatente(), prueba.getVehiculo().getModelo().getId(),
                prueba.getVehiculo().getModelo().getMarca().getNombre(), prueba.getInteresado().getTipoDocumento(),
                prueba.getInteresado().getDocumento(), prueba.getInteresado().getNombre(), prueba.getInteresado().getApellido(),
                prueba.getInteresado().getNroLicencia(), prueba.getEmpleado().getNombre(), prueba.getEmpleado().getApellido(),
                prueba.getEmpleado().getTelefono(), notificacionIncidencia.getId(), fechaActualFormatted());

        return mensajeHtml;
    }

    public String createHtmlMensajePromocion(NotificacionPromocionEntity notificacionPromocion, VehiculoDTO vehiculoDTO) {
        String mensajeHtml = String.format("""
                        <html>
                            <body>
                                <h1>Oferta imperdible!!</h1>
                                <h3>Con un imperdible descuento del %s%%!</h3>
                                <p>Vehiculo en oferta:</p>
                                  <ul>
                                    <li>Modelo: %s</li>
                                    <li>Marca: %s</li>
                                  </ul>
                                <br>
                                <p><b>La oferta termina el</b>: %s</p>
                            </body>
                        </html>
                        """, notificacionPromocion.getPorcentajeOferta(), vehiculoDTO.getModelo().getDescripcion(),
                vehiculoDTO.getModelo().getMarca().getNombre(), notificacionPromocion.getFechaFin());
        return mensajeHtml;
    }
}