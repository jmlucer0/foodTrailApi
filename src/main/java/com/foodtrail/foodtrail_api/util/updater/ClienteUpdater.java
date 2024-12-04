package com.foodtrail.foodtrail_api.util.updater;

import com.foodtrail.foodtrail_api.dtos.cliente.ClienteDto;
import com.foodtrail.foodtrail_api.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteUpdater {

    public Cliente validarYactualizarCliente(Cliente cliente, ClienteDto clienteDto){
        if (clienteDto.getNombre() != null){
            cliente.setNombre(clienteDto.getNombre());
        }
        if (clienteDto.getDireccion() != null){
            cliente.setDireccion(clienteDto.getDireccion());
        }
        if (clienteDto.getTelefono() != null){
            cliente.setTelefono(clienteDto.getTelefono());
        }
        if (clienteDto.getActivo() != null){
            cliente.setActivo(clienteDto.getActivo());
        }

        return cliente;
    }
}
