package com.foodtrail.foodtrail_api.controller;

import com.foodtrail.foodtrail_api.assembler.PedidoModelAssembler;
import com.foodtrail.foodtrail_api.dtos.pedido.ActualizarPedidoDto;
import com.foodtrail.foodtrail_api.dtos.pedido.CrearPedidoDto;
import com.foodtrail.foodtrail_api.dtos.pedido.PedidoDto;
import com.foodtrail.foodtrail_api.mapper.PedidoMapper;
import com.foodtrail.foodtrail_api.model.Pedido;
import com.foodtrail.foodtrail_api.service.PedidoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper;
    private final PedidoModelAssembler modelAssembler;
    private final PagedResourcesAssembler<PedidoDto> pedidoDtoPagedResourcesAssembler;

    public PedidoController(PedidoService pedidoService, PedidoMapper pedidoMapper, PedidoModelAssembler modelAssembler, PagedResourcesAssembler<PedidoDto> pedidoDtoPagedResourcesAssembler) {
        this.pedidoService = pedidoService;
        this.pedidoMapper = pedidoMapper;
        this.modelAssembler = modelAssembler;
        this.pedidoDtoPagedResourcesAssembler = pedidoDtoPagedResourcesAssembler;
    }

    @PostMapping("/register")
    public ResponseEntity<EntityModel<PedidoDto>> registrarPedido(@RequestBody CrearPedidoDto crearPedidoDto){
        Pedido pedido = pedidoService.registrarPedido(crearPedidoDto);
        return ResponseEntity.ok(modelAssembler.toModel(pedidoMapper.toPedidoDto(pedido)));
    }
    
    @GetMapping("/deliver")
    public ResponseEntity<PagedModel<EntityModel<PedidoDto>>> buscarPorEnviados(Pageable pageable){
        if (pageable.isUnpaged() || pageable.getPageSize() <= 0){
            pageable = Pageable.ofSize(5);
        }
        Page<PedidoDto> pedidoDtoPage = pedidoService.buscarPedidoEnviados(pageable).map(pedidoMapper::toPedidoDto);
        PagedModel<EntityModel<PedidoDto>> pedidoPageModel = pedidoDtoPagedResourcesAssembler.toModel(pedidoDtoPage, modelAssembler);
        return ResponseEntity.ok(pedidoPageModel);
    }

    @GetMapping("/notDeliver")
    public ResponseEntity<PagedModel<EntityModel<PedidoDto>>> buscarPorNoEnviados(Pageable pageable){
        if (pageable.isUnpaged() || pageable.getPageSize() <= 0){
            pageable = Pageable.ofSize(5);
        }
        Page<PedidoDto> pedidoDtoPage = pedidoService.buscarPedidosNoEnviados(pageable).map(pedidoMapper::toPedidoDto);
        PagedModel<EntityModel<PedidoDto>> pedidoPageModel = pedidoDtoPagedResourcesAssembler.toModel(pedidoDtoPage, modelAssembler);
        return ResponseEntity.ok(pedidoPageModel);
    }

    @GetMapping("/payment")
    public ResponseEntity<PagedModel<EntityModel<PedidoDto>>> buscarPorFormaDePago(Pageable pageable, @RequestParam String formaDePago){
        if (pageable.isUnpaged() || pageable.getPageSize() <= 0){
            pageable = Pageable.ofSize(5);
        }
        Page<PedidoDto> pedidoDtoPage = pedidoService.buscarPedidosPorFormaDePago(pageable, formaDePago).map(pedidoMapper::toPedidoDto);
        PagedModel<EntityModel<PedidoDto>> pedidoPageModel = pedidoDtoPagedResourcesAssembler.toModel(pedidoDtoPage, modelAssembler);
        return ResponseEntity.ok(pedidoPageModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PedidoDto>> buscarPorId(@PathVariable Long id){
        Pedido pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(modelAssembler.toModel(pedidoMapper.toPedidoDto(pedido)));
    }

    @PostMapping("/{id}")
    public ResponseEntity<EntityModel<PedidoDto>> actualizarPedido(@PathVariable Long id, @RequestBody ActualizarPedidoDto actualizarPedidoDto){
        Pedido pedido = pedidoService.actualizarPedido(id, actualizarPedidoDto);
        return ResponseEntity.ok(modelAssembler.toModel(pedidoMapper.toPedidoDto(pedido)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminarPedido(@PathVariable Long id){
        boolean eliminado = pedidoService.eliminarPedido(id);
        if (eliminado){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
