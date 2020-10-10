package com.company;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.*;

public class Main {

    enum numeralSystem{
        Decimal,
        Roman
    }

    public static void main(String[] args) {
        System.out.println("#".repeat(22));
        System.out.println("# Console Calculator #");
        System.out.println("#".repeat(22));

        Scanner scanner = new Scanner(System.in);

        numeralSystem nSystem;
        String expression;

        while(true){
            System.out.print("Please enter your expression: ");
            expression = scanner.nextLine();


            String decimalPattern = "(\\d|10)\\s*[+-/*]\\s*(\\d|10)";
            String romanPattern = "M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})\\s*[+-/*]\\s*" +
                    "M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})"; // tooo hard regex but I`m do it

            if(expression.matches(decimalPattern)){
                nSystem = numeralSystem.Decimal;
//                System.out.println("Dec");
                break;
            }
            else if(expression.matches(romanPattern)){
                nSystem = numeralSystem.Roman;
//                System.out.println("Rom");
                break;
            }
            else { System.out.println("wrong input"); }
        }

        if (nSystem == numeralSystem.Decimal) {
            System.out.println(DecimalCalculator.process(expression));
        }
        else {
            System.out.println(RomanCalculator.process(expression));
        }
    }

    public static class DecimalCalculator{
        public static double process(String exp){
            String[] left_and_right_operands = exp.split("\\s*[+-/*]\\s*");

            int first_operand = Integer.parseInt(left_and_right_operands[0]);
            int second_operand = Integer.parseInt(left_and_right_operands[1]);

            String[] regex_res = exp.split("(\\d|10)\\s*");

            char operator = regex_res[regex_res.length-1].toCharArray()[0];

            switch (operator){
                case '+':{return first_operand + second_operand;}
                case '-':{return first_operand - second_operand;}
                case '/':{return first_operand / second_operand;}
                case '*':{return first_operand * second_operand;}
            }
            return 0.0;
        }
    }

    public static class RomanCalculator{
        public static String process(String exp){
            String[] left_and_right_operands = exp.split("\\s*[+-/*]\\s*");

            String first_operand = left_and_right_operands[0];
            String second_operand = left_and_right_operands[1];

            String[] regex_res = exp.split("M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})\\s*");

            char operator = regex_res[regex_res.length-1].toCharArray()[0];

            switch (operator){
                case '+':{
                    return ConvertToRoman(ConvertToDecimal(first_operand) + ConvertToDecimal(second_operand));
                }
                case '-':{
                    return ConvertToRoman(ConvertToDecimal(first_operand) - ConvertToDecimal(second_operand));
                }
                case '/':{
                    return ConvertToRoman(Math.round(ConvertToDecimal(first_operand) / ConvertToDecimal(second_operand)));
                }
                case '*':{
                    return ConvertToRoman(ConvertToDecimal(first_operand) * ConvertToDecimal(second_operand));
                }
            }

            return "";
        }

        private static int ConvertToDecimal(String romanNumber){
            int length = romanNumber.length();

            int number = 0;
            int previous_num = 0;
            int result = 0;

            for (int i = length-1; i >= 0 ; i--) {
                char x =  romanNumber.charAt(i);
                switch(x)
                {
                    case 'I':
                        previous_num = number;
                        number = 1;
                        break;
                    case 'V':
                        previous_num = number;
                        number = 5;
                        break;
                    case 'X':
                        previous_num = number;
                        number = 10;
                        break;
                    case 'L':
                        previous_num = number;
                        number = 50;
                        break;
                    case 'C':
                        previous_num = number;
                        number = 100;
                        break;
                    case 'D':
                        previous_num = number;
                        number = 500;
                        break;
                    case 'M':
                        previous_num = number;
                        number = 1000;
                        break;
                }
                if (number<previous_num)
                {
                    result = result - number;
                }
                else
                    result = result + number;
            }
            return result;

        }
        private static String ConvertToRoman(int num) {
            String[] romanCharacters = { "M", "CM", "D", "C", "XC", "L", "X", "IX", "V", "I" };
            int[] romanValues = { 1000, 900, 500, 100, 90, 50, 10, 9, 5, 1 };
            String result = "";

            for (int i = 0; i < romanValues.length; i++) {
                int numberInPlace = num / romanValues[i];
                if (numberInPlace == 0) continue;
                result += numberInPlace == 4 && i > 0? romanCharacters[i] + romanCharacters[i - 1]:
                        new String(new char[numberInPlace]).replace("\0",romanCharacters[i]);
                num = num % romanValues[i];
            }
            return result;
        }
    }
}


