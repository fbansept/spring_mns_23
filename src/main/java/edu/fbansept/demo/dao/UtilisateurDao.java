package edu.fbansept.demo.dao;

import edu.fbansept.demo.model.ImageDto;
import edu.fbansept.demo.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurDao extends JpaRepository<Utilisateur, Integer> {
    Utilisateur findByPrenom(String prenom);

    Optional<Utilisateur> findByEmail(String email);

    @Query("FROM Utilisateur U JOIN U.pays P WHERE P.nom = :toto")
    List<Utilisateur> trouveUtilisateurSelonPays(@Param("toto") String pays);

    @Query("SELECT new edu.fbansept.demo.model.ImageDto(U.id, U.nomImageProfil) FROM Utilisateur U")
    List<ImageDto> testBidon();
}
