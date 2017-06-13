package lv.qaguru.qa2.tests;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import lv.qaguru.qa2.core.BaseFunctions;
import lv.qaguru.qa2.core.MainPage;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.io.IOException;

/**
 * Created by user on 2017.05.20..
 */
public class TestCommentsCountOnMainAndArticlePage {

    private BaseFunctions baseFunctions = new BaseFunctions();
    private static final Logger LOGGER = Logger.getLogger(TestCommentsCountOnMainAndArticlePage.class);

    @Test
    public void test() throws MessagingException, IOException {
        LOGGER.info("Start checking delfi comments on main and article inside pages");
        baseFunctions.goToUrl(MainPage.URL);
        MainPage mainPage = new MainPage(baseFunctions);
        baseFunctions.stopDriver();
    }


    @Test
    public void test2() throws MessagingException, IOException {
        LOGGER.info("Start checking delfi comments on main and article inside pages");
        baseFunctions.goToUrl(MainPage.URL);
        MainPage mainPage = new MainPage(baseFunctions);
        baseFunctions.stopDriver();
    }


    /**  Element text "(x)" to integer x */
    private int extractCommentsCount(WebElement elem) {
        int count = Integer.parseInt(   elem.getText()
                .replace("(","")
                .replace(")","") );
        return count;
    }

}

/*

Homework

1. дописать данный тест
2. Web vs. Mobile : Открыть главную страницу (в отдельном классе) Делфи
запомнить первые 5 статей (название, колво комментов)
перейти на мобильную версию и проверить присутствие данных статей: сверить название и кол-во комментов

3 В новом классе написать тест,
входной параметр: спиок названий новостей для проверки: открыть статьи, проверить кол-во комментариев,
можно пересчитать все комментарии


Освоить PAGE OBJECTS

 */




/*


    private static final String cssCommentCount = "comment-count";


    @Test
    public void commentTesting() {
        log.info("----- Init driver");

        WebDriver driver =  MainPageOpenTest.initDriver("http://rus.delfi.lv");


        log.info("----- Open main news page");
        WebElement commentsMainElem = driver.findElement(new By.ByClassName(cssCommentCount));
        int commentsMainCount = extractCommentsCount(commentsMainElem);
        log.info("commentsMainCount["+commentsMainCount+"]");

        log.info("----- Move to article page");
        //driver.findElement(new By.ByClassName("top2012-title")).click();
        driver.findElement(By.xpath("(//*[contains(@class,'top2012-title')]) [2]")).click();

        log.info("----- Get comment count on page")
        WebElement commentsArticleInsideElem = driver.findElement(new By. ByClassName(cssCommentCount));
        int commentsArticleInsideCount = extractCommentsCount(commentsArticleInsideElem);
        log.info("commentsArticleInsideCount["+commentsArticleInsideCount+"]");

        Assert.assertEquals("Wrong comment count on article page", commentsMainCount, commentsArticleInsideCount, 0);
        log.info("Comment counts on main and inside are equal");



        log.info("----- Move to comments page");
        commentsArticleInsideElem.click();

        //todo waitFor

        log.info("----- Get registered users comments count");
        WebElement commentsRegElem = driver.findElement(new By.ByClassName("comment-thread-switcher-selected-reg"));

        log.info("----- Get anonymous users comments count");
        WebElement commentsAnoElem = driver.findElement(new By.ByClassName("comment-thread-switcher-list-a-anon"));

        log.info("-----  Check whole comments count");

        log.info("quit");
       driver.quit();
    }




 */