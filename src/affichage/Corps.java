package affichage;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import simonVezina.SVector3d;

/**
 * Classe qui permet de structurer la creation des corps ( le personnage et les
 * obstacle)
 * 
 * @author Emon Dhar
 */
public class Corps {

	private SVector3d position = new SVector3d();
	private SVector3d taille = new SVector3d();
	private SVector3d vitesseInit = new SVector3d();
	private SVector3d accel = new SVector3d();
	private double vitesseAngulaire = 0;
	private double accelerationAngulaire=0;
	private double masseEnKg, e;
	private Rectangle2D.Double bloc;
	private Area aireCorps ,totale;
	private Ellipse2D.Double cercle;
	private boolean isPendu = false;
	private boolean isMort = false;
	private boolean champ=false;

	/**
	 * Constructeur de la classe Corps
	 */
	protected Corps() {
	}

	/**
	 * Constructeur de la classe Corps qui utilse seulement les position et la
	 * taille
	 * 
	 * @param position position en x et y
	 * @param taille   taille (x= longeur, y=hauteur)
	 */
	protected Corps(SVector3d position, SVector3d taille) {
		this.position = position;
		this.taille = taille;

	}

	/**
	 * Constructeur de la classe Corps qui permet de creer specialement des corps en
	 * mouvement
	 * 
	 * @param position du corps
	 * @param taille   du corps
	 * @param vitesse  du corps
	 * @param accel    du corps
	 * @param masse    du corps
	 */
	Corps(SVector3d position, SVector3d taille, SVector3d vitesse, SVector3d accel, double masse) {

		setPosition(position);
		setVitesseInit(vitesse);
		setAccel(accel);
		setTaille(taille);
		setMasseEnKg(masse);
	}

	/**
	 * Constructeur de la classe Corps qui permet de creer specialement des corps en
	 * mouvement avec la vitesse angulaire
	 * 
	 * @param position du corps
	 * @param taille   du corps
	 * @param vitesse  du corps
	 * @param accel    du corps
	 * @param masse    du corps
	 */
	Corps(SVector3d position, SVector3d taille, SVector3d vitesse, SVector3d accel, double masse, double vitesseAngulaire) {

		setPosition(position);
		setVitesseInit(vitesse);
		setAccel(accel);
		setTaille(taille);
		setMasseEnKg(masse);
		setVitesseAng(vitesseAngulaire);

	}

	/**
	 * Constructeur de la classe Corps qui utilise une autre Coprs pour le creer
	 * 
	 * @param corps
	 */
	Corps(Corps corps) {

		position = corps.position;
		taille = corps.taille;
		vitesseInit = corps.vitesseInit;
		accel = corps.accel;
		masseEnKg = corps.masseEnKg;

	}

	// **** Simplification pour touver la position du corps ****

	/**
	 * Retourne la position central de l'objet en x
	 * 
	 * @return float position.x
	 */
	float centreX() {
		return (float) (position.x + taille.x / 2);
	}

	/**
	 * Retourne la position central de l'objet en y
	 * 
	 * @return float position.y
	 */
	float centreY() {
		return (float) (position.y + taille.y / 2);
	}

	/**
	 * Retourne la position gauche de l'objet
	 * 
	 * @return float position
	 */
	float gauche() {
		return (float) position.x;
	}

	/**
	 * Retourne la position droite de l'objet
	 * 
	 * @return float position
	 */
	float droite() {
		return (float) (position.x + taille.x);
	}

	/**
	 * Retourne la position du bas de l'objet en y
	 * 
	 * @return float position.y
	 */
	float bas() {
		return (float) (position.y + taille.y);
	}

	/**
	 * Retourne la position de haut de l'objet en y
	 * 
	 * @return float position.y
	 */
	float haut() {
		return (float) (position.y);
	}

