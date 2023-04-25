package edu.fbansept.demo.contoller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.demo.dao.UtilisateurDao;
import edu.fbansept.demo.model.Role;
import edu.fbansept.demo.model.Utilisateur;
import edu.fbansept.demo.security.JwtUtils;
import edu.fbansept.demo.view.VueUtilisateur;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class UtilisateurController {

    @Autowired
    private UtilisateurDao utilisateurDao;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/utilisateurs")
    @JsonView(VueUtilisateur.class)
    public List<Utilisateur> getUtilisateurs() {
        return utilisateurDao.findAll();
    }

    @GetMapping("/utilisateur/{id}")
    @JsonView(VueUtilisateur.class)
    public ResponseEntity<Utilisateur> getUtilisateur(@PathVariable int id) {

        //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        Optional<Utilisateur> optional = utilisateurDao.findById(id);

        if(optional.isPresent()) {
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/profil")
    @JsonView(VueUtilisateur.class)
    public ResponseEntity<Utilisateur> getProfil(@RequestHeader("Authorization") String bearer) {
        String jwt = bearer.substring(7);
        Claims donnees = jwtUtils.getData(jwt);
        Optional<Utilisateur> utilisateur = utilisateurDao.findByEmail(donnees.getSubject());

        if(utilisateur.isPresent()) {
            return new ResponseEntity<>(utilisateur.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/admin/utilisateur")
    public ResponseEntity<Utilisateur> ajoutUtilisateur(@RequestBody Utilisateur nouvelUtilisateur) {

        //si l'utilisateur fournit possède un id
        if(nouvelUtilisateur.getId() != null) {

            Optional<Utilisateur> optional = utilisateurDao.findById(nouvelUtilisateur.getId());

            //si c'est un update
            if(optional.isPresent()) {

                Utilisateur userToUpdate = optional.get();
                userToUpdate.setNom(nouvelUtilisateur.getNom());
                userToUpdate.setPrenom(nouvelUtilisateur.getPrenom());
                userToUpdate.setEmail(nouvelUtilisateur.getEmail());
                userToUpdate.setPays(nouvelUtilisateur.getPays());

                utilisateurDao.save(userToUpdate);
                return new ResponseEntity<>(nouvelUtilisateur,HttpStatus.OK);
            }

            //si il y a eu une tentative d'insertion d'un utilisateur avec un id qui n'existait pas
            return new ResponseEntity<>(nouvelUtilisateur,HttpStatus.BAD_REQUEST);

        }

        Role role = new Role();
        role.setId(1);
        nouvelUtilisateur.setRole(role);

        String passwordHache = passwordEncoder.encode("root");
        nouvelUtilisateur.setMotDePasse(passwordHache);

        nouvelUtilisateur.setCreatedAt(LocalDate.now());

        utilisateurDao.save(nouvelUtilisateur);
        return new ResponseEntity<>(nouvelUtilisateur,HttpStatus.CREATED);

    }

    @DeleteMapping("/admin/utilisateur/{id}")
    @JsonView(VueUtilisateur.class)
    public ResponseEntity<Utilisateur> supprimeUtilisateur(@PathVariable int id) {

        Optional<Utilisateur> utilisateurAsupprimer = utilisateurDao.findById(id);

        if(utilisateurAsupprimer.isPresent()) {
            utilisateurDao.deleteById(id);

            return new ResponseEntity<>(null,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}