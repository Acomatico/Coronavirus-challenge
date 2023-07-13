package space.alen.corona.output;

import java.util.ArrayList;
import java.lang.Character;
import space.alen.corona.models.CoronaMonthlyStats;
import space.alen.corona.models.CoronaYearlyStats;

public class YearlyGraphOutput
{
    public static void output(CoronaYearlyStats yearlyStats)
    {
        int minValue = Integer.MAX_VALUE;
        int maxValue = Integer.MIN_VALUE;

        for (CoronaMonthlyStats monthlyStats : yearlyStats.monthlyStats()) {
            int curCases = monthlyStats.cases();
            if (minValue > curCases && curCases > 0) {
                minValue = curCases;
            }

            if (maxValue < curCases) {
                maxValue = curCases;
            }
        }

        int width = 180;
        int height = 50;
        int range = maxValue - minValue;

        float heightRatio = (float) range / height;
        float widthRatio = (float) width / 12;

        Character[][] matrix = new Character[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int month = (int) Math.floor(j / widthRatio);

                int cases = yearlyStats.monthlyStats().get(month).cases();
                
                if (cases >= i * heightRatio) {
                    matrix[i][j] = '▇';
                } else {
                    matrix[i][j] = ' ';
                }
            }
        }

        int leftSpace = 12;

        for (int i = height - 1; i >= 0; i--) {
            var row = matrix[i];
            int casesValue = (int) Math.floor(heightRatio * (i+1));

            String line = "";
            if (i % 5 == 0) {
                line = "" + casesValue;
            }

            while (leftSpace > line.length()) {
                line = " " + line;
            }
            line += " | "; 

            for (Character cell : row) {
                line += cell;
            }

            System.out.println(line);
        }

        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String legend = "";
        while (leftSpace > legend.length()) {
            legend = " " + legend;
        }
        legend += " | "; 

        for (int i = 0; i < 12; i++) {
            legend += monthNames[i];
            for (int j = 0; j < 12; j++) {
                legend += " ";
            }
        }

        System.out.println(legend);
    }
}