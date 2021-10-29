package aaplication;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import fichier.LireNiveau;
import interfaces.SpiderListener;
import mode.Edition;
import mode.Niveaux;
import mode.Normal;

/**
 * Classe, qui cree une interface,en introduisant le joueur pour jouer au jeu.
 * 
 * @author Carrie Kam
 * @author Caroline Houle
 */ 
public class App23SpiderMan extends JFrame {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static App23SpiderMan frame;
	private static boolean modeJour = true;
	private boolean state = true;

	// Variables liees a l'affichage
	private static JPanel contentPane;
	private Normal fenetreJeu;
	private Edition fenetreEdition;
	// private EditionSelection edSelection;

	// Variable liees aux boutons
	private JButton btnJouer;
	private JButton btnEdition;
	private JButton btnN1;
	private JButton btnN2;
	private JButton btnN3;

	// Varaibles liees aux niveaux
	private Niveaux n1;
	private Niveaux n2;
	private Niveaux n3;

	// Variables liees au menu d'aide
	private JMenu mnAide;
	private JMenuItem mntmGuideDutilisation;
	private JMenuItem mntmConceptsScientifiques;

	// Variables liees aux instructions visuel
	private JLabel lblGauche;
	private JLabel lblDroite;
	private JLabel lblInstruction;
	private JLabel lblEspace;
	private JLabel lblUpdown;
	private JPanel panelInstru;

	// Variable liee a l'ecouteur
	private ArrayList<SpiderListener> listeEcouteurs = new ArrayList<SpiderListener>();
	private LireNiveau lire;
	private JLabel lblImagejouerediter;
	private JLabel lblImglogospiderman;
	private JLabel lblTxtchoixdeniveau;
	private JMenu mnMode;
	private JCheckBoxMenuItem mntmModeNuit;
	private JButton btnAPropos_1;

