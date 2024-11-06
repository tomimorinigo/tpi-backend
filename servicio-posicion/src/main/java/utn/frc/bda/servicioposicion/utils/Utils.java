package utn.frc.bda.servicioposicion.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static final String TIPO_INCIDENTE_FUERA_DE_RADIO = "Fuera de radio";
    public static final String TIPO_INCIDENTE_ZONA_PELIGROSA = "Zona peligrosa";

    public static String fechaActualFormatted(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        return formatter.format(fechaHoraActual);
    }

}
