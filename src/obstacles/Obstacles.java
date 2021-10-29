package obstacles;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.io.Serializable;
import java.util.ArrayList;

import affichage.Corps;
import interfaces.InfoListener;
import simonVezina.SVector3d;

/**
 * Classe servant a implementer ses caracteristiques a tous les classes qui
 * l'herite
 * 
 * @author Carrie Kam
 * @author Emon Dhar
 */
public class Obstacles extends Corps implements Serializable  {
	private ArrayList<InfoListener> listeEcouteurs = new ArrayList<InfoListener>();
	private boolean isCollision = false;
	private int numeroObstacle;


	/**
	 * le constructeur de la classe Obstacles
	 * 
	 * @param position la position de l'obstacle
	 * @param taille   la taille de l'obstacle
	 */
	// Par Carrie Kam
	public Obstacles(SVector3d position, SVector3d taille) {
		

	}

	/**
	 * le constructeur de la classe Obstacles permertant de choisir le type
	 * d'obstacle
	 * 
	 * @param position       la position de l'obstacle
	 * @param taille         la taille de l'obstacle
	 * @param numeroObstacle le type d'obstacle (1= cactus; 2= Champ Magnetique;
	 *                       3=File Darriver; 4=Lave; 5= Mur 6= scie)
	 */
	// par Emon Dhar
	public Obstacles(SVector3d position, SVector3d taille, int numeroObstacle) {
		super(position, taille);

	}

	/**
	 * Une methode servant a dessiner les niveaux contenant les obstacles
	 * 
	 * @param g2d Contexte graphique
	 * @param mat matrice qui sert a change du monde de pixel au monde reel
	 */
	// Par Emon Dhar
	public void dessiner(Graphics2D g2d, AffineTransform mat) {

		//Mort mort = new Mort(760);
		//mort.dessiner(g2d, mat);
	}


	/**
	 * Methode qui permet de determiner s'il y une collision avec un objet
	 * 
	 * @param objet Corps est utiliser pour detecter la collision
	 * @return true s'il y a une collision sinon false
	 */
	//Emon
	public boolean enCollision(Corps objet) {

		Area intersection = new Area(this.getArea());
		intersection.intersect(objet.getArea());
		if(!intersection.isEmpty()) {
			isCollision =true;
			System.out.println("T'es mort");
			objet.setMort(true);

			//int input = JOptionPane.showOptionDialog(null, "T'es mort!, Alors, t'as plus le droit de jouer! Bye!", "OOPPS!", JOptionPane.CANCEL_OPTION, JOptionPane.YES_OPTION, null, null, null);
/*
			if(input == JOptionPane.CANCEL_OPTION||input == JOptionPane.OK_OPTION)
			{
				System.exit(1);
			}
			leverEvenValeur();
			*/
		} else {
			objet.setMort(false);
		}
		return !intersection.isEmpty();

	}
	
	/**
	 * Methode permetant de determiner le numero d'obstacle
	 * @return the numeroObstacle numero de l'obstacle
	 */
	// Emon Dhar
	public int getNumeroObstacle() {
		return numeroObstacle;
	}

	/**
	 * Methode permetant de changer le numero d'obstacle
	 * @param numeroObstacle le numero obstacle a changer
	 */
	//Emon Dhar
	public void setNumeroObstacle(int numeroObstacle) {
		this.numeroObstacle = numeroObstacle;
	}


	/**
	 * Methode qui permet de lever des evenements
	 */
	// par Emon
	private void leverEvenValeur() {

		for (InfoListener ecout : listeEcouteurs) {
			ecout.mort(true);
		}
	}

}