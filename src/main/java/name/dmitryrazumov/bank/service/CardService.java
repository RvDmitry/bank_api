package name.dmitryrazumov.bank.service;

import name.dmitryrazumov.bank.entity.Card;
import name.dmitryrazumov.bank.model.CardModel;

import java.util.List;
import java.util.Optional;

/**
 * Interface CardService
 * Интерфейс описывает действия с картой.
 * @author Dmitry Razumov
 * @version 1
 */
public interface CardService {
    /**
     * Метод сохраняет карту.
     * @param card Карта.
     * @return Сохраненная карта.
     */
    CardModel save(Card card) throws Exception;

    /**
     * Метод возвращает список карт.
     * @return Список карт.
     */
    List<CardModel> findAll();

    /**
     * Метод возврашает карту по ее идентификатору.
     * @param id Идентификатор карты.
     * @return Optional содержащий карту, если карта была найдена либо пустой.
     */
    Optional<CardModel> findById(int id);

    /**
     * Метод возвращает список карт для заданного счета.
     * @param id Идентификатор счета.
     * @return Список карт.
     */
    List<CardModel> findByAccountId(int id);

    /**
     * Метод возвращает список карт для заданного клиента.
     * @param id Идентификатор клиента.
     * @return Список карт.
     */
    List<CardModel> findByClientId(int id);
}
