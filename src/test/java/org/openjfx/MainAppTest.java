package org.openjfx;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openjfx.MainApp;

class MainAppTest {
    @Test
    public void HelloWorldNormalNumbers() {

        MainApp hw = new MainApp();
        Assertions.assertEquals("1", hw.convert(1));
        Assertions.assertEquals("2", hw.convert(2));
    }

    @Test
    public void HelloWorldThreeNumbers() {

        MainApp fb = new MainApp();
        Assertions.assertEquals("Hello", fb.convert(3));
    }

    @Test
    public void HelloWorldFiveNumbers() {

        MainApp hw = new MainApp();
        Assertions.assertEquals("World", hw.convert(5));
    }

    @Test
    public void HelloWorldThreeAndFiveNumbers() {

        MainApp hw = new MainApp();
        Assertions.assertEquals("World", hw.convert(5));
    }
}