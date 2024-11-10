package utn.frc.bda.serviciopruebas.web.api.dto;


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
