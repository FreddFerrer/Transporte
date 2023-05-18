package TransportesYIYO.seguimiento.models.services;

import TransportesYIYO.seguimiento.models.entities.Camiones;

import java.util.List;
import java.util.Optional;

public interface ICamionService {
    List<Camiones> findAll();
    Camiones save(Camiones camion);

    Optional<Camiones> getCamionById(Long id);

    void delete(Long id);
}
