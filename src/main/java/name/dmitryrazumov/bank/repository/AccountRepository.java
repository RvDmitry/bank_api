package name.dmitryrazumov.bank.repository;

import name.dmitryrazumov.bank.entity.Account;

import java.util.List;
import java.util.Optional;

/**
 * Interface AccountRepository
 * Интерфейс описывает взаимодействие с БД.
 * @author Dmitry Razumov
 * @version 1
 */
public interface AccountRepository {
    /**
     * Метод сохраняет счет в БД.
     * @param account Счет.
     * @return Сохраненный счет.
     */
    Account save(Account account);

    /**
     * Метод возвращает список счетов из БД.
     * @return Список счетов.
     */
    List<Account> findAll();

    /**
     * Метод возвращает из БД счет по его идентификатору.
     * @param id Идентификатор счета.
     * @return Optional содержащий счет, если счет был найден либо пустой.
     */
    Optional<Account> findById(int id);

    /**
     * Метод возвращает из БД список счетов клиента.
     * @param id Идентификатор клиента.
     * @return Список счетов клиента.
     */
    List<Account> findByClientId(int id);

    /**
     * Метод пополняет баланс счета.
     * @param account Счет.
     */
    void addBalance(Account account);
}
