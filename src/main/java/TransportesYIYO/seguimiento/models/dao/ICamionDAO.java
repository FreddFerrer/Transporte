package TransportesYIYO.seguimiento.models.dao;

import TransportesYIYO.seguimiento.models.entities.Camiones;
import org.springframework.data.repository.CrudRepository;

public interface ICamionDAO extends CrudRepository<Camiones, Long> {
}
