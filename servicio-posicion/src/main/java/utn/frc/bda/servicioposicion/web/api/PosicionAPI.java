package utn.frc.bda.servicioposicion.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utn.frc.bda.servicioposicion.web.api.dto.PosicionDTO;
import utn.frc.bda.servicioposicion.web.service.PosicionService;

@RestController
@RequestMapping("/posicion")
public class PosicionAPI {

    private PosicionService posicionService;

    @Autowired
    public PosicionAPI(PosicionService posicionService){
        this.posicionService = posicionService;
    }

    // Endpoint 1 -> recibir posicion y evaluar si esta dentro de zonas peligrosas o fuera de radio
    @GetMapping("/recibir-posicion")
    public ResponseEntity<String> recibirPosicion(@RequestBody PosicionDTO posicion){
        posicionService.recibirPosicion(posicion);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
