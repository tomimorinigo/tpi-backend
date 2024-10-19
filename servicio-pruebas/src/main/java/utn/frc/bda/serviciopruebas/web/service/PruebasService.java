package utn.frc.bda.serviciopruebas.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frc.bda.serviciopruebas.dal.InteresadosRepository;
import utn.frc.bda.serviciopruebas.dal.PruebasRepository;
import utn.frc.bda.serviciopruebas.entities.EmpleadoEntity;
import utn.frc.bda.serviciopruebas.entities.InteresadoEntity;
import utn.frc.bda.serviciopruebas.entities.PruebaEntity;
import utn.frc.bda.serviciopruebas.entities.VehiculoEntity;
import utn.frc.bda.serviciopruebas.web.api.dto.PruebaDTO;

@Service
public class PruebasService {

    private PruebasRepository pruebasRepository;
    private InteresadosRepository interesadoRepository;
    private VehiculoRepository vehiculoRepository;

    @Autowired
    public PruebasService(PruebasRepository pruebasRepository){
        this.pruebasRepository = pruebasRepository;
    }

    // Endpoint 1 -> crear prueba
    public PruebaDTO crearPrueba(PruebaDTO pruebaDTO){
        PruebaEntity prueba = pruebaDTO.toEntity();

        // Validar que el interesado no tenga la licencia vencida
        InteresadoEntity interesado = interesadoRepository.findById(prueba.getInteresado().getId()).orElseThrow();

        // TODO: Crear funciones para validar que el interesado no tenga la licencia vencida y que no este restringido
        if(interesado.getLicenciaVencida() && interesado.isRestringido()){
            throw new IllegalArgumentException("El interesado ya tiene la licencia vencida o está restringido a probar vehículos");
        }

        prueba.setInteresado(interesado);

        // TODO: Validar que el auto no este siendo probado en ese mismo momento
        // TODO: Crear repositories para los vehiculos y empleados
        VehiculoEntity vehiculo = vehiculoRepository.findById(prueba.getVehiculo().getId()).orElseThrow();

        if (!validarUsoVehiculo(vehiculo)){
            throw new IllegalArgumentException("El vehiculo ya está siendo probado en ese momento");
        }
        prueba.setVehiculo(vehiculo);

        // Agregamos a la lista de pruebas del vehiculo y lo guardamos
        vehiculo.addPrueba(prueba);
        vehiculoRepository.save(vehiculo);

        EmpleadoEntity empleado = empleadoRepository.findById(prueba.getEmpleado().getId()).orElseThrow();
        prueba.setEmpleado(empleado);

        return new PruebaDTO(pruebasRepository.save(prueba));
    }

    private Boolean validarUsoVehiculo(VehiculoEntity vehiculo){
        // Comprobar que el vehiculo no este siendo probado en ese mismo momento, verificando que ninguna prueba utilice
        // el mismo vehiculo y no tenga fecha de finalización
        return vehiculo.getPruebas().stream().noneMatch(p -> p.getFechaHoraFin() != null);
    }

    // Endpoint 2 -> consultar pruebas en curso
    public Iterable<PruebaEntity> consultarPruebasEnCurso(){
        return pruebasRepository.findAll();
    }

    // Endpoint 3 -> finalizar prueba con comentarios
    public void finalizarPrueba(PruebaEntity prueba, String comentarios){
        prueba.setComentarios(comentarios);
        pruebasRepository.save(prueba);
    }

}
