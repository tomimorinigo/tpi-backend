package utn.frc.bda.serviciopruebas.web.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utn.frc.bda.serviciopruebas.web.api.dto.EmpleadoDTO;

import utn.frc.bda.serviciopruebas.web.service.EmpleadoService;

@RestController
@RequestMapping("/empleados")
public class EmpleadoAPI {

    private EmpleadoService empleadoService;

    @Autowired
    public EmpleadoAPI(EmpleadoService empleadoService){
        this.empleadoService = empleadoService;
    }

    @GetMapping("/empleado")
    public ResponseEntity<EmpleadoDTO> consultarEmpleado(@RequestParam Integer legajoEmpleado){
        return new ResponseEntity<>(empleadoService.consultarEmpleado(legajoEmpleado), HttpStatus.OK);
    }
}
