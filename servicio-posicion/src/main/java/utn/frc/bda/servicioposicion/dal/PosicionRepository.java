package utn.frc.bda.servicioposicion.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.frc.bda.servicioposicion.entities.PosicionEntity;

public interface PosicionRepository extends JpaRepository<PosicionEntity, Integer> {
}
