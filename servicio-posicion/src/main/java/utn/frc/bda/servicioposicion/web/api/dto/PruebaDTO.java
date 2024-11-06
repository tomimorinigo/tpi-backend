package utn.frc.bda.servicioposicion.web.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PruebaDTO {
    private Integer id;
    private String fechaHoraInicio;
    private String fechaHoraFin;
    private String comentarios;
    private InteresadoDTO interesado;
    private EmpleadoDTO empleado;
    private VehiculoDTO vehiculo;
}
