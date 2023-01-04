package com.alexeymarino.botserver.service.parser;

import com.alexeymarino.botserver.model.MenuType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import static com.alexeymarino.botserver.model.MenuType.DOTA;
import static com.alexeymarino.botserver.model.MenuType.NEWS;
import static com.alexeymarino.botserver.model.MenuType.RESULT;
import static com.alexeymarino.botserver.util.Constants.PAGE_SIZE_5;

@Service
@Slf4j
public class DotaResultParser implements Parser {

    private final String dotaResults1 = "https://dota2.ru/esport/matches/";
    private final String dotaResults2 = "https://dota2.ru/esport/matches/?&page=2";
    private final String dotaResults3 = "https://dota2.ru/esport/matches/?&page=3";
    private final List<String> result = new ArrayList<>();

    private void getResultPage(String link) {
        Document document = null;
        try {
            document = Jsoup.connect(link)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.154 Safari/537.36")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements pastMatch = document.select("div.past-matches-list");
        Elements titles = pastMatch.select("div.list-match-item");
        for (int i = 0; i < titles.size(); i++) {
            StringBuilder resultNews = new StringBuilder();
            Element match = titles.get(i);
            resultNews.append("\u2796\u2796").append(match.select("div.time_block_mobile").text()).append("\u2796\u2796").append("\n");
            Elements teams = match.select("p.cybersport-matches__matches-name");
            String winners = match.select("span.score").attr("data-value");
            String[] winner = winners.split(":");
            int first;
            int second;
            try {
                first = Integer.parseInt(winner[0]);
                second = Integer.parseInt(winner[1]);
            } catch (NumberFormatException e) {
                continue; // на случай отмены матча организаторами по техническим причинам
            }

            String team1 = teams.get(0).text();
            String team2 = teams.get(1).text();
            if (first > second) {
                resultNews.append("*").append(first).append("*").append("   ").append("*").append(team1).append("*").append("\ud83c\udfc5");
                resultNews.append("\n");
                resultNews.append(second).append("   ").append(team2);
                resultNews.append("\n");
            } else if (first < second) {
                resultNews.append(first).append("   ").append(team1);
                resultNews.append("\n");
                resultNews.append("*").append(second).append("*").append("   ").append("*").append(team2).append("*").append("\ud83c\udfc5");
                resultNews.append("\n");
            } else {
                resultNews.append(first).append("   ").append(team1);
                resultNews.append("\n");
                resultNews.append(second).append("   ").append(team2);
                resultNews.append("\n");
            }
            resultNews.append(match.select("img").attr("data-title-tooltipe"));
            result.add(resultNews.toString());

        }
    }

    @Override
    public List<String> getContent() {
        getResultPage(dotaResults1);
        getResultPage(dotaResults2);
        getResultPage(dotaResults3);
        log.debug("Received a news sheet of the size - {}", result.size());
        return convertToContentList(result, PAGE_SIZE_5);
    }

    @Override
    public MenuType getParserType() {
        return RESULT;
    }

    @Override
    public MenuType getParserSubType() {
        return DOTA;
    }


}
