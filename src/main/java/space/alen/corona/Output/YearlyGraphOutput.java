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

        int width = 200;
        int height = 40;
        int range = maxValue - minValue;

        float heightValue = (float) range / height;
        float widthValue = (float) (width + 1) / 12;
        System.out.println(widthValue);

        Character[][] matrix = new Character[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.println(j);
                int month = (int) Math.floor(j / widthValue);

                int cases = yearlyStats.monthlyStats().get(month).cases();
                
                if (cases >= i * heightValue) {
                    matrix[height - i - 1][j] = '▇';
                } else {
                    matrix[height - i - 1][j] = ' ';
                }
            }
        }

        for (Character[] row : matrix) {
            String line = "";

            for (Character cell : row) {
                line += cell;
            }

            System.out.println(line);
        }
    }
}