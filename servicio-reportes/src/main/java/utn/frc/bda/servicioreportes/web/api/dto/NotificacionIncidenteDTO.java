package utn.frc.bda.servicioreportes.web.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class NotificacionIncidenteDTO {
    private Integer id;
    private Integer idVehiculo;
    private Integer idInteresado;
    private Integer idPrueba;
    private Integer idEmpleado;
    private String tipoIncidente;
    private String fechaHora;
}
