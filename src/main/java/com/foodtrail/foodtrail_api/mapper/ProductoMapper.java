package com.foodtrail.foodtrail_api.mapper;

import com.foodtrail.foodtrail_api.dtos.ProductoDto;
import com.foodtrail.foodtrail_api.model.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public Producto toProducto(ProductoDto productoDto){
        Producto producto = new Producto();

        producto.setId(productoDto.getId());
        producto.setNombre(productoDto.getNombre());
        producto.setPrecio(productoDto.getPrecio());
        return producto;
    }

    public ProductoDto toProductoDto(Producto producto){
        ProductoDto productoDto = new ProductoDto();

        productoDto.setId(producto.getId());
        productoDto.setNombre(producto.getNombre());
        productoDto.setPrecio(producto.getPrecio());
        return productoDto;
    }
}
