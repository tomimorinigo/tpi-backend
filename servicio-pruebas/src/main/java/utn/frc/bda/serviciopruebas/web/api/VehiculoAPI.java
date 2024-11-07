package utn.frc.bda.serviciopruebas.web.api;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utn.frc.bda.serviciopruebas.web.api.dto.VehiculoDTO;
import utn.frc.bda.serviciopruebas.web.service.VehiculoService;

@RestController
@RequestMapping("/vehiculo")
public class VehiculoAPI {

    private VehiculoService vehiculoService;

    @Autowired
    public VehiculoAPI(VehiculoService vehiculoService){
        this.vehiculoService = vehiculoService;
    }

    // Endpoint -> Recibir un id de vehiculo y evaluar si el vehiculo esta en prueba
    // Ejemplo: http://localhost:8081/vehiculo/recibir-vehiculo?idVehiculo=1
    @GetMapping("/esta-en-prueba")
    public Boolean recibirVehiculo(@RequestParam Integer idVehiculo){
        return vehiculoService.recibirVehiculo(idVehiculo);
    }

    @GetMapping("/obtener-vehiculo")
    public ResponseEntity<VehiculoDTO> obtenerVehiculo(@RequestParam Integer idVehiculo){
        VehiculoDTO vehiculoDTO = vehiculoService.obtenerVehiculo(idVehiculo);
        return new ResponseEntity<>(vehiculoDTO, HttpStatus.OK);
    }

}
