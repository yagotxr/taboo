package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TabuSearch {

    private static final Logger logger = Logger.getLogger(TabuSearch.class.getName());;

    private Solution bestSolution;
    private final float budgetLimit;
    private final ArrayList<Integer> tabuList = new ArrayList<>();
    private int iter = 0;
    private int bestIter = 0;
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

    private boolean isNotTabu(int index) {
        return !this.tabuList.contains(index);
    }

    private boolean isDone(Solution solution) {
        if (solution == null){
            return true;
        }
        return btMax != 0 && (iter - bestIter) > btMax;
    }

    public static Solution execute(float capacity){
        TabuSearch tabuSearch = new TabuSearch(capacity);
        return tabuSearch.handleExecution(Solution.generateRandomSolution(capacity));
    }

    public static Solution execute(float capacity, int btMax){
        TabuSearch tabuSearch = new TabuSearch(capacity, btMax);
        return tabuSearch.handleExecution(Solution.generateRandomSolution(capacity));
    }
}
