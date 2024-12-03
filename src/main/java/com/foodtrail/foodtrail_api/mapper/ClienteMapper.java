package com.foodtrail.foodtrail_api.mapper;

import com.foodtrail.foodtrail_api.dtos.ClienteDto;
import com.foodtrail.foodtrail_api.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toCliente(ClienteDto clienteDto){
        Cliente cliente = new Cliente();

        cliente.setId(clienteDto.getId());
        cliente.setNombre(clienteDto.getNombre());
        cliente.setTelefono(clienteDto.getTelefono());
        cliente.setDireccion(clienteDto.getDireccion());
        cliente.setActivo(clienteDto.getActivo());

        return cliente;
    }

    public ClienteDto toClienteDto(Cliente cliente){
        ClienteDto clienteDto = new ClienteDto();

        clienteDto.setId(cliente.getId());
        clienteDto.setNombre(cliente.getNombre());
        clienteDto.setTelefono(cliente.getTelefono());
        clienteDto.setDireccion(cliente.getDireccion());
        clienteDto.setActivo(cliente.getActivo());

        return clienteDto;
    }
}
