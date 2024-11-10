package utn.frc.bda.servicioreportes.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frc.bda.servicioreportes.web.service.ReporteService;

@RestController
@RequestMapping("/reportes")
public class ReporteAPI {

    private ReporteService reporteService;

    @Autowired
    public ReporteAPI(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/incidentes")
    public ResponseEntity<String> reporteIncidentes(){
        return new ResponseEntity<>(reporteService.getReporteIncidentes(), HttpStatus.OK);
    }

    @GetMapping("/incidentes-empleado")
    public ResponseEntity<String> reporteIncidentesEmpleado(@RequestParam Integer legajoEmpleado){
        return new ResponseEntity<>(reporteService.getReporteIncidentesEmpleado(legajoEmpleado), HttpStatus.OK);
    }

    @GetMapping("/pruebas-vehiculo")
    public ResponseEntity<String> reportePruebasPorVehiculo(@RequestParam Integer idVehiculo){
        return new ResponseEntity<>(reporteService.getReportePruebasVehiculo(idVehiculo), HttpStatus.OK);
    }
}
