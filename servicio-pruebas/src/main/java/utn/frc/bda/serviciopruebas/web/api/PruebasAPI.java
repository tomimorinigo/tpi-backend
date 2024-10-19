package utn.frc.bda.serviciopruebas.web.api;
;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frc.bda.serviciopruebas.entities.PruebaEntity;
import utn.frc.bda.serviciopruebas.web.api.dto.PruebaDTO;
import utn.frc.bda.serviciopruebas.web.service.PruebasService;

@RestController
@RequestMapping("/api/v1/pruebas")
public class PruebasAPI {

    private PruebasService pruebasService;

    @Autowired
    public PruebasAPI(PruebasService pruebasService){
        this.pruebasService = pruebasService;
    }

    // Endpoint 1 -> crear prueba retornando una response entity con el DTO
    @PostMapping("/crear")
    public ResponseEntity<PruebaDTO> crearPrueba(@RequestBody PruebaDTO prueba){
        return new ResponseEntity<>(pruebasService.crearPrueba(prueba), HttpStatus.CREATED);
    }

    // Endpoint 2 -> consultar pruebas en curso
    @GetMapping("/consultar")
    public Iterable<PruebaEntity> consultarPruebasEnCurso(){
        return pruebasService.consultarPruebasEnCurso();
    }

    // Endpoint 3 -> finalizar prueba con comentarios
    @PutMapping("/finalizar")
    public void finalizarPrueba(@RequestBody PruebaEntity prueba, @RequestParam String comentarios){
        pruebasService.finalizarPrueba(prueba, comentarios);
    }
}
