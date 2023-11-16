package dev.eliezer.superticket.controller;

import dev.eliezer.superticket.domain.model.Client;
import dev.eliezer.superticket.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/clients")
public class ClientRestController {
    private final ClientService clientService;
    private ClientRestController (ClientService clientService){
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Client>> findAll(){
        var client = clientService.findAll();
        return ResponseEntity.ok(client);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> findById(@PathVariable Long id){
        var client = clientService.findById(id);
        return ResponseEntity.ok(client);
    }

    @PostMapping
    public ResponseEntity<Client> insert(@RequestBody Client clientToInsert){
        var clientInserted = clientService.insert(clientToInsert);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clientInserted.getId())
                .toUri();
        return ResponseEntity.created(location).body(clientInserted);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable Long id, @RequestBody Client clientToUpdate){
        var clientUpdated = clientService.update(id, clientToUpdate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clientUpdated.getId())
                .toUri();
        return ResponseEntity.created(location).body(clientUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
