package utn.frc.bda.serviciopruebas.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.frc.bda.serviciopruebas.entities.InteresadoEntity;

public interface InteresadosRepository extends JpaRepository<InteresadoEntity, Integer> {
}
