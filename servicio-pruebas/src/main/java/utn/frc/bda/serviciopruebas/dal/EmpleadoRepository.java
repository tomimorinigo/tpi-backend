package utn.frc.bda.serviciopruebas.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.frc.bda.serviciopruebas.entities.EmpleadoEntity;

public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, Integer> {
}
