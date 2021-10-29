package interfaces;

import java.util.EventListener;

/**
 * Interface qui cree des ecouteurs entre les classes Edition, Normal et
 * App23SpiderMan afin de recevoir des informations lors des cliques de
 * l'utilisateur
 * 
 * @author Carrie Kam
 */
public interface SpiderListener extends EventListener {

	// public void choisirFichier(Niveaux niveauCree);
	/**
	 * Methode servant a retourner dans la fenetre d'introduction
	 * 
	 * @param modeAccueil l'apparence en mode Accueil
	 */
	public void modeAccueil(boolean modeAccueil);

	/**
	 * Methode servant a retourner dans la fenetre de niveaux
	 * 
	 * @param modeNiveau l'apparence que prend la classe Application (avec les
	 *                   niveaux)
	 */
	public void modeNiveaux(boolean modeNiveau);

	/**
	 * Methode servant a informer à l'App23SpiderMan pour le mode Jour dans la
	 * classe Normal
	 * 
	 * @param jour jour est une varaible sercvant a dire si l'utilisateur a change
	 *             de mode le mode Normal
	 */
	public void modeJour(boolean jour);
}
