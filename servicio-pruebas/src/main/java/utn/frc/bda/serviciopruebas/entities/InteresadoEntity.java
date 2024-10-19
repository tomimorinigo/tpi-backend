package utn.frc.bda.serviciopruebas.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name="vehiculos")
public class InteresadoEntity {
    @Id
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "tipo_documento")
    private String tipoDocumento;

    @Basic
    @Column(name = "Documento")
    private String Documento;

    @Basic
    @Column(name = "nombre")
    private String nombre;

    @Basic
    @Column(name = "apellido")
    private String apellido;

    @Basic
    @Column(name = "restringido")
    private Integer restringido;

    @Basic
    @Column(name = "nro_licencia")
    private Integer nroLicencia;

    @Basic
    @Column(name = "fecha_vencimiento_licencia")
    private String fechaVencimientoLicencia;

    @OneToMany(mappedBy = "interesado")
    private Set<PruebaEntity> pruebas;

    public void addPrueba(PruebaEntity prueba){
        if(pruebas == null){
            pruebas = new HashSet<>();
        }
        pruebas.add(prueba);

    }

    public boolean getLicenciaVencida(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaRegistrada = LocalDate.parse(fechaVencimientoLicencia, formatter);
        LocalDate fechaActual = LocalDate.now();
        if (fechaRegistrada.isBefore(fechaActual)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isRestringido(){
        if(restringido == 0){
            return false;
        }else{
            return true;
        }
    }
}
