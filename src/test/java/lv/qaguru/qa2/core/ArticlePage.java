package lv.qaguru.qa2.core;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by user on 2017.06.17..
 */
public class ArticlePage {

    public BaseFunctions baseFunctions;
    private static final Logger LOGGER = Logger.getLogger(ArticlePage.class);

    private static final By ARTICLE_TITLE_CONTAINER = By.className("article-title");
    private static final By COMMENTS_COUNT_ALL = By.className("comment-count");

    public ArticlePage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
    }


    public WebElement getCommentsAllSizeElem() {
        WebElement titleContainerElem = this.baseFunctions.driver.findElement(ARTICLE_TITLE_CONTAINER);
        WebElement elem = null;
        try {
            elem = titleContainerElem.findElement(COMMENTS_COUNT_ALL);
        } catch (Exception e) {
            LOGGER.warn("getCommentsAllSizeElem > "+e.getMessage());
        }
        return elem;
    }

    public int getCommentsAllSize() {
        WebElement elem = this.getCommentsAllSizeElem();
        int commentsCount = 0;
        if(elem != null) {
            commentsCount = Utils.extractCommentsCount(elem);
        }
        return commentsCount;
    }

}
