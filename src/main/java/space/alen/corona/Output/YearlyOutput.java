package space.alen.corona.output;

import space.alen.corona.models.CoronaMonthlyStats;
import space.alen.corona.models.CoronaYearlyStats;
import space.alen.corona.output.MonthlyOutput;

public class YearlyOutput
{
    public static void output(CoronaYearlyStats yearlyStats)
    {
        System.out.println("Corona cases for " + yearlyStats.countryCode() + " on the year " + yearlyStats.year() + " by month");
        for (CoronaMonthlyStats monthlyStats : yearlyStats.monthlyStats()) {
            MonthlyOutput.output(monthlyStats);
        }
    }
}
