/******************************************************
Cours:  LOG121
Projet: Laboratoire1 
Nom du fichier: MenuFenetre.java
Date créé: 20/01/1014
*******************************************************
Historique des modifications
*******************************************************
*@author Aissou Idriss
30/01/2014 Version personnelle
*******************************************************/ 

/******************************************************
Cours:  LOG121
Projet: Squelette du laboratoire #1
Nom du fichier: MenuFenetre.java
Date créé: 2013-05-03
*******************************************************
Historique des modifications
*******************************************************
*@author Patrice Boucher
2013-05-03 Version initiale
*******************************************************/  

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * Crée le menu de la fenêtre de l'applicationé
 */
public class MenuFenetre extends JMenuBar{
	
	private static final long serialVersionUID = 1536336192561843187L;
	private static final int  MENU_DESSIN_ARRETER_TOUCHE_MASK  = ActionEvent.CTRL_MASK;
	private static final char MENU_DESSIN_ARRETER_TOUCHE_RACC  = KeyEvent.VK_A;
	private static final int  MENU_DESSIN_DEMARRER_TOUCHE_MASK = ActionEvent.CTRL_MASK;
	private static final char MENU_DESSIN_DEMARRER_TOUCHE_RACC = KeyEvent.VK_D;
	private static final int  MENU_FICHIER_QUITTER_TOUCHE_MASK = ActionEvent.CTRL_MASK;
	private static final char MENU_FICHIER_QUITTER_TOUCHE_RACC = KeyEvent.VK_Q;
	private static final String
			MENU_FICHIER_TITRE = "app.frame.menus.file.title",
			MENU_FICHIER_QUITTER = "app.frame.menus.file.exit",
			MENU_DESSIN_TITRE = "app.frame.menus.draw.title",
			MENU_DESSIN_DEMARRER = "app.frame.menus.draw.start",
			MENU_DESSIN_ARRETER = "app.frame.menus.draw.stop",
			MENU_AIDE_TITRE = "app.frame.menus.help.title",
			MENU_AIDE_PROPOS = "app.frame.menus.help.about";
	private static final String MESSAGE_DIALOGUE_A_PROPOS = "app.frame.dialog.about"; 
	
	
	private JMenuItem arreterMenuItem, demarrerMenuItem;
	private static final int DELAI_QUITTER_MSEC = 200;
	static FenetrePrincipale fenetrePrincipale;
 	   
	CommBase comm; // Pour activer/désactiver la communication avec le serveur
	
	/**
	 * Constructeur
	 */
	public MenuFenetre(CommBase comm) {
		this.comm = comm;
		addMenuDessiner();
		addMenuClasser();
		addMenuFichier();
		addMenuAide();
	}

