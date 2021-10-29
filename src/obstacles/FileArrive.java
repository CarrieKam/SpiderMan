package obstacles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import affichage.Dessinable;
import simonVezina.SVector3d;
/**
 * Classe qui créé la file d'arrive
 * 
 * @author Philippe Cote
 */
public class FileArrive extends Obstacles implements Dessinable {

	final int TAILLE_CARRE=2;
	private double positionX;
	private double HauteurDuMonde;
	
	/**
	 * Constructeur de la classe file d'arrive 
	 * 
	 * @param position position en x de la file d'arrive
	 * @param Taille decider la taille des petits carreaux noirs et blancs de la file d'arrive
	 * @param Hauteur hauteur du monde pour dessiner une file d'arrivé qui rempli ZoneDeJeu
	 */
	public FileArrive(SVector3d position, SVector3d taille, double Hauteur) {
		super(position,taille,2);
		
		positionX=position.getX();
		HauteurDuMonde=Hauteur; 

	}
	/**
	 * Methode qui dessine la file d'arriveà
	 * 
	 * @param g2d Contexte graphique
	 * @param mat matrice qui sert a change du monde de pixel au monde reel
	 */
	public void dessiner(Graphics2D g2d, AffineTransform mat) {

		int positionY=0;

		double nbCarre=HauteurDuMonde/TAILLE_CARRE;


		for (int k=0;k<=nbCarre/2;k++) {
			Rectangle2D.Double noir = new Rectangle2D.Double(positionX,positionY,TAILLE_CARRE,TAILLE_CARRE);
			g2d.setColor(Color.BLACK);
			g2d.fill(mat.createTransformedShape(noir));
			positionY+=TAILLE_CARRE;

			Rectangle2D.Double blanc = new Rectangle2D.Double(positionX,positionY,TAILLE_CARRE,TAILLE_CARRE);
			g2d.setColor(Color.WHITE);
			g2d.fill(mat.createTransformedShape(blanc));
			positionY+=TAILLE_CARRE;
			

		}

		positionY=0;

		for (int k=0;k<=nbCarre/2;k++) {
			Rectangle2D.Double noir = new Rectangle2D.Double(positionX+TAILLE_CARRE,positionY,TAILLE_CARRE,TAILLE_CARRE);
			g2d.setColor(Color.WHITE);
			g2d.fill(mat.createTransformedShape(noir));
			positionY+=TAILLE_CARRE;

			Rectangle2D.Double blanc = new Rectangle2D.Double(positionX+TAILLE_CARRE,positionY,TAILLE_CARRE,TAILLE_CARRE);
			g2d.setColor(Color.BLACK);
			g2d.fill(mat.createTransformedShape(blanc));
			positionY+=TAILLE_CARRE;
			
		}

		positionY=0;


	}

	
	/**
	 * Retourne la position de la file d'arrive
	 * 
	 * @return positionX la position en x
	 */
	public double getPositionX() {
		return positionX;
	}
	
	/**
	 * Change la hauteur du monde 
	 * 
	 * @return hauteur Hauteur du monde
	 */
	public void setHauteur(double hauteur) {
		
		HauteurDuMonde=hauteur;
		
	}
	
	
	
}
