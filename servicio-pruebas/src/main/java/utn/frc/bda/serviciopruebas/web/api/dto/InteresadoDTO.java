package utn.frc.bda.serviciopruebas.web.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import utn.frc.bda.serviciopruebas.entities.InteresadoEntity;

@Data @NoArgsConstructor
public class InteresadoDTO {
    private Integer id;
    private String tipoDocumento;
    private String documento;
    private String nombre;
    private String apellido;
    private Integer restringido;
    private Integer nroLicencia;
    private String fechaVencimientoLicencia;

    public InteresadoDTO(InteresadoEntity interesado) {
        this.id = interesado.getId();
        this.tipoDocumento = interesado.getTipoDocumento();
        this.documento = interesado.getDocumento();
        this.nombre = interesado.getNombre();
        this.apellido = interesado.getApellido();
        this.restringido = interesado.getRestringido();
        this.nroLicencia = interesado.getNroLicencia();
        this.fechaVencimientoLicencia = interesado.getFechaVencimientoLicencia();
    }
}