package utn.frc.bda.serviciopruebas.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter @NoArgsConstructor @ToString
@Entity @Table(name="interesados")
public class InteresadoEntity {
    @Id
    @Column(name = "id")
    private Integer id;

    @Basic @Column(name = "tipo_documento")
    private String tipoDocumento;

    @Basic @Column(name = "Documento")
    private String Documento;

    @Basic @Column(name = "nombre")
    private String nombre;

    @Basic @Column(name = "apellido")
    private String apellido;

    @Basic @Column(name = "restringido")
    private Integer restringido;

    @Basic @Column(name = "nro_licencia")
    private Integer nroLicencia;

    @Basic @Column(name = "fecha_vencimiento_licencia")
    private LocalDateTime fechaVencimientoLicencia;

    @OneToMany(mappedBy = "interesado")
    private Set<PruebaEntity> pruebas;

    public void addPrueba(PruebaEntity prueba){
        if(pruebas == null){
            pruebas = new HashSet<>();
        }
        pruebas.add(prueba);

    }

    public boolean getLicenciaVencida(){
        // Formato: '2025-01-01 00:00:00'
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime fechaActual = LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);
        return fechaVencimientoLicencia.isBefore(fechaActual);
    }

    public boolean isRestringido(){
        return restringido.equals(1);
    }
}
