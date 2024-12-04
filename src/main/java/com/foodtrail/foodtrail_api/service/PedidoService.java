package com.foodtrail.foodtrail_api.service;

import com.foodtrail.foodtrail_api.dtos.pedido.ActualizarPedidoDto;
import com.foodtrail.foodtrail_api.dtos.pedido.CrearPedidoDto;
import com.foodtrail.foodtrail_api.dtos.pedido.PedidoDto;
import com.foodtrail.foodtrail_api.mapper.PedidoMapper;
import com.foodtrail.foodtrail_api.model.Cliente;
import com.foodtrail.foodtrail_api.model.Pedido;
import com.foodtrail.foodtrail_api.model.Producto;
import com.foodtrail.foodtrail_api.repository.ClienteRepository;
import com.foodtrail.foodtrail_api.repository.PedidoRepository;
import com.foodtrail.foodtrail_api.repository.ProductoRepository;
import com.foodtrail.foodtrail_api.util.updater.PedidoUpdater;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final PedidoMapper pedidoMapper;
    private final PedidoUpdater pedidoUpdater;


    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository, ProductoRepository productoRepository, PedidoMapper pedidoMapper, PedidoUpdater pedidoUpdater) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
        this.pedidoMapper = pedidoMapper;
        this.pedidoUpdater = pedidoUpdater;
    }

    @Transactional
    public Pedido registrarPedido(CrearPedidoDto pedidoDto){
        Cliente cliente = clienteRepository.getReferenceById(pedidoDto.getClienteId());
        Map<Producto, Integer> productos = pedidoMapper.convertirIdAProductos(pedidoDto.getProductos(), productoRepository);
        Pedido pedido = pedidoMapper.toPedido(pedidoDto, cliente, productos);
        pedido.setPrecioTotal(pedido.calcularPrecio());
        return pedidoRepository.save(pedido);
    }

   @Transactional
   public boolean eliminarPedido(Long id){
        return true;
   }

    @Transactional
    public Pedido actualizarPedido(Long id, ActualizarPedidoDto actualizarPedidoDto){
        Cliente cliente = clienteRepository.getReferenceById(actualizarPedidoDto.getClienteID());
        Pedido pedidoActualizado = pedidoRepository.getReferenceById(actualizarPedidoDto.getId());
        Map<Producto, Integer> nuevosProductos = pedidoMapper.convertirIdAProductos(actualizarPedidoDto.getProductos(), productoRepository);
        boolean actualizado = pedidoUpdater.actualizarPedido(pedidoActualizado, actualizarPedidoDto, nuevosProductos, cliente);
        if (actualizado){
            return pedidoRepository.save(pedidoActualizado);
        }
        return pedidoActualizado;
    }

    public Pedido buscarPorId(Long id){
        return pedidoRepository.getReferenceById(id);
    }

    public Page<Pedido> buscarPedidoEnviados(Pageable pageable){
        Page<Pedido> pedidos = pedidoRepository.findByPedidosEnviados(pageable);
        return pedidos;
    }

    public Page<Pedido> buscarPedidosNoEnviados(Pageable pageable){
        Page<Pedido> pedidos = pedidoRepository.findPedidosNoEnviados(pageable);
        return pedidos;
    }

    public Page<Pedido> buscarPedidosPorFormaDePago(String tipoDePago){
        return null;
    }
}
