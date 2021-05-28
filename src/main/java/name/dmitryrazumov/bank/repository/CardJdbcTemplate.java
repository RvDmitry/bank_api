package name.dmitryrazumov.bank.repository;

import name.dmitryrazumov.bank.entity.Account;
import name.dmitryrazumov.bank.entity.Card;
import name.dmitryrazumov.bank.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class CardJdbcTemplate implements CardRepository {

    private static final String SQL =
            "SELECT C.ID, C.NUMBER, A.ID, A.AMOUNT, A.NUMBER, CL.ID, CL.NAME FROM CARDS C "
                    + "JOIN ACCOUNTS A on A.ID = C.ACCOUNT_ID "
                    + "JOIN CLIENTS CL on A.CLIENT_ID = CL.ID";

    private final JdbcTemplate jdbcTemplate;

    private RowMapper<Card> rowMapper = (rs, row) -> {
        Card card = new Card();
        card.setId(rs.getInt(1));
        card.setNumber(rs.getString(2));
        Account account = new Account();
        account.setId(rs.getInt(3));
        account.setAmount(rs.getInt(4));
        account.setNumber(rs.getString(5));
        Client client = new Client();
        client.setId(rs.getInt(6));
        client.setName(rs.getString(7));
        account.setClient(client);
        card.setAccount(account);
        return card;
    };

    @Autowired
    public CardJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Card save(Card card) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = cn -> {
            PreparedStatement ps = cn.prepareStatement(
                    "INSERT INTO CARDS (NUMBER, ACCOUNT_ID) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, card.getNumber());
            ps.setInt(2, card.getAccount().getId());
            return ps;
        };
        jdbcTemplate.update(psc, keyHolder);
        int id;
        if (keyHolder.getKeys().size() > 1) {
            id = (int) keyHolder.getKeys().get("id");
        } else {
            id = keyHolder.getKey().intValue();
        }
        card.setId(id);
        return card;
    }

    @Override
    public List<Card> findAll() {
        return jdbcTemplate.query(SQL, rowMapper);
    }

    @Override
    public Optional<Card> findById(int id) {
        Card card = null;
        try {
            card = jdbcTemplate.queryForObject(SQL + " WHERE C.ID = ?", rowMapper, id);
        } catch (EmptyResultDataAccessException ex) {
            ex.printStackTrace();
        }
        return Optional.ofNullable(card);
    }

    @Override
    public List<Card> findByAccountId(int id) {
        return jdbcTemplate.query(SQL + " WHERE C.ACCOUNT_ID = ?", rowMapper, id);
    }

    @Override
    public List<Card> findByClientId(int id) {
        return jdbcTemplate.query(SQL + " WHERE A.CLIENT_ID = ?", rowMapper, id);
    }
}
