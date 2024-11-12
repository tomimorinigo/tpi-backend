package utn.frc.bda.servicioposicion.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frc.bda.servicioposicion.dal.PosicionRepository;
import utn.frc.bda.servicioposicion.entities.PosicionEntity;
import utn.frc.bda.servicioposicion.mapa.Coordenadas;
import utn.frc.bda.servicioposicion.web.api.dto.PosicionDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static utn.frc.bda.servicioposicion.utils.Utils.*;

@Service
public class PosicionService {

    private PosicionRepository posicionRepository;
    private AgenciaService agenciaService;
    private VehiculoService vehiculoService;
    private NotificacionService notificacionService;
    private InteresadoService interesadoService;

    @Autowired
    public PosicionService(PosicionRepository posicionRepository,
                           AgenciaService agenciaService,
                           VehiculoService vehiculoService,
                           NotificacionService notificacionService,
                           InteresadoService interesadoService){
        this.posicionRepository = posicionRepository;
        this.agenciaService = agenciaService;
        this.vehiculoService = vehiculoService;
        this.notificacionService = notificacionService;
        this.interesadoService = interesadoService;
    }

    // Endpoint 4 -> recibir posicion y evaluar si esta dentro de zonas peligrosas o fuera de radio
    public void recibirPosicion(PosicionDTO posicionDTO) {
        // Evaluar si el vehiculo se encuentra en una prueba
        if (vehiculoService.estaEnPruebaVehiculo(posicionDTO.getIdVehiculo())) {

            // Persistimos la posicion en la base de datos
            PosicionEntity posicion = posicionDTO.toEntity();
            posicion.setFechaHora(fechaActualFormatted());
            posicionRepository.save(posicion);

            // Si el vehiculo se encuentra en una prueba, evaluar si está dentro de los límites establecidos
            if (agenciaService.validarPosicionEnRadioAgencia(posicionDTO)) {
                // Si está dentro de los límites establecidos, debemos comprobar las zonas peligrosas
                if (agenciaService.validarPosicionEnZonasPeligrosas(posicionDTO)) {
                    // Generar notificación de incidencia de zona peligrosa
                    notificacionService.enviarNotificacionIncidencia(posicionDTO.getIdVehiculo(), TIPO_INCIDENTE_ZONA_PELIGROSA);
                    // Cambiarle al interesado su atributo de restringido
                    interesadoService.restringirInteresado(posicionDTO.getIdVehiculo());
                    System.out.println("El vehiculo esta en zona peligrosa");
                }

                System.out.println("El vehiculo esta dentro de radio y fuera de zonas peligrosas");
            } else {
                // Generar notificación de incidencia de fuera de radio
                notificacionService.enviarNotificacionIncidencia(posicionDTO.getIdVehiculo(), TIPO_INCIDENTE_FUERA_DE_RADIO);
                // Cambiarle al interesado su atributo de restringido
                interesadoService.restringirInteresado(posicionDTO.getIdVehiculo());
                System.out.println("El vehiculo esta fuera de radio");
            }
        } else {
           System.out.println("El vehiculo no se encuentra en una prueba");
        }
    }

    public Double calcularKilometrosVehiculo(Integer idVehiculo, String desde, String hasta) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime fechaDesde = LocalDateTime.parse(desde, formatter);
        LocalDateTime fechaHasta = LocalDateTime.parse(hasta, formatter);
        Coordenadas coordenadasAgencia = agenciaService.getCoordenadasAgencia();


        double kilometros = 0;
        for (PosicionEntity posicion : posicionRepository.findAllByIdVehiculoAndFechaHoraBetween(idVehiculo, fechaDesde, fechaHasta)) {
            // Calcular la distancia euclídea entre la posición del vehículo y la agencia
            double distancia = agenciaService.calcularDistancia(coordenadasAgencia.getLat(), coordenadasAgencia.getLon(), posicion.getLatitud(), posicion.getLongitud());
            kilometros += distancia;
        }

        return kilometros;
    }
}
