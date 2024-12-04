package com.foodtrail.foodtrail_api.dtos.cliente;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteDto {


    Long id;
    String nombre;
    String telefono;
    String direccion;
    Boolean activo = true;
}
