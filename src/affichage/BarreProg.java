package affichage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import physique.MoteurPhysique;
import simonVezina.SVector3d;
/**
 * Cette classe permet de dessigner la barre de progression ilustrant l'energie potentielle et cinetique
 * @author Emon
 *
 */ 

public class BarreProg extends JPanel    {


	private double HAUTEUR_DU_MONDE;
	private double LARGEUR_DU_MONDE;
	

	private double masse, gravite;


	private SVector3d positionBarre, tailleBarre, vitesseDuPerso, positionDuPerso;
	private Rectangle2D.Double rectEnergiePotentielleGrav, rectEnergieCinetique;
	private double hauteurDuMonde, energiePotentielle, energiePotentielleMax;

	
	private Area airePerso;

	/**
	 * Constructeur de la classe Personnage
	 * 
	 * @param positionBarre position de la barre
	 * @param tailleBarre   taille de la barre
	 * @param positionDuPerso    position du personnage
	 * @param vitesseDuPerso  vitesse du personnage
	 * @param masse    masse du personnage
	 * @param gravite    gravite que le personnage fait face
	 * @param hauteurDuMonde   hauteur du monde 
	 */

	public BarreProg(SVector3d positionBarre, SVector3d tailleBarre, SVector3d positionDuPerso, SVector3d vitesseDuPerso, double masse, double gravite, double hauteurDuMonde) {
		this.positionBarre = positionBarre;
		this.tailleBarre = tailleBarre;
		this.positionDuPerso = positionDuPerso;
		this.vitesseDuPerso = vitesseDuPerso;
		this.masse = masse;
		this.gravite = gravite;
		this.hauteurDuMonde = hauteurDuMonde;
	}

	/**
	 * Methhode qui permet de designer le personnage
	 * 
	 * @param g2d Graphics 2d
	 * @param mat matrice
	 */

