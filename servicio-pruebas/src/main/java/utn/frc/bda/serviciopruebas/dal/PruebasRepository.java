package utn.frc.bda.serviciopruebas.dal;

import utn.frc.bda.serviciopruebas.entities.PruebaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface PruebasRepository extends JpaRepository<PruebaEntity, Integer> {
    List<PruebaEntity> findAllByVehiculoIdAndFechaHoraInicioBetween(
            Integer vehiculoId,
            LocalDateTime fechaHora,
            LocalDateTime fechaHasta
    );
}
