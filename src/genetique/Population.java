package genetique;

import java.io.IOException;
import java.util.Random;

import genetique.Individual.Solution;
import methode.Methode;
public class Population {
	final static int ELITISM_K = 5;
    final static int POP_SIZE = 200 + ELITISM_K;  // population size
    final static int MAX_ITER = 2000;             // max number of iterations
    final static double MUTATION_RATE = 0.05;     // probability of mutation
    final static double CROSSOVER_RATE = 0.7;     // probability of crossover

    private static Random m_rand = new Random();  // random-number generator
    public Individual[] m_population;
    private double totalFitness;

    public Population(String text) throws IOException {
        m_population = new Individual[POP_SIZE];

        // init population
        for (int i = 0; i < POP_SIZE; i++) {
            m_population[i] = new Individual();
            m_population[i].randGenes();
         }

        // evaluate current population
        this.evaluate(text);
    }
  
    public void setPopulation(Individual[] newPop) {
        // this.m_population = newPop;
        System.arraycopy(newPop, 0, this.m_population, 0, POP_SIZE);
    }

    public Individual[] getPopulation() {
        return this.m_population;
    }

    public double evaluate(String text) throws IOException {
        this.totalFitness = 0.0;
        for (int i = 0; i < POP_SIZE; i++) {
            Solution solution=m_population[i].evaluate(text);
            this.totalFitness += solution.getFitness();
        }
        return this.totalFitness;
    }

    public Individual rouletteWheelSelection() {
        double randNum = m_rand.nextDouble() * this.totalFitness;
        int idx;
        for (idx=0; idx<POP_SIZE && randNum>0; ++idx) {
            randNum -= m_population[idx].getFitnessValue();
        }
        return m_population[idx-1];
    }
    
    public Individual findBestIndividual() {
        int idxMax = 0, idxMin = 0;
        double currentMax = 0.0;
        double currentMin = 1.0;
        double currentVal;

        for (int idx=0; idx<POP_SIZE; ++idx) {
            currentVal = m_population[idx].getFitnessValue();
            if (currentMax < currentMin) {
                currentMax = currentMin = currentVal;
                idxMax = idxMin = idx;
            }
            if (currentVal > currentMax) {
                currentMax = currentVal;
                idxMax = idx;
            }
            if (currentVal < currentMin) {
                currentMin = currentVal;
                idxMin = idx;
            }
        }

        //return m_population[idxMin];      // minimization
        return m_population[idxMax];        // maximization
    }

    public static Individual[] crossover(Individual indiv1,Individual indiv2) {
        Individual[] newIndiv = new Individual[2];
        newIndiv[0] = new Individual();
        newIndiv[1] = new Individual();

        int randPoint = m_rand.nextInt(Individual.SIZE);
        int i;
        for (i=0; i<randPoint; ++i) {
            newIndiv[0].setGene(i, indiv1.getGene(i));
            newIndiv[1].setGene(i, indiv2.getGene(i));
        }
        for (; i<Individual.SIZE; ++i) {
            newIndiv[0].setGene(i, indiv2.getGene(i));
            newIndiv[1].setGene(i, indiv1.getGene(i));
        }

        return newIndiv;
    }

    public static Individual recherche(String textCrypt,String key) throws IOException {
    	
        Population pop = new Population(textCrypt);
        Individual[] newPop = new Individual[POP_SIZE];
        Individual[] indiv = new Individual[2];

        // current population
        System.out.print("Total Fitness = " + pop.totalFitness);
        System.out.println(" ;Gene = "+pop.findBestIndividual().getEntireGene()+" ; Best Fitness = " + 
            pop.findBestIndividual().getFitnessValue());

        // main loop
        int count;
        int iter = 0;
        float bestFitness=0;
        while ( iter < MAX_ITER && bestFitness<90) {
            count = 0;

            // Elitism
            for (int i=0; i<ELITISM_K; ++i) {
                newPop[count] = pop.findBestIndividual();
                count++;
            }

            // build new Population
            while (count < POP_SIZE ) {
                // Selection
                indiv[0] = pop.rouletteWheelSelection();
                indiv[1] = pop.rouletteWheelSelection();

                // Crossover
                if ( m_rand.nextDouble() < CROSSOVER_RATE ) {
                    indiv = crossover(indiv[0], indiv[1]);
                }

                // Mutation
                if ( m_rand.nextDouble() < MUTATION_RATE ) {
                    indiv[0].mutate();
                }
                if ( m_rand.nextDouble() < MUTATION_RATE ) {
                    indiv[1].mutate();
                }

                // add to new population
                newPop[count] = indiv[0];
                newPop[count+1] = indiv[1];
                count += 2;
            }
            pop.setPopulation(newPop);

            // reevaluate current population
            pop.evaluate(textCrypt);
            System.out.print("Total Fitness = " + pop.totalFitness);
            String keyOut="";
            for(int k=0;k<key.length();k++)
            keyOut += pop.findBestIndividual().getGene(k);
            
            System.out.println(" ;Gene = "+keyOut+" Best Fitness = " +
                    pop.findBestIndividual().getFitnessValue()); 
            bestFitness=  pop.findBestIndividual().getFitnessValue();
            iter++;
            }
        	
            // best indiv
            Individual bestIndiv = pop.findBestIndividual();
            
            System.out.println("Termin�");
            
            return bestIndiv;
    }

    
    public static void main(String[] args) throws IOException {
    	String message="The school is empty";
    	String key=Individual.generate(2);
    	String textCrypt=Methode.encrypt(message,key);
    	System.out.println("Encrypted Text: "+textCrypt+" Key: "+key);
    	
        Population pop = new Population(textCrypt);
        Individual[] newPop = new Individual[POP_SIZE];
        Individual[] indiv = new Individual[2];

        // current population
        System.out.print("Total Fitness = " + pop.totalFitness);
        System.out.println(" ;Gene = "+pop.findBestIndividual().getEntireGene()+" ; Best Fitness = " + 
            pop.findBestIndividual().getFitnessValue());

        // main loop
        int count;
        int iter = 0;
        float bestFitness=0;
        while ( iter < MAX_ITER && bestFitness<75) {
            count = 0;

            // Elitism
            for (int i=0; i<ELITISM_K; ++i) {
                newPop[count] = pop.findBestIndividual();
                count++;
            }

            // build new Population
            while (count < POP_SIZE ) {
                // Selection
                indiv[0] = pop.rouletteWheelSelection();
                indiv[1] = pop.rouletteWheelSelection();

                // Crossover
                if ( m_rand.nextDouble() < CROSSOVER_RATE ) {
                    indiv = crossover(indiv[0], indiv[1]);
                }

                // Mutation
                if ( m_rand.nextDouble() < MUTATION_RATE ) {
                    indiv[0].mutate();
                }
                if ( m_rand.nextDouble() < MUTATION_RATE ) {
                    indiv[1].mutate();
                }

                // add to new population
                newPop[count] = indiv[0];
                newPop[count+1] = indiv[1];
                count += 2;
            }
            pop.setPopulation(newPop);

            // reevaluate current population
            pop.evaluate(textCrypt);
            System.out.print("Total Fitness = " + pop.totalFitness);
            System.out.println(" ;Gene = "+pop.findBestIndividual().getGene(0)+pop.findBestIndividual().getGene(1)+" Best Fitness = " +
                    pop.findBestIndividual().getFitnessValue()); 
            bestFitness=  pop.findBestIndividual().getFitnessValue();
            iter++;
            }
        	
            // best indiv
            Individual bestIndiv = pop.findBestIndividual();
            
            System.out.println("Termin�");
        }

}
