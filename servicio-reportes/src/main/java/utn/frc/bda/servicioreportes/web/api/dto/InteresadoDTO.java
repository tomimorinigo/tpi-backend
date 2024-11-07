package utn.frc.bda.servicioreportes.web.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InteresadoDTO {
    private Integer id;
    private String tipoDocumento;
    private String documento;
    private String nombre;
    private String apellido;
    private Integer restringido;
    private Integer nroLicencia;
    private String fechaVencimientoLicencia;
}
