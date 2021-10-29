package obstacles;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import affichage.Dessinable;
import simonVezina.SVector3d;

/**
 * * Classe qui dessine les laves et qui comporte toutes ses caracteristiques
 * 
 * @author Carrie Kam
 * @author Emon Dhar
 */
public class Lave extends Obstacles implements Dessinable {

	private Image img = null;
	private Area aireRect;

	/**
	 * Constructeur de la classe Lave
	 * 
	 * @param position la position de la lave
	 * @param taille   la taille de la lave
	 */
	// par Carrie
	public Lave(SVector3d position, SVector3d taille) {
		super(position, taille,4);

		super.setPosition(position);
		super.setTaille(taille);

		// https://sftextures.com/2017/07/19/burning-lava-with-yellow-magma-background/
		lireImage();
	}

	/**
	 * Methode permettant de dessiner la lave
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
		Rectangle2D.Double rect = new Rectangle2D.Double(super.getPosition().getX(), super.getPosition().getY(),
				super.getTaille().getX(), super.getTaille().getY());

		aireRect = new Area(mat.createTransformedShape(rect));
	}

	/**
	 * Methode servant a lire une image
	 */
	// Emon Dhar
	private void lireImage() {
		
		
		img = Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource("lave.gif"));
		}
	/**
	 * Methode servant a changer la position de la lave
	 * 
	 * @param position position de la lave
	 */
	//Carrie
	public void setPosition(SVector3d position) {
		super.setPosition(position);
	}

	/**
	 * Methode servant a recevoir la position de la lave
	 * 
	 * @return super.getPosition() position de la lave
	 */
	//Carrie
	public SVector3d getPosition() {
		return super.getPosition();
	}

	/**
	 * Methode servant a changer la taille de la lave
	 * 
	 * @param taille taille de la lave
	 */
	//Carrie
	public void setTaille(SVector3d taille) {
		super.setTaille(taille);
	}

	/**
	 * Methode servant a recevoir la taille de la lave
	 * 
	 * @return super.getTaille() la taille de la lave
	 */
	//Carrie
	public SVector3d getTaille() {
		return super.getTaille();
	}

	/**
	 * Methode servant a retourner l'aire de la lave
	 * 
	 * @return aireRect aire de la lave
	 */
	//Emon Dhar
	public Area getArea() {
		return aireRect;
	}
}
