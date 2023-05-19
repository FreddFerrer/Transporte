package TransportesYIYO.seguimiento.models.services;

import TransportesYIYO.seguimiento.models.entities.Pedidos;

import java.util.List;
import java.util.Optional;

public interface IPedidosService {
    List<Pedidos> findAll();

    Pedidos save (Pedidos pedidos);

    Optional<Pedidos> getPedidosById(Long id);

    Integer obtenerUltimoNumeroPedido();

    void deleteById (Long id);
}
