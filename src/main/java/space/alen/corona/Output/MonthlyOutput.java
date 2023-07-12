package space.alen.corona.output;

import java.time.Month;
import space.alen.corona.models.CoronaMonthlyStats;

public class MonthlyOutput
{
    public void output(CoronaMonthlyStats monthlyStats)
    {
        System.out.println("Cases in " + Month.of(Integer.parseInt(monthlyStats.month())) + ": " + monthlyStats.cases());
    }
}
