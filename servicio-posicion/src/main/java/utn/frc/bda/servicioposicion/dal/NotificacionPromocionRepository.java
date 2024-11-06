package utn.frc.bda.servicioposicion.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.frc.bda.servicioposicion.entities.NotificacionPromocionEntity;

public interface NotificacionPromocionRepository extends JpaRepository<NotificacionPromocionEntity, Integer> {
}
