package utn.frc.bda.serviciopruebas.web.api.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import utn.frc.bda.serviciopruebas.entities.ModeloEntity;

@Data
@NoArgsConstructor
public class ModeloDTO {
    private Integer id;
    private String descripcion;
    private MarcaDTO marca;

    public ModeloDTO(ModeloEntity modelo) {
        this.id = modelo.getId();
        this.descripcion = modelo.getDescripcion();
        this.marca = new MarcaDTO(modelo.getMarca());
    }
}