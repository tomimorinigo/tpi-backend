package utn.frc.bda.serviciopruebas.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    @GetMapping("/recibir-vehiculo")
    public Boolean recibirVehiculo(@RequestParam Integer idVehiculo){
        return vehiculoService.recibirVehiculo(idVehiculo);
    }

}
