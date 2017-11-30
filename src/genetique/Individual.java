package genetique;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import methode.Methode;

public class Individual {
    public static final int SIZE = 16;
    public char[] genes = new char[SIZE];
    private float fitnessValue;
    
    
    final class Solution {
        private final float fitness;
        private final String gene;

        public Solution(float fitness, String gene) {
            this.fitness = fitness;
            this.gene = gene;
        }

        public float getFitness() {
            return fitness;
        }

        public String getGene() {
            return gene;
        }
    }
    

    public Individual() {}

    public float getFitnessValue() {
        return fitnessValue;
    }

    public void setFitnessValue(float fitnessValue) {
        this.fitnessValue = fitnessValue;
    }

    public char getGene(int index) {
        return genes[index];
    }
    
    public String getEntireGene() {
    	return genes.toString();
    }

    public void setGene(int index, char gene) {
        this.genes[index] = gene;
    }

    public static String generate(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
        StringBuffer pass = new StringBuffer();
        for(int x=0;x<length;x++)   {
           int i = (int)Math.floor(Math.random() * (chars.length() -1));
           pass.append(chars.charAt(i));
        }
        return pass.toString();
    }
    
    public void randGenes() {
    	String key="";
        Random rand = new Random();
        for(int i=0; i<SIZE; ++i) {
        	char c=generate(1).charAt(0);
        	key+=c;
            this.setGene(i, c);
        }
        // System.out.println(key);
    }

    public void mutate() {
        Random rand = new Random();
        int index = rand.nextInt(SIZE);
        this.setGene(index,generate(1).charAt(0));    // flip
    }
    
    public Solution evaluate(String text) throws IOException {
    	
    	String gene="";
        float fitness = 0;
        
        for(int i=0; i<SIZE; ++i) {
            gene += this.getGene(i);
        }
        fitness=Methode.calculPourcentage(gene,text);
        this.setFitnessValue(fitness);
        Solution solution=new Solution(fitness,gene);
        
        return solution;
    }

    
    
    

   
}
