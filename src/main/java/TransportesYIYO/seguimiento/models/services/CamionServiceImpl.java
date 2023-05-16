package TransportesYIYO.seguimiento.models.services;

import TransportesYIYO.seguimiento.models.dao.ICamionDAO;
import TransportesYIYO.seguimiento.models.entities.Camiones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CamionServiceImpl implements ICamionService{

    @Autowired
    private ICamionDAO camionDAO;

    @Override
    public List<Camiones> findAll() {
        return (List<Camiones>) camionDAO.findAll();
    }


    @Override
    public Camiones save(Camiones camion) {
        return camionDAO.save(camion);
    }

    @Override
    public void delete(Long id) {
        camionDAO.deleteById(id);
    }


}
