package com.company;

import static com.company.Solution.DATA;

public class Main {

    public static void main(String[] args) {

        float capacidade = 5000000.0f; //Teto de Gastos

        //Executa Busca dado solução inicial e BTMax
        Solution result = TabuSearch.execute(capacidade, 100);

        //Print da Solução
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

//50.87 41.98 43.09 38.22 38.40 40.52 38.58 43.80 38.27 55.02
