package TransportesYIYO.seguimiento.controllers;

import TransportesYIYO.seguimiento.models.entities.Camiones;
import TransportesYIYO.seguimiento.models.entities.Clientes;
import TransportesYIYO.seguimiento.models.entities.Pedidos;
import TransportesYIYO.seguimiento.models.services.ICamionService;
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

        Pedidos pedido;
        Map<String, Object> response = new HashMap<>();

        try {
            pedido = pedidosService.getPedidosById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (pedido == null) {
            response.put("mensaje", "El pedido id: " + id + " no existe en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @PostMapping("/pedidos")
    public ResponseEntity<?> create(@Valid @RequestBody Pedidos pedido, BindingResult result) {
        Pedidos nuevoPedido;
        Integer ultimoNumeroPedido = pedidosService.obtenerUltimoNumeroPedido();
        Integer nuevoNumeroPedido = ultimoNumeroPedido + 1;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errores = new ArrayList<>();
            for (FieldError err : result.getFieldErrors()) {
                errores.add("El campo '" + err.getField() + "' " + err.getDefaultMessage());
            }
            response.put("errores", errores);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        pedido.setNroPedido(nuevoNumeroPedido); // Asignar el nuevo número de pedido al objeto pedido

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

    @PutMapping("/pedidos/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Pedidos pedido, BindingResult result) {
        Pedidos pedidoExistente = pedidosService.getPedidosById(id);
        Map<String, Object> response = new HashMap<>();

        if (pedidoExistente == null) {
            response.put("mensaje", "El pedido con ID " + id + " no existe");
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
        pedidoExistente.setCamion(pedido.getCamion());
        pedidoExistente.setCliente(pedido.getCliente());
        pedidoExistente.setEntregado(pedido.isEntregado());
        pedidoExistente.setFechaEstimada(pedido.getFechaEstimada());
        pedidoExistente.setDestino(pedido.getDestino());

        try {
            Pedidos pedidoActualizado = pedidosService.save(pedidoExistente);
            response.put("mensaje", "El pedido con ID " + id + " ha sido actualizado con éxito");
            response.put("pedido", pedidoActualizado);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el pedido con ID " + id);
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/pedidos/{id}/entregado")
    public ResponseEntity<?> actualizarEstadoEntregado(@PathVariable Long id, @RequestBody Pedidos pedido) {
        Pedidos pedidoActualizado;
        Map<String, Object> response = new HashMap<>();
        try {
            Pedidos pedidoExistente = pedidosService.getPedidosById(id);

            //manejo de errores
            if (pedidoExistente == null) {
                response.put("mensaje", "El pedido con ID " + id + " no existe");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            pedidoExistente.setEntregado(pedido.isEntregado());

            // Eliminar el pedido del camión si está marcado como entregado
            if (pedido.isEntregado()) {
                pedidoExistente.setCamion(null);
            } else {
                pedidoExistente.setCamion(pedido.getCamion());
            }


            pedidoActualizado = pedidosService.save(pedidoExistente);

            response.put("mensaje", "El estado 'entregado' del pedido con ID " + id + " ha sido actualizado");
            response.put("pedido", pedidoActualizado);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            response.put("mensaje", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
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
