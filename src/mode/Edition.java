package mode;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import aaplication.App23SpiderMan;
import affichage.ZoneEdition;
import simonVezina.SVector3d;

/**
 * Classe qui presente le jeu en mode edition (un endroit ou l'utilisateur creer
 * peut sa propre interface).
 * 
 * @author Emon Dhar
 * @author Carrie Kam
 * @author Caroline Houle
 */
public class Edition extends JFrame {

	// Variables liees a l'affichage
	private JPanel contentPane;
	private ZoneEdition zoneEdition;
	private Normal normal;
	private ChoixPerso fenetrePerso;
	private App23SpiderMan fenetreAccueil;

	private Boolean interieur = false;

	// Variables liees a l'interfaces
	private JLabel lblHauteur;
	private JLabel lblLargeur;
	private JLabel lblModificationObtacles;
	private JSlider sldHauteur;
	private JSlider sldLargeur;
	private boolean unChamp = false;
	// Variables liees au menu
	private JMenu menu;
	private JMenuItem menuItemParametre;
	private JMenuItem menuItemNormal;
	private JMenuItem menuItemSauvegarder;

	// Variables liees a la sauvegarde du jeu
	private JButton btnAjouterCactus;
	private JButton btnAjouterChampMag;
	private JButton btnAjouterLave;
	private JButton btnAjouterMur;
	private JButton btnAjouterScie;

	private int retourPossible = 0;

	private double xPrecedent, yPrecedent, posXPrecedent, posYPrecedent;
	private int indiceObjetSelectionne = -1;
	private boolean enClique = false;

	// Variable pour la sauvegarde du jeu
	private Niveaux niveauCree;
	private JLabel lblInfoobstacleselectione;
	private ArrayList<Niveaux> listNiveaux = new ArrayList<Niveaux>();
	private JLabel lblNiveauCree;
	private ArrayList<String> listeNom = new ArrayList<String>();
	private String nomFichier;
	private JComboBox<String> listeEdition;
	private int i = 0;

