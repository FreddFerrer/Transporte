package TransportesYIYO.seguimiento.models.services;

import TransportesYIYO.seguimiento.models.entities.Pedidos;

import java.util.List;

public interface IPedidosService {
    List<Pedidos> findAll();

    Pedidos save (Pedidos pedidos);

    void deleteById (Long id);
}
