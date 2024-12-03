package com.foodtrail.foodtrail_api.service;

import com.foodtrail.foodtrail_api.dtos.ProductoDto;
import com.foodtrail.foodtrail_api.mapper.ProductoMapper;
import com.foodtrail.foodtrail_api.model.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductoServiceTest {
    @Mock
    private ProductoMapper productoMapper; // Simulación del mapper

    @InjectMocks
    private ProductoService productoService; // Clase bajo prueba

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }

    @Test
    void registrarProducto_DeberiaRetornarProducto() {
        // Arrange
        ProductoDto productoDto = new ProductoDto();
        Producto productoEsperado = new Producto();
        when(productoMapper.toProducto(productoDto)).thenReturn(productoEsperado);

        // Act
        Producto resultado = productoService.registrarProducto(productoDto);

        // Assert
        assertNotNull(resultado, "El producto no debería ser nulo");
        assertEquals(productoEsperado, resultado, "El producto devuelto no es el esperado");
        verify(productoMapper).toProducto(productoDto); // Verifica que el mapper fue llamado
    }

    @Test
    void registrarProducto_DeberiaLanzarExcepcionCuandoDtoEsNulo() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productoService.registrarProducto(null),
                "Se esperaba una excepción al pasar un DTO nulo"
        );

        assertEquals("El productoDto no puede ser nulo", exception.getMessage());
    }

    @Test
    void registrarProducto_DeberiaLanzarExcepcionCuandoMapperFalla() {
        // Arrange
        ProductoDto productoDto = new ProductoDto();
        when(productoMapper.toProducto(productoDto)).thenThrow(new RuntimeException("Error de conversión"));

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> productoService.registrarProducto(productoDto),
                "Se esperaba una excepción al fallar el mapper"
        );

        assertTrue(exception.getMessage().contains("Error al registrar el producto"));
        assertTrue(exception.getCause().getMessage().contains("Error de conversión"));
    }
}
