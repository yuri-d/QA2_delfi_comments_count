package lv.qaguru.qa2.tests;

import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import lv.qaguru.qa2.core.ArticlePage;
import lv.qaguru.qa2.core.BaseFunctions;
import lv.qaguru.qa2.core.MainPage;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * TASK : В новом классе написать тест, входной параметр: спиок названий новостей для проверки: открыть статьи, проверить кол-во комментариев
 *
 * Created by user on 2017.05.20..
 */
public class TestCommentsCountOnMainAndArticlePage {

    private BaseFunctions baseFunctions = new BaseFunctions();
    private static final Logger LOGGER = Logger.getLogger(TestCommentsCountOnMainAndArticlePage.class);

    private static final String[] ARTICLE_TITLES = {
            "Новости",                                                                          // Пункт меню
            "Hello QA2 World",                                                                  // Несуществующий заголовок
            "Какие окна ставить в дом и квартиру: пластиковые, деревянные или алюминиевые?",    // Стандартная новость - НЕТ комментов
            "Минобороны: за пределами Адажской базы и впредь будут масштабные военные учения",  // Стандартная новость - ЕСТЬ комменты
            "В Лондоне после пожара в высотке вспыхнули стихийные демонстрации",                // Стандартная новость - ЕСТЬ комменты
            "Японцы отправили первый самолет на Курилы",                                       // Виджет "СВЕЖИЕ"
            "Лембергс: для налоговой реформы выбран некачественный сценарий"                   // Related-ссылка на новость (без картики, мелкий голубой шрифт)
    };

    private static LinkedHashMap<String, Integer> mainPageComments = new LinkedHashMap<String, Integer>();
    private static LinkedHashMap<String, Integer> articlePageComments = new LinkedHashMap<String, Integer>();


    /**
     * Get Article url-elements list and their comments count by title
     */
    @Test
    public void testMainPageFindArticleElemsByTitleList() throws MessagingException, IOException {
        LOGGER.info("testMainPageFindArticleElemsByTitleList > start");
        this.baseFunctions.initDriver();
        this.baseFunctions.goToUrl(MainPage.URL);
        MainPage mainPage = new MainPage(this.baseFunctions);

        for (String articleTitle : ARTICLE_TITLES) {
            // ===== Get article if available
            WebElement articleTitleElem = mainPage.getArticleUrlElemByArticleTitle(articleTitle);
            if (articleTitleElem != null) {
                String articleUrl = articleTitleElem.getAttribute("href");
                // ===== Get comments count if available
                int commentsCount = mainPage.getArticleCommentsCountByArticleUrlElem(articleTitleElem);
                LOGGER.info("testMainPageFindArticleElemsByTitleList > Comments:[" + commentsCount + "] Title[" + articleTitleElem.getText() + "] url[" + articleUrl + "]");
                mainPageComments.put(articleUrl, commentsCount);
            }
        }
        this.baseFunctions.stopDriver();
        LOGGER.info("testMainPageFindArticleElemsByTitleList > finish");
    }


    /**
     * Create pages by found article urls and get comments count
     */
    @Test
    public void testArticlePageGetCommentsCountByMainPageFoundElementsList() throws MessagingException, IOException {
        LOGGER.info("testArticlePageGetCommentsCountByMainPageFoundElementsList > start with found articles size[" + this.mainPageComments.size() + "]");

        if (this.mainPageComments.isEmpty()) {
            throw new MessagingException("No article links found from previous step.");
        }

        for (Map.Entry<String, Integer> entry : this.mainPageComments.entrySet()) {
            String articlePageUrl = entry.getKey();
            LOGGER.info("testArticlePageGetCommentsCountByMainPageFoundElementsList > Go to ArticlePage by MainPage url[" + articlePageUrl + "] comments[" + entry.getValue() + "]");
            this.baseFunctions.initDriver();
            this.baseFunctions.goToUrl(articlePageUrl);
            ArticlePage articlePage = new ArticlePage(this.baseFunctions);
            int articlePageCommentsSize = articlePage.getCommentsAllSize();
            LOGGER.info("testArticlePageGetCommentsCountByMainPageFoundElementsList > Found comments size on ArticlePage:[" + articlePageCommentsSize + "] vs MainPage[" + entry.getValue() + "]");
            articlePageComments.put(articlePageUrl, articlePageCommentsSize);
            this.baseFunctions.stopDriver();
        }

        LOGGER.info("testArticlePageGetCommentsCountByMainPageFoundElementsList > finish");
    }

    /**
     * Check if MinPage comments count matches ArticlePage(s) comments
     */
    @Test
    public void testMainPageCommentsSizeEqualsArticlePagesCommentsSize() throws MessagingException, IOException {
        Assert.assertFalse(mainPageComments.isEmpty());
        Assert.assertFalse(articlePageComments.isEmpty());
        Assert.assertEquals(mainPageComments, articlePageComments);
    }


    /**
     * Check if found all of given articles on a main page
     */
    @Test
    public void testMainPageAllGivenArticlesFound() throws MessagingException, IOException {
        Assert.assertEquals(ARTICLE_TITLES.length, mainPageComments.size());
    }

}