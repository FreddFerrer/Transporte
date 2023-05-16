package TransportesYIYO.seguimiento.models.dao;

import TransportesYIYO.seguimiento.models.entities.Pedidos;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPedidosDAO extends CrudRepository<Pedidos, Long> {
}
