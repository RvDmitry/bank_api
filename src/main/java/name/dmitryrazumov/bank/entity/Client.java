package name.dmitryrazumov.bank.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class Client
 * Класс характеризует клиента.
 * @author Dmitry Razumov
 * @version 1
 */
@Entity
@Table(name = "clients")
public class Client {
    /**
     * Идентификатор клиента.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Имя клиента.
     */
    private String name;
    /**
     * Список счетов клиента.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private List<Account> accounts = new ArrayList<>();

    public Client() {

    }

    public Client(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Client client = (Client) o;
        return id == client.id
                && Objects.equals(name, client.name)
                && Objects.equals(accounts, client.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, accounts);
    }
}
