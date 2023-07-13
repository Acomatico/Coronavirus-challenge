package test.space.alen.corona.models;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import space.alen.corona.models.CoronaYearlyStats;
import space.alen.corona.models.CoronaMonthlyStats;

class CoronaYearlyStatsTest
{
    @Test
    void testYearlyStatsMantainOrder()
    {
        CoronaYearlyStats yearlyStats = new CoronaYearlyStats("US", "2020");
        yearlyStats.addMonthlyStat(new CoronaMonthlyStats("02", 200));
        yearlyStats.addMonthlyStat(new CoronaMonthlyStats("05", 200));
        yearlyStats.addMonthlyStat(new CoronaMonthlyStats("01", 50));
        yearlyStats.addMonthlyStat(new CoronaMonthlyStats("03", 100));


        assertEquals("01", yearlyStats.monthlyStats().get(0).month());
        assertEquals("02", yearlyStats.monthlyStats().get(1).month());
        assertEquals("03", yearlyStats.monthlyStats().get(2).month());
        assertEquals("05", yearlyStats.monthlyStats().get(3).month());
    }
}
