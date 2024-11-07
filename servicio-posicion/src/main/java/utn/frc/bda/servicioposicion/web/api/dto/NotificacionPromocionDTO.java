package utn.frc.bda.servicioposicion.web.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import static utn.frc.bda.servicioposicion.utils.Utils.fechaActualFormatted;

import lombok.ToString;
import utn.frc.bda.servicioposicion.entities.DestinatariosPromocionEntity;
import utn.frc.bda.servicioposicion.entities.NotificacionPromocionEntity;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class NotificacionPromocionDTO {

    private Integer idVehiculo;
    private Double porcentajeOferta;
    private String fechaFin;
    private List<DestinatarioDTO> destinatarios;

    public NotificacionPromocionEntity toEntity() {
        NotificacionPromocionEntity notificacion = new NotificacionPromocionEntity(idVehiculo, porcentajeOferta, fechaActualFormatted(), fechaFin);

        destinatarios.forEach(destinatario -> {
            DestinatariosPromocionEntity destinatarioPromocion = destinatario.toEntity(notificacion);
            notificacion.getDestinatariosPromocion().add(destinatarioPromocion);
        });

        return notificacion;
    }
}
