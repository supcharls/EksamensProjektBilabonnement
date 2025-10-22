package org.example.eksamensprojektbilabonnement.Service;


import org.example.eksamensprojektbilabonnement.Model.TilstandsRapport;
import org.example.eksamensprojektbilabonnement.Repository.TilstandsrapportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TilstandsrapportService
{
    @Autowired
    private TilstandsrapportRepo tilstandsrapportRepo;


    // hent alle tilstandsrapporter
    public List<TilstandsRapport> hentAlleTilstandsrapporter() {
        return tilstandsrapportRepo.hentAlleTilstandsrapport();
    }


    // hent tilstandsrapport med ID
    public TilstandsRapport henttilstandsrapportMedId(Long id)
    {
        return tilstandsrapportRepo.hentTilstandsrapportMedId(id);
    }


    // opret tilstandsrapport
    public void opretTilstandsrapport(TilstandsRapport tilstand)
    {
        tilstandsrapportRepo.opretTilstandsrapport(tilstand);
    }


    // opdater tilstandsrapport
    public void opdaterTilstandsrapport(TilstandsRapport tilstand)
    {
        tilstandsrapportRepo.opdaterTilstandsrapport(tilstand);
    }


    // slet tilstandsrapport
    public void sletTilstandsrapport(Long id)
    {
        tilstandsrapportRepo.sletTilstandsrapport(id);
    }

}