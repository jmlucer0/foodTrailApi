package com.foodtrail.foodtrail_api.assembler;

import com.foodtrail.foodtrail_api.controller.ProductoController;
import com.foodtrail.foodtrail_api.dtos.producto.ProductoDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<ProductoDto, EntityModel<ProductoDto>> {
    @Override
    public EntityModel<ProductoDto> toModel(ProductoDto productoDto) {
        Long id = productoDto.getId();
        return EntityModel.of(productoDto,
                linkTo(methodOn(ProductoController.class).buscarProductoPorId(id)).withSelfRel(),
                linkTo(methodOn(ProductoController.class).actualizarProducto(id,productoDto)).withRel("update"),
                linkTo(methodOn(ProductoController.class).listarProductos(null)).withRel("list"),
                linkTo(methodOn(ProductoController.class).eliminarProducto(id)).withRel("delete")
                );

    }
}
