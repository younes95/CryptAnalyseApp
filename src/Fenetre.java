import java.awt.Event;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.ibm.icu.text.DecimalFormat;

import genetique.Individual;
import genetique.Population;
import methode.Methode;
import methode.Noeud;
import methode.Recherche;
import org.eclipse.swt.custom.CLabel;

public class Fenetre {

	protected Shell shell;
	private Text key_text;
	private Text encrypted_text;
	private Text key_result;
	private Text pourcentage;
	private Text text_result_decrypted;
	private Text initial_text;


	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Fenetre window = new Fenetre();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		
		final String[] items = { "Recherche locale", "Recherche Taboue", "Recherche génétique (population)"};
		
		
		shell = new Shell();
		shell.setSize(654, 305);
		shell.setText("SWT Application");
		
		key_text = new Text(shell, SWT.BORDER);
		key_text.setBounds(164, 7, 167, 21);
		
		Label lblClDeChiffrement = new Label(shell, SWT.NONE);
		lblClDeChiffrement.setBounds(10, 10, 104, 15);
		lblClDeChiffrement.setText("Cl\u00E9 de chiffrement");
		
		encrypted_text = new Text(shell, SWT.BORDER);
		encrypted_text.setBounds(164, 124, 167, 43);
		
		Label lblTexteChiffr = new Label(shell, SWT.NONE);
		lblTexteChiffr.setBounds(10, 124, 76, 15);
		lblTexteChiffr.setText("Texte Chiffr\u00E9");
		
		CCombo crypt_method = new CCombo(shell, SWT.BORDER);
		crypt_method.setBounds(164, 184, 167, 21);
		crypt_method.setItems(items);
		
		Label lblMthodeDeDchifrement = new Label(shell, SWT.NONE);
		lblMthodeDeDchifrement.setBounds(10, 184, 155, 21);
		lblMthodeDeDchifrement.setText("M\u00E9thode de d\u00E9chifrement");
		
		Label label = new Label(shell, SWT.SEPARATOR | SWT.VERTICAL);
		label.setBounds(350, 10, 2, 254);
		
		Label lblClTrouve = new Label(shell, SWT.NONE);
		lblClTrouve.setBounds(467, 10, 55, 15);
		lblClTrouve.setText("Cl\u00E9 trouv\u00E9e");
		
		key_result = new Text(shell, SWT.BORDER);
		key_result.setBounds(396, 37, 189, 21);
		
		Label lblPourcentage = new Label(shell, SWT.NONE);
		lblPourcentage.setBounds(359, 87, 76, 15);
		lblPourcentage.setText("Pourcentage");
		
		pourcentage = new Text(shell, SWT.BORDER);
		pourcentage.setBounds(473, 84, 112, 21);
		
		Button btnChercher = new Button(shell, SWT.NONE);
		btnChercher.setBounds(255, 224, 75, 25);
		btnChercher.setText("Chercher ");
		
		Label lblTexteDchifr = new Label(shell, SWT.NONE);
		lblTexteDchifr.setBounds(467, 127, 85, 15);
		lblTexteDchifr.setText("Texte d\u00E9chifr\u00E9");
		
		text_result_decrypted = new Text(shell, SWT.BORDER);
		text_result_decrypted.setBounds(396, 159, 189, 86);
		
		Label lblTempsDeCalcul = new Label(shell, SWT.NONE);
		lblTempsDeCalcul.setBounds(10, 229, 106, 15);
		lblTempsDeCalcul.setText("Temps de calcul : ");
		
		Label lblTexteChiffrer = new Label(shell, SWT.NONE);
		lblTexteChiffrer.setBounds(10, 48, 90, 15);
		lblTexteChiffrer.setText("Texte \u00E0 chiffrer");
		
		initial_text = new Text(shell, SWT.BORDER);
		initial_text.setBounds(164, 48, 167, 60);
		
		CLabel label_time_execution = new CLabel(shell, SWT.NONE);
		label_time_execution.setBounds(124, 229, 125, 15);
		label_time_execution.setText("");

