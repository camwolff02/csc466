package labs;

import SupervisedLearning.Matrix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static labs.Lab7.process;

public class Lab8 {
    public static final String PATH = "src/files/";

    public static void main(String[] args) {
        Matrix data = new Matrix(process(PATH + "data.txt"));
        ArrayList<Integer> attributeValues = getCustomerInput(data.getCategoryAttribute());
        int category = data.findCategory(attributeValues);
        System.out.println("Expected category: " + category);
    }

    public static ArrayList<Integer> getCustomerInput(int categoryAttribute) {
        Scanner in = new Scanner(System.in);
        ArrayList<Integer> values = new ArrayList<>();

        for (int i = 0; i < categoryAttribute; i++) {
            System.out.print("Enter a value for attribute " + i + ": ");
            values.add(in.nextInt());
        }

        return values;
    }
}