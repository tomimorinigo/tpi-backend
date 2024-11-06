package utn.frc.bda.servicioposicion.web.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModeloDTO {
    private Integer id;
    private String descripcion;
    private MarcaDTO marca;
}
