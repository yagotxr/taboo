package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TabuSearch {

    private static final Logger logger = Logger.getLogger(TabuSearch.class.getName());;

    private Solution bestSolution; //Guarda melhor solucao
    private final float budgetLimit; //Teto de Gastos
    private final ArrayList<Integer> tabuList = new ArrayList<>(); //Lista Tabu
    private int iter = 0; //n iteraçao
    private int bestIter = 0; //n melhor Iter
    private final int btMax;

    public TabuSearch(float budgetLimit) {
        this.budgetLimit = budgetLimit;
        this.btMax = 1;
    }

    public TabuSearch(float budgetLimit, int btMax) {
        this.budgetLimit = budgetLimit;
        this.btMax = btMax;
    }

    private Solution handleExecution(Solution initialSolution){
        this.bestSolution = initialSolution;
        Solution bestNeighbor = new Solution(bestSolution.getValues(), this.budgetLimit);
        boolean done = false;
        while(!done){
            this.iter++;
            List<Solution> neighbors = getNeighbors(bestNeighbor);
            bestNeighbor = getBestNeighbor(neighbors);
            done = isDone(bestNeighbor);
            logger.info("[Best Solution] " + this.bestSolution.getResultF());
        }

        return bestSolution;
    }

    //Gera vizinhos da solução atual
    private ArrayList<Solution> getNeighbors(Solution solution){
        ArrayList<Solution> neighbors = new ArrayList<>();
        for(int i = 0; i < solution.getValues().length; i++){
            int[] solutionCopy = solution.getValues().clone();
            if(solutionCopy[i] == 0 || Solution.DATA.getData().get(i).getPriority() >= 0.85f){
                solutionCopy[i] = 1;
            } else {
                solutionCopy[i] = 0;
            }
            neighbors.add(new Solution(solutionCopy, this.budgetLimit));
        }
        return neighbors;
    }

    //Retorna o melhor vizinho, caso viznho for melhor que solucao atual, a busca a guarda
    private Solution getBestNeighbor(List<Solution> neighbors){
        Solution bestOne = neighbors.get(0);
        int iter = 0;
        int tabuIndex = 0;
        for(Solution solution : neighbors){
            if (solution.getResultF() > bestOne.getResultF() && !solution.isTabu()){
                bestOne = solution;
                tabuIndex = iter;
            }
            iter++;
        }
        if (isNotTabu(tabuIndex)){
            this.tabuList.add(tabuIndex);
            if(bestOne.getResultF() > this.bestSolution.getResultF()){
                bestSolution = bestOne;
                this.bestIter = this.iter;
            }
            return bestOne;
        }
        neighbors.get(tabuIndex).setTabu(true);
        try{
            return getBestNeighbor(neighbors);
        } catch (Exception e){
            return this.bestSolution;
        }
    }

    //Checa se não e tabu
    private boolean isNotTabu(int index) {
        return !this.tabuList.contains(index);
    }

    //Checa se algoritmo achou ou nao uma solucao, ou se caso iteracoes chegou ao limite do BTMax
    private boolean isDone(Solution solution) {
        if (solution == null){
            return true;
        }
        return btMax != 0 && (iter - bestIter) > btMax;
    }

    //Executa o busca
    public static Solution execute(float capacity, int btMax){
        TabuSearch tabuSearch = new TabuSearch(capacity, btMax);
        return tabuSearch.handleExecution(Solution.generateRandomSolution(capacity));
    }
}
