package name.dmitryrazumov.bank.repository;

import name.dmitryrazumov.bank.entity.Account;
import name.dmitryrazumov.bank.entity.Card;
import name.dmitryrazumov.bank.entity.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CardJdbcTemplateTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CardRepository cardRepository;

    @Test
    @Transactional
    public void whenSave() {
        Client client = clientRepository.save(new Client(0, "Alex"));
        Account account = accountRepository.save(new Account(0, 100, "1111", client));
        Card card = cardRepository.save(new Card(0, "2222", account));
        assertNotEquals(0, card.getId());
    }

    @Test
    @Transactional
    public void whenFindAll() {
        Client firstClient = clientRepository.save(new Client(0, "Alex"));
        Account firstAccount = accountRepository.save(new Account(0, 100, "1234", firstClient));
        Card firstCard = cardRepository.save(new Card(0, "1234", firstAccount));
        Client secondClient = clientRepository.save(new Client(0, "Petr"));
        Account secondAccount = accountRepository.save(new Account(0, 200, "1020", secondClient));
        Card secondCard = cardRepository.save(new Card(0, "7412", secondAccount));
        assertEquals(Arrays.asList(firstCard, secondCard), cardRepository.findAll());
    }

    @Test
    @Transactional
    public void whenFindById() {
        Client client = clientRepository.save(new Client(0, "Alex"));
        Account account = accountRepository.save(new Account(0, 100, "1111", client));
        Card card = cardRepository.save(new Card(0, "2222", account));
        assertEquals(card, cardRepository.findById(card.getId()).get());
    }

    @Test
    @Transactional
    public void whenFindByAccountId() {
        Client client = clientRepository.save(new Client(0, "Alex"));
        Account account = accountRepository.save(new Account(0, 100, "1111", client));
        Card card = cardRepository.save(new Card(0, "2222", account));
        assertEquals(Arrays.asList(card), cardRepository.findByAccountId(account.getId()));
    }

    @Test
    @Transactional
    public void whenFindByClientId() {
        Client client = clientRepository.save(new Client(0, "Alex"));
        Account account = accountRepository.save(new Account(0, 100, "1111", client));
        Card card = cardRepository.save(new Card(0, "2222", account));
        assertEquals(Arrays.asList(card), cardRepository.findByClientId(client.getId()));
    }
}