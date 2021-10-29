package mode;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import affichage.DemoPerso;
import interfaces.InfoListener;
/**
 * 
 * Classe, qui cree une interface pour personnalisé l'esthétisme du personnage 
 * 
 * @author Philippe Cote
 */
public class ChoixPerso extends JFrame {

	private JPanel contentPane;
	private JPanel panelModificationPersonnage;
	private final ButtonGroup apparenceButtonGroupe = new ButtonGroup();
	private ArrayList<InfoListener> listeEcouteurs = new ArrayList<InfoListener>();
	private boolean spiderFace=false;
	/**
	 * Demarrer l'application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChoixPerso frame = new ChoixPerso();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creation d'une fenetre.
	 */
	public ChoixPerso() {
		setBounds(100, 100, 662, 446);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		panelModificationPersonnage = new JPanel();
		panelModificationPersonnage.setBounds(538, 40, 122, 218);
		contentPane.add(panelModificationPersonnage);
		panelModificationPersonnage.setLayout(null);

		DemoPerso demoPerso = new DemoPerso();
		demoPerso.setBounds(0, 0, 461, 221);
		panelModificationPersonnage.add(demoPerso);

	
		
		
		JPanel pnCouleur = new JPanel();
		pnCouleur.setBounds(31, 232, 581, 154);
		panelModificationPersonnage.add(pnCouleur);
		pnCouleur.setLayout(null);
		
		JLabel lblChoixCouleur = new JLabel("Choix de couleur\n");
		lblChoixCouleur.setBounds(12, 12, 157, 15);
		pnCouleur.add(lblChoixCouleur);
		
		JSlider sliderR = new JSlider();
		sliderR.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				
				demoPerso.setR(sliderR.getValue());
				
			}
		});
		sliderR.setValue(0);
		sliderR.setMinorTickSpacing(1);
		sliderR.setMaximum(255);
		sliderR.setBounds(0, 27, 420, 26);
		pnCouleur.add(sliderR);
		
		JSlider sliderG = new JSlider();
		sliderG.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				
				demoPerso.setG(sliderG.getValue());
				
			}
		});
		sliderG.setValue(0);
		sliderG.setMinorTickSpacing(1);
		sliderG.setMaximum(255);
		sliderG.setBounds(0, 52, 420, 33);
		pnCouleur.add(sliderG);
		
		JSlider sliderB = new JSlider();
		sliderB.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				
				demoPerso.setB(sliderB.getValue());
				
				
			}
		});
		sliderB.setValue(0);
		sliderB.setMinorTickSpacing(1);
		sliderB.setMaximum(255);
		sliderB.setBounds(0, 83, 420, 26);
		pnCouleur.add(sliderB);
		
		JSlider sliderTransparence = new JSlider();
		sliderTransparence.setMaximum(255);
		sliderTransparence.setValue(255);
		sliderTransparence.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				
				demoPerso.setTransparence(sliderTransparence.getValue());
				
			}
		});
		sliderTransparence.setBounds(80, 108, 339, 26);
		pnCouleur.add(sliderTransparence);
		
		JLabel lblTransparence = new JLabel("Transparence:");
		lblTransparence.setBounds(0, 108, 92, 26);
		pnCouleur.add(lblTransparence);
		
		JLabel lblR = new JLabel("R:");
		lblR.setBounds(10, 262, 28, 23);
		panelModificationPersonnage.add(lblR);
		
		JLabel lblG = new JLabel("G:");
		lblG.setBounds(10, 294, 46, 14);
		panelModificationPersonnage.add(lblG);
		
		JLabel lblB = new JLabel("B:");
		lblB.setBounds(10, 319, 28, 14);
		panelModificationPersonnage.add(lblB);
		
		JCheckBox chckbxSpiderFace = new JCheckBox("Apparance Spider-Man");
		chckbxSpiderFace.setSelected(true);
		chckbxSpiderFace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
					demoPerso.setSpiderFace(chckbxSpiderFace.isSelected());
			
				
			}
		});
		chckbxSpiderFace.setBounds(467, 168, 163, 23);
		panelModificationPersonnage.add(chckbxSpiderFace);
		
		JButton btnSauvegarder = new JButton("Sauvegarder");
		btnSauvegarder.setBounds(471, 198, 118, 23);
		btnSauvegarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//debut
				
				
				for (InfoListener ecout : listeEcouteurs) {
					
					ecout.sauvegarderPerso(demoPerso.getCouleur(),chckbxSpiderFace.isSelected());
					
				}
				
				setVisible(false);
				
				//fin
			}
		});
		panelModificationPersonnage.add(btnSauvegarder);
		btnSauvegarder.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		
	
		
		
	}
	/**
	 * Methode qui ajoute des listeners a l'arraylist de listeners
	 * 
	 * @param objEcout l'objet listener a ajouter
	 * 
	 */
	// Philippe Cote
	public void addInfoListener (InfoListener objEcout) {
		
		listeEcouteurs.add(objEcout);
		
	}
}
