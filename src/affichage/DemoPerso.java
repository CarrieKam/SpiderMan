package affichage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import simonVezina.SVector3d;

/**
 * Une classe vide pour qui affiche une zone de jeu mais sans animations possibles
 * @author Philippe Cote
 *
 */

public class DemoPerso extends JPanel {

	// Largeur simulé du monde
	private double LARGEUR_DU_MONDE=25;
	
	// Modele Affichage
	private ModeleAffichage modele;
	
	// Le personnage
	private Personnage perso;
	
	// Taille du personnage
	private double TaillePerso=5;
	
	// Variable pour centré le perso au milieu
	private SVector3d positionCentre;
	

	// Variables liees a la couleur du personnage
	private Color couleurPerso;
	private int R=0;
	private int G=0;
	private int B=0;
	private int transparence=255;
	
	// Variable liees a l'image spiderMan sur le personnage
	private boolean spiderFace=true;
	
	
	
	/**
	 * Constructeur de la classe DemoPerso
	 * 
	 */
	
	public DemoPerso() {
		couleurPerso = new Color(R,G,B,transparence);
		perso = new Personnage(new SVector3d(0, 0),new SVector3d(TaillePerso, TaillePerso),new SVector3d(0, 0),new SVector3d(0, 0),0,0);
		setBackground(Color.WHITE);

	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		modele = new ModeleAffichage(getWidth(), getHeight(), LARGEUR_DU_MONDE);
		AffineTransform mat = modele.getMatMC();



		positionCentre = new SVector3d(LARGEUR_DU_MONDE/2-(TaillePerso/2),modele.getHautUnitesReelles()/2-(TaillePerso/2));
		perso.setColor(couleurPerso);
		perso.setPosition(positionCentre);
		perso.setSpiderFace(spiderFace);
		perso.dessiner(g2d, mat, false);
		



	}

	/**
	 * Methode qui permet de changer la valeur R de la couleur personnalisée
	 * @param valeur Valeur de 0 à 255 du paramètre R
	 */
	
	public void setR(int valeur) {
		
		B=couleurPerso.getBlue();
		G=couleurPerso.getGreen();
		couleurPerso = new Color(valeur,G,B,transparence);
		
		repaint();
	}

	/**
	 * Methode qui permet de changer la valeur G de la couleur personnalisée
	 * @param valeur Valeur de 0 à 255 du paramètre G
	 */
	
	public void setG(int valeur) {
		
		R=couleurPerso.getRed();
		B=couleurPerso.getBlue();
		couleurPerso = new Color(R,valeur,B,transparence);
	
		repaint();
	}

	/**
	 * Methode qui permet de changer la valeur B de la couleur personnalisée
	 * @param valeur Valeur de 0 à 255 du paramètre B
	 */
	
	public void setB(int valeur) {
		
		R=couleurPerso.getRed();
		G=couleurPerso.getGreen();
		couleurPerso = new Color(R,G,valeur,transparence);
		
		repaint();
	}

	/**
	 * Methode qui permet de changer la valeur de la transparence de la couleur personnalisée
	 * @param valeur Valeur de 0 à 255 du paramètre de la transparence
	 */
	
	public void setTransparence(int valeur) {
		
		R=couleurPerso.getRed();
		G=couleurPerso.getGreen();
		B=couleurPerso.getBlue();
		transparence=valeur;
		couleurPerso = new Color(R,G,B,valeur);
		repaint();
	}

	/**
	 * Methode qui permet de connaitre la couleur personnalisée
	 * @return couleurPerso la couleur du personnage
	 */
	
	public Color getCouleur() {
		
		return couleurPerso;
	}

	/**
	 * Methode qui change si on voit ou pas le visage de spiderman
	 * @param c valeur boolean si on mon la face de spider-man s'affiche
	 */
	
	public void setSpiderFace(boolean c) {
		spiderFace=c;
		repaint();
		
	}
}
