package name.dmitryrazumov.bank.service;

import name.dmitryrazumov.bank.entity.Client;
import name.dmitryrazumov.bank.model.ClientModel;
import name.dmitryrazumov.bank.repository.ClientRepository;
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
public class ClientServiceImplTest {

    @MockBean
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @Test
    public void whenFindById() {
        given(clientRepository.findById(1)).willReturn(Optional.of(new Client(20, "Ivan")));
        Optional<ClientModel> optionalModel = clientService.findById(1);
        assertEquals(20, optionalModel.get().getId());
        assertEquals("Ivan", optionalModel.get().getName());
    }

    @Test
    public void whenFindAll() {
        List<Client> clients = Arrays.asList(
                new Client(1, "Ivan"),
                new Client(2, "Petr"),
                new Client(3, "Alex")
        );
        given(clientRepository.findAll()).willReturn(clients);
        List<ClientModel> clientModels = clients.stream()
                .map(ClientModel::toModel)
                .collect(Collectors.toList());
        assertEquals(clientModels, clientService.findAll());
    }

    @Test
    public void whenSave() {
        Client client = new Client(0, "Petr");
        given(clientRepository.save(client)).willReturn(client);
        assertEquals(ClientModel.toModel(client), clientService.save(client));
    }
}