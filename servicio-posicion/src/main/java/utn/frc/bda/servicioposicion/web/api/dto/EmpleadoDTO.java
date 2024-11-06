package utn.frc.bda.servicioposicion.web.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmpleadoDTO {
    private Integer legajo;
    private String nombre;
    private String apellido;
    private Long telefono;
}
