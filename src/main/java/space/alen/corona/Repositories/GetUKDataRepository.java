package space.alen.corona.repositories;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.util.HashMap;
import java.lang.InterruptedException;
import java.util.zip.GZIPInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;
import org.json.JSONArray;
import space.alen.corona.models.CoronaYearlyStats;
import space.alen.corona.models.CoronaMonthlyStats;

public class GetUKDataRepository implements CoronaRepositoryInterface
{
    private static final String URL = "https://api.coronavirus.data.gov.uk/v1/data?filters=areaType=overview&areaName=United+Kingdom&structure=%7B%22date%22:%22date%22,%22newCasesByPublishDate%22:%22newCasesByPublishDate%22%7D";

    public CoronaYearlyStats getStatsOfYear(String year)
    {
        var yearlyStats = new CoronaYearlyStats("UK", year);

        try {
            var client = HttpClient.newHttpClient();
            var request = HttpRequest.newBuilder().uri(URI.create(this.URL)).header("Accept-Encoding", "gzip").build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            InputStream responseStream = new ByteArrayInputStream(response.body());
            responseStream = new GZIPInputStream(responseStream);
            String body = new String(responseStream.readAllBytes(), StandardCharsets.UTF_8);
            
            var rootJson = new JSONObject(body);
            var data = rootJson.getJSONArray("data");
            var monthStatsMap = new HashMap<String, Integer>();

            for (int i = 0; i < data.length(); i++) {
                var currentRow = data.getJSONObject(i);

                var date = currentRow.getString("date");
                var dateParts = date.split("-");

                var curYear = dateParts[0];
                var curMonth = dateParts[1];
                var curDay = dateParts[2];

                if (!year.equals(curYear)) {
                    continue;
                }

                int cases = currentRow.optInt("newCasesByPublishDate", 0);

                if (monthStatsMap.get(curMonth) == null) {
                    monthStatsMap.put(curMonth, 0);
                }

                int curCases =  monthStatsMap.get(curMonth);
                monthStatsMap.put(curMonth, curCases + cases);
            }

            for (int i = 1; i <= 12; i++) {
                String key = "" + i;
                if (i < 10) {
                    key = "0" + key;
                }

                var cases = monthStatsMap.get(key);
                if (cases == null) {
                    cases = 0;
                }

                yearlyStats.addMonthlyStat(new CoronaMonthlyStats(key, cases));
            }

        } catch (IOException | InterruptedException exception) {
            System.out.println(exception);
        }

        return yearlyStats;
    }
}