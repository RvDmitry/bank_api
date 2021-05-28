package name.dmitryrazumov.bank.service;

import name.dmitryrazumov.bank.entity.Account;
import name.dmitryrazumov.bank.entity.Client;
import name.dmitryrazumov.bank.model.AccountModel;
import name.dmitryrazumov.bank.model.ClientModel;
import name.dmitryrazumov.bank.repository.AccountRepository;
import name.dmitryrazumov.bank.repository.ClientRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceImplTest {

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private ClientRepository clientRepository;

    @Autowired
    private AccountService accountService;

    private List<Account> accounts;

    @Before
    public void setUp() {
        accounts = Arrays.asList(
                new Account(10, 100, "12345", new Client(1, "Ivan")),
                new Account(20, 200, "12121", new Client(2, "Petr")),
                new Account(30, 300, "79054", new Client(3, "Alex"))
        );
    }

    @Test
    public void whenSave() throws Exception {
        Client client = new Client(1, "Ivan");
        Account account = new Account(10, 400, "123456789", client);
        given(clientRepository.findById(client.getId())).willReturn(Optional.of(client));
        given(accountRepository.save(account)).willReturn(account);
        assertEquals(AccountModel.toModel(account), accountService.save(account));
    }

    @Test(expected = Exception.class)
    public void whenSaveThenException() throws Exception {
        Client client = new Client(1, "Ivan");
        Account account = new Account(10, 400, "123456789", client);
        given(accountRepository.save(account)).willThrow(new Exception());
        accountService.save(account);
    }

    @Test
    public void whenFindAll() {
        given(accountRepository.findAll()).willReturn(accounts);
        List<AccountModel> accountModels = accounts.stream()
                .map(AccountModel::toModel)
                .collect(Collectors.toList());
        assertEquals(accountModels, accountService.findAll());
    }

    @Test
    public void whenFindById() {
        Client client = new Client(20, "Ivan");
        given(accountRepository.findById(1))
                .willReturn(
                        Optional.of(new Account(1, 100, "1234", client))
                );
        Optional<AccountModel> optionalModel = accountService.findById(1);
        assertEquals(1, optionalModel.get().getId());
        assertEquals(100, optionalModel.get().getAmount());
        assertEquals("1234", optionalModel.get().getNumber());
        assertEquals(ClientModel.toModel(client), optionalModel.get().getClient());
    }

    @Test
    public void whenFindByClientId() {
        given(accountRepository.findByClientId(2)).willReturn(Arrays.asList(accounts.get(1)));
        List<AccountModel> accountModels = Arrays.asList(AccountModel.toModel(accounts.get(1)));
        assertEquals(accountModels, accountService.findByClientId(2));
    }
}