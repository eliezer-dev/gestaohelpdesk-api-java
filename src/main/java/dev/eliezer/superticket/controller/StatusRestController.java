package dev.eliezer.superticket.controller;

import dev.eliezer.superticket.domain.model.Status;
import dev.eliezer.superticket.service.StatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/status")
public class StatusRestController {
    private final StatusService statusService;
    private StatusRestController (StatusService statusService){
        this.statusService = statusService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Status>> findAll(){
        var status = statusService.findAll();
        return ResponseEntity.ok(status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Status> findById(@PathVariable Long id){
        var status = statusService.findById(id);
        return ResponseEntity.ok(status);
    }

    @PostMapping
    public ResponseEntity<Status> insert(@RequestBody Status statusToInsert){
        var statusInserted = statusService.insert(statusToInsert);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(statusInserted.getId())
                .toUri();
        return ResponseEntity.created(location).body(statusInserted);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Status> update(@PathVariable Long id, @RequestBody Status statusToUpdate){
        var statusUpdated = statusService.update(id, statusToUpdate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(statusUpdated.getId())
                .toUri();
        return ResponseEntity.created(location).body(statusUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        statusService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
