package methode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;

public class Methode {

	//Crypte un texte 
    public static String encrypt(String text, final String key){

        String res = "";
        text = text.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++){

            char c = text.charAt(i);
             if ( (c < 'A' || c > 'Z') && c!=' ')
                 	continue;
            
            if(c!=' ')
            res += (char) ((c + key.charAt(j) - 2 * 'A') % 26 + 'A');
            else res+=' ';
            j = ++j % key.length();
        }
        return res;
    }

    //Decrypte un texte
    public static String decrypt(String text, final String key){

        String res = "";
        text = text.toUpperCase();

        for (int i = 0, j = 0; i < text.length(); i++){

            char c = text.charAt(i);
            if ( (c < 'A' || c > 'Z') && c!=' ')
                continue;

            if(c!=' ')
            res += (char) ((c - key.charAt(j) + 26) % 26 + 'A');
            else res+=' ';

            j = ++j % key.length();
        }
        return res;
    }
     
  //Calcule le pourcentage d'apparition du texte dans le dictionnaire 
    public static float calculPourcentage(String key,String crypt) throws IOException {
    	
    	String textDecrypt=decrypt(crypt,key.toUpperCase());
		String[] textArray=transformText(textDecrypt);
		int occ=0;
		String phrase="";
		for(int j=0;j<textArray.length;j++) {
			
			String motDecrypt=textArray[j];
			if(searchWord(motDecrypt,"file/dict.txt")) occ++;
		}
		float taille=(float)(textArray.length);
		float pourcentage=(occ/taille)*100;
		
    	
    	
    	return pourcentage;
    }
    
    //Génére une clé
    public static String generate(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
        StringBuffer pass = new StringBuffer();
        for(int x=0;x<length;x++)   {
           int i = (int)Math.floor(Math.random() * (chars.length() -1));
           pass.append(chars.charAt(i));
        }
        return pass.toString();
    }
    
    //Teste si la clé a été générée auparavant
    public static boolean existe(String key, ArrayList<String> list) {
    	return list.contains(key);
    }
    
    
    //Transforme un mot en tableau de caracteres
    public static String[] transformText(String text) {
    	String[] words = text.split("\\s+");
    	for (int i = 0; i < words.length; i++) {
    	     words[i] = words[i].replaceAll("[^\\w]", "");
    	}
    	return words;
    }
   
    
    //Transforme une clé
    public static String mutation(String key) {
    	int i = 0;
    	if(key.length()==2) {
    		i = (int)Math.floor(Math.random() * (key.length()));
    	}else {
    		i = (int)Math.floor(Math.random() * (key.length() -1));
    	}
    	
    	int nb=(int)Math.floor(Math.random()* 26);
    	
    	char c = (char) (((key.charAt(i) - 'A' + nb) % 26) + 'A');
    	StringBuffer pass = new StringBuffer(key);
    	pass.setCharAt(i,c);
    	
    	return pass.toString();
    }
    
    // Cherche si le mot existe dans le dictionnaire
    private static boolean searchWord(String token,String pathFile) throws IOException
    {
    	File file = new File (pathFile);
        InputStreamReader reader = new InputStreamReader(
        		new FileInputStream(file));
        String line = null;
        // Read a single line from the file. null represents the EOF.
        while((line = readLine(reader)) != null && !line.equalsIgnoreCase(token))
        {
            //System.out.println(line);
        }

        if(line != null && line.equalsIgnoreCase(token))
        {	
        	reader.close();
            return true;
        }
        else if(line != null && !line.equalsIgnoreCase(token))
        {
        	reader.close();
            return false;
        }
        else
        {
        	reader.close();
            return false;
        }
    }

    
    private static String readLine(InputStreamReader reader) throws IOException
    {
        // Test whether the end of file has been reached. If so, return null.
        int readChar = reader.read();
        if(readChar == -1)
        {
            return null;
        }
        StringBuffer string = new StringBuffer("");
        // Read until end of file or new line
        while(readChar != -1 && readChar != '\n')
        {
            // Append the read character to the string. Some operating systems
            // such as Microsoft Windows prepend newline character ('\n') with
            // carriage return ('\r'). This is part of the newline character
            // and therefore an exception that should not be appended to the
            // string.
            if(readChar != '\r')
            {
                string.append((char) readChar);
            }
            // Read the next character
            readChar = reader.read();
        }
        return string.toString();
    }
  
  
}
