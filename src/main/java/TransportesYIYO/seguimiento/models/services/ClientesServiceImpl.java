package TransportesYIYO.seguimiento.models.services;

import TransportesYIYO.seguimiento.models.dao.IClientesDAO;
import TransportesYIYO.seguimiento.models.entities.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
    public void deleteById(Long id) {
        clientesDAO.deleteById(id);
    }

}
