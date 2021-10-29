package mode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.JobOriginatingUserName;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import affichage.BarreProg;
import affichage.ZoneDeJeu;
import interfaces.InfoListener;
import interfaces.SpiderListener;
import simonVezina.SVector3d;

/**
 * Application permettant d'illustrer le jeu en mode Normal (le jeu sans de
 * parametres extra), en mode Scientifique et en mode Parametre.
 * 
 * @author Carrie Kam
 * @author Philippe Cote
 * @author Emon Dhar
 */
public class Normal extends JFrame {

	private static boolean modeJour=true;
	private static Normal frame;
	private boolean state = true;
	private boolean mat = true;


	// Variables liees a l'affichage
	private JPanel contentPane;
	private ZoneDeJeu zoneDeJeu;
	private JPanel parametreOutil;
	private JPanel panelScientifique;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private boolean isInterZone = true;

	// Variables liees a l'interface "Parametres"
	private JLabel lblParametres;
	private JLabel lblMasseDuPersonnage;
	private JSpinner spnMassePersonnage;
	private JLabel lblDstLancerCorde;
	private JSpinner spnDstLancerCorde;
	private JLabel lblVitesse;
	private JSpinner spnVitesse;
	private JButton btnPlaneteMars;
	private JButton btnPlaneteUranus;
	private JButton btnPlaneteJupiter;
	private JLabel lblGravite;
	private JSpinner spnGravite;

	// Variables liees a l'interface "Scientifique"
	private JRadioButtonMenuItem mntmNormal;
	private JButton btnArret;
	private JButton btnPasAPAs;
	private JButton btnReinitialiser;
	private ChoixPerso fenetrePerso;
	private JTable tableLineaire;
	private JTable tableAngulaire;
	private JScrollPane scrollPaneAngulaire;
	private JButton btnDemarrer;
	private JCheckBox chckbxAfficherVecteur;
	private BarreProg barreProg;

	// Variable liee entre l'interaction des classes Normal et App23SpiderMan
	private ArrayList<SpiderListener> listeEcouteurs = new ArrayList<SpiderListener>();

	/**
	 * Demarrage de l'application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Normal frame = new Normal();
					frame.setVisible(true);
					frame.zoneDeJeu.requestFocusInWindow();
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
	 * Constructeur de la classe Normal
	 */
	public Normal() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(20, 20, 1320, 677);
		Dimension ecranDimension = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(ecranDimension.width/2-getSize().width/2, ecranDimension.height/2-getSize().height/2);
		setTitle("Spiderman, Spiderman");

		fenetrePerso = new ChoixPerso();

		fenetrePerso.addInfoListener(new InfoListener() {

			public void sauvegarderPerso(Color couleur, boolean spider) {

				zoneDeJeu.setCouleurPerso(couleur);
				zoneDeJeu.setSpiderFace(spider);
			}

			@Override
			public void informationsPerso(SVector3d acceleration, SVector3d vitesse, SVector3d position,
					double vitesseA, double accelA, double g, double masse, double hauteurDuMonde) {
				barreEnergie(acceleration, vitesse, position, g, masse, hauteurDuMonde);
				// TODO Auto-generated method stub

			}

			@Override
			public void mort(boolean mort) {
				// TODO Auto-generated method stub
				System.out.println("On Passe ici morray");
				if (mort) {
					miseEnPageMort();
				}

			}

			@Override
			public void victoire() {
				// TODO Auto-generated method stub
				btnPasAPAs.setEnabled(false);

				btnDemarrer.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						leverModeNiveaux();
					}
				});

