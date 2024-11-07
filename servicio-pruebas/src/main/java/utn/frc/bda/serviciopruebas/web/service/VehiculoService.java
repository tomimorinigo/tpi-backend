package utn.frc.bda.serviciopruebas.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frc.bda.serviciopruebas.dal.VehiculoRepository;
import utn.frc.bda.serviciopruebas.entities.VehiculoEntity;
import utn.frc.bda.serviciopruebas.web.api.dto.VehiculoDTO;

import java.util.Optional;

@Service
public class VehiculoService {

    private VehiculoRepository vehiculoRepository;

    @Autowired
    public VehiculoService(VehiculoRepository vehiculoRepository){
        this.vehiculoRepository = vehiculoRepository;
    }

    public Optional<VehiculoEntity> findById(Integer id){
        return vehiculoRepository.findById(id);
    }

    public void save(VehiculoEntity vehiculo){
        vehiculoRepository.save(vehiculo);
    }

    public Boolean validarUsoVehiculo(VehiculoEntity vehiculo){
        // Comprobar que el vehiculo no este siendo probado en ese mismo momento, verificando que ninguna prueba utilice
        // el mismo vehiculo y no tenga fecha de finalización
        // Retorna true si todos los pruebas están finalizadas, es decir, no tiene ninguna prueba en curso
        return vehiculo.getPruebas().stream().allMatch(p -> p.getFechaHoraFin() != null);
    }

    public boolean recibirVehiculo(Integer idVehiculo){
        Optional<VehiculoEntity> vehiculo = findById(idVehiculo);
        // Retornamos true si el vehiculo esta siendo probado en ese momento
        return !validarUsoVehiculo(vehiculo.orElseThrow());
    }

    public VehiculoDTO obtenerVehiculo(Integer idVehiculo){
        Optional<VehiculoEntity> vehiculo = findById(idVehiculo);
        return new VehiculoDTO(vehiculo.orElseThrow());
    }

}
