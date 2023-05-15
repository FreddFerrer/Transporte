package TransportesYIYO.seguimiento.models.services;

import TransportesYIYO.seguimiento.models.dao.IClientesDAO;
import TransportesYIYO.seguimiento.models.entities.Clientes;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ClientesServiceImpl implements IClientesService{

    @Autowired
    private IClientesDAO clientesDAO;

    @Override
    public List<Clientes> findAll() {
        return (List<Clientes>) clientesDAO.findAll();
    }

    @Override
    public Clientes save(Clientes cliente) {
        return clientesDAO.save(cliente);
    }

    @Override
    public void delete(Clientes cliente) {
        clientesDAO.delete(cliente);
    }
}
