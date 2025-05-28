package org.example.eksamensprojektbilabonnement.Repository;

import org.example.eksamensprojektbilabonnement.Model.BrugerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BrugerTypeRepo
{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<BrugerType> getBrugerTypes() {
        String sql = "select * from bruger_type";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BrugerType.class));
    }


    public Optional<BrugerType> hentBrugerTypeMedId(Long id)
    {
        String sql = "select * from brugertype where brugertype_id = ?";
        List<BrugerType> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BrugerType.class), id);
        return result.stream().findFirst();
    }


    public void opretBrugerType(BrugerType brugerType)
    {
        String sql = "insert into brugertype (brugertype_navn) values (?)";
        jdbcTemplate.update(sql, brugerType.getBrugerTypeNavn());
    }


    public void opdaterBil(BrugerType brugerType)
    {
        String sql = "update brugertype set brugertype_navn = ? where bil_id = ?";
        jdbcTemplate.update(sql,
                brugerType.getBrugerTypeNavn()
        );
    }


    public void sletBrugerType(Long id)
    {
        String sql = "delete from brugertype where brugertype_id = ?";
        jdbcTemplate.update(sql, id);
    }
}
