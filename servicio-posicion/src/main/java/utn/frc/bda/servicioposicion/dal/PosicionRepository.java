package utn.frc.bda.servicioposicion.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.frc.bda.servicioposicion.entities.PosicionEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface PosicionRepository extends JpaRepository<PosicionEntity, Integer> {
    List<PosicionEntity> findAllByIdVehiculoAndFechaHoraBetween(
            Integer idVehiculo,
            LocalDateTime fechaHora,
            LocalDateTime fechaHasta
    );
}
