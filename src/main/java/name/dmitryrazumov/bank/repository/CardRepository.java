package name.dmitryrazumov.bank.repository;

import name.dmitryrazumov.bank.entity.Card;

import java.util.List;
import java.util.Optional;

/**
 * Interface CardRepository
 * Интерфейс описывает взаимодействие с БД.
 * @author Dmitry Razumov
 * @version 1
 */
public interface CardRepository {
    /**
     * Метод сохраняет карту в БД.
     * @param card Карта.
     * @return Сохраненная карта.
     */
    Card save(Card card);

    /**
     * Метод возвращает список карт из БД.
     * @return Список карт.
     */
    List<Card> findAll();

    /**
     * Метод возвращает карту из БД по ее идентификатору.
     * @param id Идентификатор карты.
     * @return Optional содержащий карту, если карта была найдена либо пустой.
     */
    Optional<Card> findById(int id);

    /**
     * Метод возвращает список карт из БД для заданного счета.
     * @param id Идентификатор счета.
     * @return Список карт.
     */
    List<Card> findByAccountId(int id);

    /**
     * Метод возвращает список карт из БД для заданного клиента.
     * @param id Идентификатор клиента.
     * @return Список карт.
     */
    List<Card> findByClientId(int id);
}
