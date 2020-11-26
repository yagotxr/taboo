package com.company;

import java.util.Random;

public class Solution {

    public static final Data DATA = Data.get(500);
//    public static final Data DATA = Data.getTestData();

    private final int[] values; //Vetor binário de soluções
    private final float benefit; //Beneficio da Solução
    private final float price; //Preco total solucao
    private final float resultF; //Função
    private boolean isTabu; //registro de Lista tabu

    public Solution(int[] values, float limit) {
        this.values = values;
        this.benefit = this.calcBenefit();
        this.price = this.calcPrice();
        this.resultF = calcFunction(limit);
        this.isTabu = false;
    }

    private float calcBenefit(){
        float total = 0f;
        for(int i = 0; i < values.length; i++){
            total += values[i] * DATA.getData().get(i).getBenefit();
        }
        return total;
    }

    private float calcPrice(){
        float total = 0.0f;
        for(int i = 0; i < values.length; i++){
            total += values[i] * DATA.getData().get(i).getPrice();
        }
        return total;
    }

    private static float calcPrice(int[] valuesArray){
        float total = 0.0f;
        for(int i = 0; i < valuesArray.length; i++){
            total += valuesArray[i] * DATA.getData().get(i).getPrice();
        }
        return total;
    }

    //Gera solucao inicial
    public static Solution generateRandomSolution(float budgetLimit){
        int[] bits = {0, 1};
        int[] value = new int[DATA.getLength()];
        Random random = new Random();
        for(int i = 0; i < 30; i++){
                int j = random.nextInt(DATA.getLength());
                value[j] = random.nextInt(bits.length);
            }
        return new Solution(value, budgetLimit);
    }

    private float calcFunction(float b){
        return this.benefit - DATA.getAlpha() * Math.max(0, this.price - b);
    }

    public int[] getValues() {
        return values;
    }

    public float getBenefit() {
        return benefit;
    }

    public float getPrice() {
        return price;
    }

    public float getResultF() {
        return resultF;
    }

    public void setTabu(boolean tabu) {
        isTabu = tabu;
    }

    public boolean isTabu() {
        return isTabu;
    }
}
