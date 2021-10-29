package affichage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import obstacles.FileArrive;
import physique.MoteurPhysique;
import physique.VecteurGraphique;
import simonVezina.SVector3d;

/**
 * Classe qui dessine le personnage et la corde
 * 
 * @author Emon Dhar
 * @author Philippe Cote
 * @author Caroline Houle
 *
 */


public class Personnage extends Corps implements Dessinable {

	// hauteur du monde en metre
	private double HAUTEUR_DU_MONDE;
	
	// masse du personnage
	private double masseEnKg = 5;
	
	
	private double g;
	
	private SVector3d position, vitesse, accel, taille;
	// final double SEUIL = 0.2;
	private Rectangle2D.Double bloc;
	private Ellipse2D.Double cercle, oeil1, oeil2;
	private Path2D.Double ressort, ligneOri, corde;
	private double angle;
	private double longueurCorde;
	private double distanceDuLancer= 12;
	private double COEFFICIENT_PERTE_ENERGIE=0.9999;
	private int compteurPendu = 0;
	private SVector3d origineCorde = new SVector3d(0, 0);
	private double Aaccel, Avitesse, vitesseT;
	private Color couleurPerso;
	private VecteurGraphique vecteur;
	private boolean spiderFace = true;
	private Image img = null;
	private boolean vecteurAfficher=false;
	

	private Area airePerso;

	/**
	 * Constructeur de la classe Personnage
	 * 
	 * @param position position du personnage
	 * @param taille   taille du personnage
	 * @param vitesse  vitesse du personnage
	 * @param accel    acceleration du personnage
	 * @param masse    masse du personnage
	 * @param gravite    gravite que le personnage fait face
	 */
	// par Emon
	public Personnage(SVector3d position, SVector3d taille, SVector3d vitesse, SVector3d accel, double masse,
			double gravite) {

		super(position, taille, vitesse, accel, masse);
		g = gravite;

		couleurPerso = new Color(0, 0, 0, (float) 1);

		lireImage();
	}

	/**
	 * Methhode qui permet de designer le personnage
	 * 
	 * @param g2d Contexte graphique
	 * @param mat matrice qui sert a change du monde de pixel au monde reel
	 */

	// par Philippe Cote
	public void dessiner(Graphics2D g2d, AffineTransform mat, boolean pendu) {

		// Garde en memoire la couleur
		Color temp = g2d.getColor();

		bloc = new Rectangle2D.Double(super.getPosition().getX() + (super.getTaille().x / 2) - 0.5,
				super.getPosition().getY() - 0.4, 1, 0.6);

		cercle = new Ellipse2D.Double(super.getPosition().getX(), super.getPosition().getY(), super.getTaille().x,
				super.getTaille().y);

		
		if (spiderFace) {

			AffineTransform matLocale = new AffineTransform(mat);
			double factX = super.getTaille().getX() / img.getWidth(null);
			double factY = super.getTaille().getY() / img.getHeight(null);

			matLocale.translate(super.getPosition().getX(), super.getPosition().getY());
			matLocale.scale(factX, factY);

			g2d.drawImage(img, matLocale, null);

		} 
		else {
			oeil1 = new Ellipse2D.Double(super.getPosition().getX() + 1, super.getPosition().getY() + 2, 1.2, 0.3);
			oeil2 = new Ellipse2D.Double(super.getPosition().getX() + 3, super.getPosition().getY() + 2, 1.2, 0.3);
			g2d.setColor(couleurPerso);
			g2d.fill(mat.createTransformedShape(cercle));

			g2d.setColor(Color.LIGHT_GRAY);
			g2d.fill(mat.createTransformedShape(bloc));

			// Creer une corde lorsqu'on appuie sur ESPACE

			g2d.setColor(Color.RED);
			g2d.fill(mat.createTransformedShape(oeil1));
			g2d.fill(mat.createTransformedShape(oeil2));
		}

		if (pendu) {
			g2d.setColor(Color.WHITE);
			corde = new Path2D.Double();

			corde.moveTo(origineCorde.getX(), origineCorde.getY());

			corde.lineTo(super.getPosition().getX() + (super.getTaille().x / 2), super.getPosition().getY());

			g2d.draw(mat.createTransformedShape(corde));
			compteurPendu++;
		}

		airePerso = new Area(mat.createTransformedShape(cercle));
		
		if (vecteurAfficher) {
			vecteur= new VecteurGraphique(super.getVitesseInit(),super.getPosition(),super.getTaille().getX());
			
			vecteur.dessiner(g2d, mat);
		}
		

		// Remet la couleur de depart stocke auparavant
		g2d.setColor(temp);
	}

//	

