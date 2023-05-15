package TransportesYIYO.seguimiento.models.dao;

import TransportesYIYO.seguimiento.models.entities.Pedidos;
import org.springframework.data.repository.CrudRepository;

public interface IPedidosDAO extends CrudRepository<Pedidos, Long> {
}