	/**
	 * Demarrer l'application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					frame = new App23SpiderMan();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creation d'une interface.
	 */
	/**
	 * constructeur de la classe App23SpiderMan
	 */
	public App23SpiderMan() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1370, 750);
		Dimension ecranDimension = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(ecranDimension.width / 2 - getSize().width / 2, ecranDimension.height / 2 - getSize().height / 2);
		// 100,100,450,300

		/*
		 * // Panel de depart contentPane = new JPanel();
		 * contentPane.setBackground(couleurBackground);
		 * contentPane.setForeground(couleurTexte); contentPane.setLayout(null);
		 * setContentPane(contentPane);
		 */
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// Place la fenetre au milieu de l'ecran
		setLocationRelativeTo(null);

		mnAide = new JMenu("Aide");
		menuBar.add(mnAide);

		ConceptScientifique panConcept = new ConceptScientifique();
		GuideUtilisation panGuide = new GuideUtilisation();

		mntmGuideDutilisation = new JMenuItem("Guide d'utilisation");
		mntmGuideDutilisation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				panGuide.setVisible(true);
				// fin
			}
		});
		mnAide.add(mntmGuideDutilisation);

		mntmConceptsScientifiques = new JMenuItem("Concepts scientifiques");
		mntmConceptsScientifiques.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
					panConcept.setVisible(true);
				// fin
			}
		});
		mnAide.add(mntmConceptsScientifiques);

		mnMode = new JMenu("Mode");
		menuBar.add(mnMode);

		mntmModeNuit = new JCheckBoxMenuItem("Mode nuit");
		mntmModeNuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// debut
				if (mntmModeNuit.isSelected()) {
					App23SpiderMan.setModeJour(!App23SpiderMan.isModeJour());
					jourNuit();
				} else {
					App23SpiderMan.setModeJour(!App23SpiderMan.isModeJour());
					jourNuit();
				}
			}
		});
		mnMode.add(mntmModeNuit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Spiderman - Accueil");
		contentPane.setBackground(new Color(230, 57, 70));

		fenetreJeu = new Normal();
		fenetreEdition = new Edition();

		n1 = new Niveaux("Niveau 1");
		n2 = new Niveaux("Niveau 2");
		n3 = new Niveaux("Niveau 3");
		creationNiveaux();

		btnJouer = new JButton("");
		btnJouer.setOpaque(false);
		btnJouer.setContentAreaFilled(false);
		btnJouer.setBorderPainted(false);
		btnJouer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				fenetreJouer();
				btnN1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// debut N-1
						setVisible(false);
						fenetreJeu.setNiveaux(n1);

						if (!fenetreJeu.isVisible()) {
							fenetreJeu.setVisible(true);
						}
						// fin N-1
					}
				});

				btnN2.setVisible(true);

				btnN2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// debut N-2
						setVisible(false);
						fenetreJeu.setNiveaux(n2);

						if (!fenetreJeu.isVisible()) {
							fenetreJeu.setVisible(true);
						}
						// fin N-2
					}
				});

				btnN3.setVisible(true);

				btnN3.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// debut N-3
						setVisible(false);
						fenetreJeu.setNiveaux(n3);

						if (!fenetreJeu.isVisible()) {
							fenetreJeu.setVisible(true);
						}
						// fin N-3
					}
				});
				// fin
			}
		});
		btnJouer.setFont(new Font("Rockwell", Font.PLAIN, 11));
		btnJouer.setBounds(294, 531, 331, 100);
		contentPane.add(btnJouer);

		btnEdition = new JButton("");
		btnEdition.setForeground(Color.RED);
		btnEdition.setBackground(Color.WHITE);
		btnEdition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// debut
				btnJouer.setEnabled(false);
				btnEdition.setEnabled(false);
				lblImagejouerediter.setEnabled(false);
				setVisible(false);

				fenetreEdition.setVisible(true);
				// fin
			}
		});
		btnEdition.setFont(new Font("Rockwell", Font.PLAIN, 11));
		btnEdition.setBounds(754, 529, 324, 102);
		btnEdition.setOpaque(false);
		btnEdition.setContentAreaFilled(false);
		btnEdition.setBorderPainted(false);
		contentPane.add(btnEdition);
		panelInstru = new JPanel();
		panelInstru.setBorder(new LineBorder(new Color(255, 228, 196), 3));
		panelInstru.setBounds(20, 11, 1331, 134);
		contentPane.add(panelInstru);
		panelInstru.setLayout(null);
		panelInstru.setVisible(false);

		lblInstruction = new JLabel("Instruction:");
		lblInstruction.setBounds(49, 7, 89, 14);
		panelInstru.add(lblInstruction);
		lblInstruction.setFont(new Font("Tahoma", Font.PLAIN, 13));

		lblEspace = new JLabel(
				"Vous pouvez  appuyer sur la barre d'espace pour activer/desactiver la toile de Spider-Man");
		lblEspace.setBounds(49, 27, 587, 14);
		panelInstru.add(lblEspace);

		URL urlHB = getClass().getClassLoader().getResource("upDown.png");
		if (urlHB == null) {
			JOptionPane.showMessageDialog(null, "Fichier clown.jpg introuvable");
			System.exit(0);
		}

		ImageIcon hautBas = new ImageIcon(urlHB);
		lblUpdown = new JLabel(hautBas);
		lblUpdown.setText("\r\n");
		lblUpdown.setBounds(39, 32, 48, 78);
		panelInstru.add(lblUpdown);

		JTextArea txtrInstruHB = new JTextArea();
		txtrInstruHB.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtrInstruHB.setBackground(UIManager.getColor("Panel.background"));
		txtrInstruHB.setWrapStyleWord(true);
		txtrInstruHB.setLineWrap(true);
		txtrInstruHB.setEditable(false);
		txtrInstruHB.setText(
				"Vous pouvez appuyer sur ces boutons pour d\u00E9placer le personnage vers le haut ou vers le bas comme une fa\u00E7on de tricher dans le jeu. De plus, la touche vers le haut sert a augmente la vitesse.");
		txtrInstruHB.setBounds(83, 51, 763, 40);
		panelInstru.add(txtrInstruHB);

		URL urlG = getClass().getClassLoader().getResource("left.png");
		if (urlG == null) {
			JOptionPane.showMessageDialog(null, "Fichier clown.jpg introuvable");
			System.exit(0);
		}

		ImageIcon gauche = new ImageIcon(urlG);
		lblGauche = new JLabel(gauche);
		lblGauche.setBounds(20, 98, 41, 40);
		panelInstru.add(lblGauche);

		URL urlD = getClass().getClassLoader().getResource("right.png");
		if (urlD == null) {
			JOptionPane.showMessageDialog(null, "Fichier clown.jpg introuvable");
			System.exit(0);
		}

		ImageIcon droit = new ImageIcon(urlD);
		lblDroite = new JLabel(droit);
		lblDroite.setBounds(61, 87, 41, 62);
		panelInstru.add(lblDroite);

		JTextArea txtrInstruGD = new JTextArea();
		txtrInstruGD.setWrapStyleWord(true);
		txtrInstruGD.setLineWrap(true);
		txtrInstruGD.setBackground(UIManager.getColor("Panel.background"));
		txtrInstruGD.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtrInstruGD.setText(
				"Vous pouvez appuyer sur ces boutons pour d\u00E9placer le personnage vers la droite ou vers la gauche, en tant qu'une fa\u00E7on de tricher. ");
		txtrInstruGD.setBounds(119, 98, 714, 40);
		panelInstru.add(txtrInstruGD);
		// creationNiveauEdition();

		lblImagejouerediter = new JLabel("image_JOUER_EDITER");
		lblImagejouerediter.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/B_Jouer_ED.png"))
				.getImage().getScaledInstance(809, 180, Image.SCALE_SMOOTH)));
		lblImagejouerediter.setBounds(282, 490, 809, 180);
		contentPane.add(lblImagejouerediter);

		btnN2 = new JButton("N-2");
		btnN2.setBounds(564, 520, 190, 98);
		contentPane.add(btnN2);
		btnN2.setBackground(Color.WHITE);
		btnN2.setForeground(Color.RED);
		btnN2.setFont(new Font("Rockwell", Font.PLAIN, 11));

		btnN3 = new JButton("N-3");
		btnN3.setBounds(849, 520, 190, 98);
		contentPane.add(btnN3);
		btnN3.setBackground(Color.WHITE);
		btnN3.setForeground(Color.RED);
		btnN3.setFont(new Font("Rockwell", Font.PLAIN, 11));

		btnN1 = new JButton("N-1");
		btnN1.setBounds(296, 520, 190, 98);
		contentPane.add(btnN1);
		btnN1.setBackground(Color.WHITE);
		btnN1.setForeground(Color.RED);
		btnN1.setFont(new Font("Rockwell", Font.PLAIN, 11));

		lblTxtchoixdeniveau = new JLabel("txt_ChoixDeNivaeu");
		lblTxtchoixdeniveau.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/TXT_Choix_De_Niveau.png"))
				.getImage().getScaledInstance(1061, 37, Image.SCALE_SMOOTH)));
		lblTxtchoixdeniveau.setBounds(156, 388, 1061, 37);
		lblTxtchoixdeniveau.setVisible(false);
		contentPane.add(lblTxtchoixdeniveau);

		lblImglogospiderman = new JLabel("imgLogoSpiderMan");
		lblImglogospiderman.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/SpiderManLogoIntro.png"))
				.getImage().getScaledInstance(1073, 141, Image.SCALE_SMOOTH)));
		lblImglogospiderman.setBounds(144, 236, 1073, 141);
		contentPane.add(lblImglogospiderman);
		btnN1.setVisible(false);
		btnN3.setVisible(false);
		btnN2.setVisible(false);
		
		btnAPropos_1 = new JButton("A propos");
		btnAPropos_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null,"Fait par Philippe C�t�, Carrie Kam et Emon Dhar\r\n" + 
						"Dans le cadre du cours 420-SCD, Int�gration des apprentisages en Science informatique et math�matiques.");
			}
		});
		btnAPropos_1.setBounds(1256, 640, 95, 45);
		contentPane.add(btnAPropos_1);
		
		associerBoutonAvecImage(btnN1, "B_N1.png");
		associerBoutonAvecImage(btnN2, "B_N2.png");
		associerBoutonAvecImage(btnN3, "B_N3.png");
		associerBoutonAvecImage(btnAPropos_1, "B_info.png");
		
		// Changement de fenetre
		setVisible(false);

		// lookAndFeel();

		fenetreJeu.addSpiderListener(new SpiderListener() {

			/**
			 * Methode servant a changer en mode accueil quand l'utilisateur le veut
			 */
			@Override
			public void modeAccueil(boolean modeAccueil) {
				if (modeAccueil) {
					fenetreAccueil();
				}

			}

			/**
			 * Methode servant a voir les niveaux quand l'utilisateur le veut
			 */
			@Override
			public void modeNiveaux(boolean modeNiveau) {
				if (modeNiveau) {
					fenetreJouer();
				}

			}

			@Override
			public void modeJour(boolean jour) {
				setModeJour(jour);

			}

		});
		
	}

	/**
	 * Methode servant a cree les trois niveaux de jeu preconcu
	 */
	private void creationNiveaux() {
		// interface niveau 1

		n1.ajouterMur(23.45454545454545, -30, 10.0, 40.0);
		n1.ajouterMur(60.0, 20.54290909090909, 10.0, 28.0);
		n1.ajouterMur(107.9090909090909, -30, 10.0, 50.0);
		n1.ajouterMur(134.27272727272725, -30, 10.0, 40.0);
		n1.ajouterMur(152.45454545454544, 28.815636363636365, 8.0, 23.0);
		n1.ajouterMur(171.27272727272725, -30.00, 10.0, 20.0);

		n1.ajouterMur(248.0909090909091, -30, 10.0, 45.0);

		n1.ajouterMur(340.3636363636364, -30, 10.0, 50.0);
		n1.ajouterMur(390.4545454545455, 20, 10.0, 26.0);
		n1.ajouterMur(443.09090909090907, -30, 10.0, 56.0);

		// interface niveau 2
		n2.ajouterMur(65.45454545454544 , -10.184363636363639, 10.0 , 20.0);
		n2.ajouterMur(365.1818181818182 , -0.0025454545454550725, 9.0 , 20.0);
		
		
		n2.ajouterCactus(42.09090909090909 , 29.345454545454547, 6.0 , 9.0);
		n2.ajouterCactus(93.54545454545455 , 25.981818181818184, 6.0 , 12.0);
		

		
		n2.ajouterScie(318.45454545454544 , 0.5429090909090901, 10.0);
		n2.ajouterScie(318.45454545454544 , 0.5429090909090901, 10.0);
		n2.ajouterScie(330.99999999999994 , 2.997454545454546, 10.0);
		n2.ajouterScie(401.5454545454546 , 2.542909090909091, 10.0);
		
		n2.ajouterLave(132.1818181818182 , 27.997454545454545, 10.0 , 10.0);
		n2.ajouterLave(142.1818181818182 , 27.997454545454545, 10.0 , 10.0);
		n2.ajouterLave(152.0909090909091 , 27.997454545454545, 10.0 , 10.0);
		n2.ajouterLave(255.27272727272728 , 23.270181818181815, 10.0 , 10.0);
		n2.ajouterLave(255.27272727272728 , 28.997454545454545, 10.0 , 10.0);
		n2.ajouterLave(260.1818181818182 , 23.179272727272725, 10.0 , 10.0);
		n2.ajouterLave(260.1818181818182 , 27.997454545454545, 10.0 , 10.0);
		n2.ajouterLave(471.3636363636364 , -6.547999999999999, 10.0 , 10.0);
		n2.ajouterLave(471.36363636363643 , -1.2752727272727276, 10.0 , 10.0);
		n2.ajouterLave(485.1818181818182 , 27.997454545454545, 10.0 , 20.0);
		n2.ajouterLave(495.09090909090907 , 27.997454545454545, 10.0 , 10.0);
		n2.ajouterLave(505.09090909090907 , 27.997454545454545, 10.0 , 10.0);
		
		n2.ajouterChampMagnetique(442.9090909090909 , 8.088363636363637, 26.0 ,  20.0, 20, true);
		
		// interface niveau 3
		n3.ajouterScie(83.81818181818183 , -0.3661818181818166, 10.0);
		n3.ajouterScie(84.63636363636363 , 27.54290909090909, 10.0);
		n3.ajouterScie(119.9090909090909 , 12.63381818181818, 10.0);
		n3.ajouterLave(95.45454545454545 , 33.63381818181818, 10.0 , 10.0);
		n3.ajouterLave(105.45454545454545 , 33.54290909090909, 10.0 , 10.0);
		n3.ajouterLave(115.45454545454545 , 33.54290909090909, 10.0 , 10.0);
		n3.ajouterLave(125.45454545454545 , 33.452, 10.0 , 10.0);
		n3.ajouterLave(135.45454545454544 , 33.54290909090909, 10.0 , 10.0);
		n3.ajouterLave(145.36363636363637 , 33.54290909090909, 10.0 , 10.0);
		n3.ajouterScie(155.0 , -0.0025454545454542676, 10.0);
		n3.ajouterScie(154.8181818181818 , 27.90654545454545, 10.0);
		
		n3.ajouterCactus(275.45454545454544 , 16.70909090909091, 10.0 , 27.163);
		n3.ajouterCactus(305.81818181818187 , 15.70909090909091, 10.0 , 27.163);
		n3.ajouterCactus(338.54545454545456 , 16.436363636363637, 10.0 , 27.163);
		
		n3.ajouterScie(389.0909090909091 , 1.8156363636363633, 10.0);
		n3.ajouterScie(426.54545454545456 , 25.451999999999998, 10.0);
		n3.ajouterScie(479.0 , 26.54290909090909, 10.0);
		n3.ajouterScie(492.8181818181818 , -7.457090909090908, 18.0);
		
		
		n3.ajouterChampMagnetique(27.63636363636364 , -0.548, 60.0 , 10.0, 12, true);
		
	}

	/**
	 * Retourne mode jour
	 * 
	 * @return modeJour boolean servant a savoir si les fenetres vont etre en mode
	 *         nuit ou non
	 */
	// Carrie Kam
	public static boolean isModeJour() {
		return modeJour;
	}

	/**
	 * Change le mode jour
	 * 
	 * @param modeJour boolean servant changer les fenetres en mode nuit ou jour
	 */
	// Carrie Kam
	public static void setModeJour(boolean modeJour) {
		App23SpiderMan.modeJour = modeJour;
	}

	/**
	 * Associe une image a un bouton en redimensionnant l'image adequatement.
	 * 
	 * @param leBouton     Le bouton auquel on veut associer l'image.
	 * @param fichierImage L'image qui sera associee au bouton.
	 */
	// par Caroline Houle
	private void associerBoutonAvecImage(JButton leBouton, String fichierImage) {
		Image imgLue = null;
		java.net.URL urlImage = getClass().getClassLoader().getResource(fichierImage);
		if (urlImage == null) {
			JOptionPane.showMessageDialog(null, "Fichier " + fichierImage + " introuvable");
		}
		try {
			imgLue = ImageIO.read(urlImage);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur pendant la lecture du fichier d'image");
		}

		Image imgRedim = imgLue.getScaledInstance(leBouton.getWidth(), leBouton.getHeight(), Image.SCALE_SMOOTH);

		leBouton.setOpaque(false);
		leBouton.setContentAreaFilled(false);
		leBouton.setBorderPainted(false);

		leBouton.setText("");
		leBouton.setIcon(new ImageIcon(imgRedim));

		imgLue.flush();
		imgRedim.flush();

	}

	/**
	 * Methode servant a changer l'apparence de l'accueil pour voir les niveaux
	 */
	private void fenetreJouer() {
		panelInstru.setVisible(true);
		btnJouer.setVisible(false);
		btnEdition.setVisible(false);
		lblImagejouerediter.setVisible(false);

		btnN1.setVisible(true);
		lblTxtchoixdeniveau.setVisible(true);

		fenetreJeu.dispose();
		fenetreEdition.dispose();
		setVisible(true);
	}

	/**
	 * Methode servant a changer l'apparence de l'accueil pour voir l'apparence du
	 * debut de l'application
	 */
	private void fenetreAccueil() {
		panelInstru.setVisible(false);
		btnJouer.setVisible(true);
		btnEdition.setVisible(true);
		lblImagejouerediter.setVisible(true);

		btnN1.setVisible(false);
		btnN2.setVisible(false);
		btnN3.setVisible(false);
		lblTxtchoixdeniveau.setVisible(false);

		fenetreJeu.dispose();
		fenetreEdition.dispose();
		setVisible(true);
	}

	/**
	 * Methode servant a mettre le l'application en mode jour ou en mode nuit
	 */
	public void jourNuit() {
		System.out.println("Jour nuit");
		if (modeJour == true) {
			contentPane.setBackground(new Color(230, 57, 70));
			mntmModeNuit.setSelected(!state);
			System.out.println(!state +" red");
			fenetreJeu.setMatin(false);
			// Normal.setModeJour(true);

		} else {
			contentPane.setBackground(new Color(51, 51, 51));
			mntmModeNuit.setSelected(state);
			System.out.println(state+" Black");
			fenetreJeu.setMatin(true);
		}
	}
}