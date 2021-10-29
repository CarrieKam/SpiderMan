package interfaces;

import java.awt.Color;
import java.util.EventListener;

import simonVezina.SVector3d;

/**
 * Interface qui cree des ecouteurs entre les classes choixPersom Normal et
 * ZoneDeJeu afin de recevoir des informations lors d'une itération ou d'une sauvegarde
 * 
 * 
 * @author Philippe Cote
 *
 */
public interface InfoListener extends EventListener {

	// méthode qui passe la couleur et l'affiche de la face de spiderman ou pas
	public void sauvegarderPerso(Color couleur,boolean spiderFace);

	// méthode qui passe l'information du personnage
	public void informationsPerso(SVector3d acceleration, SVector3d vitesse, SVector3d position, double vitesseA, double accelA, double g, double masse, double hauteurDuMonde);
	
	// méthode qui passe l'information si le personnage est mort ou pas
	public void mort(boolean mort);
	
	public void victoire();
	
	

}
