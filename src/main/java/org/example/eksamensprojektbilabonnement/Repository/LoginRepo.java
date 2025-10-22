package org.example.eksamensprojektbilabonnement.Repository;

import org.example.eksamensprojektbilabonnement.Model.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class LoginRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public LoginRepo(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Login> findByBrugernavn(String brugernavn)
    {
        String sql = "select * from login where brugernavn = ?";
        try
        {
            Login login = jdbcTemplate.queryForObject(sql, new Object[]{brugernavn}, new LoginMapper());
            return Optional.ofNullable(login);
        } catch (Exception e) {
            return Optional.empty(); // not found
        }
    }

    private static class LoginMapper implements RowMapper<Login>
    {
        @Override
        public Login mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            Login login = new Login();
            login.setLoginId(rs.getLong("login_id"));
            login.setBrugernavn(rs.getString("brugernavn"));
            login.setKodeord(rs.getString("kodeord"));

            return login;
        }
    }
}