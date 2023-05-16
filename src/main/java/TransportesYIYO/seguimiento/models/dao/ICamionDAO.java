package TransportesYIYO.seguimiento.models.dao;

import TransportesYIYO.seguimiento.models.entities.Camiones;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICamionDAO extends CrudRepository<Camiones, Long> {
}
