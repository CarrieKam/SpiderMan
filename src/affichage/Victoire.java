package affichage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * Une classe qui dessine la scene de la victoire
 * @author Philippe Cote
 *
 */

public class Victoire implements Dessinable {

	//Rectangle pour le fond en couleur
	private Rectangle2D.Double fond;
	private Color couleur;
	//Hauteur et largeur du monde
	private double hauteur;
	private int largeur;
	
	/**
	 * Constructeur de la classe DemoPerso
	 * 
	 * @param valeur Largeur du monde réel
	 */
	public Victoire(int valeur) {
		
		largeur=valeur;
		couleur = new Color(0,  220, 161, 190);
	} 
	
	/**
	 * Methode permettant de dessiner la scene de la victoire
	 * 
	 * @param g2d Contexte graphique
	 * @param mat matrice qui sert a change du monde de pixel au monde reel
	 */
	
public void dessiner(Graphics2D g2d, AffineTransform mat) {
		
		
		
		
			Color temp = g2d.getColor();
			
			fond = new Rectangle2D.Double(-1000, -1000, 1000000, 1000000);
			
			
			g2d.setColor(couleur);
			
			g2d.fill( mat.createTransformedShape(fond));
		
			
			g2d.setFont(new Font("TimesRoman", Font.BOLD, 89));
			
			g2d.setColor(Color.GREEN);
			
			 g2d.drawString("VICTORY ! ! !",(largeur/2)-230 ,300);
			 
			 g2d.setColor(temp);
			 
			 
		
}
}
