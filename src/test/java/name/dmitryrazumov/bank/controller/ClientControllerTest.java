package name.dmitryrazumov.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import name.dmitryrazumov.bank.entity.Client;
import name.dmitryrazumov.bank.model.ClientModel;
import name.dmitryrazumov.bank.service.ClientService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.anyInt;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {

    private final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Test
    public void whenGetAll() throws Exception {
        List<ClientModel> clients = Arrays.asList(
                new ClientModel(1, "Ivan"),
                new ClientModel(2, "Petr")
        );
        String jsonStr = om.writeValueAsString(clients);
        when(clientService.findAll()).thenReturn(clients);
        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonStr));
        verify(clientService).findAll();
    }

    @Test
    public void whenFindById() throws Exception {
        ClientModel client = new ClientModel(1, "Alex");
        String jsonStr = om.writeValueAsString(client);
        when(clientService.findById(1)).thenReturn(Optional.of(client));
        mockMvc.perform(get("/clients/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonStr));
        verify(clientService).findById(anyInt());
    }

    @Test
    public void whenCreate() throws Exception {
        ClientModel clientModel = new ClientModel(1, "Alex");
        when(clientService.save(any(Client.class))).thenReturn(clientModel);
        mockMvc.perform(post("/clients")
                .content(om.writeValueAsString(clientModel))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(clientModel.getId())))
                .andExpect(jsonPath("$.name", is(clientModel.getName())));
        verify(clientService).save(any(Client.class));
    }
}