package name.dmitryrazumov.bank.service;

import name.dmitryrazumov.bank.model.CardModel;
import name.dmitryrazumov.bank.repository.AccountRepository;
import name.dmitryrazumov.bank.repository.CardRepository;
import name.dmitryrazumov.bank.entity.Account;
import name.dmitryrazumov.bank.entity.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class CardServiceImpl
 *
 * @author Dmitry Razumov
 * @version 1
 */
@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cards;
    private final AccountRepository accounts;

    @Autowired
    public CardServiceImpl(CardRepository cards, AccountRepository accounts) {
        this.cards = cards;
        this.accounts = accounts;
    }

    @Override
    public CardModel save(Card card) throws Exception {
        Account account = card.getAccount();
        if (account == null || !accounts.findById(account.getId()).isPresent()) {
            throw new Exception("Не указан либо неверный идентификатор счета.");
        }
        return CardModel.toModel(cards.save(card));
    }

    @Override
    public List<CardModel> findAll() {
        return cards.findAll().stream()
                .map(CardModel::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CardModel> findById(int id) {
        Optional<CardModel> optionalModel = Optional.empty();
        Optional<Card> optional = cards.findById(id);
        if (optional.isPresent()) {
            optionalModel = Optional.of(CardModel.toModel(optional.get()));
        }
        return optionalModel;
    }

    @Override
    public List<CardModel> findByAccountId(int id) {
        return cards.findByAccountId(id).stream()
                .map(CardModel::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<CardModel> findByClientId(int id) {
        return cards.findByClientId(id).stream()
                .map(CardModel::toModel)
                .collect(Collectors.toList());
    }
}
