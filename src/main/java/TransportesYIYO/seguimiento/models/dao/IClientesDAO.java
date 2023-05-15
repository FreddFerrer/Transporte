package TransportesYIYO.seguimiento.models.dao;

import TransportesYIYO.seguimiento.models.entities.Clientes;
import org.springframework.data.repository.CrudRepository;

public interface IClientesDAO extends CrudRepository<Clientes, Long> {
}
