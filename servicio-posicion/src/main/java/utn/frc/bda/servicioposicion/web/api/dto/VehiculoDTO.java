package utn.frc.bda.servicioposicion.web.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VehiculoDTO {
    private Integer id;
    private String patente;
    private ModeloDTO modelo;
}
