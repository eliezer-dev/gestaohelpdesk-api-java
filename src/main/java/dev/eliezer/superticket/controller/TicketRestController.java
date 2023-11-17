package dev.eliezer.superticket.controller;

import dev.eliezer.superticket.domain.model.Ticket;
import dev.eliezer.superticket.service.TicketService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin //cors
@RestController
@RequestMapping("/tickets")
@Tag(name = "Tickets Controller", description = "RESTful API for managing users.")
public class TicketRestController {
    private final TicketService ticketService;
    private TicketRestController (TicketService ticketService){
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Ticket>> findAll(){
        var ticket = ticketService.findAll();
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> findById(@PathVariable Long id){
        var ticket = ticketService.findById(id);
        return ResponseEntity.ok(ticket);
    }

    @PostMapping
    public ResponseEntity<Ticket> insert(@RequestBody Ticket ticketToInsert){
        var ticketInserted = ticketService.insert(ticketToInsert);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(ticketInserted.getId())
                .toUri();
        return ResponseEntity.created(location).body(ticketInserted);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> update(@PathVariable Long id, @RequestBody Ticket ticketToUpdate){
        var ticketUpdated = ticketService.update(id, ticketToUpdate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(ticketUpdated.getId())
                .toUri();
        return ResponseEntity.created(location).body(ticketUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

