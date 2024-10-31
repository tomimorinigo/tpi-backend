package utn.frc.bda.serviciopruebas.web.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import utn.frc.bda.serviciopruebas.entities.VehiculoEntity;

@Data
@NoArgsConstructor
public class VehiculoDTO {
    private Integer id;
    private String patente;
    private ModeloDTO modelo;

    public VehiculoDTO(VehiculoEntity vehiculo) {
        this.id = vehiculo.getId();
        this.patente = vehiculo.getPatente();
        this.modelo = new ModeloDTO(vehiculo.getModelo());
    }
}

