package name.dmitryrazumov.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import name.dmitryrazumov.bank.entity.Account;
import name.dmitryrazumov.bank.model.AccountModel;
import name.dmitryrazumov.bank.model.ClientModel;
import name.dmitryrazumov.bank.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.anyInt;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    private final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    public void whenGetAll() throws Exception {
        List<AccountModel> accounts = Arrays.asList(
                new AccountModel(10, 100, "123", new ClientModel(1, "Ivan")),
                new AccountModel(20, 200, "456", new ClientModel(2, "Alex"))
        );
        when(accountService.findAll()).thenReturn(accounts);
        String jsonStr = om.writeValueAsString(accounts);
        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonStr));
        verify(accountService).findAll();
    }

    @Test
    public void whenFindById() throws Exception {
        AccountModel account = new AccountModel(10, 100, "123",
                new ClientModel(1, "Alex"));
        String jsonStr = om.writeValueAsString(account);
        when(accountService.findById(anyInt())).thenReturn(Optional.of(account));
        mockMvc.perform(get("/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonStr));
        verify(accountService).findById(anyInt());
    }

    @Test
    public void whenCreate() throws Exception {
        AccountModel account = new AccountModel(10, 100, "123",
                new ClientModel(1, "Alex"));
        when(accountService.save(any(Account.class))).thenReturn(account);
        mockMvc.perform(post("/accounts")
                .content(om.writeValueAsString(account))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(account.getId())))
                .andExpect(jsonPath("$.amount", is(account.getAmount())))
                .andExpect(jsonPath("$.number", is(account.getNumber())));
        verify(accountService).save(any(Account.class));
    }

    @Test
    public void whenGetByClient() throws Exception {
        List<AccountModel> accounts = Arrays.asList(
                new AccountModel(10, 100, "123", new ClientModel(1, "Ivan"))
        );
        when(accountService.findByClientId(anyInt())).thenReturn(accounts);
        String jsonStr = om.writeValueAsString(accounts);
        mockMvc.perform(get("/accounts/client/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonStr));
        verify(accountService).findByClientId(anyInt());
    }
}