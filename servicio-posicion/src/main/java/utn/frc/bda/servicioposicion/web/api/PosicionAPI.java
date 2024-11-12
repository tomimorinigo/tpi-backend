package utn.frc.bda.servicioposicion.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frc.bda.servicioposicion.web.api.dto.PosicionDTO;
import utn.frc.bda.servicioposicion.web.service.PosicionService;


@RestController
public class PosicionAPI {

    private PosicionService posicionService;

    @Autowired
    public PosicionAPI(PosicionService posicionService){
        this.posicionService = posicionService;
    }

    // Endpoint 4 -> recibir posicion y evaluar si esta dentro de zonas peligrosas o fuera de radio
    @PostMapping("/posicion/recibir-posicion")
    public ResponseEntity<String> recibirPosicion(@RequestBody PosicionDTO posicion){
        posicionService.recibirPosicion(posicion);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/internal/posicion/kilometros-vehiculo")
    public ResponseEntity<Double> calcularKilometrosVehiculo(@RequestParam Integer idVehiculo, @RequestParam String desde, @RequestParam String hasta) {
        return new ResponseEntity<>(posicionService.calcularKilometrosVehiculo(idVehiculo, desde, hasta), HttpStatus.OK);
    }

}
