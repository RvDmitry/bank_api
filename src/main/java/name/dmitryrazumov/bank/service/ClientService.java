package name.dmitryrazumov.bank.service;

import name.dmitryrazumov.bank.entity.Client;
import name.dmitryrazumov.bank.model.ClientModel;

import java.util.List;
import java.util.Optional;

/**
 * Interface ClientService
 * Интерфейс описывает действия с клиентом.
 * @author Dmitry Razumov
 * @version 1
 */
public interface ClientService {
    /**
     * Метод сохраняет клиента.
     * @param client Клиент.
     * @return Сохраненный клиент.
     */
    ClientModel save(Client client);

    /**
     * Мето возвращает список клиентов.
     * @return Список клиентов.
     */
    List<ClientModel> findAll();

    /**
     * Метод возвращает клиента по его идентификатору.
     * @param id Идентификатор клиента.
     * @return Optional содержащий клиента, если клиент был найден либо пустой.
     */
    Optional<ClientModel> findById(int id);
}
