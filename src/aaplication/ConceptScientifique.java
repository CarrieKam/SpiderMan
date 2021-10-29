package aaplication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Classe servant a créer un panneau pour le texte des concepts scientifiques
 * 
 * @author Carrie Kam
 */
public class ConceptScientifique extends JFrame {

	private JPanel contentPane;
	private JButton btnQuitterLapplication;
	private ImageAvecDefilement panConcepts;

	/**
	 * Demarrer l'application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuideUtilisation frame = new GuideUtilisation();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creation de la fenetre.
	 */
	/**
	 * constructeur de la classe ConceptScientifique 
	 */
	public ConceptScientifique() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 10, 1039, 1039);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.setBackground(Color.cyan);

		panConcepts = new ImageAvecDefilement();

		panConcepts.setBounds(35, 11, 1010, 890);
		contentPane.add(panConcepts);

		// Pour fixer couleur du cadre
		panConcepts.setBackground(Color.BLUE);

		// Pour modifier la largeur du cadre
		panConcepts.setLargeurCadre(10);

		panConcepts.setFichierImage("conceptScientifique-0.png");

		JButton btnSuivant = new JButton("Suivant");
		btnSuivant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (panConcepts.getFichierImage().equals("conceptScientifique-1.png")) {

				} else {
					setBounds(300, 10, 1039, 1045);
					panConcepts.setBounds(35, 11, 1010, 890);
					contentPane.add(panConcepts);

					// Pour fixer couleur du cadre
					panConcepts.setBackground(Color.BLUE);

					// Pour modifier la largeur du cadre
					panConcepts.setLargeurCadre(10);
					panConcepts.setFichierImage("conceptScientifique-1.png");
					
					
					setBounds(300, 10, 1039, 1039);
					panConcepts.setBounds(35, 11, 1010, 890);
				}
			}
		});
		btnSuivant.setBounds(906, 885, 97, 23);
		panConcepts.add(btnSuivant);

		JButton btnPrecedent = new JButton("Precedent");
		btnPrecedent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (panConcepts.getFichierImage().equals("conceptScientifique-0.png")) {

				} else {
					setBounds(300, 10, 1039, 1045);
					panConcepts.setBounds(35, 11, 1010, 890);
					contentPane.add(panConcepts);

					// Pour fixer couleur du cadre
					panConcepts.setBackground(Color.BLUE);

					// Pour modifier la largeur du cadre
					panConcepts.setLargeurCadre(10);
					panConcepts.setFichierImage("conceptScientifique-0.png");
					
					setBounds(300, 10, 1039, 1039);
					panConcepts.setBounds(35, 11, 1010, 890);
				}
			}
		});
		btnPrecedent.setBounds(10, 885, 111, 23);
		panConcepts.add(btnPrecedent);
		btnQuitterLapplication = new JButton("Quitter la fenêtre de concept scientifique");
		panConcepts.add(btnQuitterLapplication);
		btnQuitterLapplication.setBounds(817, 932, 186, 47);
		btnQuitterLapplication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();

			}
		});
	}

}