				btnArret.setEnabled(false);

			}

		});

		JMenuBar menuMode = new JMenuBar();
		setJMenuBar(menuMode);

		JMenu mnMode = new JMenu("MODE");
		menuMode.add(mnMode);

		JRadioButtonMenuItem mntmParametre = new JRadioButtonMenuItem("Parametres");
		buttonGroup.add(mntmParametre);
		mntmParametre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// debut
				parametre();
				// fin
			}
		});

		mnMode.add(mntmParametre);

		JRadioButtonMenuItem mntmScientifique = new JRadioButtonMenuItem("Scientifique");
		buttonGroup.add(mntmScientifique);
		mntmScientifique.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				scientifique();
				// fin
			}
		});
		mnMode.add(mntmScientifique);

		mntmNormal = new JRadioButtonMenuItem("Normal");
		buttonGroup.add(mntmNormal);
		mntmNormal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// debut

				normal();
				// fin
			}
		});
		mnMode.add(mntmNormal);

		JMenu mnRetour = new JMenu("RETOUR");
		menuMode.add(mnRetour);

		JMenuItem mntmAccueil = new JMenuItem("Accueil");
		mntmAccueil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// debut
				zoneDeJeu.reinitialiser();
				btnDemarrer.setEnabled(true);
				btnArret.setEnabled(true);
				btnPasAPAs.setEnabled(true);
				leverModeAccueil();
				// fin
			}
		});
		mnRetour.add(mntmAccueil);

		JMenuItem mntmPageDesNiveaux = new JMenuItem("Page des niveaux");
		mntmPageDesNiveaux.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// debut
				zoneDeJeu.reinitialiser();
				btnDemarrer.setEnabled(true);
				btnArret.setEnabled(true);
				btnPasAPAs.setEnabled(true);
				leverModeNiveaux();
				// fin
			}
		});
		mnRetour.add(mntmPageDesNiveaux);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 57, 70));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		zoneDeJeu = new ZoneDeJeu();
		// Emon Dhar
		zoneDeJeu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {


			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				zoneDeJeu.requestFocus();
			}
		});
		zoneDeJeu.addInfoListener(new InfoListener() {

			@Override
			public void sauvegarderPerso(Color couleur, boolean spiderFace) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mort(boolean mort) {
				// TODO Auto-generated method stub

				if (mort) {
					miseEnPageMort();
				}
			}

			@Override
			public void informationsPerso(SVector3d acceleration, SVector3d vitesse, SVector3d position,
					double vitesseA, double accelA, double g, double masse, double hauteurDuMonde) {

				MAJdesTableaux(acceleration, vitesse, position, vitesseA, accelA);
				barreEnergie(acceleration, vitesse, position, g, masse, hauteurDuMonde);

			}

			@Override
			public void victoire() {
				// TODO Auto-generated method stub
				btnPasAPAs.setEnabled(false);
				btnArret.setEnabled(false);

			}
		});

		zoneDeJeu.setBounds(0, 0, 1320, 627);
		contentPane.add(zoneDeJeu);
		zoneDeJeu.setLayout(null);

		btnReinitialiser = new JButton("Reinitialiser");
		btnReinitialiser.setBounds(1059, 561, 233, 49);
		zoneDeJeu.add(btnReinitialiser);
		btnReinitialiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zoneDeJeu.reinitialiser();
				btnPasAPAs.setEnabled(true);
				btnDemarrer.setEnabled(true);
				btnArret.setEnabled(true);

				if (chckbxAfficherVecteur.isSelected()) {
					zoneDeJeu.setVecteurGraphique(true);
				} else {

					zoneDeJeu.setVecteurGraphique(false);
				}

			}
		});
		associerBoutonAvecImage(btnReinitialiser, "B_J_Restar.png");

		btnArret = new JButton("Arret");
		btnArret.setBounds(888, 561, 164, 49);
		zoneDeJeu.add(btnArret);
		btnArret.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zoneDeJeu.arreter();
			}
		});
		associerBoutonAvecImage(btnArret, "B_J_Pause.png");

		btnDemarrer = new JButton("Demarrer");
		btnDemarrer.setBounds(712, 561, 164, 49);
		zoneDeJeu.add(btnDemarrer);
		btnDemarrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (zoneDeJeu.getVictoire()) {
					leverModeNiveaux();
					zoneDeJeu.reinitialiser();
					btnArret.setEnabled(true);
				}else {
					zoneDeJeu.demarrer();
					zoneDeJeu.requestFocus();

				}



			}
		});

		associerBoutonAvecImage(btnDemarrer, "B_J_Jouer.png");

		panelScientifique = new JPanel();
		panelScientifique.setBounds(1000, 0, 368, 568);
		contentPane.add(panelScientifique);
		panelScientifique.setLayout(null);
		panelScientifique.setVisible(false);

		JScrollPane scrollPaneLineaire = new JScrollPane();
		scrollPaneLineaire.setBounds(152, 39, 158, 174);
		panelScientifique.add(scrollPaneLineaire);

		tableLineaire = new JTable();
		tableLineaire.setEnabled(false);
		tableLineaire.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				boolean b = tableLineaire.isEditing();

				if (b == false) {
					JOptionPane.showMessageDialog(null, "Tu ne peux pas modifier les cellules");
				}
			}
		});
		scrollPaneLineaire.setViewportView(tableLineaire);
		tableLineaire.setRowSelectionAllowed(false);
		tableLineaire.setModel(new DefaultTableModel(new Object[][] { { null, null }, { null, null }, { null, null }, },
				new String[] { "X", "Y" }));
		tableLineaire.setRowHeight(50);

		scrollPaneAngulaire = new JScrollPane();
		scrollPaneAngulaire.setBounds(152, 226, 158, 110);
		panelScientifique.add(scrollPaneAngulaire);

		tableAngulaire = new JTable();
		tableAngulaire.setEnabled(false);
		scrollPaneAngulaire.setViewportView(tableAngulaire);
		tableAngulaire.setModel(
				new DefaultTableModel(
						new Object[][] {
							{null},
							{null},
						},
						new String[] {
								""
						}
						));

		tableAngulaire.setRowHeight(50);

		JLabel lblAccelRec = new JLabel("acc\u00E9l\u00E9ration rectiligne");
		lblAccelRec.setBounds(6, 78, 158, 14);
		panelScientifique.add(lblAccelRec);

		JLabel lblVitRec = new JLabel("vitesse rectiligne");
		lblVitRec.setBounds(30, 133, 113, 14);
		panelScientifique.add(lblVitRec);

		JLabel lblPosition = new JLabel("Position ");
		lblPosition.setBounds(83, 182, 55, 14);
		panelScientifique.add(lblPosition);

		JLabel lblVitAng = new JLabel("vitesse angulaire");
		lblVitAng.setBounds(28, 248, 113, 14);
		panelScientifique.add(lblVitAng);

		JLabel lblAccelAngulaire = new JLabel("acc\u00E9l\u00E9lration angulaire");
		lblAccelAngulaire.setBounds(6, 308, 148, 14);
		panelScientifique.add(lblAccelAngulaire);

		JLabel lblUniteAccelRec = new JLabel("m/s^2");
		lblUniteAccelRec.setBounds(317, 78, 46, 14);
		panelScientifique.add(lblUniteAccelRec);

		JLabel lblUniteVitesseRec = new JLabel("m/s");
		lblUniteVitesseRec.setBounds(317, 133, 46, 14);
		panelScientifique.add(lblUniteVitesseRec);

		JLabel lblUnitePos = new JLabel("m");
		lblUnitePos.setBounds(317, 182, 46, 14);
		panelScientifique.add(lblUnitePos);

		JLabel lblUniteVitesseAng = new JLabel("Rad/s");
		lblUniteVitesseAng.setBounds(317, 255, 46, 14);
		panelScientifique.add(lblUniteVitesseAng);

		JLabel lblUniteAccelAng = new JLabel("Rad/s^2");
		lblUniteAccelAng.setBounds(317, 308, 64, 14);
		panelScientifique.add(lblUniteAccelAng);

		chckbxAfficherVecteur = new JCheckBox("Afficher vecteur vitesse");
		chckbxAfficherVecteur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (chckbxAfficherVecteur.isSelected()) {
					zoneDeJeu.setVecteurGraphique(true);
				} else {
					System.out.println("LALILALALALALA");
					zoneDeJeu.setVecteurGraphique(false);
				}
			}
		});
		chckbxAfficherVecteur.setBounds(147, 354, 198, 23);
		panelScientifique.add(chckbxAfficherVecteur);

		// BarreProg barreProg = new BarreProg( new SVector3d(5,5), new
		// SVector3d(30,100), new SVector3d(30,10), new SVector3d(30,100), 5, 9, 20);
		barreProg = new BarreProg(new SVector3d(5, 10), new SVector3d(30, 100), new SVector3d(5, 10),
				new SVector3d(5, 10), 0, 0, 0);
		barreProg.setBounds(27, 389, 313, 157);
		panelScientifique.add(barreProg);

		JLabel lblParametresScientifique = new JLabel("Parametres Scientifiques");
		lblParametresScientifique.setBounds(17, 6, 198, 27);
		panelScientifique.add(lblParametresScientifique);
		barreProg.setVisible(false);

		parametreOutil = new JPanel();
		parametreOutil.setBounds(1000, 0, 368, 552);
		contentPane.add(parametreOutil);
		parametreOutil.setLayout(null);
		parametreOutil.setVisible(false);

		lblParametres = new JLabel("Parametres");
		lblParametres.setBounds(16, 0, 93, 27);
		parametreOutil.add(lblParametres);

		lblMasseDuPersonnage = new JLabel("Masse du personnage");
		lblMasseDuPersonnage.setBounds(16, 93, 175, 15);
		parametreOutil.add(lblMasseDuPersonnage);

		spnMassePersonnage = new JSpinner();
		spnMassePersonnage.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
		spnMassePersonnage.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				zoneDeJeu.setMasse(spnMassePersonnage.getValue());
				// fin
			}
		});
		spnMassePersonnage.setBounds(221, 93, 77, 20);
		parametreOutil.add(spnMassePersonnage);

		lblDstLancerCorde = new JLabel("Distance du lancer de la corde");
		lblDstLancerCorde.setBounds(16, 120, 195, 15);
		parametreOutil.add(lblDstLancerCorde);

		spnDstLancerCorde = new JSpinner();
		spnDstLancerCorde.setModel(new SpinnerNumberModel(new Double(12), new Double(1), null, new Double(0.2)));
		spnDstLancerCorde.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				zoneDeJeu.setDstLancerCorde((double) spnDstLancerCorde.getValue());
				// fin
			}
		});
		spnDstLancerCorde.setBounds(221, 115, 78, 20);
		parametreOutil.add(spnDstLancerCorde);

		lblVitesse = new JLabel("Vitesse initiale horizontale ( axe X )");
		lblVitesse.setBounds(16, 174, 212, 15);
		parametreOutil.add(lblVitesse);

		spnVitesse = new JSpinner();
		spnVitesse.setModel(new SpinnerNumberModel(2.0, 0.0, 100.0, 0.2));
		spnVitesse.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				zoneDeJeu.setVitesseInitiale((double) spnVitesse.getValue());
				// fin
			}
		});
		spnVitesse.setBounds(221, 174, 77, 20);
		parametreOutil.add(spnVitesse);

		JPanel choixPlanetes = new JPanel();
		choixPlanetes.setBorder(
				new TitledBorder(null, "Choix planetes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		choixPlanetes.setBounds(16, 214, 175, 184);
		parametreOutil.add(choixPlanetes);
		choixPlanetes.setLayout(null);

		btnPlaneteMars = new JButton("Mars");
		btnPlaneteMars.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				zoneDeJeu.setGravite(3.711);
				spnGravite.setValue(3.711);
			}
		});
		btnPlaneteMars.setBounds(5, 17, 97, 40);
		choixPlanetes.add(btnPlaneteMars);

		btnPlaneteUranus = new JButton("Uranus");
		btnPlaneteUranus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zoneDeJeu.setGravite(8.87);
				spnGravite.setValue(8.87);
			}
		});
		btnPlaneteUranus.setBounds(5, 119, 97, 40);
		choixPlanetes.add(btnPlaneteUranus);

		btnPlaneteJupiter = new JButton("Jupiter");
		btnPlaneteJupiter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zoneDeJeu.setGravite(24.7964249);
				spnGravite.setValue(24.7964249);
			}
		});
		btnPlaneteJupiter.setBounds(5, 68, 97, 40);
		choixPlanetes.add(btnPlaneteJupiter);

		lblGravite = new JLabel("Gravit\u00E9 :");
		lblGravite.setBounds(195, 297, 68, 15);
		parametreOutil.add(lblGravite);

		spnGravite = new JSpinner();
		spnGravite.setModel(new SpinnerNumberModel(new Double(9.8), new Double(1), null, new Double(0.2)));
		spnGravite.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// debut
				zoneDeJeu.setGravite((double) spnGravite.getValue());
				// fin
			}
		});
		spnGravite.setBounds(262, 294, 96, 20);
		parametreOutil.add(spnGravite);

		JButton btnParamtresParDfaults = new JButton("Param\u00E8tres par d\u00E9faults");
		btnParamtresParDfaults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				zoneDeJeu.setMasse(1);
				spnMassePersonnage.setValue(1);

				zoneDeJeu.setVitesseInitiale(0);
				spnVitesse.setValue(0.0);

				zoneDeJeu.setGravite(9.8);
				spnGravite.setValue(9.8);

			}
		});
		btnParamtresParDfaults.setBounds(8, 442, 322, 23);
		parametreOutil.add(btnParamtresParDfaults);

		JButton btnModficationDuPersonnage = new JButton("Modfication du Personnage");
		btnModficationDuPersonnage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!fenetrePerso.isVisible()) {
					fenetrePerso.setVisible(true);

				}
			}
		});
		btnModficationDuPersonnage.setBounds(8, 409, 322, 23);
		parametreOutil.add(btnModficationDuPersonnage);

		JCheckBox chckbxActiverSon = new JCheckBox("Son activ\u00E9");
		chckbxActiverSon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				zoneDeJeu.activerSon(chckbxActiverSon.isSelected());

			}
		});
		chckbxActiverSon.setSelected(true);
		chckbxActiverSon.setBounds(16, 52, 119, 23);
		parametreOutil.add(chckbxActiverSon);

		btnPasAPAs = new JButton("Pas a pas");
		btnPasAPAs.setBounds(8, 476, 322, 29);
		parametreOutil.add(btnPasAPAs);

		JLabel lblUniteVitesse = new JLabel("m/s");
		lblUniteVitesse.setBounds(308, 174, 46, 14);
		parametreOutil.add(lblUniteVitesse);

		JLabel lblUniteDst = new JLabel("m");
		lblUniteDst.setBounds(309, 118, 21, 14);
		parametreOutil.add(lblUniteDst);

		JLabel lblKg = new JLabel("Kg");
		lblKg.setBounds(308, 96, 46, 14);
		parametreOutil.add(lblKg);

		JCheckBox chckbxGraphiqueFaible = new JCheckBox("Graphique Faible");
		chckbxGraphiqueFaible.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				zoneDeJeu.setSimple(chckbxGraphiqueFaible.isSelected());
				zoneDeJeu.requestFocus();
				repaint();
				
			}
		});
		chckbxGraphiqueFaible.setBounds(131, 2, 151, 23);
		parametreOutil.add(chckbxGraphiqueFaible);
		btnPasAPAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zoneDeJeu.calculerUneIterationPhysique();
			}
		});

	}

	/**
	 * Methode qui desactive certains boutton lors de la scene mort et qui appelle la methode mourir de la scene ZoneDeJeu
	 * 
	 *
	 */
	// Par Philippe Cote
	public void miseEnPageMort() {
		// TODO Auto-generated method stub
		btnPasAPAs.setEnabled(false);
		btnDemarrer.setEnabled(false);
		btnArret.setEnabled(false);
		zoneDeJeu.mourir();

	}

	/**
	 * Methode qui sert a change l'interface en mode "Parametre"
	 */
	// par Carrie Kam
	public void parametre() {
		zoneDeJeu.setBounds(0, 0, 1000, 475);
		parametreOutil.setVisible(true);
		panelScientifique.setVisible(false);
		barreProg.setVisible(false);
		setTitle("Spiderman - Parametre");
		parametreED();
	}

	/**
	 * Methode permettant changer le look de l'interface en mode "Parametre"
	 */
	// par Emon Dhar
	public void parametreED() {

		setBounds(10, 10, 1374, 584);
		Dimension ecranDimension = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(ecranDimension.width/2-getSize().width/2, ecranDimension.height/2-getSize().height/2);


		btnDemarrer.setBounds(412, 479, 164, 49);
		btnArret.setBounds(587, 479, 164, 49);
		btnReinitialiser.setBounds(757, 479, 233, 49);
		contentPane.add(btnDemarrer );
		contentPane.add( btnArret);
		contentPane.add(btnReinitialiser );
	}

	/**
	 * Methode qui sert a change l'interface en mode "Normal"
	 * 
	 */
	// Par Carrie Kam
	public void normal() {
		zoneDeJeu.setBounds(0, 0, 1320, 627);
		panelScientifique.setVisible(false);
		setTitle("Spiderman - Normal");
		parametreOutil.setVisible(false);

		btnReinitialiser.setVisible(true);

		btnArret.setVisible(true);
		btnDemarrer.setVisible(true);
		barreProg.setVisible(true);
		normalED();
	}



	/**
	 * Methode permettant changer le look de l'interface en mode "Normal"
	 * 
	 */
	// Par Emon Dhar
	public void normalED() {

		setBounds(20, 20, 1320, 677);
		Dimension ecranDimension = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(ecranDimension.width/2-getSize().width/2, ecranDimension.height/2-getSize().height/2);

		btnDemarrer.setBounds(712, 561, 164, 49);
		btnArret.setBounds(888, 561, 164, 49);
		btnReinitialiser.setBounds(1059, 561, 233, 49);
		zoneDeJeu.add(btnDemarrer );
		zoneDeJeu.add( btnArret);
		zoneDeJeu.add(btnReinitialiser );
	}

	/**
	 * Methode qui sert a change l'interface en mode "Scientifique"
	 */
	// Par Carrie
	public void scientifique() {

		zoneDeJeu.setBounds(0, 0, 1000, 475);
		setBounds(10, 10, 1374, 552);
		barreProg.setVisible(true);
		parametreOutil.setVisible(false);
		panelScientifique.setVisible(true);

		setTitle("Spiderman - Scientifique");
		scientifiqueED();
	}

	/**
	 * Methode permettant changer le look de l'interface en mode "Scientifique"
	 */
	// Par Emon Dhar
	public void scientifiqueED() {

		setBounds(10, 10, 1374, 584);
		Dimension ecranDimension = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(ecranDimension.width/2-getSize().width/2, ecranDimension.height/2-getSize().height/2);

		btnDemarrer.setBounds(412, 479, 164, 49);
		btnArret.setBounds(587, 479, 164, 49);
		btnReinitialiser.setBounds(757, 479, 233, 49);

		contentPane.add(btnDemarrer );
		contentPane.add( btnArret);
		contentPane.add(btnReinitialiser );
	}

	/**
	 * Methode servant a savoir quel niveau la zoneDeJeu va etre changer
	 * 
	 * @param niveau Niveau choisi
	 * @return niveau Niveau choisi
	 */
	// Carrie Kam
	public void setNiveaux(Niveaux niveau) {
		this.zoneDeJeu.setNiveaux(niveau);
	}

	/**
	 * Methode servant a obtenir la zone de jeu
	 * 
	 * @return zoneDeJeu la zoneDeJeu utilisee
	 */
	// Par Carrie Kam
	public ZoneDeJeu getZoneDeJeu() {
		return this.zoneDeJeu;
	}

	/**
	 * 
	 * @param acceleration acceleration du personnage
	 * @param vitesse vitesse du personnage	
	 * @param position position du perosnnage
	 * @param vitesseA vitanesse angulaire du personnage
	 * @param accelA 	acceleration angulaire du prsonnage
	 */
	// Par Philippe Cote
	public void MAJdesTableaux(SVector3d acceleration, SVector3d vitesse, SVector3d position, double vitesseA,
			double accelA) {
		tableLineaire
		.setModel(new DefaultTableModel(
				new Object[][] {
					{ String.format("%.3f", acceleration.getX()),
						"-"+String.format("%.3f", acceleration.getY()) },
					{ String.format("%.3f", vitesse.getX()), "-"+String.format("%.3f", vitesse.getY()) },
					{ String.format("%.3f", position.getX()), "-"+String.format("%.3f", position.getY()) }, },
				new String[] { "X", "Y" }));

		tableAngulaire
		.setModel(new DefaultTableModel(
				new Object[][] { { String.format("%.3f", vitesseA) },
					{ String.format("%.3f", accelA), String.format("%.3f", accelA) }, },
				new String[] { "" }));

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


	/**
	 * Methode permettant d'assigner les valeur necessaire pour la barre d'energie  
	 * 
	 * @param acceleration
	 * @param vitesse
	 * @param position
	 * @param g
	 * @param masse
	 * @param hauteurDuMonde
	 */
	// Par Emon
	public void barreEnergie(SVector3d acceleration, SVector3d vitesse, SVector3d position, double g, double masse,
			double hauteurDuMonde) {


		barreProg.setPositionDuPerso(position);
		barreProg.setVitesseDuPerso(vitesse);
		barreProg.setMasse(masse);
		barreProg.setGravite(g);
		barreProg.setHauteurDuMonde(hauteurDuMonde);
		repaint();
	}

	/**
	 * Methode servant a ajoute des ecouteurs
	 * @param objEcout objet qui ecoute les evenements entre les classes
	 */
	//Carrie
	public void addSpiderListener(SpiderListener objEcout) {
		listeEcouteurs.add(objEcout);
	}

	/**
	 * Methode servant a savoir si l'utilisateur a choisi de retourner a l'acceuil
	 */
	//Carrie
	private void leverModeAccueil() {
		for (SpiderListener objEcout : listeEcouteurs) {
			objEcout.modeAccueil(true);
		}
	}

	/**
	 * Methode servant a savoir si l'utilisateur a choisi de retourner dans le mode des niveaux
	 */
	//Carrie
	private void leverModeNiveaux() {
		for (SpiderListener objEcout : listeEcouteurs) {
			objEcout.modeNiveaux(true);
		}
	}









	/**
	 * Methode servant a communiquer entre les classes App23SpiderMan et Normal pour
	 * le mode nuit
	 * 
	 * @param mat variable servant a communiquer entre les classes App23SpiderMan et
	 *            Normal pour le mode nuit et jour
	 */
	//Carrie Kam
	public void setMatin(boolean mat) {
		setModeJour(mat);
		jourNuit();
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
		Normal.modeJour = modeJour;
	}

	/**
	 * Methode servant a mettre le l'application en mode jour ou en mode nuit
	 */
	// Carrie Kam
	public void jourNuit() {
		if (modeJour == true && mat == true) {
			System.out.println("Red");
			state = false;
			//	contentPane.setBackground(new Color(230, 57, 70));
			contentPane.setBackground(new Color(51,51,51));

		} else {
			System.out.println("Noir: modeJour " + modeJour + " mat = " + mat);
			state = true;
			contentPane.setBackground(new Color(230, 57, 70));
			//	contentPane.setBackground(new Color(51,51,51));

		}
	}
}