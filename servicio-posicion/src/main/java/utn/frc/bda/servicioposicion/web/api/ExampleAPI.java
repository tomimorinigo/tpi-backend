package utn.frc.bda.servicioposicion.web.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posicion")
public class ExampleAPI {

    @GetMapping("/")
    public String hello() {
        return "Hello World!";
    }

}
