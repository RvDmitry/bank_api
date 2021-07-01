package name.dmitryrazumov.bank.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Class Card
 * Класс характеризует карту клиента.
 * @author Dmitry Razumov
 * @version 1
 */
@Entity
@Table(name = "cards")
public class Card {
    /**
     * Идентификатор карты.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Номер карты.
     */
    private String number;
    /**
     * Счет, к которому относится карта.
     */
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Card() {

    }

    public Card(int id, String number, Account account) {
        this.id = id;
        this.number = number;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Card card = (Card) o;
        return id == card.id
                && Objects.equals(number, card.number)
                && Objects.equals(account, card.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, account);
    }
}
