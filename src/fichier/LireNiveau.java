package fichier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import mode.Niveaux;
import obstacles.Obstacles;

/**
 * Classe servant a lire un fichier (un niveau cree)
 * 
 * @author Carrie Kam
 */
public class LireNiveau {
	private final String NOM_FICHIER_NIVEAU;

	private ObjectInputStream ois = null;
	private File fichierDeTravail;

	private Niveaux niveauCree;
	private ArrayList<Obstacles> obstaclesList = new ArrayList<Obstacles>();

	public LireNiveau(String nom) {
		NOM_FICHIER_NIVEAU = nom + ".dat";
		fichierDeTravail = new File(NOM_FICHIER_NIVEAU);

		try {

			ois = new ObjectInputStream(new FileInputStream(fichierDeTravail));

			obstaclesList = (ArrayList<Obstacles>) ois.readObject();

			niveauCree = new Niveaux("Vag");
			niveauCree.setObstaclesList(obstaclesList);

		} catch (ClassNotFoundException e) {
			System.out.println("L'objet lu est d'une classe inconnue");
			e.printStackTrace();
		} catch (InvalidClassException e) {
			System.out.println("Les classes utilisées pour l'écriture et la lecture diffèrent!");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Fichier  " + fichierDeTravail.getAbsolutePath() + "  introuvable!");
			System.exit(0);
		}

		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur rencontree lors de la lecture");
			e.printStackTrace();
			System.exit(0);
		}

		finally {
			// on exécutera toujours ceci, erreur ou pas
			try {
				ois.close();
			} catch (IOException e) {
				System.out.println("Erreur rencontrée lors de la fermeture!");
			}
		}
	}// fin finally

	/**
	 * Methode servant a obtenir le niveau cree
	 * 
	 * @return niveauCree le niveau cree
	 */
	public Niveaux getNiveauCree() {
		return niveauCree;
	}

}// fin classe