	/**
	 * Methode qui permet de determiner s'il y une collision avec un objet
	 * 
	 * @param objet
	 * @return true s'il y a une collision sinon false
	 */
	public boolean enCollision(Corps objet) {

		if (Math.abs(objet.centreX() - centreX()) >= (taille.x / 2 + objet.taille.x / 2)
				|| Math.abs((objet.centreY() - centreY())) >= (taille.y / 2 + objet.taille.y / 2)) {
			
			return false;
		}
		if (Math.abs(objet.centreX() - centreX()) <= (taille.x / 2 + objet.taille.x / 2)
				|| Math.abs((objet.centreY() - centreY())) <= (taille.y / 2 + objet.taille.y / 2)) {
	
			return true;
		} else {

			double distanceCoin = Math.pow((Math.abs(objet.centreX() - centreX()) - objet.taille.x / 2), 2)
					+ Math.pow((Math.abs(objet.centreX() - centreX()) - objet.taille.y / 2), 2);

			return (distanceCoin <= (Math.pow(this.taille.x / 2, 2)));
		}





	}

	/**
	 * Permet de calculer la vitesse final de l'objet apres une collision
	 * 
	 * @param objet
	 * @return vitesseFinal
	 */
	public SVector3d collisionEnCour(Corps objet) {

		// SVector3d normale = null;
		// normale.setXY((this.centreX()-objet.centreX())/Math.abs(this.centreX()-objet.centreX()),
		// (this.centreY()-objet.centreY())/Math.abs(this.centreY()-objet.centreY()));

		// return (float) this.vitesseInit.substract(objet.vitesseInit).dot(normale);
		
		System.currentTimeMillis();
		SVector3d vitesseFinal;
		SVector3d quantiteMouvInit = vitesseInit.multiply(masseEnKg).add(objet.vitesseInit.multiply(objet.masseEnKg));

		vitesseFinal = new SVector3d(
				quantiteMouvInit.substract(objet.vitesseInit.multiply(objet.masseEnKg)).x / masseEnKg,
				quantiteMouvInit.substract(objet.vitesseInit.multiply(objet.masseEnKg)).y / masseEnKg);

		if (vitesseFinal.y <= 0.0001 && vitesseFinal.y >= -0.0001) {
			return new SVector3d(0, 0);

		} else {

			float distancePlusCourt = 0;
			int direct = 0;

			distancePlusCourt = Math.abs(objet.droite() - gauche());
			direct = 1;

			if (Math.abs(droite() - objet.gauche()) <= distancePlusCourt) {
				distancePlusCourt = Math.abs(droite() - objet.gauche());
				// return new Vec2(distancePlusCourt, 0);
				direct = 3;
			}

			if (Math.abs(objet.haut() - bas()) <= distancePlusCourt) {
				distancePlusCourt = Math.abs(objet.haut() - bas());
				direct = 4;
			}

			if (Math.abs(haut() - objet.bas()) <= distancePlusCourt) {
				distancePlusCourt = Math.abs(haut() - objet.bas());
				// return new Vec2(0, distancePlusCourt);
				direct = 2;
			}

			if (direct == 1) {

				// droite
				// return new SVector3d(distancePlusCourt, 0);
				setVitesseAng(-getVitesseAng());
				return new SVector3d(-vitesseFinal.x, vitesseFinal.y);

			}
			if (direct == 2) {
				// return new SVector3d(0, -distancePlusCourt);
				// Bas
				setVitesseAng(-getVitesseAng());
				return new SVector3d(vitesseFinal.x, -vitesseFinal.y);

			}
			if (direct == 3) {
				// return new SVector3d(-distancePlusCourt, 0);
				setVitesseAng(-getVitesseAng());
				// gauche
				return new SVector3d(-vitesseFinal.x, vitesseFinal.y);
			} else {

				// Haut
				// return new SVector3d(0, distancePlusCourt);
				setVitesseAng(-getVitesseAng());
				return new SVector3d(vitesseFinal.x, -vitesseFinal.y);
			}
		}
	


	}


	// public static SVector3
	/**
	 * Methode qui permet de dessiner
	 * 
	 * @param g2d
	 * @param mat
	 * @param pendu
	 */
	public void dessiner(Graphics2D g2d, AffineTransform mat, boolean pendu) {
		cercle= new Ellipse2D.Double(this.getPosition().getX(), this.getPosition().getY(), this.getTaille().x, this.getTaille().y);
		bloc = new Rectangle2D.Double(position.getX(), position.getY(), taille.x, taille.y);


		aireCorps = new Area(mat.createTransformedShape(cercle));

		Area  totale  =  new  Area(  mat.createTransformedShape(cercle) );



	}



