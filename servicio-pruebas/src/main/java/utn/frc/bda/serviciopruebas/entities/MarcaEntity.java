package utn.frc.bda.serviciopruebas.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity @Table(name="marca")
@Getter @Setter @ToString @NoArgsConstructor
public class MarcaEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Long id;

    @Column(name="nombre", nullable=false)
    private String nombre;

    @OneToMany(mappedBy="marca")
    private Set<ModeloEntity> modelos;

}
