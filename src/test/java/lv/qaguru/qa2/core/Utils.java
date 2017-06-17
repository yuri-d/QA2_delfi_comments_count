package lv.qaguru.qa2.core;

import org.openqa.selenium.WebElement;

/**
 * Created by user on 2017.06.17..
 */
public class Utils {

    /**  Element text "(x)" to integer x */
    public static int extractCommentsCount(WebElement elem) {
        int count = Integer.parseInt(   elem.getText()
                .replace("(","")
                .replace(")","") );
        return count;
    }

}
