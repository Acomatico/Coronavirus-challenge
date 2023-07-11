package space.alen.corona.models;

public class CoronaMonthlyStats
{
    private String month;
    private int cases;

    public CoronaMonthlyStats(String month, int cases)
    {
        this.month = month;
        this.cases = cases;
    }

    public String month()
    {
        return this.month;
    }

    public int cases()
    {
        return this.cases;
    }
}