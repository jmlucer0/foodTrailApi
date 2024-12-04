package com.foodtrail.foodtrail_api.util.updater;

import com.foodtrail.foodtrail_api.dtos.pedido.ActualizarPedidoDto;
import com.foodtrail.foodtrail_api.model.Cliente;
import com.foodtrail.foodtrail_api.model.Pedido;
import com.foodtrail.foodtrail_api.model.Producto;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PedidoUpdater {

    public boolean actualizarPedido(Pedido pedidoExistente, ActualizarPedidoDto actualizarPedidoDto,
                                    Map<Producto, Integer> nuevosProductos, Cliente cliente) {
        boolean actualizado = false;

        if (!pedidoExistente.getCliente().getId().equals(actualizarPedidoDto.getClienteID())) {
            pedidoExistente.setCliente(cliente);
            actualizado = true;
        }

        if (!pedidoExistente.getProductos().equals(nuevosProductos)) {
            pedidoExistente.setProductos(nuevosProductos);
            actualizado = true;
        }

        if (actualizarPedidoDto.getFormaDePago() != null &&
                !pedidoExistente.getFormaDePago().equals(actualizarPedidoDto.getFormaDePago())) {
            pedidoExistente.setFormaDePago(actualizarPedidoDto.getFormaDePago());
            actualizado = true;
        }

        double nuevoPrecioTotal = pedidoExistente.calcularPrecio();
        if (Double.compare(pedidoExistente.getPrecioTotal(), nuevoPrecioTotal) != 0) {
            pedidoExistente.setPrecioTotal(nuevoPrecioTotal);
            actualizado = true;
        }

        return actualizado;
    }
}
