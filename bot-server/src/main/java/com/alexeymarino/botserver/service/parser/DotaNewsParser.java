package com.alexeymarino.botserver.service.parser;

import com.alexeymarino.botserver.exception.ContentParseException;
import com.alexeymarino.botserver.model.MenuType;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import static com.alexeymarino.botserver.exception.ExceptionMessage.CONTENT_PARSE_ERROR;
import static com.alexeymarino.botserver.exception.ExceptionMessage.PARSE_CONNECT_ERROR;
import static com.alexeymarino.botserver.model.MenuType.DOTA;
import static com.alexeymarino.botserver.model.MenuType.NEWS;
import static com.alexeymarino.botserver.service.parser.Parsers.NEWS_DOTA;
import static com.alexeymarino.botserver.util.Constants.PAGE_SIZE_5;

@Service
public class DotaNewsParser implements Parser {

    private final String CYBER_SPORT = "www.cybersport.ru";
    private final String CYBER_SPORT_DOTA = "https://www.cybersport.ru/dota-2";

    public List<String> getContent() {
        List<String> content = parseContent();
        if(content.isEmpty()) {
            content.add(CONTENT_PARSE_ERROR);
            throw new ContentParseException(CONTENT_PARSE_ERROR);
        }
        return convertToContentList(content, PAGE_SIZE_5);
    }

    private List<String> parseContent() {
        Document document = null;
        try {
            document = Jsoup.connect(CYBER_SPORT_DOTA)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.154 Safari/537.36")
                    .get();
        } catch (Exception e) {
            throw new ContentParseException(PARSE_CONNECT_ERROR);
        }

        Elements news = document.select("div.no-margin");

        return IntStream.range(0, news.size()).boxed().map(i ->
                String.format("\u2694\ufe0f [%s](%s%s)\n",
                                news.get(i).select("h3").text(),
                                CYBER_SPORT,
                                news.get(i).select("a").attr("href")))
                .collect(Collectors.toList());
    }
    @Override
    public MenuType getParserType() {
        return NEWS;
    }

    @Override
    public MenuType getParserSubType() {
        return DOTA;
    }
}
