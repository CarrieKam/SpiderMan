package affichage;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import obstacles.Cactus;
import obstacles.ChampMagnetique;
import obstacles.FileArrive;
import obstacles.Lave;
import obstacles.Mur;
import obstacles.Obstacles;
import obstacles.Scie;
import simonVezina.SVector3d;



/**
 * Cette classe controle la scene d'edition
 * @author Emon Dhar
 *
 */

public class ZoneEdition extends JPanel {



	private double vitInitX = 2, vitInitY = 0;
	private ModeleAffichage modele;
	private double HAUTEUR_DU_MONDE;
	private double largeurDuMonde = 80;	
	private FileArrive file = new FileArrive(new SVector3d(500, 0), new SVector3d(0, 0), HAUTEUR_DU_MONDE);
	private Rectangle2D.Double plafond, plancher, limiteGauche, limiteDroite;
	private double longueurCordeMaximale;
	private SVector3d transCamera = new SVector3d(0,0);
	private Personnage perso;
	private ArrayList <Obstacles> obstaclesList = new ArrayList<Obstacles>();
	private SVector3d position = new SVector3d(5, 5);
	private SVector3d accel = new SVector3d(0, 0);
	private SVector3d vitesse = new SVector3d(0, 0);
	private SVector3d taille = new SVector3d(5,5);


