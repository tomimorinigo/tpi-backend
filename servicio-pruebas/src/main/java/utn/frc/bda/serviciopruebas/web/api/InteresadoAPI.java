package utn.frc.bda.serviciopruebas.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utn.frc.bda.serviciopruebas.web.service.InteresadoService;

@RestController
public class InteresadoAPI {

    private InteresadoService interesadoService;

    @Autowired
    public InteresadoAPI(InteresadoService interesadoService){
        this.interesadoService = interesadoService;
    }

    // Endpoint -> Recibir un id del interesado y asignarlo como restringido
    // Ejemplo: http://localhost:8081/interesado/restringir-interesado?idVehiculo=1
    @PutMapping("/internal/interesado/restringir-interesado")
    public void restringirInteresado(@RequestParam Integer idVehiculo){
        interesadoService.restringirInteresado(idVehiculo);
    }

}
