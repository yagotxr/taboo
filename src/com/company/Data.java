package com.company;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class Data {

    //Geração de dados para a solucao
    private final int length;
    private final Map<Integer, Info> data;
    private final float alpha;

    public Data(int length, Map<Integer, Info> data) {
        this.length = length;
        this.data = data;
        this.alpha = this.calcAlpha();
    }

    //Benefico alpha
    private float calcAlpha() {
        float total = 0f;
        for(Info info : data.values()){
            total += info.getBenefit();
        }
        return total;
    }

    public static Data get(int lenght){
        Random random = new Random();
        Map<Integer, Info> data = new HashMap<>();
        for(int i = 0; i < lenght; i++){
            float price = (float) (Math.random() * (200000 - 10000)) + 10000;
            float benefit = random.nextFloat();
            float priority = random.nextFloat();
            data.put(i, new Info(UUID.randomUUID().toString(), price, priority, benefit));
        }
        return new Data(lenght, data);
    }

    public int getLength() {
        return length;
    }

    public Map<Integer, Info> getData() {
        return data;
    }

    public static Data getTestData(){
        Map<Integer, Info> test = new HashMap<>();
        test.put(0, new Info("a",4, 0, 2));
        test.put(1, new Info("b", 5, 0, 2));
        test.put(2, new Info("c", 7, 0, 3));
        test.put(3, new Info("d",9, 0, 4));
        test.put(4, new Info("e",6, 0, 4));
        return new Data(5, test);
    }

    public float getAlpha() {
        return alpha;
    }

    public static class Info {

        private final String code;
        private final float price;
        private final float priority;
        private final float benefit;

        public Info(String code, float price, float priority, float benefit) {
            this.code = code;
            this.price = price;
            this.priority = priority;
            this.benefit = benefit;
        }

        public float getPrice() {
            return price;
        }

        public float getPriority() {
            return priority;
        }

        public float getBenefit() {
            return benefit;
        }

        public String getCode() {
            return code;
        }
    }


}