		btnChercher.addListener(SWT.Selection,new Listener() {
			 public void handleEvent(Event event) {
				
			 }

			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event arg0) {
				// TODO Auto-generated method stub

				
				 String encrypted = null;
				 String key = key_text.getText();
				 String method = null;
				 String text=initial_text.getText();
				 
				 if(crypt_method.getSelectionIndex()!=-1)
				  method = crypt_method.getItem(crypt_method.getSelectionIndex());
				
				 if (key == null || key.isEmpty() || method == null || method.isEmpty() || text==null || 
						 text.isEmpty()) {
				     String errorMsg = null;
				     MessageBox messageBox = new MessageBox(shell, SWT.OK | SWT.ICON_ERROR);
				     messageBox.setText("Alert");
				 
				     if (key == null || key.isEmpty()) {
				    	 errorMsg = "Donner une clé de chiffrement";
					 }  else if(method ==  null|| method.isEmpty()){
				    	 errorMsg = "Selectionner une méthode de recherche";
				     } else if(text == null || text.isEmpty()) {
				    	 errorMsg ="Donner le message à crypter";
				     }
				 	 
				     if (errorMsg != null) {
				    	 messageBox.setMessage(errorMsg);
				    	 messageBox.open();
				     }
				 	 
				 } else {
					 long startTime = System.currentTimeMillis();
					 
					 encrypted=Methode.encrypt(text, key);
					 encrypted_text.setText(encrypted);
					 
					// Lancer la recherche locale 
				 	if(crypt_method.getSelectionIndex()== 0) {
				 		try {
							Noeud noeud=Recherche.rechercheLocale(encrypted, key.length(),1000);
							
							// Affichage des résultats
							
							key_result.setText(noeud.getCle());
							pourcentage.setText(Float.toString(noeud.getPourcentage()));
							text_result_decrypted.setText(Methode.decrypt(encrypted, noeud.getCle()));
							float stopTime = System.currentTimeMillis();
						    float elapsedTime = stopTime - startTime;
						    elapsedTime=(elapsedTime/60);
						    DecimalFormat formatter = new DecimalFormat("#.00");
						    
						    label_time_execution.setText(formatter.format(elapsedTime)+" Secondes");
						    
				 		} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				 	}
				 	
				 	// Lancer la recherche taboue
				 	if(crypt_method.getSelectionIndex()== 1) {
				 		try {
							Noeud noeud=Recherche.rechercheLocale(encrypted, key.length(),1000);
							
							// Affichage des résultats
							
							key_result.setText(noeud.getCle());
							pourcentage.setText(Float.toString(noeud.getPourcentage()));
							text_result_decrypted.setText(Methode.decrypt(encrypted, noeud.getCle()));
							long stopTime = System.currentTimeMillis();
						    long elapsedTime = stopTime - startTime;
						    elapsedTime=(elapsedTime/60);
						    DecimalFormat formatter = new DecimalFormat("#.00");
						    label_time_execution.setText(formatter.format(elapsedTime)+" Secondes");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				 	}
				 	
				 	// Lancer la recherche genetique (population) 
				 	if(crypt_method.getSelectionIndex()== 2) {
				 		try {
							Individual indiv= Population.recherche(encrypted, key) ;
							pourcentage.setText(Float.toString(indiv.getFitnessValue()));
							String key_founded="";
							for(int i=0;i<key.length();i++) {
								key_founded+=indiv.getGene(i);
							}
							text_result_decrypted.setText(Methode.decrypt(encrypted,key_founded));
							key_result.setText(key_founded);
							
							long stopTime = System.currentTimeMillis();
						    long elapsedTime = stopTime - startTime;
						    elapsedTime=(elapsedTime/60);
						    DecimalFormat formatter = new DecimalFormat("#.00");
						    label_time_execution.setText(formatter.format(elapsedTime)+" Secondes");
						
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							
							// Affichage des résultats
							
							
							
						}
				 	}
					 
				 }
			}
		});
	}
}