	/**
	 *  Créer le menu "Draw". 
	 */
	protected void addMenuDessiner() {
		JMenu menu = creerMenu(MENU_DESSIN_TITRE,new String[] { MENU_DESSIN_DEMARRER, MENU_DESSIN_ARRETER });

		demarrerMenuItem = menu.getItem(0);
		demarrerMenuItem.addActionListener(new ActionListener(){
		  public void actionPerformed(ActionEvent arg0) {
			try {
				//FENETRE
				JTextField adresse_ip = new JTextField();
				JTextField num_port = new JTextField();
				
				//JOptionPane jConnexion = new JOptionPane();
				
				int validation = JOptionPane.showConfirmDialog(fenetrePrincipale, 
					      new Object[] {"L'hote de connexion :", adresse_ip, "Num�ro de port :", num_port},
					      "Hote de connexion distant",
					      JOptionPane.OK_CANCEL_OPTION); 
				
				
							    
			    try{
			    	if (validation == JOptionPane.OK_OPTION){
			    		comm.recupereJFrame(fenetrePrincipale);
			    		comm.start(adresse_ip.getText(),Integer.parseInt(num_port.getText()));	
			    	}
			    	
			    }
			    catch(NumberFormatException e){
			    	JOptionPane.showMessageDialog(fenetrePrincipale, 
							"Format incorrect", 
							"Fin",
							JOptionPane.INFORMATION_MESSAGE); 
				}
			    	
			    }
			    	 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rafraichirMenus();
		  }
		});
		demarrerMenuItem.setAccelerator(KeyStroke.getKeyStroke(
				MENU_DESSIN_DEMARRER_TOUCHE_RACC,
				MENU_DESSIN_DEMARRER_TOUCHE_MASK));

		arreterMenuItem = menu.getItem(1);
		arreterMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			comm.stop();
			rafraichirMenus();
		    }
	    });
		
		arreterMenuItem.setAccelerator(KeyStroke.getKeyStroke(
				MENU_DESSIN_ARRETER_TOUCHE_RACC,
				MENU_DESSIN_ARRETER_TOUCHE_MASK));
		add(menu);
	}

	/** 
	 * Créer le menu "File". 
	 */
	protected void addMenuFichier() {
		JMenu menu = creerMenu(MENU_FICHIER_TITRE, new String[] { MENU_FICHIER_QUITTER });
		menu.getItem(0).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				comm.stop();
			    try {
						Thread.sleep(DELAI_QUITTER_MSEC);
				} catch (InterruptedException e) {
						e.printStackTrace();
				}
				System.exit(0);
			}
		});
		menu.getItem(0).setAccelerator(
				KeyStroke.getKeyStroke(MENU_FICHIER_QUITTER_TOUCHE_RACC,
						MENU_FICHIER_QUITTER_TOUCHE_MASK));
		add(menu);
	}

	/**
	 *  Créer le menu "Help". 
	 */
	private void addMenuAide() {
		JMenu menu = creerMenu(MENU_AIDE_TITRE, new String[] { MENU_AIDE_PROPOS });
		menu.getItem(0).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null,  LangueConfig.getResource(MESSAGE_DIALOGUE_A_PROPOS), 
						LangueConfig.getResource(MENU_AIDE_PROPOS), JOptionPane.INFORMATION_MESSAGE);
			}
		});
		add(menu);
	}
	
	
	private void addMenuClasser() {
		   		//Menu Classer_Formes
		   		JMenu menuClasser = new JMenu("Classer_Formes");
		   		//Cr�ation du groupe de boutons
		   		ButtonGroup groupeboutton = new ButtonGroup();
		   		//Cr�ation de l'ecouteur des boutons
		   		EcouteurClasser ecouteur= new EcouteurClasser();
		   		
		   	   // Creation des sous menus
			   JRadioButtonMenuItem sequenceCroissant = new JRadioButtonMenuItem("Sequence_Croissant");//num�ro de s�quence croissant
			   sequenceCroissant.setSelected(true);
			   
			   JRadioButtonMenuItem sequenceDecroissant = new JRadioButtonMenuItem("Sequence_Decroissant");//num�ro de s�quence d�croissant
			   JRadioButtonMenuItem aireFormeCroissante = new JRadioButtonMenuItem("Aire_Forme_Croissante");//Aire croissante
			   JRadioButtonMenuItem aireFormeDecroissante = new JRadioButtonMenuItem("Aire_Forme_Decroissante");//Aire d�croissante
			   JRadioButtonMenuItem parTypeDeFormes = new JRadioButtonMenuItem("Par_type_De_formes");//carr�, rectangle, cercle, ovale, ligne
			   JRadioButtonMenuItem parTypeDeFormesInverse = new JRadioButtonMenuItem("Par_type_De_formes_inverse");//ligne, ovale, cercle, rectangle, carr�**/
			  
			   //Ajout de l �couteur aux boutons
			   sequenceCroissant.addActionListener(ecouteur);
			   sequenceDecroissant.addActionListener(ecouteur);
			   aireFormeCroissante.addActionListener(ecouteur);
			   aireFormeDecroissante.addActionListener(ecouteur);
			   parTypeDeFormes.addActionListener(ecouteur);
			   parTypeDeFormesInverse.addActionListener(ecouteur);
			   
			   //Ajout des boutons au groupe
			   groupeboutton.add(sequenceCroissant);
			   groupeboutton.add(sequenceDecroissant);
			   groupeboutton.add(aireFormeCroissante);
			   groupeboutton.add(aireFormeDecroissante);
			   groupeboutton.add(parTypeDeFormes);
			   groupeboutton.add(parTypeDeFormesInverse);
		   
			   //Ajout des boutons au super menu
			   menuClasser.add(sequenceCroissant);
			   menuClasser.add(sequenceDecroissant);
			   menuClasser.add(aireFormeCroissante);
			   menuClasser.add(aireFormeDecroissante);
			   menuClasser.add(parTypeDeFormes);
			   menuClasser.add(parTypeDeFormesInverse);

			   menuClasser.addSeparator();
			   
			   //Ajout du menuClasser Globale
			   add(menuClasser);
		   
	}
