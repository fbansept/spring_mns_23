package edu.fbansept.demo.contoller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.demo.dao.UtilisateurDao;
import edu.fbansept.demo.model.Utilisateur;
import edu.fbansept.demo.security.JwtUtils;
import edu.fbansept.demo.security.MonUserDetails;
import edu.fbansept.demo.view.VueUtilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@CrossOrigin
public class ConnexionController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UtilisateurDao utilisateurDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/connexion")
    public ResponseEntity<String> connexion(@RequestBody Utilisateur utilisateur) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            utilisateur.getEmail(),
                            utilisateur.getMotDePasse()
                    )
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(jwtUtils.generateJwt(new MonUserDetails(utilisateur)), HttpStatus.OK);
    }

    @PostMapping("/inscription")
    @JsonView(VueUtilisateur.class)
    public ResponseEntity<Utilisateur> inscription (@RequestBody Utilisateur utilisateur) {

        if(utilisateur.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(utilisateur.getMotDePasse().length() <= 3) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);

        if(!pattern.matcher(utilisateur.getEmail()).matches()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Utilisateur> optional = utilisateurDao.findByEmail(utilisateur.getEmail());

        if(optional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String passwordHache = passwordEncoder.encode(utilisateur.getMotDePasse());
        utilisateur.setMotDePasse(passwordHache);

        utilisateurDao.save(utilisateur);

        return new ResponseEntity<>(utilisateur,HttpStatus.CREATED);
    }


}
