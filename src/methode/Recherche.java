package methode;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;
import methode.Methode;
import methode.Noeud;

public class Recherche {

public static Noeud rechercheLocale(String text,int taille,int maxIter) throws IOException {
    	
    	//DefaultMutableTreeNode Noeud=(buildTree("file/key.txt"));
    	float pourcentage=0;
    	int i = 0 ;
    	Noeud gagnant=null;
    	String key=Methode.generate(taille);
    	String old_key=null;
    	Noeud noeud=new Noeud(0,key);
    	Noeud n=null;
    	DefaultMutableTreeNode Tree= new DefaultMutableTreeNode(noeud);
    	DefaultMutableTreeNode new_tete=null,tete=null;
    	ArrayList<String> tabKey=new ArrayList<String>();
    	
    	while(pourcentage< 50){
    			if(Methode.existe(key,tabKey)) {
    				while(Methode.existe(key,tabKey)) {
    					key=Methode.mutation(key);
    				}
    			}else {
    				tabKey.add(key);
    			}
    			n=new Noeud(0,key);
        		float new_pourcentage=Methode.calculPourcentage(n.getCle(),text);
        		n.setPourcentage(new_pourcentage);
        		if(new_tete!=null) {
        			if(tete!=null) {
        				Tree.add(new_tete);
        			}else {
        				tete=new_tete;
        			}
        			
        		}
        		if(new_pourcentage>=pourcentage) {
        			pourcentage=new_pourcentage;
        			old_key=key;
        			key=Methode.mutation(key);
        			new_tete=new DefaultMutableTreeNode(n);
        			gagnant=n;
        			
        		}else {
        			key=Methode.mutation(old_key);
        		}
        	i++;
         }
    	return gagnant;
    }


public static void main(String[] args) throws IOException
{	
	float pourcentage=50;
	String key=Methode.generate(2);
	System.out.println("Clé de chiffrement :"+key);
	String pathFile="file/dict.txt";
	char set[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    int cpt = 16;
  
    System.out.println("debut");
    String message = "Go to your application centre";
    String textCrypt=Methode.encrypt(message,key);
   
    Noeud noeudGagnant=rechercheLocale(textCrypt,4,17000);
    System.out.println("Clé : ,"+noeudGagnant.getCle()+" Pourcentage : "+noeudGagnant.getPourcentage());
 
    System.out.println("Terminé");
}


}
