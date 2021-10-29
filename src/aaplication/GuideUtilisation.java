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
public class GuideUtilisation extends JFrame {

	private JPanel contentPane;
	private JButton btnQuitterLapplication;

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
	public GuideUtilisation() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//		Dimension ecran = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(300, 10, 1039, 1039);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.setBackground(Color.cyan);

		ImageAvecDefilement panGuide = new ImageAvecDefilement();

		panGuide.setBounds(35, 11, 1010, 890);
		contentPane.add(panGuide);

		// Pour fixer couleur du cadre
		panGuide.setBackground(Color.PINK);

		// Pour modifier la largeur du cadre
		panGuide.setLargeurCadre(10);

		// Pour charger l'image pre-fabriquee
		panGuide.setFichierImage("guideUtilisateur.1.png");

		btnQuitterLapplication = new JButton("Quitter la fenêtre de guide d'utilisation");
		panGuide.add(btnQuitterLapplication);
		btnQuitterLapplication.setBounds(766, 932, 237, 47);
		btnQuitterLapplication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// debut
				dispose();

			}
		});
	}
}
