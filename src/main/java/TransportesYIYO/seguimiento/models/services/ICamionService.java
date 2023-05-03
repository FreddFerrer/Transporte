package TransportesYIYO.seguimiento.models.services;

import TransportesYIYO.seguimiento.models.entities.Camion;
import TransportesYIYO.seguimiento.models.entities.Pedido;

import java.util.List;

public interface ICamionService {
    List<Camion> findAll();
    Camion save(Camion camion);
}
