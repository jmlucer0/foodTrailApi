package com.foodtrail.foodtrail_api.dtos.pedido;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.foodtrail.foodtrail_api.dtos.cliente.ClienteDto;
import com.foodtrail.foodtrail_api.dtos.producto.ProductoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PedidoDto {

    Long id;
    LocalDate fechaDePedido;
    Boolean enviado;
    ClienteDto cliente;
    Map<ProductoDto, Integer> productos;
    Double precioTotal;
    String formaDePago;
}
