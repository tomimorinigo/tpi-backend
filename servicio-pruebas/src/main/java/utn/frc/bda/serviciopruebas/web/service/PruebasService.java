package utn.frc.bda.serviciopruebas.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frc.bda.serviciopruebas.dal.EmpleadoRepository;
import utn.frc.bda.serviciopruebas.dal.InteresadosRepository;
import utn.frc.bda.serviciopruebas.dal.PruebasRepository;
import utn.frc.bda.serviciopruebas.dal.VehiculoRepository;
import utn.frc.bda.serviciopruebas.entities.EmpleadoEntity;
import utn.frc.bda.serviciopruebas.entities.InteresadoEntity;
import utn.frc.bda.serviciopruebas.entities.PruebaEntity;
import utn.frc.bda.serviciopruebas.entities.VehiculoEntity;
import utn.frc.bda.serviciopruebas.web.api.dto.PruebaDTO;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PruebasService {

    private PruebasRepository pruebasRepository;
    private InteresadosRepository interesadoRepository;
    private VehiculoRepository vehiculoRepository;
    private EmpleadoRepository empleadoRepository;

    @Autowired
    public PruebasService(PruebasRepository pruebasRepository){
        this.pruebasRepository = pruebasRepository;
    }

    // Endpoint 1 -> crear prueba
    public PruebaDTO crearPrueba(PruebaDTO pruebaDTO){
        PruebaEntity prueba = pruebaDTO.toEntity();

        // Validar que el interesado no tenga la licencia vencida
        InteresadoEntity interesado = interesadoRepository.findById(prueba.getInteresado().getId()).orElseThrow();

        // valida que el interesado no tenga la licencia vencida y que no este restringido
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

        EmpleadoEntity empleado = empleadoRepository.findById(prueba.getEmpleado().getLegajo()).orElseThrow();
        prueba.setEmpleado(empleado);

        return new PruebaDTO(pruebasRepository.save(prueba));
    }

    private Boolean validarUsoVehiculo(VehiculoEntity vehiculo){
        // Comprobar que el vehiculo no este siendo probado en ese mismo momento, verificando que ninguna prueba utilice
        // el mismo vehiculo y no tenga fecha de finalización
        return vehiculo.getPruebas().stream().noneMatch(p -> p.getFechaHoraFin() != null);
    }

    // Endpoint 2 -> consultar pruebas en curso
    public List<PruebaDTO> consultarPruebasEnCurso(){
        List<PruebaEntity> listaPruebas = pruebasRepository.findAll();

        // Obtener las pruebas que no tienen fecha de finalización
        return listaPruebas.stream().filter(p -> p.getFechaHoraFin() == null)
                    .map(p -> new PruebaDTO(p)).toList();
    }

    // Endpoint 3 -> finalizar prueba con comentarios

    public boolean finalizarPrueba(PruebaDTO prueba, String comentarios){
        PruebaEntity pruebaFinalizada = prueba.toEntity();

        // Comprobar que la prueba exista
        if (pruebasRepository.findById(pruebaFinalizada.getId()).isEmpty()){
            throw new IllegalArgumentException("La prueba no existe");
        }

        // Comprobar que la prueba no este finalizada
        if(pruebaFinalizada.getFechaHoraFin() != null){
            throw new IllegalArgumentException("La prueba ya está finalizada");
        }

        pruebaFinalizada.setComentarios(comentarios);
        pruebaFinalizada.setFechaHoraFin(String.valueOf(LocalDateTime.now()));

        pruebasRepository.save(pruebaFinalizada);
        return true;
    }

}
