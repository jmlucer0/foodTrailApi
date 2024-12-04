package com.foodtrail.foodtrail_api.dtos.pedido;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.foodtrail.foodtrail_api.model.FormaDePago;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrearPedidoDto {

    Long clienteId;
    FormaDePago formaDePago;
    Map<Long, Integer> productos;
}
