package utn.frc.bda.servicioposicion.web.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.frc.bda.servicioposicion.entities.DestinatariosPromocionEntity;
import utn.frc.bda.servicioposicion.entities.NotificacionPromocionEntity;

@Data @NoArgsConstructor @AllArgsConstructor
public class DestinatarioDTO {
    private String email;

    public DestinatariosPromocionEntity toEntity(NotificacionPromocionEntity notificacionPromocionEntity) {
        return new DestinatariosPromocionEntity(email, notificacionPromocionEntity);
    }
}
