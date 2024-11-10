package utn.frc.bda.servicioreportes.web.api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionPruebasVehiculoDTO {
    private Integer id;
    private Integer idVehiculo;
    private Integer idPrueba;
}
