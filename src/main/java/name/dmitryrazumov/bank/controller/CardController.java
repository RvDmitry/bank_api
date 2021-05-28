package name.dmitryrazumov.bank.controller;

import name.dmitryrazumov.bank.entity.Card;
import name.dmitryrazumov.bank.model.CardModel;
import name.dmitryrazumov.bank.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Class CardController
 *
 * @author Dmitry Razumov
 * @version 1
 */
@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Card card) {
        try {
            return new ResponseEntity<>(cardService.save(card), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok(cardService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable int id) {
        Optional<CardModel> optionalModel = cardService.findById(id);
        return new ResponseEntity<>(optionalModel.orElse(new CardModel()),
                optionalModel.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/client/{id}")
    public ResponseEntity getByClient(@PathVariable int id) {
        return ResponseEntity.ok(cardService.findByClientId(id));
    }

    @GetMapping("/account/{id}")
    public ResponseEntity getByAccount(@PathVariable int id) {
        return ResponseEntity.ok(cardService.findByAccountId(id));
    }
}
