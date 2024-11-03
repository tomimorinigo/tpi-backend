package utn.frc.bda.serviciopruebas.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frc.bda.serviciopruebas.dal.InteresadosRepository;
import utn.frc.bda.serviciopruebas.entities.InteresadoEntity;
import utn.frc.bda.serviciopruebas.entities.PruebaEntity;
import utn.frc.bda.serviciopruebas.entities.VehiculoEntity;

import java.util.Optional;

@Service
public class InteresadoService {

    private VehiculoService vehiculoService;
    private InteresadosRepository interesadosRepository;

    @Autowired
    public InteresadoService(InteresadosRepository interesadosRepository, VehiculoService vehiculoService){
        this.interesadosRepository = interesadosRepository;
        this.vehiculoService = vehiculoService;
    }

    public Optional<InteresadoEntity> findById(Integer id){
        return interesadosRepository.findById(id);
    }

    public void save(InteresadoEntity interesado){
        interesadosRepository.save(interesado);
    }

    public void restringirInteresado(Integer idVehiculo){
        // Buscar interesado asociado al vehiculo
        VehiculoEntity vehiculo = vehiculoService.findById(idVehiculo).orElseThrow();
        // Obtenemos la prueba asociada al vehiculo que no tiene fecha de finalización
        PruebaEntity prueba = vehiculo.getPruebas().stream().filter(p -> p.getFechaHoraFin() == null).findFirst().orElseThrow();

        InteresadoEntity interesado = prueba.getInteresado();
        if (interesado.getRestringido() == 1){
            interesado.setRestringido(0);
        }
        interesado.setRestringido(1);
        save(interesado);
    }

}
