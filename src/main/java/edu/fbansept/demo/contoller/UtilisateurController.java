package edu.fbansept.demo.contoller;

import edu.fbansept.demo.dao.UtilisateurDao;
import edu.fbansept.demo.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UtilisateurController {

    @Autowired
    private UtilisateurDao utilisateurDao;

    @GetMapping("/utilisateurs")
    public List<Utilisateur> getUtilisateurs() {
        return utilisateurDao.findAll();
    }

    @GetMapping("/utilisateur/{id}")
    public Utilisateur getUtilisateurFranck(@PathVariable int id) {

//        Optional<Utilisateur> optional = utilisateurDao.findById(id);
//        return optional.orElse(null);

        return utilisateurDao.findById(id).orElse(null);

    }

    @PostMapping("/utilisateur")
    public boolean ajoutUtilisateur(@RequestBody Utilisateur nouvelUtilisateur) {
        utilisateurDao.save(nouvelUtilisateur);
        return true;
    }

    @DeleteMapping("/utilisateur/{id}")
    public boolean supprimeUtilisateur(@PathVariable int id) {

        utilisateurDao.deleteById(id);
        return true;
    }

}