	// par Emon Dhar
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		// Garde en memoire la couleur
		Color temp = g2d.getColor();
		energiePotentielleMax= MoteurPhysique.energiePotentielleGrav(masse, gravite, hauteurDuMonde, new SVector3d(0,0));
		energiePotentielle = MoteurPhysique.energiePotentielleGrav(masse, gravite, hauteurDuMonde, positionDuPerso);
		double pourcentageEnergiePoten = energiePotentielle/energiePotentielleMax;
		
		
		rectEnergiePotentielleGrav  = new Rectangle2D.Double(positionBarre.x, positionBarre.y, tailleBarre.x, pourcentageEnergiePoten*tailleBarre.y);
			rectEnergieCinetique  = new Rectangle2D.Double(positionBarre.x, positionBarre.y, tailleBarre.x,  tailleBarre.y);
		if(energiePotentielle<=0.005 ) {
			//setBackground(new Color(29,53,87)); //arrire-plan
//			g2d.setColor(new Color(230,57,70));
//			g2d.drawString("Valeur invalide", 20, 20);
			rectEnergiePotentielleGrav  = new Rectangle2D.Double(positionBarre.x, positionBarre.y, tailleBarre.x, 0);
			
			g2d.setColor(new Color(168,218,220));
			g2d.fill(rectEnergieCinetique);
			g2d.setColor(new Color(230,57,70));
			g2d.fill(rectEnergiePotentielleGrav);
			
			g2d.setColor(new Color(230,57,70));
			g2d.drawString(  "0 J,  l'energie potentielle gravitationnelle", (int)(positionBarre.x+tailleBarre.x+3), (int)(positionBarre.y));

			g2d.setColor(new Color(168,218,220));
			g2d.drawString(String.format(" %.1f", energiePotentielleMax) +" J,  l'energie cinetique", (int)((positionBarre.x+tailleBarre.x+3)), (int)(positionBarre.y+pourcentageEnergiePoten*tailleBarre.y));

			
		}
		if((energiePotentielleMax-energiePotentielle)<=0 ) {
			rectEnergiePotentielleGrav  = new Rectangle2D.Double(positionBarre.x, positionBarre.y, tailleBarre.x, tailleBarre.y);
			
			
			g2d.setColor(new Color(168,218,220));
			g2d.fill(rectEnergieCinetique);
			g2d.setColor(new Color(230,57,70));
			g2d.fill(rectEnergiePotentielleGrav);
			
			g2d.setColor(new Color(230,57,70));
			g2d.drawString(  String.format(" %.1f", energiePotentielleMax) + " J,  l'energie potentielle gravitationnelle", (int)(positionBarre.x+tailleBarre.x+3), (int)(positionBarre.y));

			g2d.setColor(new Color(168,218,220));
			g2d.drawString( 0 +" J,  l'energie cinetique", (int)((positionBarre.x+tailleBarre.x+3)), (int)(tailleBarre.y));

			
		}
		else {

			//setBackground(new Color(29,53,87));

			



			g2d.setColor(new Color(168,218,220));
			g2d.fill(rectEnergieCinetique);
			g2d.setColor(new Color(230,57,70));
			g2d.fill(rectEnergiePotentielleGrav);		
			

			g2d.setColor(new Color(230,57,70));
			g2d.drawString( String.format(" %.1f", (energiePotentielle)) + " J,  l'energie potentielle gravitationnelle", (int)(positionBarre.x+tailleBarre.x+3), (int)(positionBarre.y));

			g2d.setColor(new Color(168,218,220));
			g2d.drawString(String.format(" %.1f", energiePotentielleMax-(energiePotentielle)) +" J,  l'energie cinetique", (int)((positionBarre.x+tailleBarre.x+3)), (int)(positionBarre.y+pourcentageEnergiePoten*tailleBarre.y));


		}
		// Remet la couleur de depart stocke auparavant
		g2d.setColor(temp);
	}

	//	

	/**
	 * Methode qui appelle le moteur physique en lui donnnant en parametres les
	 * variables souhaitees
	 * 
	 * @param deltaT, le temps avec lequel on souhaite faire nos calculs
	 * @param pendu, retourne si le personnage est pendu ou pas
	 */

	public void unPasEuler(double deltaT, boolean pendu) {

	}
	
	
	
	/**
	 * Methode qui retourne la masse du personage
	 * @return masse la masse du personage
	 */
	public double getMasse() {
		return masse;
	}

	/**
	 * Methode qui modifie la masse du personnage
	 * @param masse la nouvelle masse
	 */
	public void setMasse(double masse) {
		this.masse = masse;
	}

	/**
	 * Methode qui retourne la gravite
	 * @return gravite la gravite
	 */
	public double getGravite() {
		return gravite;
	}

	/**
	 * Methode qui modifie la gravite
	 * @param gravite la nouvelle gravite
	 */
	public void setGravite(double gravite) {
		this.gravite = gravite;
	}

	
	/**
	 * Methode qui retourne la vitesse du personnage
	 * @return vitesseDuPerso la vitesse du personnage
	 */
	public SVector3d getVitesseDuPerso() {
		return vitesseDuPerso;
	}

	/**
	 * Methode qui modifie  la viesse du personnage
	 * @param vitesseDuPerso la nouvelle vitesse du personage
	 */
	public void setVitesseDuPerso(SVector3d vitesseDuPerso) {
		this.vitesseDuPerso = vitesseDuPerso;
	}

	/**
	 * Methode qui retourne la pososition du personnage
	 * @return the positionDuPerso
	 */
	public SVector3d getPositionDuPerso() {
		return positionDuPerso;
	}

	/**
	 * Methode qui modifie la position du personnage
	 * @param positionDuPerso the positionDuPerso to set
	 */
	public void setPositionDuPerso(SVector3d positionDuPerso) {
		this.positionDuPerso = positionDuPerso;
	}

	/**
	 * Methode qui retourne la hauteur du monde
	 * @return the hauteurDuMonde
	 */
	public double getHauteurDuMonde() {
		return hauteurDuMonde;
	}

	

	

	/**
	 * Associe une hauteur, ou modifie la hauteur courante
	 * 
	 * @param hauteur
	 */
	
	public void setHauteur(double hauteur) {
		HAUTEUR_DU_MONDE = hauteur;
	}
	



	/**
	 * Methode qui retourne la position du Barre
	 * @return positionBarre la position du barre
	 */
	public SVector3d getPositionBarre() {
		return positionBarre;
	}

	/** Methode qui modifie la position du Barre
	 * @param positionBarre la nouvelle position du barre 
	 */
	public void setPositionBarre(SVector3d positionBarre) {
		this.positionBarre = positionBarre;
	}

	/**
	 * Methode qui retourne la taille du barre
	 * @return tailleBarre la taille du barre
	 */
	public SVector3d getTailleBarre() {
		return tailleBarre;
	}

	/**
	 * Methode qui modifie la taille du barre
	 * @param tailleBarre la nouvelle taille
	 */
	public void setTailleBarre(SVector3d tailleBarre) {
		this.tailleBarre = tailleBarre;
	}

	
	

	/**
	 * Methode qui modifie l'hauteur du monde
	 * @param hauteurDuMonde la nouvelle hauteur
	 */
	public void setHauteurDuMonde(double hauteurDuMonde) {
		this.hauteurDuMonde = hauteurDuMonde;
	}

	/**
	 * Methode qui retourne l'energie potencielle
	 * @return  energiePotencielle l'Energie potencielle
	 */
	public double getEnergiePotencielle() {
		return energiePotentielle;
	}

	/**
	 * Methode qui modifie l'energie potentielle
	 * @param energiePotencielle la nouvelle energie potentielle
	 */
	public void setEnergiePotencielle(double energiePotencielle) {
		this.energiePotentielle = energiePotencielle;
	}

	/**
	 *  Methode qui retourne energie potencielle maximum
	 * @return the energiePotencielleMax l'energie potencielle maximum
	 */
	public double getEnergiePotencielleMax() {
		return energiePotentielleMax;
	}

	/**
	 * Methode qui modifie l'energie potencielle maximum
	 * @param energiePotencielleMax la nouvelle energie potentielle maximum
	 */
	public void setEnergiePotencielleMax(double energiePotencielleMax) {
		this.energiePotentielleMax = energiePotencielleMax;
	}

}
