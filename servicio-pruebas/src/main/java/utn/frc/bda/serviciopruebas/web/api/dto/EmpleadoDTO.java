package utn.frc.bda.serviciopruebas.web.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import utn.frc.bda.serviciopruebas.entities.EmpleadoEntity;

@Data
@NoArgsConstructor
public class EmpleadoDTO {
    private Integer legajo;
    private String nombre;
    private String apellido;
    private Long telefono;

    public EmpleadoDTO(EmpleadoEntity empleado) {
        this.legajo = empleado.getLegajo();
        this.nombre = empleado.getNombre();
        this.apellido = empleado.getApellido();
        this.telefono = empleado.getTelefono();
    }
}