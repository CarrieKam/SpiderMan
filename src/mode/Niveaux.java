package mode;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import obstacles.Cactus;
import obstacles.ChampMagnetique;
import obstacles.Lave;
import obstacles.Mur;
import obstacles.Obstacles;
import obstacles.Scie;
import simonVezina.SVector3d;

/**
 * Classe servant a creer les niveaux en mettant des obstacles
 * 
 * @author Carrie Kam
 * @author Emon Dhar
 */

public class Niveaux {
	private ArrayList<Obstacles> obstacleList;
	private SVector3d position;
	private SVector3d taille;
	private SVector3d b;
	private String nomNiveau;

	/**
	 * Constructeur de la classe Niveaux
	 * @param nomFichier 
	 */
	public Niveaux(String nomNiveau) {
		position = new SVector3d(0, 0, 0);
		taille = new SVector3d(0, 0, 0);
		b = new SVector3d(0,0,0);
		obstacleList = new ArrayList<Obstacles>();
		this.nomNiveau = nomNiveau;
	}

	/**
	 * Methode servant a ajoute des cactus
	 * 
	 * @param x        le coin en x de l'image de cactus
	 * @param y        le coin en y de l'image de cactus
	 * @param longueur la longueur de l'image du cactus
	 * @param largeur  la largeur de l'imagen du cactus
	 */
	// Par Carrie Kam
	public void ajouterCactus(double x, double y, double longueur, double largeur) {

		position.setXY(x, y);
		taille.setXY(longueur, largeur);

		Cactus cactus = new Cactus(position, taille);
		/*
		 * cactus.setPosition(position); cactus.setTaille(taille);
		 */
		/*
		 * JOptionPane.showMessageDialog(null,"Bibi: Cactus pos: "+
		 * position.getX()+"---	,	---"+position.getY() + " taille: "+taille.getX()
		 * +"---	,	---" + taille.getY());
		 */
		obstacleList.add(cactus);

	}

	/**
	 * Methode servant a ajoute des champ magnetique
	 * 
	 * @param x     le coin en x de l'image du champ magnetique
	 * @param y     le coin en y de l'image du champ magnetique
	 * @param rayon le rayon du champ magnetique
	 */
	// Par Carrie Kam
		public void ajouterChampMagnetique(double x, double y, double rayon,double champ,double charge,boolean activer) {
	
	//public void ajouterChampMagnetique(double x, double y, double rayon) {
	
		position.setXY(x, y);
		taille.setXY(rayon, 0);
		b.setZ(champ);

		ChampMagnetique champMagnetique = new ChampMagnetique(position, taille,b,charge,activer);
		
		//ChampMagnetique champMagnetique = new ChampMagnetique(position, taille);
		/*
		 * JOptionPane.showMessageDialog(null,"Bibi: ChampMagnetique pos: "+
		 * position.getX()+"---	,	---"+position.getY() + " taille: "+taille.getX()
		 * +"---	,	---" + taille.getY());
		 */
		obstacleList.add(champMagnetique);
	}

	/**
	 * Methode servant a ajoute de la lave
	 * 
	 * @param x        le coin en x de l'image de la lave
	 * @param y        le coin en y de l'image de la lave
	 * @param longueur la longueur de l'imagede la lave
	 * @param largeur  la largeur de l'imagen de la lave
	 */
	// Par Carrie Kam
	public void ajouterLave(double x, double y, double longueur, double largeur) {

		position.setXY(x, y);
		taille.setXY(longueur, largeur);

		Lave lave = new Lave(position, taille);

		/*
		 * JOptionPane.showMessageDialog(null,"Bibi: Lave pos: "+
		 * position.getX()+"---	,	---"+position.getY() + " taille: "+taille.getX()
		 * +"---	,	---" + taille.getY());
		 */
		obstacleList.add(lave);
	}

	/**
	 * Methode servant a ajoute des scies
	 * 
	 * @param x     le coin en x de l'image de la scie
	 * @param y     le coin en y de l'image de la scie
	 * @param rayon le rayon de l'image de la scie
	 */
	// Par Carrie Kam
	public void ajouterScie(double x, double y, double rayon) {

		position.setXY(x, y);
		taille.setXY(rayon, rayon);

		Scie scie = new Scie(position, taille);
		/*
		 * JOptionPane.showMessageDialog(null,"Bibi: Scie pos: "+
		 * position.getX()+"---	,	---"+position.getY() + " taille: "+taille.getX()
		 * +"---	,	---" + taille.getY());
		 */
		obstacleList.add(scie);
	}

	/**
	 * Methode servant a ajoute des murs
	 * 
	 * @param x        le coin en x de l'image du mur
	 * @param y        le coin en y de l'image du mur
	 * @param longueur la longueur de l'image du mur
	 * @param hauteur  la largeur de l'image du mur
	 */
	// Par Carrie Kam
	public void ajouterMur(double x, double y, double longueur, double hauteur) {

		position.setXY(x, y);
		taille.setXY(longueur, hauteur);

		Mur mur = new Mur(position, taille);
		/*
		 * JOptionPane.showMessageDialog(null,"Bibi: Mur pos: "+
		 * position.getX()+"---	,	---"+position.getY() + " taille: "+taille.getX()
		 * +"---	,	---" + taille.getY());
		 */
		obstacleList.add(mur);

	}

	/*
	 * public void dessinerObstacles() { int i; for(i=0;i<obstacleList.size();i++) {
	 * obstacleList.get(i).dessiner(); } }
	 */
	// Par Carrie Kam
	public void dessiner(Graphics2D g2d, AffineTransform mat) {
		int i;
		for (i = 0; i < obstacleList.size(); i++) {
			obstacleList.get(i).dessiner(g2d, mat);
			// System.out.println("Dessin d'un obstacle" + obstacleList.get(i));
		}
	}

	// Par Carrie Kam
	public int nbObstacles() {
		return obstacleList.size();
	}

	/**
	 * Methode permettant de set obstaclesList de la classe ZoneDeJeu.
	 * 
	 * @param list C'est ArrayList de type Obstacles
	 */
	// Par Emon Dhar
	public void setObstaclesList(ArrayList<Obstacles> list) {
		obstacleList = list;

	}

	/**
	 * Methode qui permet de retourner l'ArrayList des obstacles
	 * 
	 * @return obstaclesList ArrayListdes obstacles
	 */
	// Par Emon
	public ArrayList<Obstacles> getObstaclesList() {

		return obstacleList;
	}

	/**
	 * @return nomNiveau 
	 */
	//Carrie Kam
	public String getNomFichier() {
		return nomNiveau;
	}

	/**
	 * @param nomNiveau the nomFichier to set
	 */
	public void setNomFichier(String nomNiveau) {
		this.nomNiveau = nomNiveau;
	}

}
