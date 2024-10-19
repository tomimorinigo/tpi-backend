package utn.frc.bda.serviciopruebas.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.frc.bda.serviciopruebas.entities.VehiculoEntity;

public interface VehiculoRepository extends JpaRepository<VehiculoEntity, Integer> {
}
