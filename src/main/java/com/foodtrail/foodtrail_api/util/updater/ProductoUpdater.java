package com.foodtrail.foodtrail_api.util.updater;

import com.foodtrail.foodtrail_api.dtos.ProductoDto;
import com.foodtrail.foodtrail_api.model.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoUpdater {

    public Producto validarYactualizarProducto(Producto producto, ProductoDto productoDto){
        if (productoDto.getNombre() != null){
            producto.setNombre(productoDto.getNombre());
        }
        if ( productoDto.getPrecio() != null){
            producto.setPrecio(productoDto.getPrecio());
        }
        return producto;
    }
}
