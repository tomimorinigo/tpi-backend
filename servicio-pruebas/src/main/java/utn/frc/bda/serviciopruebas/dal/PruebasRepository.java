package utn.frc.bda.serviciopruebas.dal;

import utn.frc.bda.serviciopruebas.entities.PruebaEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PruebasRepository extends JpaRepository<PruebaEntity, Integer> {
}
