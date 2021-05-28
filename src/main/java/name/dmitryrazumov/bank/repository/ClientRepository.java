package name.dmitryrazumov.bank.repository;

import name.dmitryrazumov.bank.entity.Client;

import java.util.List;
import java.util.Optional;

/**
 * Interface ClientRepository
 * Интерфейс описывает взаимодействие с БД.
 * @author Dmitry Razumov
 * @version 1
 */
public interface ClientRepository {
    /**
     * Метод сохраняет клиента в БД.
     * @param client Клиент.
     * @return Сохраненный клиент.
     */
    Client save(Client client);

    /**
     * Метод возвращает список клиентов из БД.
     * @return Список клиентов.
     */
    List<Client> findAll();

    /**
     * Метод возвращает клиента по его идентификатору.
     * @param id Идентификатор клиента.
     * @return Optional содержащий клиента, если клиент был найден либо пустой.
     */
    Optional<Client> findById(int id);
}
