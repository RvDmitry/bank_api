package name.dmitryrazumov.bank.service;

import name.dmitryrazumov.bank.entity.Account;
import name.dmitryrazumov.bank.entity.Card;
import name.dmitryrazumov.bank.entity.Client;
import name.dmitryrazumov.bank.model.AccountModel;
import name.dmitryrazumov.bank.model.CardModel;
import name.dmitryrazumov.bank.model.ClientModel;
import name.dmitryrazumov.bank.repository.AccountRepository;
import name.dmitryrazumov.bank.repository.CardRepository;
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
public class CardServiceImplTest {

    @MockBean
    private CardRepository cardRepository;

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private CardService cardService;

    private List<Card> cards;

    @Before
    public void setUp() {
        cards = Arrays.asList(
                new Card(1, "1234",
                        new Account(10, 100, "12345",
                                new Client(1, "Ivan"))),
                new Card(2, "4567",
                        new Account(20, 200, "12121",
                                new Client(2, "Petr"))),
                new Card(3, "9803",
                        new Account(30, 300, "79054",
                                new Client(3, "Alex")))
        );
    }

    @Test
    public void whenSave() throws Exception {
        Client client = new Client(123, "Alex");
        Account account = new Account(100, 400, "1234", client);
        Card card = new Card(1, "12345", account);
        given(accountRepository.findById(account.getId())).willReturn(Optional.of(account));
        given(cardRepository.save(card)).willReturn(card);
        assertEquals(CardModel.toModel(card), cardService.save(card));
    }

    @Test(expected = Exception.class)
    public void whenSaveThenException() throws Exception {
        Card card = new Card(1, "12345", null);
        given(cardRepository.save(card)).willThrow(new Exception());
        cardService.save(card);
    }

    @Test
    public void whenFindAll() {
        given(cardRepository.findAll()).willReturn(cards);
        List<CardModel> cardModels = cards.stream()
                .map(CardModel::toModel)
                .collect(Collectors.toList());
        assertEquals(cardModels, cardService.findAll());
    }

    @Test
    public void whenFindById() {
        Account account = new Account(10, 100, "12345", new Client(1, "Ivan"));
        Card card = new Card(1, "1234", account);
        given(cardRepository.findById(1)).willReturn(Optional.of(card));
        Optional<CardModel> optionalModel = cardService.findById(1);
        assertEquals(1, optionalModel.get().getId());
        assertEquals("1234", optionalModel.get().getNumber());
        assertEquals(AccountModel.toModel(account), optionalModel.get().getAccount());
    }

    @Test
    public void whenFindByAccountId() {
        given(cardRepository.findByAccountId(10)).willReturn(Arrays.asList(cards.get(0)));
        List<CardModel> cardModels = Arrays.asList(CardModel.toModel(cards.get(0)));
        assertEquals(cardModels, cardService.findByAccountId(10));
    }

    @Test
    public void whenFindByClientId() {
        given(cardRepository.findByClientId(1)).willReturn(Arrays.asList(cards.get(0)));
        List<CardModel> cardModels = Arrays.asList(CardModel.toModel(cards.get(0)));
        assertEquals(cardModels, cardService.findByClientId(1));
    }
}