package utn.frc.bda.servicioreportes.web.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModeloDTO {
    private Integer id;
    private String descripcion;
    private MarcaDTO marca;
}
