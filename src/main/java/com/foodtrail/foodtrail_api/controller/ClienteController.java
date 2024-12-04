package com.foodtrail.foodtrail_api.controller;

import com.foodtrail.foodtrail_api.assembler.ClienteModelAssembler;
import com.foodtrail.foodtrail_api.dtos.cliente.ClienteDto;
import com.foodtrail.foodtrail_api.mapper.ClienteMapper;
import com.foodtrail.foodtrail_api.model.Cliente;
import com.foodtrail.foodtrail_api.service.ClienteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/client")
@Tag(name = "Client", description = "Controller for Client")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;
    private final ClienteModelAssembler modelAssembler;
    private final PagedResourcesAssembler<ClienteDto> pagedResourcesAssembler;


    public ClienteController(ClienteService clienteService, ClienteMapper clienteMapper, ClienteModelAssembler modelAssembler, PagedResourcesAssembler<ClienteDto> pagedResourcesAssembler) {
        this.clienteService = clienteService;
        this.clienteMapper = clienteMapper;
        this.modelAssembler = modelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @PostMapping("/register")
    public ResponseEntity<EntityModel<ClienteDto>> registrarCliente(@RequestBody ClienteDto clienteDto){
        Cliente cliente = clienteService.registrarCliente(clienteDto);
        return ResponseEntity.ok(modelAssembler.toModel(clienteMapper.toClienteDto(cliente)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ClienteDto>> buscarClientePorId(@PathVariable Long id){
        Cliente cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok(modelAssembler.toModel(clienteMapper.toClienteDto(cliente)));
    }

    @PostMapping("/{id}")
    public ResponseEntity<EntityModel<ClienteDto>> actualizarCliente(@PathVariable Long id, @RequestBody ClienteDto clienteDto){
        Cliente cliente = clienteService.actualizarCliente(id, clienteDto);
        return ResponseEntity.ok(modelAssembler.toModel(clienteMapper.toClienteDto(cliente)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminarCliente(@PathVariable Long id){
        boolean eliminado = clienteService.borrarCliente(id);
        if (eliminado){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<ClienteDto>>> listarClientesActivos(Pageable pageable) {
        if (pageable.isUnpaged() || pageable.getPageSize() <= 0){
            pageable = Pageable.ofSize(5);
        }
        Page<ClienteDto> clienteDtoPage = clienteService.listarClientesActivos(pageable).map(clienteMapper::toClienteDto);
        PagedModel<EntityModel<ClienteDto>> clientePageModel = pagedResourcesAssembler.toModel(clienteDtoPage, modelAssembler);
        return ResponseEntity.ok(clientePageModel);
    }
}
