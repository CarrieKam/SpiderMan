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

import affichage.Corps;
import affichage.Dessinable;
import affichage.ZoneDeJeu;
import simonVezina.SVector3d;

/**
 * Classe qui dessine le champ magnetique et qui comporte toutes ses
 * caracteristiques
 * 
 * @author Carrie Kam
 * @author Caroline Houle
 */
public class ChampMagnetique extends Obstacles implements Dessinable {

	private Image img = null;
	private SVector3d champMagnetique;
	private Area aireCercle;
	private double charge;
	private boolean activer=false;
	private double angle=0;
	private ZoneDeJeu zoneDeJeu;
	/**
	 * Constructeur de la classe du champ magnetique
	 * 
	 * @param position la position du champ magnetique
	 * @param taille   la taille du champ magnetique
	 */
	// par Carrie
	public ChampMagnetique(SVector3d position, SVector3d taille, SVector3d champMagnetique, double charge,
			boolean activer) {
		// public ChampMagnetique(SVector3d position, SVector3d taille) {
		super(position, taille,2);
		// https://pixabay.com/vectors/plus-symbol-math-addition-flat-2718200/

		super.setPosition(position);
		super.setTaille(taille);
		super.setNumeroObstacle(2);
		this.champMagnetique = champMagnetique;
		lireImage();
	}

	/**
	 * Methode permettant de dessiner un champ magnetique
	 * 
	 * @param g2d Contexte graphique
	 * @param mat matrice qui sert a change du monde de pixel au monde reel
	 */
	// Carrie
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

		aireCercle = new Area(mat.createTransformedShape(cercle));

		/*
		 * g2d.setColor(Color.blue); g2d.fill(aireCercle);
		 * 
		 * 
		 * System.out.println("Champ dessine");
		 * System.out.println("La position du champ magnetique est " +
		 * super.getPosition().getX() + " , " + super.getPosition().getY() +
		 * " La taille champ magnetique est " + super.getTaille().getX() + " , " +
		 * super.getTaille().getY());
		 */
		isActiver();
	}
	/**
	 * Methode qui permet de determiner s'il y une collision avec un objet
	 * 
	 * @param objet
	 * @return true s'il y a une collision sinon false
	 */
	//Carrie
	public boolean enCollision(Corps objet) {

		Area intersection = new Area(this.getArea());
		intersection.intersect(objet.getArea());
		if(!intersection.isEmpty()) {
			objet.setChamp(!intersection.isEmpty());
		} else {
			objet.setChamp(false);
		}
		return !intersection.isEmpty();

	}
	
	/**
	 * Methode servant a lire une image
	 */
	// Caroline Houle
	private void lireImage() {
		URL fich = getClass().getClassLoader().getResource("champMagnetique.png");
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
	 * Methode servant a changer la position du champ magnetique
	 * 
	 * @param position position du champ magnetique
	 */
	//Carrie
	public void setPosition(SVector3d position) {
		super.setPosition(position);
	}

	/**
	 * Methode servant a recevoir la position du champ magnetique
	 * 
	 * @return super.getPosition() position du champ magnetique
	 */
	//Carrie
	public SVector3d getPosition() {
		return super.getPosition();
	}

	/**
	 * Methode servant a changer la taille du champ magnetique
	 * 
	 * @param taille taille du champ magnetique
	 */
	//Carrie
	public void setTaille(SVector3d taille) {
		super.setTaille(taille);
	}

	/**
	 * Methode servant a recevoir la taille du champ magnetique
	 * 
	 * @return super.getTaille() la taille du champ magnetique
	 */
	//Carrie
	public SVector3d getTaille() {
		return super.getTaille();
	}

	/**
	 * Methode servant a recevoir le champ magnetique
	 * 
	 * @return champMagnetique champ magnetique de la zone
	 */
	//Carrie
	public SVector3d getChampMagnetique() {
		return champMagnetique;
	}

	/**
	 * Methode servant a modifier le champ magnetique
	 * 
	 * @param champMagnetique champMagnetique pour modifier
	 */
	//Carrie
	public void setChampMagnetique(SVector3d champMagnetique) {
		this.champMagnetique = champMagnetique;
	}

	/**
	 * Methode servant a retourner le champ magnetique
	 * 
	 * @return aireCercle l'aire du champ magnetique
	 */
	//Carrie
	public Area getArea() {
		return aireCercle;
	}

	/**
	 * 
	 * @return the charge
	 */
	//Carrie
	public double getCharge() {
		return charge;
	}

	/**
	 * 
	 * @param charge the charge to set
	 */
	//Carrie
	public void setCharge(double charge) {
		this.charge = charge;
	}

	/**
	 * 
	 * @return the activer
	 */
	//Carrie
	public boolean isActiver() {
		
		return activer;
	}

	/**
	 * Methode permetant de faire des rotation a la scie.
	 * @param matLocalED matrice de transformation
	 */
	//Emon Dhar
	public void faireRotationLaScie( AffineTransform matLocalED) {
		matLocalED.rotate(angle, super.getPosition().x + super.getTaille().x/2, super.getPosition().y + super.getTaille().x/2);
		angle= angle-0.09;
	}
	/**
	 * @param activer the activer to set
	 */
	//Carrie
	public void setActiver(boolean activer) {
		this.activer = activer;
	}

}
