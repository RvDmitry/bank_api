package name.dmitryrazumov.bank.controller;

import name.dmitryrazumov.bank.entity.Account;
import name.dmitryrazumov.bank.model.AccountModel;
import name.dmitryrazumov.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Class AccountController
 * Класс контроллер для действий со счетом.
 * @author Dmitry Razumov
 * @version 1
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Account account) {
        try {
            return new ResponseEntity<>(accountService.save(account), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok(accountService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable int id) {
        Optional<AccountModel> optionalModel = accountService.findById(id);
        return new ResponseEntity<>(optionalModel.orElse(new AccountModel()),
                optionalModel.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/client/{id}")
    public ResponseEntity getByClient(@PathVariable int id) {
        return ResponseEntity.ok(accountService.findByClientId(id));
    }

    @PatchMapping
    public ResponseEntity depositing(@RequestBody Account account) {
        try {
            accountService.addBalance(account);
            return ResponseEntity.ok("Пополнение счета прошло успешно.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
