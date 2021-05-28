package name.dmitryrazumov.bank.service;

import name.dmitryrazumov.bank.model.ClientModel;
import name.dmitryrazumov.bank.repository.ClientRepository;
import name.dmitryrazumov.bank.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class ClientServiceImpl
 *
 * @author Dmitry Razumov
 * @version 1
 */
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clients;

    @Autowired
    public ClientServiceImpl(ClientRepository clients) {
        this.clients = clients;
    }

    @Override
    public ClientModel save(Client client) {
        return ClientModel.toModel(clients.save(client));
    }

    @Override
    public List<ClientModel> findAll() {
        return clients.findAll().stream()
                .map(ClientModel::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ClientModel> findById(int id) {
        Optional<ClientModel> optionalModel = Optional.empty();
        Optional<Client> optional = clients.findById(id);
        if (optional.isPresent()) {
            optionalModel = Optional.of(ClientModel.toModel(optional.get()));
        }
        return optionalModel;
    }
}
