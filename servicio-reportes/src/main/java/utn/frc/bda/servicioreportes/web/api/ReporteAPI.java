package utn.frc.bda.servicioreportes.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frc.bda.servicioreportes.web.service.ReporteService;

@RestController
public class ReporteAPI {

    private ReporteService reporteService;

    @Autowired
    public ReporteAPI(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    // Reporte 1
    @GetMapping("/incidentes")
    public ResponseEntity<String> reporteIncidentes(){
        return new ResponseEntity<>(reporteService.getReporteIncidentes(), HttpStatus.OK);
    }

    // Reporte 2
    @GetMapping("/incidentes-empleado")
    public ResponseEntity<String> reporteIncidentesEmpleado(@RequestParam Integer legajoEmpleado){
        return new ResponseEntity<>(reporteService.getReporteIncidentesEmpleado(legajoEmpleado), HttpStatus.OK);
    }

    // Reporte 3
    @GetMapping("/kilometros-vehiculo")
    public ResponseEntity<String> reporteKilometrosVehiculo(@RequestParam Integer idVehiculo, @RequestParam String desde, @RequestParam String hasta){
        return new ResponseEntity<>(reporteService.getReporteKilometrosVehiculo(idVehiculo, desde, hasta), HttpStatus.OK);
    }

    // Reporte 4
    @GetMapping("/pruebas-vehiculo")
    public ResponseEntity<String> reportePruebasPorVehiculo(@RequestParam Integer idVehiculo){
        return new ResponseEntity<>(reporteService.getReportePruebasVehiculo(idVehiculo), HttpStatus.OK);
    }
}
