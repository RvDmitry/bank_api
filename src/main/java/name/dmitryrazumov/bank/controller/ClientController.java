package name.dmitryrazumov.bank.controller;

import name.dmitryrazumov.bank.entity.Client;
import name.dmitryrazumov.bank.model.ClientModel;
import name.dmitryrazumov.bank.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Class ClientController
 *
 * @author Dmitry Razumov
 * @version 1
 */
@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Client client) {
        return new ResponseEntity<>(clientService.save(client), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable int id) {
        Optional<ClientModel> optionalModel = clientService.findById(id);
        return new ResponseEntity<>(optionalModel.orElse(new ClientModel()),
                optionalModel.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
