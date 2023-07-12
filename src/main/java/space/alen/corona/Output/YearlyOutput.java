package space.alen.corona.output;

import space.alen.corona.models.CoronaMonthlyStats;
import space.alen.corona.models.CoronaYearlyStats;
import space.alen.corona.output.MonthlyOutput;

public class YearlyOutput
{
    private MonthlyOutput monthlyStatsOutput;

    public YearlyOutput()
    {
        this.monthlyStatsOutput = new MonthlyOutput();
    }

    public void output(CoronaYearlyStats yearlyStats)
    {
        System.out.println("Corona cases for the year: " + yearlyStats.year() + " by month");
        for (CoronaMonthlyStats monthlyStats : yearlyStats.monthlyStats()) {
            this.monthlyStatsOutput.output(monthlyStats);
        }
    }
}