	/**
	 * Methode qui permet de determiner s'il y une collision avec un objet
	 * 
	 * @param objet
	 * @return true s'il y a une collision sinon false
	 */
	//Emon
	public boolean enCollision(Corps objet) {

		Area intersection = new Area(this.getArea());
		intersection.intersect(objet.getArea());
		if(!intersection.isEmpty()) {
		

			
		}
		return !intersection.isEmpty();

	}
	/**
	 * Methode qui appelle le moteur physique en lui donnnant en parametres les
	 * variables souhaitees
	 * 
	 * @param deltaT, le temps avec lequel on souhaite faire nos calculs
	 * @param pendu, retourne si le personnage est pendu ou pas
	 */
	// auteur Philippe Cote

	public void unPasEuler(double deltaT, boolean pendu) {
		// vieillePositionX=position.getX();

		if (pendu) {
			// System.out.println(Avitesse);
			if (compteurPendu == 0) {
				initialiserPendule();
				
			}
			
			calculerPendule(deltaT);
			
			calculerVitesseRectiligne();

		} else {
		
			
			MoteurPhysique.unPasEuler(deltaT, super.getPosition(), super.getVitesseInit(), super.getAccel(),
					super.getMasseEnKg(), g, super.getChamp());
			compteurPendu = 0;

		}

		System.out.println("vitesse "+super.getVitesseInit() );
		
		//super.setVitesseInit(super.getVitesseInit().multiply(COEFFICIENT_PERTE_ENERGIE));

	}

	/**
	 * Utilise la vitesse angulaire pour calculer la vitesse rectiligne
	 */
	// par Philippe Cote
	
	private void calculerVitesseRectiligne() {
		
		vitesseT = longueurCorde * Avitesse;

		
		vitesse.setX(Math.cos(angle) * vitesseT);
		vitesse.setY(Math.sin(angle) * -vitesseT);
	
		super.setVitesseInit(vitesse);
}

	/**
	 * Calcule la vitesse et acceleration angulaire pour le pendule 
	 * 
	 * @param deltaT le nombre de secondes pour lequel la prochaine itï¿½ration est calculï¿½
	 */
	// par Philippe Cote
	
	
	private void calculerPendule(double deltaT) {
		
		position = new SVector3d(super.getPosition().getX(), super.getPosition().getY());

		vitesse = new SVector3d(super.getVitesseInit().getX(), super.getVitesseInit().getY());
		
		super.setAccelAng((-9.8 * masseEnKg / longueurCorde) * Math.sin(angle));

		Avitesse= super.getVitesseAng();
		
		Avitesse += (super.getAccelAng() * deltaT);
		
		angle += (Avitesse * deltaT);

		//	Avitesse *= COEFFICIENT_PERTE_ENERGIE;
		
		super.setVitesseAng(Avitesse);

		position.setX(origineCorde.getX() + Math.sin(angle) * longueurCorde - 2.5);
		
		position.setY(origineCorde.getY() + Math.cos(angle) * longueurCorde);

		super.setVitesseInit(vitesse);
		super.setPosition(position);
}

