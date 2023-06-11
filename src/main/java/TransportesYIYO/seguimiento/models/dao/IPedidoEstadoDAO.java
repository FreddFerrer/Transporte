package TransportesYIYO.seguimiento.models.dao;

import TransportesYIYO.seguimiento.models.entities.EstadoPedidos;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPedidoEstadoDAO extends CrudRepository<EstadoPedidos, Long> {
}
