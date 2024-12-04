package com.foodtrail.foodtrail_api.service;

import com.foodtrail.foodtrail_api.dtos.producto.ProductoDto;
import com.foodtrail.foodtrail_api.mapper.ProductoMapper;
import com.foodtrail.foodtrail_api.model.Producto;
import com.foodtrail.foodtrail_api.repository.ProductoRepository;
import com.foodtrail.foodtrail_api.util.updater.ProductoUpdater;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;
    private final ProductoUpdater productoUpdater;

    public ProductoService(ProductoRepository productoRepository, ProductoMapper productoMapper, ProductoUpdater productoUpdater) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
        this.productoUpdater = productoUpdater;
    }


    @Transactional
    public Producto registrarProducto(ProductoDto productoDto) {
        Producto producto = productoMapper.toProducto(productoDto);
        productoRepository.save(producto);
        return producto;
    }

    public Producto buscarProductoPorID(Long id) {
        return productoRepository.getReferenceById(id);
    }

    public Page<ProductoDto> listarTodosLosProductos(Pageable pageable) {
        Page<Producto> productos = productoRepository.findAll(pageable);
        return productos.map(productoMapper::toProductoDto);
    }

    @Transactional
    public Producto actualizarProducto(Long id, ProductoDto productoDto) {
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
        productoUpdater.validarYactualizarProducto(producto, productoDto);
        productoRepository.save(producto);
        return producto;
    }

    @Transactional
    public boolean borrarProducto(Long id) {
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontro el producto"));
        productoRepository.deleteById(id);
        if (productoRepository.findById(producto.getId()).isPresent()) {
            return false;
        }
        return true;
    }
}
