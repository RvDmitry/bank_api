package name.dmitryrazumov.bank.service;

import name.dmitryrazumov.bank.model.AccountModel;
import name.dmitryrazumov.bank.repository.AccountRepository;
import name.dmitryrazumov.bank.repository.ClientRepository;
import name.dmitryrazumov.bank.entity.Account;
import name.dmitryrazumov.bank.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class AccountServiceImpl
 *
 * @author Dmitry Razumov
 * @version 1
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accounts;
    private final ClientRepository clients;

    @Autowired
    public AccountServiceImpl(AccountRepository accounts, ClientRepository clients) {
        this.accounts = accounts;
        this.clients = clients;
    }

    @Override
    public AccountModel save(Account account) throws Exception {
        Client client = account.getClient();
        if (client == null || !clients.findById(client.getId()).isPresent()) {
            throw new Exception("Не указан либо неверный идентификатор клиента.");
        }
        return AccountModel.toModel(accounts.save(account));
    }

    @Override
    public List<AccountModel> findAll() {
        return accounts.findAll().stream()
                .map(AccountModel::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AccountModel> findById(int id) {
        Optional<AccountModel> optionalModel = Optional.empty();
        Optional<Account> optional = accounts.findById(id);
        if (optional.isPresent()) {
            optionalModel = Optional.of(AccountModel.toModel(optional.get()));
        }
        return optionalModel;
    }

    @Override
    public List<AccountModel> findByClientId(int id) {
        return accounts.findByClientId(id).stream()
                .map(AccountModel::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public void addBalance(Account account) throws Exception {
        if (!accounts.findById(account.getId()).isPresent()) {
            throw new Exception("Неверный идентификатор счета.");
        }
        accounts.addBalance(account);
    }
}