	/**
	 * Modifie la position Note: ici on decide de simplement refaire la forme
	 * sous-jacente!
	 * 
	 * @param pos Vecteur incluant les positions en x et y
	 */

	public void setPosition(SVector3d pos) {
		SVector3d nouveauVec = new SVector3d(pos.getX(), pos.getY());
		this.position = nouveauVec;
	}

	/**
	 * Retourne la position courante
	 * 
	 * @return position la position courante
	 */

	public SVector3d getPosition() {
		return (position);
	}

	/**
	 * Retourne si le corps est pendu ou non
	 * 
	 * @return isPendu si le corps est pendu ou non
	 */

	public boolean isPendu() {
		return (isPendu);
	}

	/**
	 * Associe une taille, ou modifie la taille courante
	 * 
	 * @param taille Vecteur incluant la largeur et la longeur ( x et y )
	 */

	public void setIsPendu(boolean isPendu) {

		this.isPendu = isPendu;
	}

	/**
	 * Associe une taille, ou modifie la taille courante
	 * 
	 * @param taille Vecteur incluant la largeur et la longeur ( x et y )
	 */

	public void setTaille(SVector3d taille) {
		SVector3d nouveauTaille = new SVector3d(taille.getX(), taille.getY());
		this.taille = nouveauTaille;
	}

	/**
	 * Retourne la vitesse finale
	 * 
	 * @return vitesse la vitesse Initiale
	 */

	public SVector3d getVitesseInit() {
		return (vitesseInit);
	}

	/**
	 * Associe une vitesse, ou modifie la vitesse finale
	 * 
	 * @param vitesse Vecteur incluant les vitesses en x et y
	 */

	public void setVitesseInit(SVector3d vitesse) {
		SVector3d nouveauVec = new SVector3d(vitesse.getX(), vitesse.getY());
		this.vitesseInit = nouveauVec;
	}

	/**
	 * Associe une acceleration, ou modifie l'acceleration courante
	 * 
	 * @param accel Vecteur incluant les accelerations en x et y
	 */

	public void setAccel(SVector3d accel) {
		SVector3d nouveauVec = new SVector3d(accel.getX(), accel.getY());
		this.accel = nouveauVec;
	}

	/**
	 * Retourne l'acceleration courante
	 * 
	 * @return accel acceleration courante
	 */

	public SVector3d getAccel() {
		return (accel);
	}

	/**
	 * Retourne la masse en Kg
	 * 
	 * @return La masse en kg
	 */

	public double getMasseEnKg() {
		return masseEnKg;
	}

	/**
	 * Modifie la masse
	 * 
	 * @param masseEnKg La massee exprimee en kg
	 */

	public void setMasseEnKg(double masseEnKg) {
		this.masseEnKg = masseEnKg;
	}

	/**
	 * Modifie la vitesse angulaire
	 * 
	 * @param vitesseAng la vitesse angulaire
	 */

	public void setVitesseAng(double vitesseAng) {
		this.vitesseAngulaire = vitesseAng;
	}

	/**
	 * Retourne la taille
	 * 
	 * @return la taille du personnage Vecteur
	 */

	public SVector3d getTaille() {
		return (taille);
	}

	/**
	 * Retourne la vitesse angulaire
	 * 
	 * @return la vitesse angulaire du personnage
	 */

	public double getVitesseAng() {
		return (vitesseAngulaire);
	}

	 /**
	 * Methode qui permet de connaitre l'aire du coprs
	 *
	 * @return aireCorps, l'aire du corps
	 */

	public Area getArea() {
		return aireCorps;
	}


	/**
	 * Modifie l'acceleration angulaire
	 * 
	 * @param aaccel l'acceleration angulaire
	 */
	public void setAccelAng(double aaccel) {
		
		accelerationAngulaire=aaccel;

	}
	/**
	 * Retourne l'acceleration angulaire
	 * 
	 * @return l'acceleration angulaire du personnage
	 */
	public double getAccelAng() {
		return accelerationAngulaire;

	}
	
	public void setMort(boolean b) {
		isMort=b;
	}
	
	
	public boolean getMort() {
		return isMort;
	}

	public void setChamp(boolean b) {
	
		champ=b;
	}
	
	public boolean getChamp() {
		
		return champ;
	}
	

}
