package utn.frc.bda.servicioreportes.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import utn.frc.bda.servicioreportes.web.api.dto.*;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class ReporteService {

    @Value("${notificacion.service.url}")
    private String urlNotificacion;
    @Value("${pruebas.service.url}")
    private String urlPruebas;
    @Value("${empleado.service.url}")
    private String urlEmpleado;
    @Value("${vehiculo.service.url}")
    private String urlVehiculo;
    @Value("${posicion.service.url}")
    private String urlPosiciones;

    // REPORTE 1 - Reporte de Incidentes
    public String getReporteIncidentes() {
        RestTemplate restTemplate = new RestTemplate();
        // Consumimos el endpoint y mapeamos el JSON a una lista de NotificacionIncidenteDTO
        ResponseEntity<List<NotificacionIncidenteDTO>> response = restTemplate.exchange(
                urlNotificacion + "/obtener-incidentes",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<NotificacionIncidenteDTO>>() {
                }
        );

        List<NotificacionIncidenteDTO> notificaciones = response.getBody();
        StringBuilder mensaje = new StringBuilder("=== Reporte de Incidente ===\n");

        for (NotificacionIncidenteDTO notificacion : notificaciones) {

            // Filtramos que sea de Fuera de Radio
            if (!notificacion.getTipoIncidente().equals("Fuera de radio")) {
                continue;
            }

            // Consultar api de pruebas
            PruebaDTO prueba = restTemplate.getForObject(urlPruebas + "/prueba?idPrueba=" + notificacion.getIdPrueba(), PruebaDTO.class);
            String mensajePrueba = String.format(
                    "\t== Incidente %d ==\n" +
                            "\t\tTipo de Incidente: %s\n" +
                            "\t\tFecha y Hora: %s\n\n",
                    notificacion.getId(),
                    notificacion.getTipoIncidente(),
                    notificacion.getFechaHora()
            );

            // Información de la prueba asociada
            mensajePrueba += String.format(
                    "\tPrueba: %d \n" +
                            "\t\tFecha y Hora de Inicio: %s\n" +
                            "\t\tFecha y Hora de Fin: %s\n" +
                            "\t\tComentarios: %s\n\n",
                    prueba.getId(),
                    prueba.getFechaHoraInicio(),
                    prueba.getFechaHoraFin(),
                    prueba.getComentarios()
            );

            // Información del interesado
            InteresadoDTO interesado = prueba.getInteresado();
            mensajePrueba += String.format(
                    "\tInteresado\n" +
                            "\t\tNombre: %s %s\n" +
                            "\t\tTipo de Documento: %s\n" +
                            "\t\tDocumento: %s\n" +
                            "\t\tLicencia: %d\n" +
                            "\t\tFecha de Vencimiento de Licencia: %s\n\n",
                    interesado.getNombre(),
                    interesado.getApellido(),
                    interesado.getTipoDocumento(),
                    interesado.getDocumento(),
                    interesado.getNroLicencia(),
                    interesado.getFechaVencimientoLicencia()
            );

            // Información del empleado
            EmpleadoDTO empleado = prueba.getEmpleado();
            mensajePrueba += String.format(
                    "\tEmpleado\n" +
                            "\t\tLegajo: %d\n" +
                            "\t\tNombre: %s %s\n" +
                            "\t\tTeléfono: %d\n\n",
                    empleado.getLegajo(),
                    empleado.getNombre(),
                    empleado.getApellido(),
                    empleado.getTelefono()
            );

            // Información del vehículo
            VehiculoDTO vehiculo = prueba.getVehiculo();
            mensajePrueba += String.format(
                    "\tVehículo \n" +
                            "\t\tPatente: %s\n" +
                            "\t\tMarca: %s\n" +
                            "\t\tModelo: %s\n",
                    vehiculo.getPatente(),
                    vehiculo.getModelo().getMarca().getNombre(),
                    vehiculo.getModelo().getDescripcion()
            );

            mensajePrueba += "\n---------------------------------------- \n";
            mensaje.append(mensajePrueba).append("\n");
        }

        return mensaje.toString();
    }

    // REPORTE 2 - Reporte de Incidentes por Empleado
    public String getReporteIncidentesEmpleado(Integer idEmpleado) {
        RestTemplate restTemplate = new RestTemplate();
        // Consumimos el endpoint y mapeamos el JSON a una lista de NotificacionIncidenteDTO
        ResponseEntity<List<NotificacionIncidenteDTO>> response = restTemplate.exchange(
                urlNotificacion + "/obtener-incidentes",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<NotificacionIncidenteDTO>>() {
                }
        );

        List<NotificacionIncidenteDTO> notificaciones = response.getBody();
        StringBuilder mensaje = new StringBuilder("=== Reporte de Incidente Empleado ===\n");

        EmpleadoDTO empleado = restTemplate.getForObject(urlEmpleado + "/empleado?legajoEmpleado=" + idEmpleado, EmpleadoDTO.class);
        if (empleado != null) {
            String datosEmpleado = String.format(
                    "\tEmpleado\n" +
                            "\t\tLegajo: %d\n" +
                            "\t\tNombre: %s %s\n" +
                            "\t\tTeléfono: %d\n\n",
                    empleado.getLegajo(),
                    empleado.getNombre(),
                    empleado.getApellido(),
                    empleado.getTelefono()
            );
            mensaje.append(datosEmpleado).append("\n");
        } else {
            String datosEmpleado = "No existe empleado buscado";
            mensaje.append(datosEmpleado).append("\n");
        }

        for (NotificacionIncidenteDTO notificacion : notificaciones) {
            // Consultar api de pruebas
            PruebaDTO prueba = restTemplate.getForObject(urlPruebas + "/prueba?idPrueba=" + notificacion.getIdPrueba(), PruebaDTO.class);

            // Filtramos que sea el empleado deseado
            if (prueba.getEmpleado().getLegajo() != idEmpleado) {
                continue;
            }

            String mensajePrueba = String.format(
                    "---------------------------------------- \n\n" +
                            "\t== Incidente %d ==\n" +
                            "\t\tTipo de Incidente: %s\n" +
                            "\t\tFecha y Hora: %s\n\n",
                    notificacion.getId(),
                    notificacion.getTipoIncidente(),
                    notificacion.getFechaHora()
            );

            mensajePrueba += String.format(
                    "\tEmpleado\n" +
                            "\t\tLegajo: %d\n",
                    prueba.getEmpleado().getLegajo()
            );

            // Información de la prueba asociada
            mensajePrueba += String.format(
                    "\tPrueba: %d \n" +
                            "\t\tFecha y Hora de Inicio: %s\n" +
                            "\t\tFecha y Hora de Fin: %s\n" +
                            "\t\tComentarios: %s\n\n",
                    prueba.getId(),
                    prueba.getFechaHoraInicio(),
                    prueba.getFechaHoraFin(),
                    prueba.getComentarios()
            );

            // Información del interesado
            InteresadoDTO interesado = prueba.getInteresado();
            mensajePrueba += String.format(
                    "\tInteresado\n" +
                            "\t\tNombre: %s %s\n" +
                            "\t\tTipo de Documento: %s\n" +
                            "\t\tDocumento: %s\n" +
                            "\t\tLicencia: %d\n" +
                            "\t\tFecha de Vencimiento de Licencia: %s\n\n",
                    interesado.getNombre(),
                    interesado.getApellido(),
                    interesado.getTipoDocumento(),
                    interesado.getDocumento(),
                    interesado.getNroLicencia(),
                    interesado.getFechaVencimientoLicencia()
            );


            // Información del vehículo
            VehiculoDTO vehiculo = prueba.getVehiculo();
            mensajePrueba += String.format(
                    "\tVehículo \n" +
                            "\t\tPatente: %s\n" +
                            "\t\tMarca: %s\n" +
                            "\t\tModelo: %s\n",
                    vehiculo.getPatente(),
                    vehiculo.getModelo().getMarca().getNombre(),
                    vehiculo.getModelo().getDescripcion()
            );

            mensajePrueba += "\n---------------------------------------- \n";
            mensaje.append(mensajePrueba).append("\n");
        }

        return mensaje.toString();
    }

    // REPORTE 4 - Reporte de Pruebas por Vehiculo
    public String getReportePruebasVehiculo(Integer idVehiculo) {
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder mensaje = new StringBuilder("=== Reporte de Pruebas por Vehiculo ===\n");

        VehiculoDTO vehiculo = restTemplate.getForObject(urlVehiculo + "/obtener-vehiculo?idVehiculo=" + idVehiculo, VehiculoDTO.class);
        if (vehiculo != null) {
            String datosVehiculo = String.format(
                    "\tVehículo \n" +
                            "\t\tPatente: %s\n" +
                            "\t\tMarca: %s\n" +
                            "\t\tModelo: %s\n",
                    vehiculo.getPatente(),
                    vehiculo.getModelo().getMarca().getNombre(),
                    vehiculo.getModelo().getDescripcion()
            );
            mensaje.append(datosVehiculo).append("\n");
        } else {
            String datosVehiculo = "No existe vehiculo buscado";
            mensaje.append(datosVehiculo).append("\n");
        }

        // Consumimos el endpoint y mapeamos el JSON a una lista de PruebaDTO
        ResponseEntity<List<PruebaDTO>> response = restTemplate.exchange(
                urlPruebas + "/vehiculo?idVehiculo=" + idVehiculo,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PruebaDTO>>() {
                }
        );

        List<PruebaDTO> pruebas = response.getBody();

        // Ordenamos las pruebas por ID
        pruebas.sort(Comparator.comparing(PruebaDTO::getId));

        for (PruebaDTO prueba : pruebas) {
            String mensajePrueba = String.format(
                    "---------------------------------------- \n\n" +
                            "\t== Prueba %d ==\n" +
                            "\t\tFecha y Hora de Inicio: %s\n" +
                            "\t\tFecha y Hora de Fin: %s\n" +
                            "\t\tComentarios: %s\n\n",
                    prueba.getId(),
                    prueba.getFechaHoraInicio(),
                    prueba.getFechaHoraFin(),
                    prueba.getComentarios()
            );

            // Información del interesado
            InteresadoDTO interesado = prueba.getInteresado();
            mensajePrueba += String.format(
                    "\tInteresado\n" +
                            "\t\tNombre: %s %s\n" +
                            "\t\tTipo de Documento: %s\n" +
                            "\t\tDocumento: %s\n" +
                            "\t\tLicencia: %d\n" +
                            "\t\tFecha de Vencimiento de Licencia: %s\n\n",
                    interesado.getNombre(),
                    interesado.getApellido(),
                    interesado.getTipoDocumento(),
                    interesado.getDocumento(),
                    interesado.getNroLicencia(),
                    interesado.getFechaVencimientoLicencia()
            );

            // Información del empleado
            EmpleadoDTO empleado = prueba.getEmpleado();
            mensajePrueba += String.format(
                    "\tEmpleado\n" +
                            "\t\tLegajo: %d\n" +
                            "\t\tNombre: %s %s\n" +
                            "\t\tTeléfono: %d\n\n",
                    empleado.getLegajo(),
                    empleado.getNombre(),
                    empleado.getApellido(),
                    empleado.getTelefono()
            );
            mensaje.append(mensajePrueba).append("\n");
        }

        return mensaje.toString();
    }

    public String getReporteKilometrosVehiculo(Integer idVehiculo, String desde, String hasta) {
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder mensaje = new StringBuilder("=== Reporte de Kilometros por Vehiculo ===\n");

        VehiculoDTO vehiculo = restTemplate.getForObject(urlVehiculo + "/obtener-vehiculo?idVehiculo=" + idVehiculo, VehiculoDTO.class);

        String datosVehiculo = String.format(
                "\t-- Vehiculo --\n" +
                        "\t\tPatente: %s\n" +
                        "\t\tMarca: %s\n" +
                        "\t\tModelo: %s\n",
                vehiculo.getPatente(),
                vehiculo.getModelo().getMarca().getNombre(),
                vehiculo.getModelo().getDescripcion()
        );
        mensaje.append(datosVehiculo).append("\n");

        mensaje.append(String.format("\t-- En periodo: %s - %s\n", desde, hasta));

        Double kilometros = restTemplate.getForObject(urlPosiciones + "/kilometros-vehiculo?idVehiculo=" + idVehiculo + "&desde=" + desde + "&hasta=" + hasta, Double.class);
        mensaje.append(String.format("\t-- Kilometros recorridos: %.2f km\n", kilometros));

        return mensaje.toString();
    }
}
