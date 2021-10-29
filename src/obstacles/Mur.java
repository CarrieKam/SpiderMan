package obstacles;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import affichage.Corps;
import affichage.Dessinable;
import simonVezina.SVector3d;

/**
 * Classe qui dessine les murs et qui comporte toutes ses caracteristiques
 * 
 * @author Carrie Kam
 * @author Caroline Houle
 * @author Emon Dhar
 */

public class Mur extends Obstacles implements Dessinable {

	private Image img = null;
	private Area aireMur;
	/**
	 * Constructeur de la classe Mur
	 * 
	 * @param position la position du mur
	 * @param taille   la taille du mur
	 */
	// Carrie et Emon
	public Mur(SVector3d position, SVector3d taille) {
		super(position, taille,5);
		super.setPosition(position);
		super.setTaille(taille);
		

		// https://goo.gl/images/X6yJgd
		lireImage();
	}
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
	 * Methode permettant de dessiner un mur
	 * 
	 * @param g2d Contexte graphique
	 * @param mat matrice qui sert a change du monde de pixel au monde reel
	 */
	// Carrie
	public void dessiner(Graphics2D g2d, AffineTransform mat) {
		// application des transformations sur g2d et dessine l'image
		AffineTransform matLocale = new AffineTransform(mat);
		double factX = super.getTaille().getX() / img.getWidth(null);
		double factY = super.getTaille().getY() / img.getHeight(null);

		matLocale.translate(super.getPosition().getX(), super.getPosition().getY());
		matLocale.scale(factX, factY);

		g2d.drawImage(img, matLocale, null);

		// creation d'un rectangle

		Rectangle2D.Double rect = new Rectangle2D.Double(super.getPosition().x, super.getPosition().y,
				super.getTaille().x, super.getTaille().y);

		
		aireMur = new Area(mat.createTransformedShape(rect));

	}

	/**
	 * Methode servant a lire une image
	 */
	// Caroline Houle
	private void lireImage() {
		URL fich = getClass().getClassLoader().getResource("mur.png");
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
	 * Methode servant a changer la position du mur
	 * 
	 * @param position position du mur
	 */
	//Carrie Kam
	public void setPositionCactus(SVector3d position) {
		super.setPosition(position);
	}

	/**
	 * Methode servant a recevoir la position du mur
	 * 
	 * @return super.getPosition() position du mur
	 */
	//Carrie Kam
	public SVector3d getPositionCactus() {
		return super.getPosition();
	}

	/**
	 * Methode servant a changer la taille du mur
	 * 
	 * @param taille taille du mur
	 */
	//Carrie Kam
	public void setTailleCactus(SVector3d taille) {
		super.setTaille(taille);
	}

	/**
	 * Methode servant a recevoir la taille du mur
	 * 
	 * @return super.getTaille() la taille du mur
	 */
	// Carrie Kam
	public SVector3d getTailleCactus() {
		return super.getTaille();
	}
	
	/**
	 * Methode qui permet de connaitre l'aire du bloc
	 * @return aireBloc l'aire du bloc
	 */
	//Emon Dhar
	public Area getArea() {

		return aireMur;
	}
}
