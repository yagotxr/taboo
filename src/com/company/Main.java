package com.company;

import static com.company.Solution.DATA;

public class Main {

    public static void main(String[] args) {

        float capacidade = 5000000.0f;

        Solution result = TabuSearch.execute(capacidade, 5);

        String output = "Product Code\t\t\t\t\t\t\tPrice\t\t\tPriority\tBenefit\n";
        for(int i = 0; i < DATA.getLength(); i++ ){
            if(result.getValues()[i] == 1){
                Data.Info info = DATA.getData().get(i);
                output = output.concat(info.getCode() + "\t" + String.format("R$ %.2f",info.getPrice()) + "\t" + info.getPriority() + "\t" + info.getBenefit() + "\n");
            }
        }
        System.out.println(output);
        System.out.printf("Total charge: R$ %.2f \n", result.getPrice());
        System.out.printf("%.2f percent beneficial\n", result.getResultF());
    }
}
