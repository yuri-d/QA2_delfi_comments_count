package lv.qaguru.qa2.core;

import org.openqa.selenium.By;
import org.apache.log4j.Logger;

/**
 * Created by user on 2017.05.20..
 */
public class MainPage {

    BaseFunctions baseFunctions;
    private static final Logger LOGGER = Logger.getLogger(MainPage.class);
    public static final String URL = "http://rus.delfi.lv";

    /*
    id.column1-top
        div.top2012-big
                h3.top2012-title
                    a.href.top2012-title : название
                    a.href.comment-count : кол-во каментов

         div.top2012-small

*/



    private static final By TOP_ARTICLES_LIST = By.id("column1-top");


    public MainPage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
        this.getTopArticlesList();
    }

    /** Check if page content is ready */
    public boolean isPresentTopArticlesDiv() {
        baseFunctions.waitForElement(TOP_ARTICLES_LIST, 500);
        return baseFunctions.isPresentElement(TOP_ARTICLES_LIST);
    }


    public void getTopArticlesList() {
        if(isPresentTopArticlesDiv()) {
            LOGGER.info("getTopArticlesList > " + TOP_ARTICLES_LIST + " presents. Start parse.");
        } else {
            LOGGER.warn("getTopArticlesList > " + TOP_ARTICLES_LIST + " not present. Ignore parsing");
        }


    }


    // Title, commentsTotal cnt, registered, anonim, url

}
