package edu.fbansept.demo.contoller;

import edu.fbansept.demo.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConnexionController {

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/connexion")
    public String connexion(@RequestBody Utilisateur utilisateur) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        utilisateur.getEmail(),
                        utilisateur.getMotDePasse()
                )
        );

        return null;

    }

}
