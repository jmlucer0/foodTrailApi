package com.foodtrail.foodtrail_api.service;


import com.foodtrail.foodtrail_api.dtos.cliente.ClienteDto;
import com.foodtrail.foodtrail_api.exception.ClienteException;
import com.foodtrail.foodtrail_api.mapper.ClienteMapper;
import com.foodtrail.foodtrail_api.model.Cliente;
import com.foodtrail.foodtrail_api.repository.ClienteRepository;
import com.foodtrail.foodtrail_api.util.updater.ClienteUpdater;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final ClienteUpdater clienteUpdater;


    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper, ClienteUpdater clienteUpdater) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
        this.clienteUpdater = clienteUpdater;
    }


    @Transactional
    public Cliente registrarCliente(ClienteDto clienteDto){
        Cliente cliente = clienteMapper.toCliente(clienteDto);
        clienteRepository.save(cliente);
        return cliente;
    }

    public Cliente buscarPorId(Long id){
        return clienteRepository.getReferenceById(id);
    }

    @Transactional
    public Cliente actualizarCliente(Long id, ClienteDto clienteDto){
        Cliente cliente = clienteRepository.getReferenceById(id);
        clienteUpdater.validarYactualizarCliente(cliente, clienteDto);
        clienteRepository.save(cliente);
        return cliente;
    }

    @Transactional
    public boolean borrarCliente(Long id){
        Cliente cliente = clienteRepository.getReferenceById(id);
        if (!cliente.getActivo()) {
            throw new ClienteException("El cliente ya está inactivo");
        }
        cliente.setActivo(false);
        return true;
    }

    public Page<Cliente> listarClientesActivos(Pageable pageable){
        Page<Cliente> clientes = clienteRepository.findClientesActivos(pageable);
        return clientes;
    }
}
