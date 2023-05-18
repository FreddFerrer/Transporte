package TransportesYIYO.seguimiento.controllers;

import TransportesYIYO.seguimiento.models.entities.Camiones;
import TransportesYIYO.seguimiento.models.entities.Clientes;
import TransportesYIYO.seguimiento.models.services.CamionServiceImpl;
import TransportesYIYO.seguimiento.models.services.ICamionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api")
public class CamionController {

    @Autowired
    private ICamionService camionService;

    @GetMapping("/camiones")
    public List<Camiones> mostrarCamiones(){
        return camionService.findAll();
    }

    @GetMapping("/camiones/{id}")
    public ResponseEntity<?> mostrarCamion(@PathVariable Long id) {

        // MANEJO DE ERRORES

        Optional<Camiones> camionOptional;
        Map<String, Object> response = new HashMap<>();

        try {
            camionOptional = camionService.getCamionById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (camionOptional.isEmpty()) {
            response.put("mensaje", "El camion id: " + id + " no existe en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Camiones camion = camionOptional.get();
        return new ResponseEntity<>(camion, HttpStatus.OK);
    }

    @PostMapping("/camiones")
    public ResponseEntity<?> create(@Valid @RequestBody Camiones camion, BindingResult result) {
        Camiones nuevoCamion;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errores = new ArrayList<>();
            for (FieldError err : result.getFieldErrors()) {
                errores.add("El campo '" + err.getField() + "' " + err.getDefaultMessage());
            }
            response.put("errores", errores);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            nuevoCamion = camionService.save(camion);
        } catch (DataAccessException e) {
            response.put("mensaje", "error al realizar el insert a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        response.put("mensaje", "el camion ha sido creado con exito");
        response.put("camion", nuevoCamion);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/camiones/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        //MANEJO DE ERRORES

        Map<String, Object> response = new HashMap<>();

        try{
            camionService.delete(id);
        } catch (DataAccessException e){
            response.put("mensaje", "Error al borrar el camion de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Camion eliminado con exito");

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
