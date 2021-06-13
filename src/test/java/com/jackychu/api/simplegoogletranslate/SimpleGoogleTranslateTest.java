package com.jackychu.api.simplegoogletranslate;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class SimpleGoogleTranslateTest
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SimpleGoogleTranslateTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(SimpleGoogleTranslateTest.class);
    }

    public void testTestDoTranslate() {
        SimpleGoogleTranslate translate = new SimpleGoogleTranslate();
        try {
            String result = translate.doTranslate(Language.zh_cn, Language.zh_tw, "记得按下订阅按钮");
            assertEquals("記得按下訂閱按鈕", result);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
