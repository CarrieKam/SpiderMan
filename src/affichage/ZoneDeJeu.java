package affichage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import interfaces.InfoListener;
import mode.Musique;
import mode.Niveaux;
import obstacles.FileArrive;
import obstacles.Obstacles;
import simonVezina.SVector3d;

/**
 * Classe qui controle la scene d'animation
 * 
 * @author Philippe Cote
 * @author Emon Dhar
 * @author Carrie Kam
 */

public class ZoneDeJeu extends JPanel implements Runnable {

	private int TEMPS_DU_SLEEP = 22;

	private double deltaT = 0.02;

	// Acceleration gravitationnelle
	private double g = 9.8;

	// Temps ecoule
	private double tempsTotalEcoule = 0;

	// Different parametre initial du personnage
	private SVector3d taille = new SVector3d(5, 5);
	private SVector3d position = new SVector3d(5, 5);
	private SVector3d accel = new SVector3d(0, 0);
	private SVector3d vitesse ;
	private double masse = 1;
	private double vitInitX = 2, vitInitY = 0;

	private ModeleAffichage modele;
	private double HAUTEUR_DU_MONDE;
	private double LARGEUR_DU_MONDE = 80;
	private Boolean jouer = true;
	private Rectangle2D.Double plafond;
	public static boolean simple = false;
	private boolean enCoursDanimation = false;
	private Color couleurPerso;
	private boolean pendu = false; // pour dessiner une corde
	private Personnage perso, perso2;
	private BarreProg barre;
	private boolean pause=false;

	private int transCamera = 0;
	private FileArrive file = new FileArrive(new SVector3d(500, 0), new SVector3d(0, 0), HAUTEUR_DU_MONDE);

	private ArrayList<Obstacles> obstaclesList = new ArrayList<Obstacles>();

	private ArrayList<InfoListener> listeEcouteurs = new ArrayList<InfoListener>();
	private Niveaux niveau;
	private Obstacles obstacle;

	// scene mort
	private Mort mort;
	// scene victoire
	private Victoire victoire;
	// valeur pour savoir si le personnage est mort ou vivant
	private boolean vivant = true;
	// objet musique pour activer la musique de fond et le son de mort
	private Musique musique;
	// compteur pour �viter d'activer la musique de fond plusieurs fois en m�me
	// temps
	private int compteurMusique = 0;
	// L'image de fond de la zone de jeu
	private Image img = null;
	private Image img2 = null;
	// valeur pour activer et desactiver la musique et le son de mort
	private boolean sonActiver = true;
	// valeur pour activer et desactiver l'image de spiderman sur le personnage
	private boolean spiderFace = true;
	private int taux1 = 53;
	private int taux2 = 53;
	private boolean victoireB=false;


	/**
	 * Creation d'une fenetre.
	 */
	// Par Emon Dhar


	public ZoneDeJeu() {

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					if (!enCoursDanimation) {
						demarrer();
					}
					if (tempsTotalEcoule>0.5)
						pendu = true;

				}
				
				if (e.getKeyCode() == KeyEvent.VK_J) {
					jouer = !jouer;
				}



