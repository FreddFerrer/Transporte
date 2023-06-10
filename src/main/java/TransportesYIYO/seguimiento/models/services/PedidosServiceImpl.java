package TransportesYIYO.seguimiento.models.services;

import TransportesYIYO.seguimiento.models.dao.ICamionDAO;
import TransportesYIYO.seguimiento.models.dao.IPedidosDAO;
import TransportesYIYO.seguimiento.models.entities.Camiones;
import TransportesYIYO.seguimiento.models.entities.Pedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service
public class PedidosServiceImpl implements IPedidosService{

    @Autowired
    private IPedidosDAO pedidosDAO;
    @Autowired
    private ICamionDAO camionDAO;

    @Override
    public List<Pedidos> findAll() {
        return (List<Pedidos>) pedidosDAO.findAll();
    }

    @Override
    public Pedidos save(Pedidos pedido) {
        return pedidosDAO.save(pedido);
    }

    @Override
    public Pedidos getPedidosById(Long id) {
        return pedidosDAO.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El pedido con ID " + id + " no existe"));
    }

    @Override
    public void deleteById(Long id) {
        pedidosDAO.deleteById(id);
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Integer obtenerUltimoNumeroPedido() {
        Query query = entityManager.createQuery("SELECT MAX(p.nroPedido) FROM Pedidos p");
        Integer ultimoNumeroPedido = (Integer) query.getSingleResult();
        return ultimoNumeroPedido != null ? ultimoNumeroPedido : 99; // Empezar en 100
    }

    @Override
    public Pedidos actualizarEstadoEntregado(Long id, boolean entregado) {
        Pedidos pedidoExistente = pedidosDAO.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El pedido con ID " + id + " no existe"));

        pedidoExistente.setEntregado(entregado);
        return pedidosDAO.save(pedidoExistente);
    }
}