public class EcouteurClasser  implements ActionListener{
		
		public EcouteurClasser(){ }

	 public void actionPerformed(ActionEvent e) {
		 
		 /*
		  * (sequenceCroissant = 0)(sequenceDecroissant = 1)
		  * (aireFormeCroissante=2)(aireFormeDecroissante=3)
		  * (parTypeDeFormes=4)(parTypeDeFormesInverse=5)
		  */
		try{
		   if(e.getActionCommand().equals("Sequence_Croissant")){
			   System.out.println("tri sequenceCroissant  appel de fenetrePrincipale.aiguilleurDeTraitement(0)");
			   MenuFenetre.fenetrePrincipale.aiguilleurDeTraitement(0);
			  
		   }
		   		else
		   				if(e.getActionCommand().equals( "Sequence_Decroissant")){
		   					System.out.println("tri sequenceDecroissant appel de  fenetrePrincipale.aiguilleurDeTraitement(1)" );
		   				    MenuFenetre.fenetrePrincipale.aiguilleurDeTraitement(1);

		   				
		   				}
		   
		   				else 
		   					if(e.getActionCommand().equals( "Aire_Forme_Croissante")){
		   						System.out.println("tri aireFormeCroissante appel de fenetrePrincipale.aiguilleurDeTraitement(2)");
		   						MenuFenetre.fenetrePrincipale.aiguilleurDeTraitement(2);
		   					
		   					}
		   					else 
			   					if(e.getActionCommand().equals("Aire_Forme_Decroissante")){
			   					System.out.println("tri aireFormeDecroissante appel de fenetrePrincipale.aiguilleurDeTraitement(3)");
			   					MenuFenetre.fenetrePrincipale.aiguilleurDeTraitement(3);
			   					
			   					}
		   					else 
		   						if(e.getActionCommand().equals("Par_type_De_formes")){
		   						System.out.println("tri TypeDeFormes appel de fenetrePrincipale.aiguilleurDeTraitement(4)");
		   						MenuFenetre.fenetrePrincipale.aiguilleurDeTraitement(4);
		   						
				   		
		   						}
		   						else 
		   							if(e.getActionCommand().equals( "Par_type_De_formes_inverse")){
		   							System.out.println("tri TypeDeFormesInverse appel de fenetrePrincipale.aiguilleurDeTraitement(5)");
		   							MenuFenetre.fenetrePrincipale.aiguilleurDeTraitement(5);
		   							
					   		
		   							}
        }catch(Exception e1){}
		   }
	}

	/**
	 *  Activer ou désactiver les items du menu selon la sélection. 
	 */
	private void rafraichirMenus() {
		demarrerMenuItem.setEnabled(!comm.isActif());
		arreterMenuItem.setEnabled(comm.isActif());
	}
	
	/**
	 * Créer un élément de menu à partir d'un champs principal et ses éléments
	 * @param titleKey champs principal
	 * @param itemKeys éléments
	 * @return le menu
	 */
	private static JMenu creerMenu(String titleKey,String[] itemKeys) {
        JMenu menu = new JMenu(LangueConfig.getResource(titleKey));
        for(int i=0; i < itemKeys.length; ++i) {
           menu.add(new JMenuItem(LangueConfig.getResource(itemKeys[i])));
        }
        return menu;
   }
	
	public void setJFrame(FenetrePrincipale fenetre){
		fenetrePrincipale=fenetre;
		
	}
}
