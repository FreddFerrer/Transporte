package TransportesYIYO.seguimiento.models.services;

import TransportesYIYO.seguimiento.models.entities.Clientes;

import java.util.List;
import java.util.Optional;

public interface IClientesService {
    List<Clientes> findAll();

    Clientes save (Clientes clientes);

    Optional<Clientes> getClienteById (Long id);

    void deleteById (Long clientes);
}
