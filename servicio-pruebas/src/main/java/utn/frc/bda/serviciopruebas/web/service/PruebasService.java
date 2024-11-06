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
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PruebasService {

    private PruebasRepository pruebasRepository;

    private InteresadoService interesadoService;
    private EmpleadoService empleadoService;
    private VehiculoService vehiculoService;

    @Autowired
    public PruebasService(PruebasRepository pruebasRepository,
                              InteresadoService interesadoService,
                              EmpleadoService empleadoService,
                              VehiculoService vehiculoService,
                              InteresadosRepository interesadoRepository,
                              VehiculoRepository vehiculoRepository,
                              EmpleadoRepository empleadoRepository){
        this.pruebasRepository = pruebasRepository;
        this.interesadoService = interesadoService;
        this.empleadoService = empleadoService;
        this.vehiculoService = vehiculoService;
    }

    // Endpoint 1 -> crear prueba
    public PruebaDTO crearPrueba(PruebaDTO pruebaDTO){

        System.out.println(pruebaDTO.getInteresado());
        System.out.println(pruebaDTO.getEmpleado());
        System.out.println(pruebaDTO.getVehiculo());

        Integer interesadoId = pruebaDTO.getInteresado().getId();
        Integer empleadoId = pruebaDTO.getEmpleado().getLegajo();
        Integer vehiculoId = pruebaDTO.getVehiculo().getId();
        InteresadoEntity interesado = interesadoService.findById(interesadoId).orElseThrow();
        EmpleadoEntity empleado = empleadoService.findById(empleadoId).orElseThrow();
        VehiculoEntity vehiculo = vehiculoService.findById(vehiculoId).orElseThrow();

        PruebaEntity prueba = pruebaDTO.toEntity();

        // Obtencion de la fecha y hora actual de la prueba
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        prueba.setFechaHoraInicio(formatter.format(fechaHoraActual));

        // Valida que el interesado no tenga la licencia vencida y que no este restringido
        if(interesado.getLicenciaVencida() && interesado.isRestringido()){
            throw new IllegalArgumentException("El interesado ya tiene la licencia vencida o está restringido a probar vehículos");
        }
        prueba.setInteresado(interesado);
        interesado.addPrueba(prueba);
        interesadoService.save(interesado);

        // Validar que el auto no este siendo probado en ese mismo momento
        if (!vehiculoService.validarUsoVehiculo(vehiculo)){
            throw new IllegalArgumentException("El vehiculo ya está siendo probado en ese momento");
        }
        prueba.setVehiculo(vehiculo);

        // Agregamos a la lista de pruebas del vehiculo y lo guardamos
        vehiculo.addPrueba(prueba);
        vehiculoService.save(vehiculo);

        prueba.setEmpleado(empleado);
        empleado.addPrueba(prueba);
        empleadoService.save(empleado);

        return new PruebaDTO(pruebasRepository.save(prueba));
    }


    // Endpoint 2 -> consultar pruebas en curso
    public List<PruebaDTO> consultarPruebasEnCurso(){
        List<PruebaEntity> listaPruebas = pruebasRepository.findAll();

        // Obtener las pruebas que no tienen fecha de finalización
        return listaPruebas.stream().filter(p -> p.getFechaHoraFin() == null)
                    .map(p -> new PruebaDTO(p)).toList();
    }

    // Endpoint 3 -> finalizar prueba con comentarios
    public boolean finalizarPrueba(PruebaDTO prueba){
        try{
            PruebaEntity pruebaFinalizada = pruebasRepository.getById(prueba.getId());
            // Comprobar que la prueba exista
            if (pruebaFinalizada == null){
                throw new IllegalArgumentException("La prueba no existe");
            }
            // Comprobar que la prueba no este finalizada
            if(pruebaFinalizada.getFechaHoraFin() != null){
                throw new IllegalArgumentException("La prueba ya está finalizada");
            }
            pruebaFinalizada.setComentarios(prueba.getComentarios());

            // Obtencion de la fecha actual para la fecha de finalización de la prueba
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime fechaHoraActual = LocalDateTime.now();
            pruebaFinalizada.setFechaHoraFin(String.valueOf(fechaHoraActual));

            // Borramos la prueba de la lista de pruebas del vehiculo
            VehiculoEntity vehiculo = pruebaFinalizada.getVehiculo();
            vehiculo.getPruebas().remove(pruebaFinalizada);
            vehiculoService.save(vehiculo);

            pruebasRepository.save(pruebaFinalizada);
            return true;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al finalizar la prueba");
        }
    }

    public PruebaDTO consultarPruebaActual(Integer idVehiculo){
        //PruebaEntity prueba = pruebasRepository.findById(idVehiculo).orElseThrow();
        VehiculoEntity vehiculo = vehiculoService.findById(idVehiculo).orElseThrow();
        // Comprobamos que la prueba no este finalizada
        PruebaEntity prueba = vehiculo.getPruebas().stream().filter(p -> p.getFechaHoraFin() == null).findFirst().orElseThrow();
        // Retornamos la prueba
        return new PruebaDTO(prueba);
    }

}
