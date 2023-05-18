package TransportesYIYO.seguimiento.controllers;

import TransportesYIYO.seguimiento.models.entities.Clientes;
import TransportesYIYO.seguimiento.models.entities.Pedidos;
import TransportesYIYO.seguimiento.models.services.IPedidosService;
import TransportesYIYO.seguimiento.models.services.PedidosServiceImpl;
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
@RequestMapping("api")
public class PedidoController {

    @Autowired
    private IPedidosService pedidosService;

    @GetMapping("/pedidos")
    public List<Pedidos> getPedidos() {
        return pedidosService.findAll();
    }

    @GetMapping("/pedidos/{id}")
    public ResponseEntity<?> mostrarPedido(@PathVariable Long id) {

        // MANEJO DE ERRORES

        Optional<Pedidos> pedidoOptional;
        Map<String, Object> response = new HashMap<>();

        try {
            pedidoOptional = pedidosService.getPedidosById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (pedidoOptional.isEmpty()) {
            response.put("mensaje", "El pedido id: " + id + " no existe en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Pedidos pedido = pedidoOptional.get();
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @PostMapping("/pedidos")
    public ResponseEntity<?> create(@Valid @RequestBody Pedidos pedido, BindingResult result) {
        Pedidos nuevoPedido;
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
            nuevoPedido = pedidosService.save(pedido);
        } catch (DataAccessException e) {
            response.put("mensaje", "error al realizar el insert a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        response.put("mensaje", "el pedido ha sido creado con exito");
        response.put("cliente", nuevoPedido);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/pedidos/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        //MANEJO DE ERRORES

        Map<String, Object> response = new HashMap<>();

        try{
            pedidosService.deleteById(id);
        } catch (DataAccessException e){
            response.put("mensaje", "Error al borrar el cliente de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Cliente eliminado con exito");

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
