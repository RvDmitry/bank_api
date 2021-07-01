package name.dmitryrazumov.bank.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class Account
 * Класс характеризует счет клиента.
 * @author Dmitry Razumov
 * @version 1
 */
@Entity
@Table(name = "accounts")
public class Account {
    /**
     * Идентификатор счета.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Количество средств на счете.
     */
    private int amount;
    /**
     * Номер счета.
     */
    private String number;
    /**
     * Список карт относящихся к счету.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private List<Card> cards = new ArrayList<>();
    /**
     * Клиент, к которому относится счет.
     */
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Account() {

    }

    public Account(int id, int amount, String number, Client client) {
        this.id = id;
        this.amount = amount;
        this.number = number;
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return id == account.id
                && amount == account.amount
                && Objects.equals(number, account.number)
                && Objects.equals(cards, account.cards)
                && Objects.equals(client, account.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, number, cards, client);
    }
}
