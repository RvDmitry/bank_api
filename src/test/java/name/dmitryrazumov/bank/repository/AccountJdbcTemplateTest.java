package name.dmitryrazumov.bank.repository;

import name.dmitryrazumov.bank.entity.Account;
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
public class AccountJdbcTemplateTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    @Transactional
    public void whenSave() {
        Client client = clientRepository.save(new Client(0, "Petr"));
        Account account = accountRepository.save(new Account(0, 100, "123", client));
        assertNotEquals(0, account.getId());
    }

    @Test
    @Transactional
    public void whenFindAll() {
        Client firstClient = clientRepository.save(new Client(0, "Petr"));
        Account firstAccount = accountRepository.save(new Account(0, 100, "123", firstClient));
        Client secondClient = clientRepository.save(new Client(0, "Ivan"));
        Account secondAccount = accountRepository.save(new Account(0, 200, "456", secondClient));
        assertEquals(Arrays.asList(firstAccount, secondAccount), accountRepository.findAll());
    }

    @Test
    @Transactional
    public void whenFindById() {
        Client client = clientRepository.save(new Client(0, "Petr"));
        Account account = accountRepository.save(new Account(0, 100, "123", client));
        assertEquals(account, accountRepository.findById(account.getId()).get());
    }

    @Test
    @Transactional
    public void whenFindByClientId() {
        Client client = clientRepository.save(new Client(0, "Petr"));
        Account account = accountRepository.save(new Account(0, 100, "123", client));
        assertEquals(Arrays.asList(account), accountRepository.findByClientId(client.getId()));
    }

    @Test
    @Transactional
    public void whenAddBalance() {
        Client client = clientRepository.save(new Client(0, "Petr"));
        Account account = accountRepository.save(new Account(0, 100, "123", client));
        accountRepository.addBalance(new Account(account.getId(), 200, "", null));
        assertEquals(300, accountRepository.findById(account.getId()).get().getAmount());
    }
}