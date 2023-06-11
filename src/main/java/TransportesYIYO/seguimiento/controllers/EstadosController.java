package TransportesYIYO.seguimiento.controllers;

import TransportesYIYO.seguimiento.models.entities.EstadoPedidos;
import TransportesYIYO.seguimiento.models.entities.Pedidos;
import TransportesYIYO.seguimiento.models.services.IPedidosService;
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
public class EstadosController {

    @Autowired
    private IPedidosService pedidosService;

    @GetMapping("/pedidos/{id}/estados")
    public ResponseEntity<List<EstadoPedidos>> obtenerEstadosPedido(@PathVariable Long id) {
        Pedidos pedido = pedidosService.getPedidosById(id);

        List<EstadoPedidos> estados = pedido.getEstados();
        estados.sort(Comparator.comparing(EstadoPedidos::getFecha));

        return ResponseEntity.ok(estados);
    }

    @PostMapping("/pedidos/{id}/estados")
    public ResponseEntity<?> agregarEstadoPedido(@PathVariable Long id,
                                                 @Valid @RequestBody EstadoPedidos estado,
                                                 BindingResult result) {
        Pedidos pedido = pedidosService.getPedidosById(id);
        Map<String, Object> response = new HashMap<>();

        if (pedido == null) {
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

        estado.setPedido(pedido); // Establecer la relación bidireccional

        pedido.agregarEstado(estado);


        try {
            pedidosService.save(pedido);
            response.put("mensaje", "El pedido con ID " + id + " ha sido actualizado con éxito");
            response.put("pedido", pedido);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el pedido con ID " + id);
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
