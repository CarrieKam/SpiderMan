package physique;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import affichage.Dessinable;
import simonVezina.SVector3d;

/**
 * Classe qui dessine un vecteur vitesse
 * 
 * @author Philipe Cote
 *
 */
public class VecteurGraphique implements Dessinable {

	private SVector3d vitesse;
	private SVector3d position;
	private double diametreBloc;
	private double angle;
	private double pointRotationX;
	private double pointRotationY;

	/**
	 * Constructeur: cree le composant
	 * 
	 * @param vitesse, vitesse du personnage
	 * @param position, Vecteur position du bloc
	 * @param positionX, position de depart du bloc en x
	 * @param positionY, position de depart du bloc en y
	 * @param diametre, diametre du bloc
	 */
	public VecteurGraphique(SVector3d vitesse, SVector3d position, double diametre) {
		this.vitesse = vitesse;
		this.position = position;
		this.diametreBloc = diametre;

	}

	/**
	 * Methode qui redefenit la methode dessiner de l'interface dessinable
	 * 
	 * @param g Le contexte graphique
	 * @param   mat, la matrice de modifications du dessin
	 */
	public void dessiner(Graphics2D g2d, AffineTransform mat1) {
		AffineTransform mat = new AffineTransform(mat1);
		Path2D vecteur = new Path2D.Double();
		Path2D fleche = new Path2D.Double();

		g2d.setStroke(new BasicStroke(7));

		if (vitesse != null) {
			if (vitesse.getX() < 0) {

				vecteur.moveTo(position.getX(), position.getY() + diametreBloc / 2);
				vecteur.lineTo(position.getX() + vitesse.getX() / 6,
						position.getY() + vitesse.getY() / 6 + diametreBloc / 2);

				fleche.moveTo(position.getX() + vitesse.getX() / 6,
						position.getY() + vitesse.getY() / 6 - diametreBloc / 5 + diametreBloc / 2);
				fleche.lineTo(position.getX() + vitesse.getX() / 6,
						position.getY() + vitesse.getY() / 6 + diametreBloc / 5 + diametreBloc / 2);
				fleche.lineTo(position.getX() + vitesse.getX() / 6 - diametreBloc / 5,
						position.getY() + vitesse.getY() / 6 + diametreBloc / 2);
				fleche.lineTo(position.getX() + vitesse.getX() / 6,
						position.getY() + vitesse.getY() / 6 - diametreBloc / 5 + diametreBloc / 2);

				pointRotationX = position.getX() + vitesse.getX() / 6;
				pointRotationY = position.getY() + vitesse.getY() / 6 + diametreBloc / 2;

			} else {

				vecteur.moveTo(position.getX() + diametreBloc, position.getY() + diametreBloc / 2);
				vecteur.lineTo(position.getX() + diametreBloc + vitesse.getX() / 6,
						position.getY() + vitesse.getY() / 6 + diametreBloc / 2);

				fleche.moveTo(position.getX() + diametreBloc + vitesse.getX() / 6,
						position.getY() + vitesse.getY() / 6 - diametreBloc / 5 + diametreBloc / 2);
				fleche.lineTo(position.getX() + diametreBloc + vitesse.getX() / 6,
						position.getY() + vitesse.getY() / 6 + diametreBloc / 5 + diametreBloc / 2);
				fleche.lineTo(position.getX() + diametreBloc + vitesse.getX() / 6 + diametreBloc / 5,
						position.getY() + vitesse.getY() / 6 + diametreBloc / 2);
				fleche.lineTo(position.getX() + diametreBloc + vitesse.getX() / 6,
						position.getY() + vitesse.getY() / 6 - diametreBloc / 5 + diametreBloc / 2);

				pointRotationX = position.getX() + diametreBloc + vitesse.getX() / 6;
				pointRotationY = position.getY() + vitesse.getY() / 6 + diametreBloc / 2;

			}
			g2d.setColor(Color.CYAN);

			g2d.draw(mat.createTransformedShape(vecteur));

			// Dessiner la tete de la fleche du vecteur de la force de rappel
			angle = calculerAngle(vitesse);

			mat.rotate(angle, pointRotationX, pointRotationY);

			g2d.fill(mat.createTransformedShape(fleche));

		}
	}

	/**
	 * Methode qui calculer l'angle de rotation
	 * 
	 * @param vitesse le vecteur vitesse
	 * @return angle angle que forme d'inclinaison du vecteur
	 */
	// Philippe Cote

	private double calculerAngle(SVector3d vitesse) {

		return Math.atan(vitesse.getY() / vitesse.getX());
	}

}
