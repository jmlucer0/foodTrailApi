package com.foodtrail.foodtrail_api.assembler;

import com.foodtrail.foodtrail_api.controller.PedidoController;
import com.foodtrail.foodtrail_api.dtos.pedido.PedidoDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoModelAssembler implements RepresentationModelAssembler<PedidoDto, EntityModel<PedidoDto>> {


    @Override
    public EntityModel<PedidoDto> toModel(PedidoDto pedidoDto) {
        Long id = pedidoDto.getId();
        return EntityModel.of(pedidoDto,
                linkTo(methodOn(PedidoController.class).buscarPorId(id)).withSelfRel(),
                linkTo(methodOn(PedidoController.class).actualizarPedido(id, null)).withRel("update"),
                linkTo(methodOn(PedidoController.class).buscarPorEnviados(null)).withRel("searchByDeliver"),
                linkTo(methodOn(PedidoController.class).buscarPorNoEnviados(null)).withRel("searchByNotDeliver"),
                linkTo(methodOn(PedidoController.class).buscarPorFormaDePago(null, pedidoDto.getFormaDePago())).withRel("searchByFormaDePago"),
                linkTo(methodOn(PedidoController.class).eliminarPedido(id)).withRel("delete")
                );
    }
}
