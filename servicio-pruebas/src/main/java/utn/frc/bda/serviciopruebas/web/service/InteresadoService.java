package utn.frc.bda.serviciopruebas.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frc.bda.serviciopruebas.dal.InteresadosRepository;
import utn.frc.bda.serviciopruebas.entities.InteresadoEntity;

import java.util.Optional;

@Service
public class InteresadoService {

    private InteresadosRepository interesadosRepository;

    @Autowired
    public InteresadoService(InteresadosRepository interesadosRepository){
        this.interesadosRepository = interesadosRepository;
    }

    public Optional<InteresadoEntity> findById(Integer id){
        return interesadosRepository.findById(id);
    }

}
