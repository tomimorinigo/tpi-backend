package utn.frc.bda.serviciopruebas.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frc.bda.serviciopruebas.dal.EmpleadoRepository;
import utn.frc.bda.serviciopruebas.entities.EmpleadoEntity;
import utn.frc.bda.serviciopruebas.entities.PruebaEntity;
import utn.frc.bda.serviciopruebas.web.api.dto.EmpleadoDTO;
import utn.frc.bda.serviciopruebas.web.api.dto.PruebaDTO;

import java.util.Optional;

@Service
public class EmpleadoService {

    private EmpleadoRepository empleadoRepository;

    @Autowired
    public EmpleadoService(EmpleadoRepository empleadoRepository){
        this.empleadoRepository = empleadoRepository;
    }

    public Optional<EmpleadoEntity> findById(Integer id){
        return empleadoRepository.findById(id);
    }

    public void save(EmpleadoEntity empleado){
        empleadoRepository.save(empleado);
    }

    public EmpleadoDTO consultarEmpleado(Integer legajoEmpleado){
        EmpleadoEntity empleado = empleadoRepository.findById(legajoEmpleado).orElseThrow();
        return new EmpleadoDTO(empleado);
    }
}
