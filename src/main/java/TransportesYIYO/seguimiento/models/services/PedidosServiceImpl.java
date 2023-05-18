package TransportesYIYO.seguimiento.models.services;

import TransportesYIYO.seguimiento.models.dao.IPedidosDAO;
import TransportesYIYO.seguimiento.models.entities.Pedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidosServiceImpl implements IPedidosService{

    @Autowired
    private IPedidosDAO pedidosDAO;

    @Override
    public List<Pedidos> findAll() {
        return (List<Pedidos>) pedidosDAO.findAll();
    }

    @Override
    public Pedidos save(Pedidos pedido) {
        return pedidosDAO.save(pedido);
    }

    @Override
    public Optional<Pedidos> getPedidosById(Long id) {
        return pedidosDAO.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        pedidosDAO.deleteById(id);
    }
}
