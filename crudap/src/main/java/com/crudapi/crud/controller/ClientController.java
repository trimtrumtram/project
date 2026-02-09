package com.crudapi.crud.controller;

import com.crudapi.crud.dto.client.ClientResponseDTO;
import com.crudapi.crud.dto.client.CreateClientDTO;
import com.crudapi.crud.dto.client.UpdateClientDTO;
import com.crudapi.crud.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
@Tag(name = "Client", description = "Client controller")
public class ClientController {

    private final ClientService clientService;
    private ClientResponseDTO clientDto;

    @PostMapping
    @Operation(
            summary = "Create a new client",
            description = "Creating a new client",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Client created successfully"),
                    @ApiResponse(responseCode = "400", description = "Client is not created")
            }
    )
    public ResponseEntity<ClientResponseDTO> createClient (@RequestBody @Valid CreateClientDTO dto) {
        clientDto = clientService.createClient(dto);
        return ResponseEntity.ok(clientDto);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing client",
            description = "Updating an existing client",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Client updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Client is not updated")
            }
    )
    public ResponseEntity<ClientResponseDTO> updateClient (@PathVariable Long id, @RequestBody @Valid UpdateClientDTO dto) {
        clientDto = clientService.updateClient(id, dto);
        return ResponseEntity.ok(clientDto);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete an existing client",
            description = "Deleting an existing client",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Client is not deleted")
            }
    )
    public boolean deleteClient (@PathVariable Long id) {
        clientService.deleteClient(id);
        return true;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a client by id",
            description = "Getting a client by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client found successfully"),
                    @ApiResponse(responseCode = "404", description = "Client not found")
            }
    )
    public ResponseEntity<ClientResponseDTO> getClientById(@PathVariable Long id) {
        clientDto = clientService.findById(id);
        return ResponseEntity.ok(clientDto);
    }
}
