package com.portfolio.sanchellios.yandexmusictraining.views;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by aleksandrvasilenko on 19.04.16.
 */
public class TimeEvaluatorTest {
    @Test
    public void testGetTimeInString(){
        TimeEvaluator te = new TimeEvaluator();
        System.out.println(te.getCurrentTime());
        Assert.assertNotNull(te.getCurrentTime());
    }

}