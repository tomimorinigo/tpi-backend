package utn.frc.bda.serviciopruebas.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frc.bda.serviciopruebas.web.api.dto.PruebaDTO;
import utn.frc.bda.serviciopruebas.web.service.PruebasService;

import java.util.List;

@RestController
@RequestMapping("/pruebas")
public class PruebasAPI {

    private PruebasService pruebasService;

    @Autowired
    public PruebasAPI(PruebasService pruebasService){
        this.pruebasService = pruebasService;
    }

    // Endpoint 1 -> crear prueba
    @PostMapping("/crear")
    public ResponseEntity<PruebaDTO> crearPrueba(@RequestBody PruebaDTO prueba){
        // Creamos la prueba y retornamos el DTO
        return new ResponseEntity<>(pruebasService.crearPrueba(prueba), HttpStatus.CREATED);
    }

    // Endpoint 2 -> consultar pruebas en curso
    @GetMapping("/en-curso")
    public ResponseEntity<List<PruebaDTO>> consultarPruebasEnCurso(){
        // Consultamos la lista de pruebas en curso
        List<PruebaDTO> pruebas = pruebasService.consultarPruebasEnCurso();

        // Si no hay pruebas en curso, retornamos un status 204 (No Content), si no, retornamos la lista de pruebas
        return pruebas.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(pruebas, HttpStatus.OK);
    }

    // Endpoint 3 -> finalizar prueba con comentarios
    @PutMapping("/finalizar")
    public ResponseEntity finalizarPrueba(@RequestBody PruebaDTO prueba){
        return pruebasService.finalizarPrueba(prueba)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/prueba-actual")
    public ResponseEntity<PruebaDTO> consultarPruebaActual(@RequestParam Integer idVehiculo){
        return new ResponseEntity<>(pruebasService.consultarPruebaActual(idVehiculo), HttpStatus.OK);
    }
}
