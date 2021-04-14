package com.formation.exemple.model;

import java.util.Map;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.validator.constraints.Length;

public class InputProduct {

	@NotEmpty
	@Length(min = 5, max = 5)
	private String reference;
	@NotEmpty
	private String nom;

	private Float hauteur, largeur, longueur;

	private long id;
	private int availability;
	private String description;
	private double prixUnitaire;
	private int fournisseurId;

	@JsonProperty("dimension")
	private void unpackDimension(Map<String, Object> dimension) {
		this.hauteur = ((Double) dimension.get("hauteur")).floatValue();
		this.largeur = ((Double) dimension.get("largeur")).floatValue();
		this.longueur = ((Double) dimension.get("longueur")).floatValue();
	}

	@JsonProperty("fournisseur")
	private void unpackFournisseur(Map<String, Object> fournisseur) {
		this.fournisseurId = (Integer) fournisseur.get("id");

	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Float getHauteur() {
		return hauteur;
	}

	public void setHauteur(Float hauteur) {
		this.hauteur = hauteur;
	}

	public Float getLargeur() {
		return largeur;
	}

	public void setLargeur(Float largeur) {
		this.largeur = largeur;
	}

	public Float getLongueur() {
		return longueur;
	}

	public void setLongueur(Float longueur) {
		this.longueur = longueur;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getAvailability() {
		return availability;
	}

	public void setAvailability(int availability) {
		this.availability = availability;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrixUnitaire() {
		return prixUnitaire;
	}

	public void setPrixUnitaire(double prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}

	public int getFournisseurId() {
		return fournisseurId;
	}

	public void setFournisseurId(int fournisseurId) {
		this.fournisseurId = fournisseurId;
	}

}