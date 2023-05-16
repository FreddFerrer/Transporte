package TransportesYIYO.seguimiento.models.dao;

import TransportesYIYO.seguimiento.models.entities.Clientes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientesDAO extends CrudRepository<Clientes, Long> {
}
