package name.dmitryrazumov.bank.repository;

import name.dmitryrazumov.bank.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class ClientJdbcTemplate implements ClientRepository {

    private static final String SQL = "SELECT * FROM CLIENTS";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClientJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Client save(Client client) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = cn -> {
            PreparedStatement ps = cn.prepareStatement(
                    "INSERT INTO CLIENTS (NAME) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, client.getName());
            return ps;
        };
        jdbcTemplate.update(psc, keyHolder);
        int id;
        if (keyHolder.getKeys().size() > 1) {
            id = (int) keyHolder.getKeys().get("id");
        } else {
            id = keyHolder.getKey().intValue();
        }
        client.setId(id);
        return client;
    }

    @Override
    public List<Client> findAll() {
        return jdbcTemplate.query(SQL, new BeanPropertyRowMapper<>(Client.class));
    }

    @Override
    public Optional<Client> findById(int id) {
        Client client = null;
        try {
            client = jdbcTemplate.queryForObject(
                    SQL + " WHERE ID = ?",
                    new BeanPropertyRowMapper<>(Client.class),
                    id
            );
        } catch (EmptyResultDataAccessException ex) {
            ex.printStackTrace();
        }
        return Optional.ofNullable(client);
    }
}
