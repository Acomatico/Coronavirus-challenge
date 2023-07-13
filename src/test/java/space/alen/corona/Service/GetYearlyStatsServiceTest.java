package test.space.alen.corona.service;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import space.alen.corona.models.CoronaYearlyStats;
import space.alen.corona.models.CoronaMonthlyStats;
import space.alen.corona.service.GetYearlyStatsService;
import space.alen.corona.repositories.CoronaRepositoryInterface;

class GetYearlyStatsServiceTest
{

    @Test
    void testServiceWorks()
    {
        var yearlyStats = new CoronaYearlyStats("US", "2020");
        yearlyStats.addMonthlyStat(new CoronaMonthlyStats("01", 200));

        CoronaRepositoryInterface repository = mock(CoronaRepositoryInterface.class);

        when(repository.getStatsOfYear("2020")).thenReturn(yearlyStats);

        var service = new GetYearlyStatsService(repository);
        CoronaYearlyStats actualStats = service.execute("2020");

        assertEquals(yearlyStats.countryCode(), actualStats.countryCode());
        assertEquals(yearlyStats.year(), actualStats.year());
        assertEquals(yearlyStats.monthlyStats(), actualStats.monthlyStats());
    }
}
