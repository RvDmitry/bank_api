package name.dmitryrazumov.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import name.dmitryrazumov.bank.entity.Card;
import name.dmitryrazumov.bank.model.AccountModel;
import name.dmitryrazumov.bank.model.CardModel;
import name.dmitryrazumov.bank.model.ClientModel;
import name.dmitryrazumov.bank.service.CardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CardControllerTest {

    private final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardService cardService;

    @Test
    public void whenGetAll() throws Exception {
        List<CardModel> cards = Arrays.asList(
                new CardModel(111, "4545",
                        new AccountModel(10, 100, "123",
                                new ClientModel(1, "Ivan"))),
                new CardModel(222, "2323",
                        new AccountModel(20, 200, "789",
                                new ClientModel(2, "Petr")))
        );
        when(cardService.findAll()).thenReturn(cards);
        String jsonStr = om.writeValueAsString(cards);
        mockMvc.perform(get("/cards"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonStr));
        verify(cardService).findAll();
    }

    @Test
    public void whenFindById() throws Exception {
        CardModel card = new CardModel(111, "4545",
                new AccountModel(10, 100, "123",
                        new ClientModel(1, "Ivan")));
        String jsonStr = om.writeValueAsString(card);
        when(cardService.findById(anyInt())).thenReturn(Optional.of(card));
        mockMvc.perform(get("/cards/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonStr));
        verify(cardService).findById(anyInt());
    }

    @Test
    public void whenCreate() throws Exception {
        CardModel card = new CardModel(111, "4545",
                new AccountModel(10, 100, "123",
                        new ClientModel(1, "Ivan")));
        when(cardService.save(any(Card.class))).thenReturn(card);
        mockMvc.perform(post("/cards")
                .content(om.writeValueAsString(card))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(card.getId())))
                .andExpect(jsonPath("$.number", is(card.getNumber())));
        verify(cardService).save(any(Card.class));
    }

    @Test
    public void whenGetByClient() throws Exception {
        List<CardModel> cards = Arrays.asList(
                new CardModel(111, "4545",
                        new AccountModel(10, 100, "123",
                                new ClientModel(1, "Ivan")))
        );
        when(cardService.findByClientId(anyInt())).thenReturn(cards);
        String jsonStr = om.writeValueAsString(cards);
        mockMvc.perform(get("/cards/client/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonStr));
        verify(cardService).findByClientId(anyInt());
    }

    @Test
    public void whenGetByAccount() throws Exception {
        List<CardModel> cards = Arrays.asList(
                new CardModel(111, "4545",
                        new AccountModel(10, 100, "123",
                                new ClientModel(1, "Ivan")))
        );
        when(cardService.findByAccountId(anyInt())).thenReturn(cards);
        String jsonStr = om.writeValueAsString(cards);
        mockMvc.perform(get("/cards/account/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonStr));
        verify(cardService).findByAccountId(anyInt());
    }
}