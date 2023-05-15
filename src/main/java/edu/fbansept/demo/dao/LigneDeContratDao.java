package edu.fbansept.demo.dao;

import edu.fbansept.demo.model.LigneDeContrat;
import edu.fbansept.demo.model.Pays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LigneDeContratDao extends JpaRepository<LigneDeContrat, LigneDeContrat.IdLignedeContrat> {

}
