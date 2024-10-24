package utn.frc.bda.serviciopruebas.web.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import utn.frc.bda.serviciopruebas.entities.EmpleadoEntity;
import utn.frc.bda.serviciopruebas.entities.InteresadoEntity;
import utn.frc.bda.serviciopruebas.entities.PruebaEntity;
import utn.frc.bda.serviciopruebas.entities.VehiculoEntity;

@Data @NoArgsConstructor
public class PruebaDTO {
    private Integer id;
    private String fechaHoraInicio;
    private String fechaHoraFin;
    private String comentarios;
    private Integer interesadoId;
    private Integer empleadoId;
    private Integer vehiculoId;


    public PruebaDTO(PruebaEntity prueba){
        this.id = prueba.getId();
        this.fechaHoraInicio = prueba.getFechaHoraInicio();
        this.fechaHoraFin = prueba.getFechaHoraFin();
        this.comentarios = prueba.getComentarios();
        this.interesadoId = prueba.getInteresado().getId();
        this.empleadoId = prueba.getEmpleado().getLegajo();
        this.vehiculoId = prueba.getVehiculo().getId();
    }

    public PruebaEntity toEntity(){
        return new PruebaEntity(id, fechaHoraInicio, fechaHoraFin, comentarios);
    }
}
