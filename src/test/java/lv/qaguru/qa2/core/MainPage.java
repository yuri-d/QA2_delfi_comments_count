package lv.qaguru.qa2.core;

import org.openqa.selenium.By;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 2017.05.20..
 */
public class MainPage {

    public BaseFunctions baseFunctions;
    private static final Logger LOGGER = Logger.getLogger(MainPage.class);
    public static final String URL = "http://rus.delfi.lv";

    private static final By TOP_ARTICLES_LIST = By.id("column1-top");

    /** Delfi.LV Article-type classes. All other classes are simple links - not article links */
    public static final List<String> VALID_ARTICLE_LINK_CLASSES = Arrays.asList(
            "top2012-title",
            "article-title",
            "recomm-link",
            "article-related-link",
            "mostread-car-link",
            "latvija-var-headline-title-link"
    );

    /** Delfi.LV Article-type parent classes for article links that has no classes defined */
    public static final List<String> VALID_ARTICLE_LINK_PARENT_CLASSES = Arrays.asList(
            "title",
            "article_small"
    );

    /*
    id.column1-top
        div.top2012-big
                h3.top2012-title
                    a.href.top2012-title : название
                    a.href.comment-count : кол-во каментов

         div.top2012-small
*/


    public MainPage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
        baseFunctions.scrollDownToPageEnd();
        baseFunctions.scrollUpToPageTop();
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


    public WebElement getArticleUrlElemByArticleTitle(String title) {
        WebElement elem = null;
        try {
            // === Find "a href" element
            elem =  this.baseFunctions.driver.findElement(By.linkText(title));
            // === Check it's an article url. Invalid urls will be ignored.
            if(!this.isValidArticleUrlElem(elem)) {
                elem = null;
            }

        } catch(Exception e) {
            LOGGER.warn("Article with title["+title+"] not exists");
        }
        return elem;
    }

    /** Check if element belongs to articles (not a link to somewhere else such as menu items, ads etc) */
    private boolean isValidArticleUrlElem(WebElement elem) {
        String elemClass = elem.getAttribute("class").trim();
        // === First-line validation: by element class
        boolean result = VALID_ARTICLE_LINK_CLASSES.contains(elemClass);
        // === Second-line validation: by parent elem class
        String parentElemClass = "";
        if(!result) {
            WebElement parentElem = elem.findElement(By.xpath(".."));
            parentElemClass = parentElem.getAttribute("class").trim();
            result = VALID_ARTICLE_LINK_PARENT_CLASSES.contains(parentElemClass);
        }
        LOGGER.info("isValidArticleUrlElem > Element with text["+ elem.getText()+"], elemClass["+elemClass+"] parentElemClass["+parentElemClass+"] : is valid article url element["+result+"]" );
        return result;
    }


    public WebElement getArticleCommentsCountElemByArticleUrlElem(WebElement articleTitleElem) {
        WebElement countElem = null;
        try {
            countElem = articleTitleElem.findElement(By.xpath("following-sibling::a"));
            //LOGGER.info("Comments count element found for title["+countElem.getText()+"]");
        } catch (Exception e) {
            LOGGER.warn("Comments count element NOT found for title["+articleTitleElem.getText()+"]");
        }
        return countElem;
    }

    public int getArticleCommentsCountByArticleUrlElem(WebElement articleTitleElem) {
        WebElement countElem = this.getArticleCommentsCountElemByArticleUrlElem(articleTitleElem);
        int commentsCount = 0;
        if(countElem != null) {
            commentsCount = Utils.extractCommentsCount(countElem);
        }
        return commentsCount;
    }

}
