package TransportesYIYO.seguimiento.controllers;

import TransportesYIYO.seguimiento.models.entities.Clientes;
import TransportesYIYO.seguimiento.models.services.ClientesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ClienteController {

    @Autowired
    private ClientesServiceImpl clientesService;

    @GetMapping("/clientes")
    public List<Clientes> mostrarClientes() {
        return clientesService.findAll();
    }

    @PostMapping("/clientes")
    public ResponseEntity<?> create(@Valid @RequestBody Clientes cliente, BindingResult result) {
        Clientes nuevoCliente;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errores = new ArrayList<>();
            for (FieldError err : result.getFieldErrors()) {
                errores.add("El campo '" + err.getField() + "' " + err.getDefaultMessage());
            }
            response.put("errores", errores);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            nuevoCliente = clientesService.save(cliente);
        } catch (DataAccessException e) {
            response.put("mensaje", "error al realizar el insert a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        response.put("mensaje", "el cliente ha sido creado con exito");
        response.put("cliente", nuevoCliente);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
