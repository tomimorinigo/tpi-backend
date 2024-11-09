package utn.frc.bda.servicioreportes.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import utn.frc.bda.servicioreportes.web.api.dto.*;

import java.util.List;

@Service
public class ReporteService {

    //@Value("notificacion.service.url")
    private String urlNotificacion = "http://localhost:8082/api/v1/notificacion";
    private String urlPruebas = "http://localhost:8081/api/v1/pruebas";
    private String urlEmpleado = "http://localhost:8081/api/v1/empleados";

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

        EmpleadoDTO empleado = restTemplate.getForObject(urlEmpleado + "/empleado?idEmpleado=" + idEmpleado, EmpleadoDTO.class);
        if(empleado != null) {
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
        }else{
            String datosEmpleado = "no existe empleado buscado";
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

}
