package utn.frc.bda.servicioposicion.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "Destinatarios_Promociones")
@Getter @Setter @NoArgsConstructor
public class DestinatariosPromocionEntity {

    @Id @Column(name="id")
    private Integer id;

    @Column(name="email")
    private String email;

    @ManyToOne
    @JoinColumn(name="notificacion_id")
    private NotificacionPromocionEntity notificacionPromocion;

    public DestinatariosPromocionEntity(String email, NotificacionPromocionEntity notificacionPromocion){
        this.email = email;
        this.notificacionPromocion = notificacionPromocion;
    }
}
