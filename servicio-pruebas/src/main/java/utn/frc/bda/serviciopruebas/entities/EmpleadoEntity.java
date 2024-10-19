package utn.frc.bda.serviciopruebas.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name="empleados")
public class EmpleadoEntity {
    @Id
    @Column(name="legajo")
    private Integer legajo;

    @Basic
    @Column(name = "nombre")
    private String nombre;

    @Basic
    @Column(name = "apellido")
    private String apellido;

    @Basic @Column(name = "telefono")
    private Integer telefono;

    @OneToMany(mappedBy = "empleado")
    private Set<PruebaEntity> pruebas;

    public void addPrueba(PruebaEntity prueba) {
        if (pruebas == null) {
            pruebas = new HashSet<>();
        }
        pruebas.add(prueba);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmpleadoEntity that = (EmpleadoEntity) o;
        return Objects.equals(legajo, that.legajo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(legajo);
    }
}
