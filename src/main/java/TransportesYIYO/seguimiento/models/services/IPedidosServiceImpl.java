package TransportesYIYO.seguimiento.models.services;

import TransportesYIYO.seguimiento.models.dao.IPedidosDAO;
import TransportesYIYO.seguimiento.models.entities.Pedidos;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class IPedidosServiceImpl implements IPedidosService{

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
    public void delete(Pedidos pedido) {
        pedidosDAO.delete(pedido);
    }
}
