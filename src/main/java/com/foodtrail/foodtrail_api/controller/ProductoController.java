package com.foodtrail.foodtrail_api.controller;

import com.foodtrail.foodtrail_api.assembler.ProductoModelAssembler;
import com.foodtrail.foodtrail_api.dtos.ProductoDto;
import com.foodtrail.foodtrail_api.mapper.ProductoMapper;
import com.foodtrail.foodtrail_api.model.Producto;
import com.foodtrail.foodtrail_api.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
@Tag(name = "Product", description = "Controller for Products")
public class ProductoController {

    private final ProductoService productoService;
    private final ProductoMapper productoMapper;
    private final ProductoModelAssembler modelAssembler;

    public ProductoController(ProductoService productoService, ProductoMapper productoMapper, ProductoModelAssembler modelAssembler) {
        this.productoService = productoService;
        this.productoMapper = productoMapper;
        this.modelAssembler = modelAssembler;
    }

    @Operation(
            summary = "Register a new Product",
            tags = {"Product Management"},
            description = "Creates a new Product with the provided details and returns the registered product information.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Required data to register a new Product.",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ProductoDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product successfully registered",
                            content = @Content(
                                    schema = @Schema(implementation = EntityModel.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<EntityModel<ProductoDto>> registrarProducto(@RequestBody ProductoDto productoDto){
            Producto producto = productoService.registrarProducto(productoDto);
            return ResponseEntity.ok(modelAssembler.toModel(productoMapper.toProductoDto(producto)));
    }

    @PostMapping("/{id}")
    @Operation(
            summary = "Update a Product",
            description = "Updates an existing Product with the provided ID and new data. Returns the updated Product details.",
            tags = {"Product Management"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated Product details.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductoDto.class)
                    )
            )
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product updated successfully.",
                    content = @Content(
                            schema = @Schema(implementation = EntityModel.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid Product data.",
                    content = @Content(mediaType = "text/plain")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found.",
                    content = @Content(mediaType = "text/plain")
            )
    }
    )
    public ResponseEntity<EntityModel<ProductoDto>> actualizarProducto(@PathVariable Long id, @RequestBody ProductoDto productoDto){
            Producto producto = productoService.actualizarProducto(id, productoDto);
            return ResponseEntity.ok(modelAssembler.toModel(productoMapper.toProductoDto(producto)));
    }

    @Operation(
            summary = "Get List of all Products",
            description = "Returns a paginated list of Products. If no pagination is provided, the default page size is 5.",
            tags = {"Product Management"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "A paginated list of Products.",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "",
                    content = @Content(mediaType = "text/plain")
            )
    })
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<ProductoDto>>> listarProductos(Pageable pageable){
        if (pageable.isUnpaged() || pageable.getPageSize() <= 0){
            pageable = Pageable.ofSize(5);
        }
        Page<ProductoDto> productoDtoPage = productoService.listarTodosLosProductos(pageable);
        List<EntityModel<ProductoDto>> entityModels = productoDtoPage.stream()
                .map(modelAssembler::toModel).collect(Collectors.toList());
        PagedModel<EntityModel<ProductoDto>> productoPageModel = PagedModel.of(entityModels,
                new PagedModel.PageMetadata(productoDtoPage.getSize(), productoDtoPage.getNumber(), productoDtoPage.getTotalElements())
                );
        return ResponseEntity.ok(productoPageModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProductoDto>> buscarProductoPorId(@PathVariable Long id) {
        Producto producto = productoService.buscarProductoPorID(id);
        return ResponseEntity.ok(modelAssembler.toModel(productoMapper.toProductoDto(producto)));
    }


    @Operation(
            summary = "Delete a Product",
            description = "Deletes a Product from the system based on the provided ID.",
            tags = {"Product Management"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Product successfully deleted.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found.",
                    content = @Content
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarProducto(@PathVariable Long id){
        boolean eliminado = productoService.borrarProducto(id);
        if (eliminado){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
