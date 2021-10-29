package physique;

import simonVezina.SVector3d;

/**
 * Classe servant a mettre les calculs physique
 * 
 * @author Philippe Cote
 * @author Carrie Kam
 * @author Emon Dhar
 */
public class MoteurPhysique {

	
	private static double charge = 1;
	private static SVector3d champMagnetique = new SVector3d(0,0,1) ;
	
	
	/**
	 * Methode qui calcule l'acceleration, la vitesse et la position du personnage
	 * pour la prochaine iteration
	 * 
	 * @param deltaT la différence de temps entre deux itérations
	 * @param position position du personnage
	 * @param vitesse vitesse du personnage
	 * @param accel	Acceleration du personnage
	 * @param masse Masse du personnage 
	 * 
	 */
	// par Philippe Cote

	public static void unPasEuler(double deltaT, SVector3d position, SVector3d vitesse, SVector3d accel, double masse,
			double g, boolean champ ) {

		SVector3d sommeDesForces;
		SVector3d forceGrav = new SVector3d(0,g*masse,0);
		if (champ) {
			
		SVector3d forceMagnetique= calculChampMagnetique(vitesse,charge,champMagnetique);
		
		sommeDesForces = forceGrav.add(forceMagnetique);
		
		System.out.println("On est magnétique oh mon dieu");
		} else {
			sommeDesForces = forceGrav;
		}
		
		accel.setX(sommeDesForces.getX());
		accel.setY(sommeDesForces.getY());

		SVector3d deltaVitesse = SVector3d.multiply(accel, deltaT);
		SVector3d resultV = vitesse.add(deltaVitesse);
		vitesse.setX(resultV.getX()); // on change le vecteur vitesse
		vitesse.setY(resultV.getY());

		SVector3d deltaPosition = SVector3d.multiply(vitesse, deltaT);
		SVector3d resultP = position.add(deltaPosition); // on calcule la position en considerant la nouvelle vitesse
		position.setX(resultP.getX()); // on change le vecteur position
		position.setY(resultP.getY());

	}// fin methode

	/**
	 * Methode servant a calculer le deplacement du personnage selon le champ
	 * magnetique
	 * 
	 * @param vitesse         vitesse du personnage
	 * @param charge          charge du personnage
	 * @param champMagnetique champ magnetique de la zone touche
	 */
	// Par Carrie
	public static SVector3d calculChampMagnetique(SVector3d vitesse, double charge, SVector3d champMagnetique) {
		// https://academic.oup.com/mnras/article/401/1/347/1006503
		
		return (vitesse.cross(champMagnetique)).multiply(charge);

	}

	/**
	 * Cette methode permet de calculer l'energie potentielle gravitationnelle
	 * 
	 * @param masse           masse du personnage
	 * @param gravite         la force gravite que le personnage fait face
	 * @param hauteurDuMonde  le hauteur du monde
	 * @param positionDuPerso la position du personnage
	 * @return l'energie potentielle gravitationnelle
	 */
	//Emon Dhar
	public static double energiePotentielleGrav(double masse, double gravite, double hauteurDuMonde,
			SVector3d positionDuPerso) {

		return masse * gravite * (hauteurDuMonde - positionDuPerso.y);
	}

}
