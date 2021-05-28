package name.dmitryrazumov.bank.service;

import name.dmitryrazumov.bank.entity.Account;
import name.dmitryrazumov.bank.model.AccountModel;

import java.util.List;
import java.util.Optional;

/**
 * Interface AccountService
 * Интерфейс описывает действия со счетом.
 * @author Dmitry Razumov
 * @version 1
 */
public interface AccountService {
    /**
     * Метод сохраняет счет.
     * @param account Счет.
     * @return Сохраненный счет.
     */
    AccountModel save(Account account) throws Exception;

    /**
     * Метод возвращает список счетов.
     * @return Список счетов.
     */
    List<AccountModel> findAll();

    /**
     * Метод возвращает счет по его идентификатору.
     * @param id Идентификатор счета.
     * @return Optional содержащий счет, если счет был найден либо пустой.
     */
    Optional<AccountModel> findById(int id);

    /**
     * Метод возвращает список счетов для заданного клиента.
     * @param id Идентификатор клиента.
     * @return Список счетов.
     */
    List<AccountModel> findByClientId(int id);

    /**
     * Метод пополняет баланс счета.
     * @param account Счет.
     */
    void addBalance(Account account) throws Exception;
}