				switch (e.getKeyCode()) {

				case KeyEvent.VK_A:
					bougerCam(2);
					victoire();
					repaint();

					break;
				case KeyEvent.VK_D:
					bougerCam(-2);

					repaint();

					break;

				case KeyEvent.VK_P:
					pause=!pause;
					if(pause) {
						enCoursDanimation = false;
					} else {
						demarrer();
					}

					repaint();




					break;

				case KeyEvent.VK_DOWN:
					perso.setVitesseInit(new SVector3d(perso.getVitesseInit().x, (perso.getVitesseInit().y) + 2));
					// System.out.println(position.toString());
					repaint();


					break;
				case KeyEvent.VK_UP:
					perso.setVitesseInit(new SVector3d(perso.getVitesseInit().x, (perso.getVitesseInit().y) - 2));
					repaint();
					// System.out.println("up");

					break;
				case KeyEvent.VK_RIGHT:
					perso.setVitesseInit(new SVector3d((perso.getVitesseInit().x + 2), (perso.getVitesseInit().y)));

					repaint();

					break;
				case KeyEvent.VK_LEFT:
					perso.setVitesseInit(new SVector3d((perso.getVitesseInit().x - 2), (perso.getVitesseInit().y)));

					repaint();

					break;

				} 
			}

			// Par Philippe Cote
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					pendu = false;
				}
			}
		});



		vitesse = new SVector3d(vitInitX, vitInitY);
		perso = new Personnage(position, taille, vitesse, accel, masse, g);
		barre = new BarreProg(new SVector3d(2, 7), new SVector3d(3, 13), position, vitesse, masse, g, HAUTEUR_DU_MONDE);

		perso.setHauteur(HAUTEUR_DU_MONDE);
		couleurPerso = new Color(156, 255, 0);
		musique = new Musique();
		lireImage();

	}

	@Override
	/**
	 * Methode servant a dessiner la file d'arrivee et le personnage
	 * 
	 * @param g Contexte graphique
	 */
	// Par Emon
	// par Philippe
	public void paintComponent(Graphics g) {
		taux1 = (int)(0.404*(perso.getPosition().x)+53);

		if(taux1<=0) {
			taux1=0;
		}
		if(taux1>=255) {
			taux1=255;
		}


		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		modele = new ModeleAffichage(getWidth(), getHeight(), LARGEUR_DU_MONDE);
		AffineTransform mat = modele.getMatMC();

		AffineTransform matTrans = new AffineTransform(mat);
		AffineTransform matTransSpecialRalentit = new AffineTransform(mat);
		HAUTEUR_DU_MONDE = modele.getHautUnitesReelles();

		matTrans.translate(-perso.getPosition().x + 35 + transCamera, 0);
		matTransSpecialRalentit.translate(-perso.getPosition().x * 0.4 + 35 + transCamera, 0);
		//		
		//		// Dessiner l'image de fond
		//		
		//				AffineTransform matLocale = new AffineTransform(mat);
		//				double factX =	LARGEUR_DU_MONDE/ img2.getWidth(null);
		//				double factY = HAUTEUR_DU_MONDE / img2.getHeight(null);
		//				matLocale.scale(factX, factY);
		//				g2d.drawImage(img2, matLocale, null);
		//
		//
		if(!simple){		
			fondEcran(matTransSpecialRalentit, g2d, -100, 500, img2, 0);
			mat = matTrans;

			fondEcran(mat, g2d, -100, 740, img, 6);
			// g2d.setBackground(color);
		} else {
			mat = matTrans;
		}

		file.setHauteur(HAUTEUR_DU_MONDE);

		file.dessiner(g2d, mat);
		niveau.dessiner(g2d, mat);
		// obstacle.dessiner(g2d, mat);

		// File d'arriver
		
		if(pause) {
			Color temp = g2d.getColor();
			Font tempFont = g2d.getFont();
			Rectangle2D.Double fond = new Rectangle2D.Double(-1000, -1000, 1000000, 1000000);


			g2d.setColor( new Color(190,  0, 0, 190));

			g2d.fill( mat.createTransformedShape(fond));

			g2d.setFont(new Font("TimesRoman", Font.BOLD, 89));

			g2d.setColor(Color.YELLOW);

			g2d.drawString("PAUSE!",(int)(getWidth()/2)-230 ,300);
			g2d.setFont(tempFont);
			g2d.setColor(temp);

		}


		
		g2d.setColor(Color.YELLOW);
		g2d.drawString(
				"Largeur du monde : " + LARGEUR_DU_MONDE + " m" + "  Hauteur du monde: " + modele.getHautUnitesReelles()
				+ "   Position du perso: " + String.format(" %.1f", perso.getPosition().x),
				(int) (LARGEUR_DU_MONDE / 10), 29 * getHeight() / 30);





		g2d.setColor(couleurPerso);

		perso.dessiner(g2d, mat, pendu);
		// barre.dessiner(g2d, matLocale);

		if (!vivant) {
			mort = new Mort(getWidth());
			mort.dessiner(g2d, mat);
		}
		if (victoireB) {
			victoire = new Victoire(getWidth());
			victoire.dessiner(g2d, mat);
		}


		if ((perso.getPosition().y + perso.getTaille().y) >= HAUTEUR_DU_MONDE + 6) {
			if (vivant) {

				leverEvenMort();
			}

		}

		if ((perso.getPosition().y + perso.getTaille().y) < -10) {
			if (vivant) {

				leverEvenMort();
			}

		}
		// Dessiner le plafond
		g2d.setColor(Color.BLACK);
		plafond = new Rectangle2D.Double(-100, -50, 10000000, 50);
		g2d.fill(mat.createTransformedShape(plafond));
		changerCouleurFont();

		if (perso.arrive(file)&& perso.getPosition().x>=300) {

			victoire();
			System.out.println(" WESH J'AI GAGN� ");
		}




	}



	/**
	 * Methode servant a rouler l'animation
	 * 
	 * 
	 */
	// par Philippe

	public void run() {
		// TODO Auto-generated method stub

		while (enCoursDanimation) {

			calculerUneIterationPhysique();
			repaint();
			tempsTotalEcoule+=deltaT;
			try {
				Thread.sleep(TEMPS_DU_SLEEP);
			} catch (InterruptedException e) {
				System.out.println("Processus interrompu!");
			}
		}
		System.out.println("Le thread est mort.");
	}

	/**
	 * Methode qui calcule les prochaines valeurs physiques qu'une seule fois, soit
	 * apres un deltaT
	 */
	// auteur Philippe Cote

	public void calculerUneIterationPhysique() {

		tempsTotalEcoule += deltaT;

		for (int i = 0; i < niveau.getObstaclesList().size(); i++) {

			if (niveau.getObstaclesList().get(i).enCollision(perso)) {
				if (perso.getMort()) {
					leverEvenMort();
				}
				if (!perso.getChamp()) {
					perso.setVitesseInit(perso.collisionEnCour(niveau.getObstaclesList().get(i)));
				}
			}
		}

		perso.unPasEuler(deltaT, pendu);
		leverEvenValeur();
		repaint();

	}

	/**
	 * Methode qui demarre un thread tant que l'animation est en mouvement
	 */
	// auteur Philippe Cote
	public void demarrer() {

		if (!enCoursDanimation) {
			Thread proc = new Thread(this);
			proc.start();
			pause = false;
			enCoursDanimation = true;
			requestFocusInWindow();
			if (compteurMusique == 0) {
				musique.playMusiqueFond();
				compteurMusique++;
			}


		}
	}

	/**
	 * Methode qui arrete l'animation
	 */
	// auteur Philippe Cote
	public void arreter() {
		if(enCoursDanimation){
		pause = true;
		}
		enCoursDanimation = false;
		requestFocusInWindow();
		repaint();
	}

	/**
	 * Methode qui arrete et affiche l'ecran de victoire
	 * 
	 * @param g2d Contexte graphique
	 * @param mat matrice qui sert a change du monde de pixel au monde reel
	 * 
	 */
	//Philippe Cote
	private void victoire() {
		// TODO Auto-generated method stub

		leverEvenVictoire();
		arreter();
		pause = false;
		victoireB=true;
		repaint();


	}

	/**
	 * Reinitialise toutes les donnees comme position,vitesse,acceleration et autres
	 * Redemande le focus afin de pouvoir utiliser la barre espace
	 */
	// Par Philippe Cote
	public void reinitialiser() {
		arreter();
		Color colortemp;
		transCamera = 0;
		colortemp = perso.getColor();
		pause=false;
		perso = new Personnage(position, taille, vitesse, accel, masse, g);
		vivant = true;
		victoireB=false;
		pendu = false;

		perso.setColor(colortemp);
		setSpiderFace(spiderFace);

		tempsTotalEcoule = 0.0;
		repaint();
		requestFocusInWindow();
	}

	/**
	 * Methode servant a savoir quel niveau la zoneDeJeu va etre changer
	 * 
	 * @param niveau Niveau choisi
	 * @return niveau Niveau choisi
	 */
	// Carrie Kam
	public void setNiveaux(Niveaux niveau) {
		this.niveau = niveau;
		// JOptionPane.showMessageDialog(null,"Changement de niveau");
	}

	/**
	 * Methode qui change la masse du personnage
	 * 
	 * @param value masse envoye par le mode scientifique
	 * 
	 */
	// Philippe Cote
	public void setMasse(Object value) {
		// TODO Auto-generated method stub
		masse = (int) value;

	}



	public void setSimple(boolean value) {
		// TODO Auto-generated method stub
		simple = value;

	}



	public boolean getSimple() {
		// TODO Auto-generated method stub
		return simple;

	}


	/**
	 * Methode qui change la longueur maximale de la corde
	 * 
	 * @param value longueur de corde maximale envoye par le mode scientifique
	 * 
	 */
	// Philippe Cote
	public void setDstLancerCorde(double value) {
		// TODO Auto-generated method stub
		perso.setDstLancer(value);
	}

	/**
	 * Methode qui change la vitesse initiale
	 * 
	 * @param value vitesse initiale envoye par le mode scientifique
	 * 
	 */
	// Philippe Cote
	public void setVitesseInitiale(double value) {
		// TODO Auto-generated method stub
		vitesse.setX(value);
		reinitialiser();
	}

	/**
	 * Methode qui change la gravite
	 * 
	 * @param value gravite envoye par le mode scientifique
	 * 
	 */
	// Philippe Cote
	public void setGravite(double value) {
		// TODO Auto-generated method stub
		g = value;
		reinitialiser();
	}

	/**
	 * Methode qui change la couleur du background de la zone de jeu
	 * 
	 * @param value gravite envoye par le mode scientifique
	 * 
	 */
	// Philippe Cote
	public void changerBackground() {
		// setBackGround(Color.BLACK);
	}

	/**
	 * Methode qui deplace la camera
	 * 
	 * @param value nombre a ajouter pour faire une translation
	 * 
	 */
	// Emon Dhar
	public void bougerCam(int value) {
		// TODO Auto-generated method stub
		transCamera = transCamera + value;

	}

	/**
	 * Methode qui ajoute des listeners a l'arraylist de listeners
	 * 
	 * @param objEcout l'objet listener a ajouter
	 * 
	 */
	// Philippe Cote

	public void addInfoListener(InfoListener objEcout) {

		listeEcouteurs.add(objEcout);

	}

	/**
	 * Methode qui permet de changer la couleur du personnage
	 * 
	 * @param couleur Couleur du personnage
	 */
	// par Philippe Cote
	public void setCouleurPerso(Color couleur) {
		// TODO Auto-generated method stub
		perso.setColor(couleur);
		repaint();
	}

	/**
	 * Methode qui envoie l'informations du personnage
	 * 
	 */
	// par Philippe Cote
	private void leverEvenValeur() {
		if (pendu) {
			for (InfoListener ecout : listeEcouteurs) {
				ecout.informationsPerso(perso.getAccel(), perso.getVitesseInit(), perso.getPosition(),
						perso.getVitesseAngulaire(), perso.getAccelerationAngulaire(), g, perso.getMasseEnKg(),
						HAUTEUR_DU_MONDE);
			}
		} else {
			for (InfoListener ecout : listeEcouteurs) {
				ecout.informationsPerso(perso.getAccel(), perso.getVitesseInit(), perso.getPosition(), 0, 0, g,
						perso.getMasseEnKg(), HAUTEUR_DU_MONDE);
			}
		}

	}

	/**
	 * M�thode qui change si on affiche l'image de spiderman ou pas
	 * 
	 * @param spider Valeur si on affiche l'image de spiderman sur le personnage
	 */
	// auteur Philippe Cote
	public void setSpiderFace(boolean spider) {
		// TODO Auto-generated method stub
		spiderFace = spider;
		perso.setSpiderFace(spider);
	}

	/**
	 * M�thode qui change si on affiche le vecteur vitesse
	 * 
	 * @param b Valeur si on affiche le vecteur vitesse sur le personnage
	 */
	// auteur Philippe Cote
	public void setVecteurGraphique(boolean b) {
		// TODO Auto-generated method stub
		perso.afficherVecteur(b);
		repaint();

	}

	/**
	 * Methode servant a lire une image
	 */
	// Caroline Houle
	private void lireImage() {
		URL fich = getClass().getClassLoader().getResource("BG_DeJEU1.png");
		URL fich2 = getClass().getClassLoader().getResource("BG_DeJEU2.png");
		// https://pixabay.com/illustrations/spiderman-hero-spider-superhero-2313262/
		if (fich == null) {
			JOptionPane.showMessageDialog(null, "Fichier d'image introuvable!");
		} else {
			try {
				img = ImageIO.read(fich);
			} catch (IOException e) {
				System.out.println("Erreur de lecture du fichier d'image");
			}
		}

		if (fich2 == null) {
			JOptionPane.showMessageDialog(null, "Fichier d'image introuvable!");
		} else {
			try {
				img2 = ImageIO.read(fich2);
			} catch (IOException e) {
				System.out.println("Erreur de lecture du fichier d'image");
			}
		}
	}

	/**
	 * Methode qui entraine les actions necessaire l'hors d'une mort
	 * 
	 */
	// auteur Philippe Cote
	public void mourir() {

		if (sonActiver) {
			musique.playSonMort();
		}
		pause = false;
		vivant = false;
		enCoursDanimation = false;
		repaint();

	}

	/**
	 * Lever de l'evenement mort, qui envoie si le personnage est mort
	 * 
	 */
	// auteur Philippe Cote
	public void leverEvenMort() {

		for (InfoListener ecout : listeEcouteurs) {

			ecout.mort(vivant);

		}

	}

	/**
	 * Lever de l'evenement mort, qui envoie si le personnage est mort
	 * 
	 */
	// auteur Philippe Cote
	public void leverEvenVictoire() {

		for (InfoListener ecout : listeEcouteurs) {

			ecout.victoire();;

		}

	}

	/**
	 * Methode qui retourne si le personnage est vivant ou non
	 * 
	 */
	// auteur Philippe Cote
	public boolean getVivant() {
		return vivant;
	}

	/**
	 * Methode qui active et desactive le son du jeu
	 * 
	 * @param b Si le son est activer ou pas
	 */
	// auteur Philippe Cote
	public void activerSon(boolean b) {
		if (b) {
			sonActiver = true;
			musique.playMusiqueFond();
		} else {
			sonActiver = false;
			musique.stopMusiqueFond();
		}

	}

	/**
	 * Methode permetant de dessigner le fond ecran en repetition
	 * 
	 * @param mat           Matrice de tansformation
	 * @param g2d           graphique 2d
	 * @param positionInt   position de depart
	 * @param positionFinal position pour finir de designer
	 * @param imageChoisi   L'image qui est utiliser
	 * @param transY        La translation vertical (en y)
	 */
	private void fondEcran(AffineTransform mat, Graphics2D g2d, double positionInt, double positionFinal,
			Image imageChoisi, int transY) {

		for (int i = 0; i < positionFinal; i = i + (int) LARGEUR_DU_MONDE) {
			AffineTransform matLocaleED = new AffineTransform(mat);
			double factXe = (LARGEUR_DU_MONDE / imageChoisi.getWidth(null));
			double factYe = (HAUTEUR_DU_MONDE / imageChoisi.getHeight(null));

			matLocaleED.translate(positionInt + i, transY);
			matLocaleED.scale(factXe, factYe);

			g2d.drawImage(imageChoisi, matLocaleED, null);

		}
	}
	/**
	 * Methode permetant de changer la couleur du font avec la position du personnage
	 */
	//Emon Dhar
	private void changerCouleurFont() {
		taux1 = (int)(0.404*(perso.getPosition().x)+53);

		if(taux1<=0) {
			taux1=0;
		}
		if(taux1>=255) {
			taux1=255;
		}

		setBackground(new Color(29, taux1, taux1));
	}
	/**
	 * Methode servant a recevoir s'il y a une victoire ou non
	 * @return victoireB la victoire 
	 */
	//Emon Dhar
	public boolean getVictoire() {

		return victoireB;
	}

}
