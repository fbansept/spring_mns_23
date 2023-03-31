INSERT INTO `pays` (`id`, `nom`) VALUES
(1, 'France'),
(2, 'Allemagne'),
(3, 'Espagne');

INSERT INTO `entreprise` (`id`, `nom`) VALUES
(1, 'Amazon'),
(2, 'Google'),
(3, 'Red hat');

INSERT INTO `emploi` (`id`, `nom`) VALUES
(1, 'Developpeur'),
(2, 'Testeur'),
(3, 'Chef de projet');

INSERT INTO utilisateur(prenom, nom, pays_id, entreprise_id, email, mot_de_passe, admin)
VALUES
("John","DOE",2,1,"jd@a.com","root",0),
("Franck","BANSEPT",3,1,"fb@a.com","root",1);

INSERT INTO `recherche_emploi_utilisateur` (`utilisateur_id`, `emploi_id`) VALUES
(1, 1),
(1, 2),
(2, 2);

