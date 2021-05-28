package name.dmitryrazumov.bank.repository;

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
public class ClientJdbcTemplateTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    @Transactional
    public void whenSave() {
        Client client = clientRepository.save(new Client(0, "Ivan"));
        assertNotEquals(0, client.getId());
    }

    @Test
    @Transactional
    public void whenFindAll() {
        Client first = clientRepository.save(new Client(0, "Ivan"));
        Client second = clientRepository.save(new Client(0, "Petr"));
        Client third = clientRepository.save(new Client(0, "Alex"));
        assertEquals(Arrays.asList(first, second, third), clientRepository.findAll());
    }

    @Test
    @Transactional
    public void whenFindById() {
        Client client = clientRepository.save(new Client(0, "Ivan"));
        assertEquals(client, clientRepository.findById(client.getId()).get());
    }
}