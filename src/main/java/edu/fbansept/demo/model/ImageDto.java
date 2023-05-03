package edu.fbansept.demo.model;


import java.io.Serializable;

public class ImageDto implements Serializable {

    public ImageDto() {
    }

    public ImageDto(int idUtilisateur, String imageProfile) {
        this.idUtilisateur = idUtilisateur;
        this.imageProfile = imageProfile;
    }

    private int idUtilisateur;
    private String imageProfile;
}
