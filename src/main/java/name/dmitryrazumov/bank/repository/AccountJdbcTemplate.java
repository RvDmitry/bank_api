package name.dmitryrazumov.bank.repository;

import name.dmitryrazumov.bank.entity.Account;
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
public class AccountJdbcTemplate implements AccountRepository {

    private static final String SQL = "SELECT A.ID, A.AMOUNT, A.NUMBER, C.ID, C.NAME "
            + "FROM ACCOUNTS A, CLIENTS C WHERE A.CLIENT_ID = C.ID";

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Account> rowMapper = (rs, row) -> {
        Account account = new Account();
        account.setId(rs.getInt(1));
        account.setAmount(rs.getInt(2));
        account.setNumber(rs.getString(3));
        Client client = new Client();
        client.setId(rs.getInt(4));
        client.setName(rs.getString(5));
        account.setClient(client);
        return account;
    };

    @Autowired
    public AccountJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account save(Account account) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = cn -> {
            PreparedStatement ps = cn.prepareStatement(
                    "INSERT INTO ACCOUNTS (AMOUNT, NUMBER, CLIENT_ID) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setInt(1, account.getAmount());
            ps.setString(2, account.getNumber());
            ps.setInt(3, account.getClient().getId());
            return ps;
        };
        jdbcTemplate.update(psc, keyHolder);
        int id;
        if (keyHolder.getKeys().size() > 1) {
            id = (int) keyHolder.getKeys().get("id");
        } else {
            id = keyHolder.getKey().intValue();
        }
        account.setId(id);
        return account;
    }

    @Override
    public List<Account> findAll() {
        return jdbcTemplate.query(SQL, rowMapper);
    }

    @Override
    public Optional<Account> findById(int id) {
        Account account = null;
        try {
            account = jdbcTemplate.queryForObject(SQL + " AND A.ID = ?", rowMapper, id);
        } catch (EmptyResultDataAccessException ex) {
            ex.printStackTrace();
        }
        return Optional.ofNullable(account);
    }

    @Override
    public List<Account> findByClientId(int id) {
        return jdbcTemplate.query(SQL + " AND A.CLIENT_ID = ?", rowMapper, id);
    }

    @Override
    public void addBalance(Account account) {
        jdbcTemplate.update(
                "UPDATE ACCOUNTS SET AMOUNT = AMOUNT + ? WHERE ID = ?",
                account.getAmount(),
                account.getId()
        );
    }
}
