package obstacles;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import affichage.Dessinable;
import affichage.ZoneDeJeu;
import simonVezina.SVector3d;

/**
 * Classe qui dessine les scies et qui comporte toutes ses caracteristiques
 * 
 * @author Carrie Kam
 * @author Caroline Houle
 * @author Emon Dhar
 */
public class Scie extends Obstacles implements Dessinable {
	private Image img = null;
	private Area aireCercle;
	private int angle=0;

	/**
	 * Constructeur de la classe Scie
	 * 
	 * @param position la position de la scie
	 * @param taille   la taille de la scie
	 */
	// par Carrie
	public Scie(SVector3d position, SVector3d taille) {
		super(position, taille,6);

		super.setPosition(position);
		super.setTaille(taille);
		

		lireImage();
	}

	/**
	 * Methode servant a lire une image //credit c'est Emon qui a change l'image
	 */
	// Caroline Houle
	private void lireImage() {
		URL fich = getClass().getClassLoader().getResource("Scie.png");
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
	 * Methode servant a dessine la scie
	 * 
	 * @param g2d Contexte graphique
	 * @param mat matrice qui sert a change du monde de pixel au monde reel
	 */
	// par Carrie Kam
	public void dessiner(Graphics2D g2d, AffineTransform mat) {
		// application des transformations sur g2d et dessine l'image
		AffineTransform matLocalED = new AffineTransform(mat);
		if(!ZoneDeJeu.simple){
			faireRotationLaScie(matLocalED);
		}
		AffineTransform matLocale = new AffineTransform(matLocalED);
		double factX = super.getTaille().getX() / img.getWidth(null);
		double factY = super.getTaille().getX() / img.getHeight(null);

		matLocale.translate(super.getPosition().getX(), super.getPosition().getY());
		matLocale.scale(factX, factY);
		
		
		g2d.drawImage(img, matLocale, null);

		// creation d'un cercle
		Ellipse2D.Double cercle = new Ellipse2D.Double(super.getPosition().getX(), super.getPosition().getY(),
				super.getTaille().getX(), super.getTaille().getX());

		aireCercle = new Area(matLocalED.createTransformedShape(cercle));
		
		
	}// fin methode


	/**
	 * Methode servant a changer la position de la scie
	 * 
	 * @param position position de la scie
	 */
	//Emon Dhar
	public void setPosition(SVector3d position) {
		super.setPosition(position);
	}

	/**
	 * Methode servant a recevoir la position de la scie
	 * 
	 * @return super.getPosition() position de la scie
	 */
	//Emon Dhar
	public SVector3d getPosition() {
		return super.getPosition();
	}

	/**
	 * Methode servant a changer la taille de la scie
	 * 
	 * @param taille taille de la scie
	 */
	//Emon Dhar
	public void setTaille(SVector3d taille) {
		super.setTaille(taille);
	}

	/**
	 * Methode servant a recevoir la taille de la scie
	 * 
	 * @return super.getTaille() la taille de la scie
	 */
	//Emon Dhar
	public SVector3d getTaille() {
		return super.getTaille();
	}
	/**
	 * Methode permetant de faire des rotation a la scie.
	 * @param matLocalED matrice de transformation
	 */
	//Emon Dhar
	public void faireRotationLaScie( AffineTransform matLocalED) {
		matLocalED.rotate(angle, super.getPosition().x + super.getTaille().x/2, super.getPosition().y + super.getTaille().x/2);
		angle= angle-10;
	}

	/**
	 * Methode servant a retourner l'aire de la scie
	 * 
	 * @return aireCercle aire de la scie
	 */
	//Emon Dhar
	public Area getArea() {
		return aireCercle;
	}
}
