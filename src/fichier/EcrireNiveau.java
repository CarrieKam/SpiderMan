package fichier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import affichage.ZoneEdition;

/**
 * Classe servant a ecrire un fichier (un niveau cree)
 * 
 * @author Carrie Kam
 */
public class EcrireNiveau {

	private String NOM_FICHIER_NIVEAU;
	private ObjectOutputStream oos;
	private File fichierDeTravail;
	private ZoneEdition zEdition;

	public EcrireNiveau(String nomFichier) {
		zEdition = new ZoneEdition();
		NOM_FICHIER_NIVEAU = nomFichier+".dat";
		fichierDeTravail = new File(NOM_FICHIER_NIVEAU);

		try {
			oos = new ObjectOutputStream(new FileOutputStream(fichierDeTravail));
			oos.writeObject(zEdition.getObstaclesList());

			JOptionPane.showMessageDialog(null,
					"\nLe fichier " + fichierDeTravail.getAbsolutePath() + " est pret pour la lecture");

		} catch (IOException e) {
			System.out.println("Erreur à l'écriture:");
			e.printStackTrace();

		} finally {
			// on exécutera toujours ceci, erreur ou pas
			try {
				oos.close();

			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erreur rencontrée lors de la fermeture!");

			}

		}
	}
}
