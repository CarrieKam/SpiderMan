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

import affichage.Dessinable;
import simonVezina.SVector3d;

/**
 * Classe qui dessine les cactus et qui comporte toutes ses caracteristiques
 * 
 * @author Carrie Kam
 * @author Caroline Houle
 */
public class Cactus extends Obstacles implements Dessinable {

	private Image img = null;
	private Area aireRect;

	/**
	 * Constructeur de la classe Cactus
	 * 
	 * @param position la position du cactus
	 * @param taille   la taille du cactus
	 */
	// par Carrie
	public Cactus(SVector3d position, SVector3d taille) {
		super(position, taille,1);
		super.setPosition(position);
		super.setTaille(taille);

		// https://fr.wikipedia.org/wiki/Fichier:Twemoji2_1f335.svg
		lireImage();
	}

	/**
	 * Methode servant a lire une image
	 */
	// Caroline Houle
	private void lireImage() {
		URL fich = getClass().getClassLoader().getResource("cactus.png");
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
	 * Methode permettant de dessiner le cactus
	 * 
	 * @param g2d Contexte graphique
	 * @param mat matrice qui sert a change du monde de pixel au monde reel
	 */
	// par Carrie
	public void dessiner(Graphics2D g2d, AffineTransform mat) {
		// application des transformations sur g2d et dessine l'image
		AffineTransform matLocale = new AffineTransform(mat);
		double factX = super.getTaille().getX() / img.getWidth(null);
		double factY = super.getTaille().getY() / img.getHeight(null);

		matLocale.translate(super.getPosition().getX(), super.getPosition().getY());
		matLocale.scale(factX, factY);

		g2d.drawImage(img, matLocale, null);

		// creation de trois rectangles
		Rectangle2D.Double rect = new Rectangle2D.Double(super.getPosition().getX(), super.getPosition().getY(),
				super.getTaille().getX(), super.getTaille().getY());

		aireRect = new Area(mat.createTransformedShape(rect));

	}

	/**
	 * Methode servant a changer la position du cactus
	 * 
	 * @param position position du cactus
	 */
	//Carrie
	public void setPosition(SVector3d position) {
		super.setPosition(position);
	}

	/**
	 * Methode servant a recevoir la position du cactus
	 * 
	 * @return super.getPosition() position du cactus
	 */
	//Carrie
	public SVector3d getPosition() {
		return super.getPosition();
	}

	/**
	 * Methode qui retourne l'aire du cactus
	 * 
	 * @return aireRect Le area du cactus
	 */
	//Carrie
	public Area getArea() {
		return aireRect;
	}

	/**
	 * Methode servant a changer la taille du cactus
	 * 
	 * @param taille taille du cactus
	 */
	//Carrie
	public void setTaille(SVector3d taille) {
		super.setTaille(taille);
	}

	/**
	 * Methode servant a recevoir la taille du cactus
	 * 
	 * @return super.getTaille() la taille du cactus
	 */
	//Carrie
	public SVector3d getTaille() {
		return super.getTaille();
	}
}
