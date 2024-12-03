package com.foodtrail.foodtrail_api.assembler;

import com.foodtrail.foodtrail_api.controller.ClienteController;
import com.foodtrail.foodtrail_api.dtos.ClienteDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ClienteModelAssembler implements RepresentationModelAssembler<ClienteDto, EntityModel<ClienteDto>> {
    @Override
    public EntityModel<ClienteDto> toModel(ClienteDto clienteDto) {
        Long id = clienteDto.getId();
        return EntityModel.of(clienteDto,
                linkTo(methodOn(ClienteController.class).buscarClientePorId(id)).withSelfRel(),
                linkTo(methodOn(ClienteController.class).actualizarCliente(id, clienteDto)).withRel("update"),
                linkTo(methodOn(ClienteController.class).eliminarCliente(id)).withRel("delete"),
                linkTo(methodOn(ClienteController.class).listarClientesActivos(null)).withRel("list")
                );

    }
}
