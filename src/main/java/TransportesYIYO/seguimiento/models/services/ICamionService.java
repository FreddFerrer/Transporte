package TransportesYIYO.seguimiento.models.services;

import TransportesYIYO.seguimiento.models.entities.Camiones;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ICamionService {
    List<Camiones> findAll();
    Camiones save(Camiones camion);

    void delete(Long id);
}
