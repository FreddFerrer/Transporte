package TransportesYIYO.seguimiento.models.services;

import TransportesYIYO.seguimiento.models.dao.ICamionDAO;
import TransportesYIYO.seguimiento.models.entities.Camion;
import TransportesYIYO.seguimiento.models.entities.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class CamionServiceImpl implements ICamionService{

    @Autowired
    private ICamionDAO camionDAO;

    @Override
    public List<Camion> findAll() {
        return (List<Camion>) camionDAO.findAll();
    }

    @Override
    public Camion save(Camion camion) {
        return camionDAO.save(camion);
    }

}
