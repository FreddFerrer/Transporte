package TransportesYIYO.seguimiento.models.services;

import TransportesYIYO.seguimiento.models.entities.Clientes;

import java.util.List;

public interface IClientesService {
    List<Clientes> findAll();

    Clientes save (Clientes clientes);

    void delete (Clientes clientes);
}
