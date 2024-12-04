package com.foodtrail.foodtrail_api.mapper;

import com.foodtrail.foodtrail_api.dtos.pedido.CrearPedidoDto;
import com.foodtrail.foodtrail_api.dtos.pedido.PedidoDto;
import com.foodtrail.foodtrail_api.model.Cliente;
import com.foodtrail.foodtrail_api.model.FormaDePago;
import com.foodtrail.foodtrail_api.model.Pedido;
import com.foodtrail.foodtrail_api.model.Producto;
import com.foodtrail.foodtrail_api.repository.ProductoRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    private final ClienteMapper clienteMapper;
    private final ProductoMapper productoMapper;

    public PedidoMapper(ClienteMapper clienteMapper, ProductoMapper productoMapper) {
        this.clienteMapper = clienteMapper;
        this.productoMapper = productoMapper;
    }

    public Pedido toPedido(CrearPedidoDto crearPedidoDto, Cliente cliente, Map<Producto, Integer> productos) {
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setProductos(productos);
        pedido.setFormaDePago(crearPedidoDto.getFormaDePago());
        return pedido;
    }

    public PedidoDto toPedidoDto(Pedido pedido) {
        PedidoDto pedidoDto = new PedidoDto();
        pedidoDto.setId(pedido.getId());
        pedidoDto.setFechaDePedido(pedido.getFechaDePedido());
        pedidoDto.setEnviado(pedido.getEnviado());
        pedidoDto.setCliente(clienteMapper.toClienteDto(pedido.getCliente()));
        pedidoDto.setProductos(pedido.getProductos().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> productoMapper.toProductoDto(entry.getKey()),
                        Map.Entry::getValue
                )));
        pedidoDto.setPrecioTotal(pedido.getPrecioTotal());
        pedidoDto.setFormaDePago(pedido.getFormaDePago().name());
        return pedidoDto;
    }

    public Map<Producto, Integer> convertirIdAProductos(Map<Long, Integer> productosId, ProductoRepository productoRepository){
        Map<Producto, Integer> productos = new HashMap<>();
        for (Map.Entry<Long, Integer> entry : productosId.entrySet()) {
            Producto producto = productoRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + entry.getKey()));
            productos.put(producto, entry.getValue());
        }
        return productos;
    }
}
