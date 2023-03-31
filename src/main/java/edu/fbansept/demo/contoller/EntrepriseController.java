package edu.fbansept.demo.contoller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.demo.dao.EntrepriseDao;
import edu.fbansept.demo.model.Entreprise;
import edu.fbansept.demo.view.VueEntreprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EntrepriseController {

    @Autowired
    private EntrepriseDao entrepriseDao;

    @GetMapping("/entreprises")
    @JsonView(VueEntreprise.class)
    public List<Entreprise> listeEntreprise () {

        return entrepriseDao.findAll();
    }

    @GetMapping("/entreprise/{id}")
    @JsonView(VueEntreprise.class)
    public Entreprise getEntreprise(@PathVariable int id) {

        Entreprise entreprise = entrepriseDao
                .findById(id)
                .orElse(null);

        return entreprise;
    }

    @DeleteMapping("/admin/entreprise/{id}")
    public boolean supprimeEntreprise(@PathVariable int id) {
        entrepriseDao.deleteById(id);
        return true;
    }

    @PostMapping("/admin/entreprise")
    public Entreprise enregistrerEntreprise(@RequestBody Entreprise entrepriseAenregistrer) {

        entrepriseDao.save(entrepriseAenregistrer);

        return entrepriseAenregistrer;
    }


}
