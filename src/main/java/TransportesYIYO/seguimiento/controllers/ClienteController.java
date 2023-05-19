package TransportesYIYO.seguimiento.controllers;

import TransportesYIYO.seguimiento.models.entities.Clientes;
import TransportesYIYO.seguimiento.models.entities.Pedidos;
import TransportesYIYO.seguimiento.models.services.ClientesServiceImpl;
import TransportesYIYO.seguimiento.models.services.IClientesService;
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
@CrossOrigin(origins = "http://localhost:3000")
public class ClienteController {

    @Autowired
    private IClientesService clientesService;

    @GetMapping("/clientes")
    public List<Clientes> mostrarClientes() {
        return clientesService.findAll();
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> mostrarCliente(@PathVariable Long id) {

        // MANEJO DE ERRORES

        Clientes cliente;
        Map<String, Object> response = new HashMap<>();

        try {
            cliente = clientesService.getClienteById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cliente == null) {
            response.put("mensaje", "El cliente id: " + id + " no existe en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Clientes cliente, BindingResult result) {
        Clientes clienteExistente;
        clienteExistente = clientesService.getClienteById(id);
        Map<String, Object> response = new HashMap<>();

        if (clienteExistente == null) {
            response.put("mensaje", "El cliente con ID " + id + " no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        if (result.hasErrors()) {
            List<String> errores = new ArrayList<>();
            for (FieldError err : result.getFieldErrors()) {
                errores.add("El campo '" + err.getField() + "' " + err.getDefaultMessage());
            }
            response.put("errores", errores);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Actualizar los campos del pedido existente con los valores del pedido recibido
        clienteExistente.setEmail(cliente.getEmail());
        clienteExistente.setNombre(cliente.getNombre());
        clienteExistente.setApellido(cliente.getApellido());
        clienteExistente.setDireccion(cliente.getDireccion());

        try {
            Clientes clienteActualizado = clientesService.save(clienteExistente);
            response.put("mensaje", "El pedido con ID " + id + " ha sido actualizado con éxito");
            response.put("pedido", clienteActualizado);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el pedido con ID " + id);
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            nuevoCliente = clientesService.save(cliente);
        } catch (DataAccessException e) {
            response.put("mensaje", "error al realizar el insert a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        response.put("mensaje", "el cliente ha sido creado con exito");
        response.put("cliente", nuevoCliente);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        //MANEJO DE ERRORES

        Map<String, Object> response = new HashMap<>();

        try{
            clientesService.deleteById(id);
        } catch (DataAccessException e){
            response.put("mensaje", "Error al borrar el cliente de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Cliente eliminado con exito");

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
