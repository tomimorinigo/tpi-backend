package utn.frc.bda.servicioposicion.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.frc.bda.servicioposicion.entities.NotificacionIncidenciaEntity;

public interface NotificacionIncidenciaRepository extends JpaRepository<NotificacionIncidenciaEntity, Integer> {
}
