package mode;

import java.applet.Applet;
import java.applet.AudioClip;

/**
 * Classe, qui cree un objet musique pour activer et desactiver musique et sons
 * 
 * @author Philippe Cote
 */

public class Musique {

	AudioClip musiqueFond;
	AudioClip sonMort;
	
	/**
	 * Constructeur de la classe Musique
	 * 
	 */
	public Musique() {
		musiqueDeFond();
		sonMort();
		
	}
	
	
	
	
	/**
	 * Methode qui initialise l'objet audioclip pret a jouer la musique
	 */
	
	public void musiqueDeFond() {
		// TODO Auto-generated method stub
		
			java.net.URL fich = getClass().getClassLoader().getResource("MusiqueFond.wav");
			
			musiqueFond = Applet.newAudioClip(fich);
		
		
	}
	
		/**
		 * Methode qui initialise l'objet audioclip pour pret a jouer le son
		 */
		
		public void sonMort() {
			// TODO Auto-generated method stub
			
			
			
				java.net.URL fich = getClass().getClassLoader().getResource("Death sound.wav");
				
				sonMort = Applet.newAudioClip(fich);
			
}
		/**
		 * Methode qui joue la musique en continue (loop)
		 */
		public void playMusiqueFond() {
			musiqueFond.loop();
		}
		
		/**
		 * Methode qui joue le son de mort
		 */
		public void playSonMort( ) {
			sonMort.play();
		}



		/**
		 * Methode qui arrête la musique de fond
		 */
		public void stopMusiqueFond() {
			// TODO Auto-generated method stub
			musiqueFond.stop();
		}
		
}