	/**
	 * Calcule la longueur de la corde dependament de son point d'origine
	 * 
	 * @return retourne la longueur de la corde actuelle
	 */
	// par Philippe Cote
	private double calculerLongueurCorde() {
		// TODO Auto-generated method stub
		double x = distanceDuLancer;
		double y = super.getPosition().getY();

		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	/**
	 * Calcule l'angle entre la corde et un droite imaginaire perpendiculaire au plafond
	 * 
	 */
	// par Philippe Cote
	
	private void calculerAngle() {
		double x = distanceDuLancer;
		double y = super.getPosition().getY();
		// angle=Math.toRadians(90+Math.toDegrees(Math.atan(y/x)));
		angle = Math.atan(y / x) - (Math.PI / 2);
	}

	/**
	 * Associe une hauteur, ou modifie l'hauteur courante
	 * 
	 * @param hauteur
	 */
	// auteur Emon Dhar
	public void setHauteur(double hauteur) {
		HAUTEUR_DU_MONDE = hauteur;
	}

	
	/**
	 * Initialise les différents paramètres pour le bon fonctionnement du pendule
	 * comme l'angle, longueur de la corde, acceleration angulaire ,vitesse angulaire et les coordonées de l'origine de la corde
	 * 
	 */
	// par Philippe Cote
	
	public void initialiserPendule() {
		longueurCorde = calculerLongueurCorde();
		calculerAngle();
		
		Aaccel = super.getAccel().modulus() / longueurCorde;
		Avitesse = super.getVitesseInit().modulus() / longueurCorde;

		super.setVitesseAng(Avitesse);
		super.setAccelAng(Aaccel);
		origineCorde = new SVector3d(
				super.getPosition().getX() + (super.getTaille().x / 2) + distanceDuLancer, 0);
	}

	/**
	 * 
	 * Méthode qui vérifie si le personnage est arrive a la file d'arrivé
	 * 
	 */
	// par Philippe Cote
	
	public boolean arrive(FileArrive file) {
		
		if (file.getPositionX() >= super.getPosition().getX() + (super.getTaille().getX() / 2)) {
	
			return false;
		} else {
			return true;
		}

	}

	/**
	 * Change la couleur personnalisé du personnage
	 * 
	 * @param couleur
	 */
	// par Philippe Cote
	public void setColor(Color couleur) {
		// TODO Auto-generated method stub
		couleurPerso = couleur;
	}

	/**
	 * Retourne la vitesse angulaire
	 * 
	 * @return super.getVitesseAng() vitesse angulaire
	 */
	// auteur Philippe Cote
	public double getVitesseAngulaire() {
		// TODO Auto-generated method stub
		return super.getVitesseAng();
	}

	/**
	 * Retourne l'acceleration angulaire
	 * 
	 * @return super.getAccelAng() Acceleration angulaire
	 */
	// auteur Philippe Cote
	public double getAccelerationAngulaire() {
		// TODO Auto-generated method stub
		return super.getAccelAng();
	}

	/**
	 * Methode qui permet de connaitre l'aire du bloc
	 * 
	 * @return aireBloc, l'aire du bloc
	 */
	// Emon Dhar
	public Area getArea() {

		return airePerso;
	}

	/**
	 * Retourne la couleur personnalisé
	 * 
	 * @return couleurPerso La couleur personnalisé choisie par l'utilisateur
	 */
	// auteur Philippe Cote
	
	public Color getColor() {
		// TODO Auto-generated method stub
		return couleurPerso;
	}
	

	/**
	 * Methode servant a lire une image
	 */
	// Caroline Houle
	private void lireImage() {
		URL fich = getClass().getClassLoader().getResource("spidermanFace_burned.png");
		// http://clipart-library.com/clipart/piodBg4bT.htm
		if (fich == null) {
			JOptionPane.showMessageDialog(null, "Fichier d'image introuvable!");
		} else {
			try {
				img = ImageIO.read(fich);
			} catch (IOException e) {
				System.out.println("Erreur de lecture du fichier d'image");
			}
		}
	}

	/**
	 * Change la variable spiderFace
	 * 
	 * @param valeur Si on affiche l'image de Spider-Man sur le personnage ou pas
	 */
	// auteur Philippe Cote
	
	public void setSpiderFace(boolean valeur) {
		// TODO Auto-generated method stub
		spiderFace = valeur;
		
	}
	
	/**
	 * Change la variable vecteurAfficher, qui affiche ou pas le vecteur vitesse sur le personnage
	 * 
	 * @param b Si on affiche le vecteur vitesse
	 */
	// auteur Philippe Cote
	public void afficherVecteur(boolean b) {
		vecteurAfficher=b;
	}

	/**
	 * Change la diatance de lancer de la corde
	 * 
	 * @param valeur le nombre de mètres entre le personnage et le point d'origine de la corde
	 */
	// auteur Philippe Cote
	public void setDstLancer(double valeur) {
		// TODO Auto-generated method stub
		distanceDuLancer=valeur;
		
		
	}
	
}
