package TransportesYIYO.seguimiento.models.dao;

import TransportesYIYO.seguimiento.models.entities.Camion;
import org.springframework.data.repository.CrudRepository;

public interface ICamionDAO extends CrudRepository<Camion, Long> {
}
