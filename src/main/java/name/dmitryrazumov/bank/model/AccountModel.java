package name.dmitryrazumov.bank.model;

import name.dmitryrazumov.bank.entity.Account;

import java.util.Objects;

public class AccountModel {

    private int id;

    private int amount;

    private String number;

    private ClientModel client;

    public AccountModel() {

    }

    public AccountModel(int id, int amount, String number, ClientModel client) {
        this.id = id;
        this.amount = amount;
        this.number = number;
        this.client = client;
    }

    public static AccountModel toModel(Account account) {
        AccountModel accountModel = new AccountModel();
        accountModel.setId(account.getId());
        accountModel.setAmount(account.getAmount());
        accountModel.setNumber(account.getNumber());
        accountModel.setClient(ClientModel.toModel(account.getClient()));
        return accountModel;
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

    public ClientModel getClient() {
        return client;
    }

    public void setClient(ClientModel client) {
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
        AccountModel that = (AccountModel) o;
        return id == that.id
                && amount == that.amount
                && Objects.equals(number, that.number)
                && Objects.equals(client, that.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, number, client);
    }
}
