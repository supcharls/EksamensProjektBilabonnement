package org.example.eksamensprojektbilabonnement.Repository;

import org.example.eksamensprojektbilabonnement.Model.Bil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BilRepo
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // hent alle biler
    public List<Bil> hentAlleBiler()
    {
        String sql = "select * from bil";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Bil.class));
    }

    // hent bil med ID
    public Optional <Bil> hentBilMedId(Long id)
    {
        String sql = "select * from bil where bil_id = ?";
        List<Bil> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Bil.class), id);
        return result.stream().findFirst();
    }


    // opret bil
    public void opretBil(Bil bil)
    {
        String sql = "insert into bil (brand, model, nummer_plade, stel_nummer, pris, produktion_aar, braendstoftype, bil_status) values (?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                bil.getBrand(),
                bil.getModel(),
                bil.getNummerPlade(),
                bil.getStelNummer(),
                bil.getPris(),
                bil.getProduktionAar(),
                bil.getBraendstoftype(),
                bil.getBilStatus()
        );
    }



    // opdater bil
    public void opdaterBil(Bil bil)
    {
        String sql = "update bil set brand = ?, model = ?, nummer_plade = ?, stel_nummer = ?, pris = ?, produktion_aar = ?, braendstoftype = ?, bil_status = ? where bil_id = ?";
        jdbcTemplate.update(sql,
                bil.getBrand(),
                bil.getModel(),
                bil.getNummerPlade(),
                bil.getStelNummer(),
                bil.getPris(),
                bil.getProduktionAar(),
                bil.getBraendstoftype(),
                bil.getBilStatus(),
                bil.getBilId()
        );
    }


    // slet bil
    public void sletBil(Long id)
    {
        String sql = "delete from bil where bil_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Bil> findBilerDerKanLejes() {
        String sql = "select * from bil WHERE bil_status IN ('Klar', 'Skadet')";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Bil.class));
    }

    public List<Bil> findBilerDerRedigeres(long BilIdSystemet) {
        String sql = "select * from bil WHERE bil_status IN ('Klar', 'Skadet') OR bil_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Bil.class), BilIdSystemet);
    }

}