	/**
	 * Demarrer l'application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Edition frame = new Edition();
					frame.setVisible(true);
					frame.zoneEdition.requestFocusInWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creation d'une interface.
	 */
	// Par Emon 
	public Edition() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1142, 637);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(29, 53, 87));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Spiderman - Edition");

		normal = new Normal();
		fenetrePerso = new ChoixPerso();

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(230, 57, 70));

		menuBar.setBounds(0, 0, 67, 21);
		contentPane.add(menuBar);

		menu = new JMenu("Fichier");
		menu.setForeground(new Color(0, 0, 0));
		menu.setBackground(new Color(230, 57, 70));
		menuBar.add(menu);

		menuItemParametre = new JMenuItem("Parametres");
		menuItemParametre.setBackground(new Color(230, 57, 70));
		menuItemParametre.addActionListener(new ActionListener() {

			// Par Emon Dhar
			public void actionPerformed(ActionEvent e) {
				// debut 
				newLvl();

				if (!normal.isVisible()) {
					normal.parametre();
					normal.setVisible(true);
				}
				// fin
			}
		});
		menu.add(menuItemParametre);

		menuItemNormal = new JMenuItem("Normal");
		menuItemNormal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				setVisible(false);
				newLvl();

				if (!normal.isVisible()) {
					normal.setVisible(true);
				}
				// fin
			}
		});
		menu.add(menuItemNormal);

		menuItemSauvegarder = new JMenuItem("Sauvegarder");
		menuItemSauvegarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				nomFichier = JOptionPane.showInputDialog(null, "Veuillez nommer votre niveau");
				ajoutNomNiveau(nomFichier);
				// fin
			}
		});
		menu.add(menuItemSauvegarder);

		JMenuItem mntmQuitter = new JMenuItem("Quitter");
		mntmQuitter.setBackground(Color.BLUE);
		mntmQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				confirmationEtSortie();
				// fin
			}
		});
		menu.add(mntmQuitter);

		zoneEdition = new ZoneEdition();
		zoneEdition.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				// debut
				int rotation = e.getWheelRotation();
				if (e.isShiftDown()) { // horizontal

					// zoneEdition.setPositionCam(new SVector3d(zoneEdition.getPositionCam().x,
					// zoneEdition.getPositionCam().y - e.getWheelRotation()));

					repaint();

					// if (rotation > 0) {
					//
					// System.out.println(e.getWheelRotation());
					//
					// zoneEdition.setPositionCam(new SVector3d(e.getWheelRotation()+
					// zoneEdition.getPositionCam().x, zoneEdition.getPositionCam().y));
					//
					// // weel vas vers le haut
					// // zoneEdition.setleZoom(e.getUnitsToScroll());
					// repaint();
					//
					// } else {
					// System.out.println(e.getWheelRotation());
					// // weel vas vers le bas
					// // zoneEdition.setleZoom(-e.getUnitsToScroll());
					//
					// zoneEdition.setPositionCam(new SVector3d(e.getWheelRotation()+
					// zoneEdition.getPositionCam().x, zoneEdition.getPositionCam().y));
					// }

					// fin

				} else { // vertical

					// System.out.println(e.getWheelRotation());
					if (zoneEdition.getPositionCam().x < 0) {
						zoneEdition.setPositionCam(new SVector3d(zoneEdition.getPositionCam().x - e.getWheelRotation(),
								zoneEdition.getPositionCam().y));
						repaint();
					} else {
						if (rotation > 0) {
							zoneEdition
									.setPositionCam(new SVector3d(zoneEdition.getPositionCam().x - e.getWheelRotation(),
											zoneEdition.getPositionCam().y));
							repaint();
						}

					}
					repaint();

				}
			}

		});

		zoneEdition.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				zoneEdition.requestFocusInWindow();
				repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				enClique = true;

				// debut
				trouverQuelObjetEstSeletionne(e);
				// fin
				bougerObstacle(e);
			}

			@Override
			public void mouseReleased(MouseEvent e2) {
				// interieur = false;
				enClique = false;
				interieur = false;
				posXPrecedent = -1;
				posYPrecedent = -1;

			}
		});

		zoneEdition.setBackground(new Color(168, 218, 220));
		zoneEdition.setBounds(6, 25, 880, 418);
		contentPane.add(zoneEdition);

		lblModificationObtacles = new JLabel("Modification de l'obtacle");
		lblModificationObtacles.setForeground(new Color(230, 57, 70));
		lblModificationObtacles.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblModificationObtacles.setBounds(11, 463, 163, 14);
		contentPane.add(lblModificationObtacles);

		JPanel panel = new JPanel();
		panel.setBackground(couleurOfficiel(5));
		panel.setBounds(10, 494, 648, 100);
		contentPane.add(panel);
		panel.setLayout(null);

		sldHauteur = new JSlider();
		sldHauteur.setMinimum(5);
		sldHauteur.setOpaque(false);
		sldHauteur.setForeground(new Color(230, 57, 70));
		sldHauteur.addChangeListener(new ChangeListener() {
			// Par Emon Dhar
			public void stateChanged(ChangeEvent e) {

				// debut
				JSlider source = (JSlider) e.getSource();
				if (!zoneEdition.getObstaclesList().isEmpty()) {
					zoneEdition.getObstaclesList().get(indiceObjetSelectionne)
							.setTaille(new SVector3d(
									zoneEdition.getObstaclesList().get(indiceObjetSelectionne).getTaille().x,
									source.getValue()));
					// zoneEdition.getObstaclesList().get(indiceObjetSelectionne).setPosition(new
					// SVector3d(zoneEdition.getObstaclesList().get(indiceObjetSelectionne).getPosition().x,indiceObjetSelectionne));

					repaint();
				} else {

					if (zoneEdition.requestFocusInWindow()) {

						JOptionPane
								.showMessageDialog(null,
										"Ben, tu fais quoi? \n" + "Oublie pas que tu ne peux pas changer la hauteur \n"
												+ "d'un objet qui n'existe pas!",
										"Yo, arrete!", JOptionPane.ERROR_MESSAGE);

					}
				}
				// fin
			}
		});
		sldHauteur.setValue(10);
		sldHauteur.setMaximum(30);
		sldHauteur.setPaintLabels(true);
		sldHauteur.setMajorTickSpacing(5);
		sldHauteur.setMinorTickSpacing(1);
		sldHauteur.setSnapToTicks(true);
		sldHauteur.setPaintTicks(true);
		sldHauteur.setBounds(10, 45, 315, 36);
		panel.add(sldHauteur);

		sldLargeur = new JSlider();
		sldLargeur.setMinimum(5);
		sldLargeur.setOpaque(false);
		sldLargeur.setForeground(new Color(230, 57, 70));
		sldLargeur.addChangeListener(new ChangeListener() {
			// Par Emon Dhar
			public void stateChanged(ChangeEvent e) {

				// debut
				System.out.println(" indice : " + indiceObjetSelectionne);
				JSlider source = (JSlider) e.getSource();
				if (!zoneEdition.getObstaclesList().isEmpty()) {
					zoneEdition.getObstaclesList().get(indiceObjetSelectionne).setTaille(new SVector3d(

							source.getValue(),
							zoneEdition.getObstaclesList().get(indiceObjetSelectionne).getTaille().y));
					;

					repaint();
				} else {

					if (zoneEdition.requestFocusInWindow()) {

						JOptionPane
								.showMessageDialog(null,
										"Ben, tu fais quoi? \n" + "Oublie pas que tu ne peux pas changer la hauteur \n"
												+ "d'un objet qui n'existe pas!",
										"Yo, arrete!", JOptionPane.ERROR_MESSAGE);

					}
				}
				// fin
			}
		});
		sldLargeur.setMajorTickSpacing(5);
		sldLargeur.setSnapToTicks(true);
		sldLargeur.setPaintTicks(true);
		sldLargeur.setPaintLabels(true);
		sldLargeur.setMinorTickSpacing(1);
		sldLargeur.setMaximum(80);
		sldLargeur.setValue(10);
		sldLargeur.setBounds(337, 46, 303, 36);
		panel.add(sldLargeur);

		lblHauteur = new JLabel("Hauteur");
		lblHauteur.setForeground(new Color(230, 57, 70));
		lblHauteur.setBounds(22, 20, 303, 14);
		panel.add(lblHauteur);

		lblLargeur = new JLabel("Largeur");
		lblLargeur.setForeground(new Color(230, 57, 70));
		lblLargeur.setBounds(348, 20, 205, 14);
		panel.add(lblLargeur);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(couleurOfficiel(5));
		panel_1.setBounds(892, 25, 233, 373);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		btnAjouterCactus = new JButton("Ajouter une cactus");
		btnAjouterCactus.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				// debut
				lblInfoobstacleselectione.setText(zoneEdition.descriptionObst(1));

				//
			}
		});
		btnAjouterCactus.addActionListener(new ActionListener() {
			// Par Emon Dhar
			public void actionPerformed(ActionEvent e) {
				// debut

				zoneEdition.ajouterCactus(
						new SVector3d(30 - zoneEdition.getPositionCam().x, 11.8 - zoneEdition.getPositionCam().y),
						new SVector3d(10, 27.163));
				zoneEdition.setNumeroCactus(zoneEdition.getNumeroCactus() + 1);
				zoneEdition.requestFocusInWindow();
				retourPossible++;
				lblInfoobstacleselectione.repaint();
				repaint();

				// fin
			}
		});
		btnAjouterCactus.setBounds(6, 48, 100, 100);
		panel_1.add(btnAjouterCactus);

		btnAjouterChampMag = new JButton("Ajouter un champ magnetique");
		btnAjouterChampMag.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				// debut

				lblInfoobstacleselectione.setText(zoneEdition.descriptionObst(2));
				// fin
			}
		});
		btnAjouterChampMag.addActionListener(new ActionListener() {
			// Par Emon Dhar
			public void actionPerformed(ActionEvent e) {
				// debut
				retourPossible++;
				if (!unChamp) {
					zoneEdition.ajouterChampMag(
							new SVector3d(35 - zoneEdition.getPositionCam().x, 14.452 - zoneEdition.getPositionCam().y),
							new SVector3d(10, 10), new SVector3d(0, 0, 12.0), 12, true);
					zoneEdition.setNumeroChampMag(zoneEdition.getNumeroChampMag() + 1);
					zoneEdition.requestFocusInWindow();
					unChamp = true;
					repaint();
				} else {
					JOptionPane.showMessageDialog(null, "Opps, on peut juste ajouter un seul champ magn�tique",
							"NON tu n'as pas le droit!", JOptionPane.ERROR_MESSAGE);
				}

				// fin
			}
		});
		btnAjouterChampMag.setBounds(118, 48, 100, 100);
		panel_1.add(btnAjouterChampMag);

		btnAjouterLave = new JButton("Ajouter une lave");
		btnAjouterLave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblInfoobstacleselectione.setText(zoneEdition.descriptionObst(4));
			}
		});
		btnAjouterLave.addActionListener(new ActionListener() {
			// Par Emon Dhar
			public void actionPerformed(ActionEvent e) {
				// debut
				retourPossible++;
				zoneEdition.ajouterLave(
						new SVector3d(35 - zoneEdition.getPositionCam().x, 14.452 - zoneEdition.getPositionCam().y),
						new SVector3d(10, 10));
				zoneEdition.setNumeroLave(zoneEdition.getNumeroLave() + 1);
				zoneEdition.requestFocusInWindow();
				repaint();

				// fin

			}
		});
		btnAjouterLave.setBounds(118, 156, 100, 100);
		panel_1.add(btnAjouterLave);

		btnAjouterMur = new JButton("Ajouter un mur");
		btnAjouterMur.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblInfoobstacleselectione.setText(zoneEdition.descriptionObst(5));
			}
		});

		btnAjouterMur.addActionListener(new ActionListener() {
			// Par Emon Dhar
			public void actionPerformed(ActionEvent e) {
				// debut
				retourPossible++;
				zoneEdition.ajouterMur(
						new SVector3d(35 - zoneEdition.getPositionCam().x, 14.452 - zoneEdition.getPositionCam().y),
						new SVector3d(10, 10));
				zoneEdition.setNumeroMur(zoneEdition.getNumeroMur() + 1);
				zoneEdition.requestFocusInWindow();
				repaint();

				// fin
			}
		});
		btnAjouterMur.setBounds(6, 156, 100, 100);
		panel_1.add(btnAjouterMur);

		btnAjouterScie = new JButton("Ajouter une scie");
		btnAjouterScie.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblInfoobstacleselectione.setText(zoneEdition.descriptionObst(6));
			}
		});
		btnAjouterScie.addActionListener(new ActionListener() {
			// Par Emon Dhar
			public void actionPerformed(ActionEvent e) {
				// debut
				retourPossible++;
				zoneEdition.ajouterScie(
						new SVector3d(35 - zoneEdition.getPositionCam().x, 14.452 - zoneEdition.getPositionCam().y),
						new SVector3d(10, 10));
				zoneEdition.setNumeroScie(zoneEdition.getNumeroScie() + 1);
				zoneEdition.requestFocusInWindow();
				repaint();

				// fin
			}
		});
		btnAjouterScie.setBounds(6, 268, 100, 100);
		panel_1.add(btnAjouterScie);

		associerBoutonAvecImage(btnAjouterCactus, "B_Cactus.png");
		associerBoutonAvecImage(btnAjouterChampMag, "B_Mag.png");
		associerBoutonAvecImage(btnAjouterLave, "B_Lave.png");
		associerBoutonAvecImage(btnAjouterMur, "B_Mur.png");
		associerBoutonAvecImage(btnAjouterScie, "B_Scie.png");

		JLabel lblAjoutezQuelqueChose = new JLabel("AJOUTEZ QUELQUE CHOSE!");
		lblAjoutezQuelqueChose.setForeground(new Color(230, 57, 70));
		lblAjoutezQuelqueChose.setBounds(6, 14, 212, 16);
		panel_1.add(lblAjoutezQuelqueChose);

		JButton btnRetour = new JButton("Retour");
		btnRetour.setBounds(112, 268, 106, 29);
		panel_1.add(btnRetour);

		JButton btnEffacer = new JButton("Effacer");
		btnEffacer.setBounds(112, 304, 106, 29);
		panel_1.add(btnEffacer);

		JButton btnEffacerTout = new JButton("Effacer Tout");
		btnEffacerTout.setBounds(112, 339, 106, 29);
		panel_1.add(btnEffacerTout);
		btnEffacerTout.addActionListener(new ActionListener() {
			// Par Emon Dhar
			public void actionPerformed(ActionEvent e) {
				// debut
				if (retourPossible > 0) {
					zoneEdition.enleverTousLesObs();
					retourPossible = 0;
					repaint();

					unChamp = false;

				} else {
					JOptionPane.showMessageDialog(null, "Tu fais quoi? Il n'y a rien a effacer ici!",
							"NON tu n'as pas le droit!", JOptionPane.ERROR_MESSAGE);
				}
				// fin
			}
		});
		btnEffacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				if (!zoneEdition.getObstaclesList().isEmpty()) {
					System.out.println(
							zoneEdition.getObstaclesList().get(indiceObjetSelectionne).getNumeroObstacle() + " fgbdfg");
					if (zoneEdition.getObstaclesList().get(indiceObjetSelectionne).getNumeroObstacle() == 2) {
						unChamp = false;
					}
					zoneEdition.effacerObjetSelec(indiceObjetSelectionne);
					indiceObjetSelectionne = indiceObjetSelectionne - 1;
					repaint();
				} else {
					JOptionPane.showMessageDialog(null, "Oops, on ne peut pas effacer l'ineffaçable!",
							"NON tu n'as pas le droit!", JOptionPane.ERROR_MESSAGE);
				}

				// fin
			}
		});
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				if (retourPossible > 0) {

					if (zoneEdition.getObstaclesList().get(zoneEdition.getObstaclesList().size() - 1)
							.getNumeroObstacle() == 2 && !zoneEdition.getObstaclesList().isEmpty()) {
						unChamp = false;
						repaint();
					}
					zoneEdition.enleverlaDerniereObs();
					retourPossible = retourPossible - 1;

					indiceObjetSelectionne = indiceObjetSelectionne - 1;

					repaint();
				} else {
					JOptionPane.showMessageDialog(null, "Oops, on ne peut pas faire un retour en arriere!",
							"NON tu n'as pas le droit!", JOptionPane.ERROR_MESSAGE);
				}
				// fin
			}
		});

		lblInfoobstacleselectione = new JLabel(zoneEdition.descriptionObst(1));
		lblInfoobstacleselectione.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblInfoobstacleselectione.setBounds(900, 397, 225, 90);
		lblInfoobstacleselectione.setForeground(new Color(230, 57, 70));
		contentPane.add(lblInfoobstacleselectione);
		
		listeEdition = new JComboBox();
		listeEdition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// debut
				nomFichier = (String) listeEdition.getSelectedItem();

				System.out.println("Le nom du fichier est " + nomFichier);
				System.out.println(listeNom.size());

				for (int i = 0; i < listeNom.size(); i++) {

					System.out.println("liste niveau =" + (listNiveaux.get(i).getNomFichier()));

					if (listNiveaux.get(i).getNomFichier().equals(nomFichier)) {
						normal.setNiveaux(listNiveaux.get(i));
						setVisible(false);
						if (!normal.isVisible()) {
							normal.setVisible(true);
						}
					}
				}
				// fin
			}
		});
		listeEdition.setBounds(668, 494, 182, 20);
		contentPane.add(listeEdition);

		lblNiveauCree = new JLabel("Niveau cree");
		lblNiveauCree.setForeground(new Color(230, 57, 70));
		lblNiveauCree.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNiveauCree.setBounds(703, 465, 104, 14);
		contentPane.add(lblNiveauCree);
	}

	/**
	 * Methode qui oblige a choisir de retourner vers ou apres la sauvegarde
	 */
	// par Carrie
	private void confirmationEtSortie() {

		String[] retour = { "Accueil", "Jeu" };
		int rep = JOptionPane.showOptionDialog(null,
				"Voulez-vous retourner a l'accueil ou commencer la partie avec votre nouvel environnement?",
				"Retour vers ou", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, retour, null);

		if (rep == JOptionPane.YES_OPTION) {
			fenetreAccueil = new App23SpiderMan();
			setVisible(false);
			fenetreAccueil.setVisible(true);
			dispose();// Methode qui sert a detruire cette instance

		} else {
			setVisible(false);
			// lire = new LireNiveau();
			normal.setVisible(true);
		}
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

		// Code a changer

	}

	// /**
	// * Methode qui verifie si le clic de la souris est a l'interieur de l'aire du
	// coprs
	// * @param sceneAnime, composant personnalise
	// * @param e, evenement de souris
	// */
	//
	// private void sourisVerifierInterieur(ZoneEdition zoneEdition, MouseEvent e) {
	// for(int i=0; i<zoneEdition.getObstaclesList().size(); i++) {
	// if(zoneEdition.getObstaclesList().get(i).contient(e.getX(), e.getY())) {
	// interieur=true;
	//
	// }
	// }
	// }

	/**
	 * Methode qui permet de retourner une coleur de la palette
	 * 
	 * @param numeroDeCouleur Pend en paramettre le numero de coleur demander
	 * @return renourne une couleur
	 */
	// Par Emon
	private Color couleurOfficiel(int numeroDeCouleur) {
		if (numeroDeCouleur == 1) {

			return new Color(230, 57, 70);
		}

		if (numeroDeCouleur == 2) {

			return new Color(241, 250, 238);
		}
		if (numeroDeCouleur == 3) {

			return new Color(168, 218, 220);
		}
		if (numeroDeCouleur == 4) {

			return new Color(168, 218, 220);
		} else {
			return new Color(29, 53, 87);
		}

	}

	/**
	 * Methode permettant de touver l'objet selectionner
	 * 
	 * @param e c'est une MouseEvent qui permet de savoir la position de la sourris
	 */
	// Par Emon Dhar
	private void trouverQuelObjetEstSeletionne(MouseEvent e) {

		if (!zoneEdition.getObstaclesList().isEmpty()) {

			for (int k = 0; k < zoneEdition.getObstaclesList().size(); k++) {
				if (zoneEdition.getObstaclesList().get(k).getArea().contains(e.getX(), e.getY())) {
					indiceObjetSelectionne = k;
					lblInfoobstacleselectione.repaint();
					interieur = true;

				}
			}

		}

		System.out.println("Position Obstacle + Taille: ("
				+ zoneEdition.getObstaclesList().get(indiceObjetSelectionne).getPosition().getXY() + ", "
				+ zoneEdition.getObstaclesList().get(indiceObjetSelectionne).getTaille().getXY() + ")");

		posXPrecedent = zoneEdition.getObstaclesList().get(indiceObjetSelectionne).getPosition().x;
		posYPrecedent = zoneEdition.getObstaclesList().get(indiceObjetSelectionne).getPosition().y;

		xPrecedent = e.getX();
		yPrecedent = e.getY();
	}

	/**
	 * Methode permettant de bouger l'obstacle
	 * 
	 * @param e c'est une MouseEvent qui permet de savoir la position de la sourris
	 */
	// Par Emon Dhar
	private void bougerObstacle(MouseEvent e) {
		zoneEdition.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent nouv) {
				if (interieur) {

					zoneEdition.getObstaclesList().get(indiceObjetSelectionne).setPosition(
							new SVector3d(zoneEdition.valeurEnReelleX(nouv.getX() - xPrecedent) + posXPrecedent,
									zoneEdition.valeurEnReelleY(nouv.getY() - yPrecedent) + posYPrecedent));

					repaint();
					enClique = false;
				}

			}

		});
	}

	/**
	 * Methode servant a ajouter le nom a la liste deroulante
	 */
	// Carrie Kam
	private void ajoutNomNiveau(String nomFichier) {

		System.out.println("Debut ajout nom " + nomFichier);

		listeEdition.addItem(nomFichier);
		listeNom.add(nomFichier);

		if (i < 5) {
			i++;
		} else {
			JOptionPane.showMessageDialog(null, "Vous avez atteint au maximum de niveaux!");
		}
		niveauCree = new Niveaux("");
		niveauCree.setObstaclesList(zoneEdition.getObstaclesList());
		niveauCree.setNomFichier(nomFichier);
		listNiveaux.add(niveauCree);

	}

	/**
	 * Creation servant a cree un nouveau niveau
	 */
	// Carrie Kam
	private void newLvl() {
		setVisible(false);
		niveauCree = new Niveaux("");
		niveauCree.setObstaclesList(zoneEdition.getObstaclesList());
		listNiveaux.add(niveauCree);
		normal.setNiveaux(niveauCree);
	}

}