	private int numeroMur=0;
	private int numeroCactus=0;
	private int numeroLave=0;
	private int numeroChampMag=0;
	private int numeroScie=0;
	private SVector3d zoomPositionSouris;
	private double zoomMonde=0;
	private int i = 0;
	private double xPrecedent, yPrecedent;
	private int indiceObjetSelectionne = 0;
	private boolean enClique = false;
	private boolean interieur = false;
	AffineTransform mat;
	/**
	 * Creation d'un panneau
	 */
	public ZoneEdition() {


		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				switch (e.getKeyCode()) {

				case KeyEvent.VK_DOWN:

					transCamera.setXY(transCamera.x, transCamera.y-5);

					repaint();

					break;
				case KeyEvent.VK_UP:

					transCamera.setXY(transCamera.x, transCamera.y+5);
					repaint();


					break;
				case KeyEvent.VK_RIGHT:

					transCamera.setXY(transCamera.x-5, transCamera.y);
					repaint();

					break;
				case KeyEvent.VK_LEFT:
					transCamera.setXY(transCamera.x+5, transCamera.y);
					repaint();

					break;
				}

			}


		});



	}
	@Override
	/**
	 * Methode servant a dessiner la file d'arrivee et le personnage
	 * 
	 * @param g Contexte graphique
	 */


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		//g2d.translate(0, 2*2);
		//g2d.scale(2, 2);


		modele = new ModeleAffichage(getWidth(), getHeight(), largeurDuMonde);
		mat = modele.getMatMC();

		AffineTransform matTrans = new AffineTransform(mat);

		matTrans.translate(transCamera.x,transCamera.y);

		mat = matTrans;
		//AffineTransform matZoom = new AffineTransform(mat);
		//matZoom.scale(1, 1);
		//mat = matZoom;

		HAUTEUR_DU_MONDE = modele.getHautUnitesReelles();
		perso = new Personnage(position, taille, vitesse, accel, 5, 0);




		if(!obstaclesList.isEmpty()) {
			for ( int i= 0; i<obstaclesList.size(); i++) {
				obstaclesList.get(i).dessiner(g2d, matTrans);

			}

		}

		g2d.setColor(couleurOfficiel(1));
		plafond = new Rectangle2D.Double(0, -500, 10000, 500);
		plancher = new Rectangle2D.Double(0, HAUTEUR_DU_MONDE, 10000, 500);
		limiteGauche = new Rectangle2D.Double(-10000, -10000, 10000, 20000);
		limiteDroite = new Rectangle2D.Double(510, -10000, 10000, 20000);
		g2d.fill(mat.createTransformedShape(plafond));
		g2d.fill(mat.createTransformedShape(plancher));
		g2d.fill(mat.createTransformedShape(limiteDroite));
		g2d.fill(mat.createTransformedShape(limiteGauche));

		file.setHauteur(HAUTEUR_DU_MONDE);

		file.dessiner(g2d, mat);



		g2d.setColor(couleurOfficiel(2));
		if(transCamera.x == 0 && transCamera.y !=0) {
			g2d.drawString(
					"Largeur du monde : " + largeurDuMonde + " m" + "  Hauteur du monde: " + modele.getHautUnitesReelles() + "  Position du camera : (" + transCamera.x+ ", " + -transCamera.y +")",
					(int) (largeurDuMonde / 10), 29 * getHeight() / 30);

		} else if (transCamera.y == 0 && transCamera.x !=0) {
			g2d.drawString(
					"Largeur du monde : " + largeurDuMonde + " m" + "  Hauteur du monde: " + modele.getHautUnitesReelles() + "  Position du camera : (" + -transCamera.x+ ", " + transCamera.y+")",
					(int) (largeurDuMonde / 10), 29 * getHeight() / 30);
		} else if (transCamera.x == 0 && transCamera.y ==0) {
			g2d.drawString(
					"Largeur du monde : " + largeurDuMonde + " m" + "  Hauteur du monde: " + modele.getHautUnitesReelles() + "  Position du camera : (" + transCamera.x+ ", " + transCamera.y+")",
					(int) (largeurDuMonde / 10), 29 * getHeight() / 30);
		}
		else {
			g2d.drawString(
					"Largeur du monde : " + largeurDuMonde + " m" + "  Hauteur du monde: " + modele.getHautUnitesReelles() + "  Position du camera : (" + -transCamera.x+ ", " + -1*transCamera.y+")",
					(int) (largeurDuMonde / 10), 29 * getHeight() / 30);
		}


		perso.dessiner(g2d, mat, false);
	}
	/**
	 * Methode qui permet de retourner une coleur de la palette
	 * @param numeroDeCouleur Pend en paramettre le numero de coleur demander
	 * @return renourne une couleur
	 */
	private Color couleurOfficiel( int numeroDeCouleur) {
		if(numeroDeCouleur ==1) {

			return new Color(230,57,70);
		}

		if(numeroDeCouleur ==2) {

			return new Color(241,250,238);
		}
		if(numeroDeCouleur ==3) {

			return new Color(168,218,220);
		}
		if(numeroDeCouleur ==4) {

			return new Color(168,218,220);
		}
		else {
			return new Color(29,53,87);
		}

	}


	/**
	 * Methode permettant a ajoute des murs
	 * @param position le coin gauche superieur du mur en X et Y
	 * @param taille la taille du mur (x= largeur; y=hauteur)
	 */
	public void ajouterMur(SVector3d position, SVector3d taille) {

		Mur mur = new Mur(position, taille);
		obstaclesList.add(mur);
	}

	/**
	 * Methode permettant a ajoute des cactus
	 * @param position le coin gauche superieur du cactus en X et Y
	 * @param taille la taille du cactus (x= largeur; y=hauteur)
	 */
	public void ajouterCactus(SVector3d position, SVector3d taille) {

		Cactus o = new Cactus(position, taille);
		obstaclesList.add(o);
	}

	/**
	 * Methode permettant a ajoute des scie
	 * @param position le coin gauche superieur de la scie en X et Y
	 * @param taille la taille de la scie (x= largeur; y= largeur aussi )
	 */
	public void ajouterScie(SVector3d position, SVector3d taille) {

		Scie o = new Scie(position, taille);
		obstaclesList.add(o);
	}

	/**
	 * Methode permettant a ajoute de lave
	 * @param position le coin gauche superieur de lave en X et Y
	 * @param taille la taille de lave (x= largeur; y=hauteur)
	 */
	public void ajouterLave(SVector3d position, SVector3d taille) {

		Lave o = new Lave(position, taille);
		obstaclesList.add(o);
	}

	/**
	 * Methode permettant a ajoute des Champ magnetique
	 * @param position le coin gauche superieur du Champ magnetique en X et Y
	 * @param taille la taille du cactus (x= largeur; y=hauteur)
	 */
	public void ajouterChampMag(SVector3d position, SVector3d taille,SVector3d champMagnetique,double charge,boolean activer) {

		ChampMagnetique o = new ChampMagnetique(position, taille,champMagnetique,charge,activer);
		obstaclesList.add(o);
	}


	/**
	 * Methode permettant enlever la dernier obstacle
	 */
	public void enleverlaDerniereObs() {

		obstaclesList.remove(obstaclesList.size()-1);
	}

	/**
	 * Methode qui permet de retourner l'ArrayList des obstacles
	 * @return obstaclesList ArrayListdes obstacles
	 */
	public ArrayList<Obstacles> getObstaclesList() {

		return obstaclesList;
	}

	/**
	 * Methode permettant enlever la dernier obstacle
	 */
	public void enleverTousLesObs() {

		obstaclesList.removeAll(obstaclesList);
	}

	/**
	 * Methode permettant d'effacer l'obstacle selectionner
	 */
	public void effacerObjetSelec( int objetSelec) {

		obstaclesList.remove(objetSelec);
	}

	/**
	 * Methode permettant de retourner la position du camera
	 * @return transCamera C'est la position du camera
	 */
	public SVector3d getPositionCam() {
		return transCamera;
	}

	/**
	 * Methode permettant de changer a position du camera
	 * @param positionCam la position du camera
	 */
	public void setPositionCam(SVector3d positionCam) {
		transCamera = positionCam;
	}

	/**
	 * Methode permettant de changer le numero du mur selectionner
	 * @param numeroMur Numero du selectionner
	 */
	public void setNumeroMur(int numeroMur) {
		this.numeroMur = numeroMur;
	}
	/**
	 * Methode permettant de changer le numero du cactus selectionner
	 * @param numeroCactus the numeroCactus to set
	 */
	public void setNumeroCactus(int numeroCactus) {
		this.numeroCactus = numeroCactus;
	}
	/**
	 * Methode permettant de changer le numero de lave selectionner
	 * @param numeroLave the numeroLave to set
	 */
	public void setNumeroLave(int numeroLave) {
		this.numeroLave = numeroLave;
	}
	/**
	 * Methode permettant de changer le numero du champ magnetique selectionner
	 * @param numeroChampMag the numeroChampMag to set
	 */
	public void setNumeroChampMag(int numeroChampMag) {
		this.numeroChampMag = numeroChampMag;
	}
	/**
	 * Methode permettant de changer le numero de la scie selectionner
	 * @param numeroScie the numeroScie to set
	 */
	public void setNumeroScie(int numeroScie) {
		this.numeroScie = numeroScie;
	}
	/**
	 * Methode permettant de retourner la numero du mur selectionner
	 * @return numeroMur
	 */
	public int getNumeroMur() {
		return numeroMur;
	}
	/**
	 * Methode permettant de retourner la numero du cactus selectionner
	 * @return numeroCactus
	 */
	public int getNumeroCactus() {
		return numeroCactus;
	}
	/**
	 * Methode permettant de retourner la numero du champ magnetique selectionner
	 * @return numeroChampMag
	 */
	public int getNumeroChampMag() {
		return numeroChampMag;
	}
	/**
	 * Methode permettant de retourner la numero de la scie selectionner
	 * @return numeroScie
	 */
	public int getNumeroScie() {
		return numeroScie;
	}
	/**
	 * Methode permettant de retourner la numero lave selectionner
	 * @return numeroLave
	 */
	public int getNumeroLave() {
		return numeroLave;
	}

	/**
	 * Methode qui permet de savoir c'est koi l'hauteur du monde
	 * @return HAUTEUR_DU_MONDE hauteur du monde
	 */
	public double getHauteurDuMonde(){
		return HAUTEUR_DU_MONDE;
	}

	/**
	 * Methode qui permet de savoir c'est koi l'hauteur du monde
	 * @return LARGEUR_DU_MONDE largeur du monde
	 */
	public double getLageurDuMonde(){
		return largeurDuMonde;
	}
	/**
	 * Methode qui permet changer le largeur du monde
	 * @param valeur le nouveau largeur
	 */
	public void setLageurDuMonde( double valeur) {
		largeurDuMonde = valeur;
	}

	/**
	 * Methode qui permet changer l'argrandissement du monde
	 * @param valeur valeur du zoom
	 */
	public void setleZoom( double valeur) {
		zoomMonde = valeur;
	}


	/**
	 * Methode qui prend en parametre une valeur en pixel et la convertit en unites reelles en X
	 * @param pixel, la valeur qu'on veut convertir
	 * @return la valeur convertit en unites reelles
	 */

	public double valeurEnReelleX(double pixel) {
		return pixel/modele.getPixelsParUniteX();
	}

	/**
	 * Methode qui prend en parametre une valeur en pixel et la convertit en unites reelles en Y
	 * @param pixel, la valeur qu'on veut convertir
	 * @return la valeur convertit en unites reelles
	 */

	public double valeurEnReelleY(double pixel) {
		return pixel/modele.getPixelsParUniteY();
	}

	/**
	 * Methode permettant de touver l'objet selectionner
	 * @param e c'est une MouseEvent qui permet de savoir la position de la sourris
	 */
	public void trouverQuelObjetEstSeletionne(MouseEvent e) {
		double xPrecedent = e.getX();
		double yPrecedent = e.getY();
		//double xPrecedent =0;
		//double yPrecedent = 0;
		if(getObstaclesList().size()>indiceObjetSelectionne) {
			while(getObstaclesList().get(i).getArea().contains(e.getX(), e.getY())&&getObstaclesList().size()>i&& enClique) {


				interieur=true;
				//System.out.println("clic sur un obstacle!!");
				indiceObjetSelectionne =i;
				i++;
				if(i==getObstaclesList().size()) {

					i=0;
					enClique= false;
				}
				//break;


			}
		}

	}

	/**
	 * Methode permettant de bouger l'objet
	 * @param e c'est une MouseEvent qui permet de savoir la position de la sourris
	 */

	public void bougerObstacle (MouseEvent e) {
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent nouv) {
				if (interieur) {
					//					
					getObstaclesList().get(indiceObjetSelectionne).setPosition(new SVector3d(valeurEnReelleX(nouv.getX() - xPrecedent)- getPositionCam().x, valeurEnReelleY(nouv.getY() - yPrecedent)- getPositionCam().y));
					repaint();
					enClique= false;
				}

			}

		});
	}

	public String descriptionObst(int numObs) {
		if(numObs==1) {
			return "<html><p><span style=\"font-family:arial,helvetica,sans-serif;\">Voici un <strong>cactus</strong></span></p>\r\n" + 
					"<p style=\"margin-right: 0cm; margin-left: 0cm; caret-color: rgb(0, 0, 0);\"><span style=\"color:#f1faee;\"><span style=\"font-family:arial,helvetica,sans-serif;\"><span lang=\"FR-CA\" style=\"font-size: 12pt;\">Plante grasse &eacute;pineuse de la famille des cactac&eacute;es.</span><span lang=\"FR-CA\"><font size=\"3\">&nbsp;</font></span><span style=\"font-size: 12pt;\">C&#39;est un</span><span class=\"apple-converted-space\" style=\"font-size: 12pt;\">&nbsp;</span><span style=\"font-size: 12pt;\">obstacle</span><span class=\"apple-converted-space\" style=\"font-size: 12pt;\">&nbsp;</span><span style=\"font-size: 12pt;\">tr&egrave;s</span><span class=\"apple-converted-space\" style=\"font-size: 12pt;\">&nbsp;</span><strong style=\" color:#f1faee; font-family: Arial, sans-serif; font-size: 12pt;\">dangereux</strong><span class=\"apple-converted-space\" style=\"font-size: 12pt;\">&nbsp;</span><span style=\"font-size: 12pt;\">pour notre Spiderman.</span></span></span></p>\r\n" + 
					"</html>"
 ;
		}
		if(numObs==2) {
			
			return "<html><p><span style=\"font-family:arial,helvetica,sans-serif;\">Voici un <strong>aimant</strong></span></p>\r\n" + 
					"<p style=\"margin-right: 0cm; margin-left: 0cm; caret-color: rgb(0, 0, 0);\"><span style=\"color:#f1faee;\"><span style=\"font-family:arial,helvetica,sans-serif;\"><span lang=\"FR-CA\" style=\"font-size: 12pt;\">Corps magn&eacute;tis&eacute; qui a la propri&eacute;t&eacute; d&#39;attirer les objets m&eacute;talliques.</span><span lang=\"FR-CA\"><font size=\"3\">&nbsp;</font></span><span style=\"font-size: 12pt;\">C&#39;est un</span><span class=\"apple-converted-space\" style=\"font-size: 12pt;\">&nbsp;</span><span style=\"font-size: 12pt;\">obstacle</span><span class=\"apple-converted-space\" style=\"font-size: 12pt;\">&nbsp;</span><span style=\"font-size: 12pt;\">tr&egrave;s</span><span class=\"apple-converted-space\" style=\"font-size: 12pt;\">&nbsp;</span><strong style=\" color:#f1faee; font-family: Arial, sans-serif; font-size: 12pt;\">&eacute;puisant</strong><span class=\"apple-converted-space\" style=\"font-size: 12pt;\">&nbsp;</span><span style=\"font-size: 12pt;\">pour notre Spiderman.</span></span></span></p>\r\n" + 
					"</html>" ;

		}
		if(numObs==4) {
			return "<html><p><span style=\"font-family:arial,helvetica,sans-serif;\">Voici de <strong>lave</strong></span></p>\r\n" + 
					"<p style=\"margin-right: 0cm; margin-left: 0cm; caret-color: rgb(0, 0, 0);\"><span style=\"color:#f1faee;\"><span style=\"font-family:arial,helvetica,sans-serif;\"><span lang=\"FR-CA\" style=\"font-size: 12pt;\">Mati&egrave;re visqueuse &eacute;mise lors de l&#39;&eacute;ruption d&#39;un volcan.</span><span lang=\"FR-CA\"><font size=\"3\">&nbsp;</font></span><span style=\"font-size: 12pt;\">C&#39;est un</span><span class=\"apple-converted-space\" style=\"font-size: 12pt;\">&nbsp;</span><span style=\"font-size: 12pt;\">obstacle</span><span class=\"apple-converted-space\" style=\"font-size: 12pt;\">&nbsp;</span><span style=\"font-size: 12pt;\">tr&egrave;s</span><span class=\"apple-converted-space\" style=\"font-size: 12pt;\">&nbsp;</span><strong style=\" color:#f1faee; font-family: Arial, sans-serif; font-size: 12pt;\">dangereux</strong><span class=\"apple-converted-space\" style=\"font-size: 12pt;\">&nbsp;</span><span style=\"font-size: 12pt;\">pour notre Spiderman.</span></span></span></p>\r\n" + 
					"</html>" ;

		}
		if(numObs==5) {
			return "<html><p><span style=\"font-family:arial,helvetica,sans-serif;\">Voici un <strong>mur</strong></span></p>\r\n" + 
					"<p style=\"margin-right: 0cm; margin-left: 0cm; caret-color: rgb(0, 0, 0);\"><span style=\"color:#f1faee;\"><span style=\"font-family:arial,helvetica,sans-serif;\"><span lang=\"FR-CA\" style=\"font-size: 12pt;\">Ouvrage en ma&ccedil;onnerie emp&ecirc;chant les personnes &agrave; le traverser.</span><span lang=\"FR-CA\"><font size=\"3\">&nbsp;</font></span><span style=\"font-size: 12pt;\">C&#39;est un</span><span class=\"apple-converted-space\" style=\"font-size: 12pt;\">&nbsp;</span><span style=\"font-size: 12pt;\">obstacle</span><span class=\"apple-converted-space\" style=\"font-size: 12pt;\">&nbsp;</span><span style=\"font-size: 12pt;\">tr&egrave;s</span><span class=\"apple-converted-space\" style=\"font-size: 12pt;\">&nbsp;</span><strong style=\" color:#f1faee; font-family: Arial, sans-serif; font-size: 12pt;\">d&eacute;rangeant</strong><span class=\"apple-converted-space\" style=\"font-size: 12pt;\">&nbsp;</span><span style=\"font-size: 12pt;\">pour notre Spiderman.</span></span></span></p>\r\n" + 
					"</html>" ;

		}
		if(numObs==6) {
			
			return "<html><p><span style=\"font-family:arial,helvetica,sans-serif;\">Voici une <strong>scie</strong></span></p>\r\n" + 
					"<p style=\"margin-right: 0cm; margin-left: 0cm; caret-color: rgb(0, 0, 0);\"><span style=\"color:#f1faee;\"><span style=\"font-family:arial,helvetica,sans-serif;\"><span lang=\"FR-CA\" style=\"font-size: 12pt;\">Machine fixe comportant plusieurs organes coupants.</span><span lang=\"FR-CA\"><font size=\"3\">&nbsp;</font></span><span style=\"font-size: 12pt;\">C&#39;est un</span><span class=\"apple-converted-space\" style=\"font-size: 12pt;\">&nbsp;</span><span style=\"font-size: 12pt;\">obstacle</span><span class=\"apple-converted-space\" style=\"font-size: 12pt;\">&nbsp;</span><span style=\"font-size: 12pt;\">tr&egrave;s</span><span class=\"apple-converted-space\" style=\"font-size: 12pt;\">&nbsp;</span><strong style=\" color:#f1faee; font-family: Arial, sans-serif; font-size: 12pt;\">dangereux</strong><span class=\"apple-converted-space\" style=\"font-size: 12pt;\">&nbsp;</span><span style=\"font-size: 12pt;\">pour notre Spiderman.</span></span></span></p>\r\n" + 
					"</html>" ;

		}




		return "";
	}



}// Fin Classe
