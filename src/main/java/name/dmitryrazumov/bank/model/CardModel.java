package name.dmitryrazumov.bank.model;

import name.dmitryrazumov.bank.entity.Card;

import java.util.Objects;

public class CardModel {

    private int id;

    private String number;

    private AccountModel account;

    public CardModel() {

    }

    public CardModel(int id, String number, AccountModel account) {
        this.id = id;
        this.number = number;
        this.account = account;
    }

    public static CardModel toModel(Card card) {
        CardModel cardModel = new CardModel();
        cardModel.setId(card.getId());
        cardModel.setNumber(card.getNumber());
        cardModel.setAccount(AccountModel.toModel(card.getAccount()));
        return cardModel;
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

    public AccountModel getAccount() {
        return account;
    }

    public void setAccount(AccountModel account) {
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
        CardModel cardModel = (CardModel) o;
        return id == cardModel.id
                && Objects.equals(number, cardModel.number)
                && Objects.equals(account, cardModel.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, account);
    }
}
