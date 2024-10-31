package utn.frc.bda.serviciopruebas.web.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import utn.frc.bda.serviciopruebas.entities.PruebaEntity;

@Data @NoArgsConstructor
public class PruebaDTO {
    private Integer id;
    private String fechaHoraInicio;
    private String fechaHoraFin;
    private String comentarios;
    private InteresadoDTO interesado;
    private EmpleadoDTO empleado;
    private VehiculoDTO vehiculo;

    public PruebaDTO(PruebaEntity prueba) {
        this.id = prueba.getId();
        this.fechaHoraInicio = prueba.getFechaHoraInicio();
        this.fechaHoraFin = prueba.getFechaHoraFin();
        this.comentarios = prueba.getComentarios();
        this.interesado = new InteresadoDTO(prueba.getInteresado());
        this.empleado = new EmpleadoDTO(prueba.getEmpleado());
        this.vehiculo = new VehiculoDTO(prueba.getVehiculo());
    }

    public PruebaEntity toEntity(){
        return new PruebaEntity();
    }
}
