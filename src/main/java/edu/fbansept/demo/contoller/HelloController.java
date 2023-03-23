package edu.fbansept.demo.contoller;

import edu.fbansept.demo.dao.UtilisateurDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Access;

@RestController
public class HelloController {

    @Autowired
    private UtilisateurDao utilisateurDao;

    @GetMapping("/")
    public String hello() {
        return "Le serveur marche mais y'a rien ici";
    }
}
