package utn.frc.bda.serviciopruebas.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frc.bda.serviciopruebas.dal.VehiculoRepository;
import utn.frc.bda.serviciopruebas.entities.VehiculoEntity;

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

}
