package affichage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;


/**
 * Une classe qui dessine la scene de la mort
 * @author Philippe Cote
 *
 */

public class Mort implements Dessinable {


	private Rectangle2D.Double fond;
	private Color couleurRouge;
	private double hauteur;
	private int largeur;

	
	/**
	 * Constructeur de la classe Mort
	 * 
	 * @param valeur Largeur du monde réel
	 */
	public Mort(int valeur) {

		largeur = valeur;
		couleurRouge = new Color(255,  0, 0, 190);
	}

	/**
	 * Methode permettant de dessiner la scene de mort
	 * 
	 * @param g2d Contexte graphique
	 * @param mat matrice qui sert a change du monde de pixel au monde reel
	 */
	
	public void dessiner(Graphics2D g2d, AffineTransform mat) {



	

			Color temp = g2d.getColor();

			fond = new Rectangle2D.Double(-1000, -1000, 1000000, 1000000);


			g2d.setColor(couleurRouge);

			g2d.fill( mat.createTransformedShape(fond));

			g2d.setColor(Color.BLACK);

			g2d.setFont(new Font("TimesRoman", Font.BOLD, 135));

			g2d.drawString("Vous êtes mort .",(largeur/2)-450,300);

			g2d.setColor(temp);


		}













	}



