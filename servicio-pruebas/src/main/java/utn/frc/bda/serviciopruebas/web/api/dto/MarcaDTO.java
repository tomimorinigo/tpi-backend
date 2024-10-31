package utn.frc.bda.serviciopruebas.web.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import utn.frc.bda.serviciopruebas.entities.MarcaEntity;

@Data
@NoArgsConstructor
public class MarcaDTO {

    private Integer id;
    private String nombre;

    public MarcaDTO(MarcaEntity marca) {
        this.id = marca.getId();
        this.nombre = marca.getNombre();
    }

}
