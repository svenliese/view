package de.sl.view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

/**
 * @author SL
 */
public class TextButtonTest {

    private static final String EXCEPTION_EXPECTED = "exception expected";

    @Test
    void testText() {
        final String text1 = "1";
        final String text2 = "2";

        final TextButton<Color> testObj = new TextButton<>(
            text1,
            Color.WHITE,
            Color.BLACK
        );
        Assertions.assertEquals(text1, testObj.getText());

        try {
            testObj.setText(null);
            Assertions.fail(EXCEPTION_EXPECTED);
        } catch(IllegalArgumentException ex) {
            Assertions.assertEquals(ViewBase.ERR_NULL_PARAM, ex.getMessage());
        }

        testObj.setText(text2);
        Assertions.assertEquals(text2, testObj.getText());
    }

    @Test
    void testTextColor() {
        final Color color1 = Color.BLACK;
        final Color color2 = Color.WHITE;

        final TextButton<Color> testObj = new TextButton<>(
            "test",
            color1,
            Color.BLACK
        );
        Assertions.assertEquals(color1, testObj.getTextColor());

        try {
            testObj.setTextColor(null);
            Assertions.fail(EXCEPTION_EXPECTED);
        } catch(IllegalArgumentException ex) {
            Assertions.assertEquals(ViewBase.ERR_NULL_PARAM, ex.getMessage());
        }

        testObj.setTextColor(color2);
        Assertions.assertEquals(color2, testObj.getTextColor());
    }

    @Test
    void testBgColor() {
        final Color color1 = Color.BLACK;
        final Color color2 = Color.WHITE;

        final TextButton<Color> testObj = new TextButton<>(
            "test",
            Color.WHITE,
            color1
        );
        Assertions.assertEquals(color1, testObj.getBgColor());

        try {
            testObj.setBgColor(null);
            Assertions.fail(EXCEPTION_EXPECTED);
        } catch(IllegalArgumentException ex) {
            Assertions.assertEquals(ViewBase.ERR_NULL_PARAM, ex.getMessage());
        }

        testObj.setBgColor(color2);
        Assertions.assertEquals(color2, testObj.getBgColor());
    }

    @Test
    void testBorderColor() {
        final Color color1 = Color.BLACK;
        final Color color2 = Color.WHITE;

        final TextButton<Color> testObj = new TextButton<>(
            "test",
            Color.WHITE,
            Color.BLACK,
            new LineProperties<>(color1, 2.0f, false)
        );
        Assertions.assertEquals(color1, testObj.getBorderColor());

        try {
            testObj.setBorderColor(null);
            Assertions.fail(EXCEPTION_EXPECTED);
        } catch(IllegalArgumentException ex) {
            Assertions.assertEquals(ViewBase.ERR_NULL_PARAM, ex.getMessage());
        }

        testObj.setBorderColor(color2);
        Assertions.assertEquals(color2, testObj.getBorderColor());
    }

    @Test
    void testHAlign() {

        final TextButton<Color> testObj = new TextButton<>(
            "test",
            Color.WHITE,
            Color.BLACK
        );
        Assertions.assertEquals(SimpleText.CENTER, testObj.getHAlign());

        try {
            testObj.setHAlign(-1);
            Assertions.fail(EXCEPTION_EXPECTED);
        } catch(IllegalArgumentException ex) {
            Assertions.assertTrue(ex.getMessage().startsWith(ViewBase.ERR_UNKNOWN_VALUE));
        }

        testObj.setHAlign(SimpleText.LEFT);
        Assertions.assertEquals(SimpleText.LEFT, testObj.getHAlign());
    }

    @Test
    void testOrientation() {

        final TextButton<Color> testObj = new TextButton<>(
            "test",
            Color.WHITE,
            Color.BLACK
        );
        Assertions.assertEquals(SimpleText.HORIZONTAL, testObj.getOrientation());

        try {
            testObj.setOrientation(-1);
            Assertions.fail(EXCEPTION_EXPECTED);
        } catch(IllegalArgumentException ex) {
            Assertions.assertTrue(ex.getMessage().startsWith(ViewBase.ERR_UNKNOWN_VALUE));
        }

        testObj.setOrientation(SimpleText.VERTICAL);
        Assertions.assertEquals(SimpleText.VERTICAL, testObj.getOrientation());
    }

    @Test
    void testTextSize() {

        final TextButton<Color> testObj = new TextButton<>(
            "test",
            Color.WHITE,
            Color.BLACK
        );
        Assertions.assertEquals(8, testObj.getTextSize());

        try {
            testObj.setTextSize(0);
            Assertions.fail(EXCEPTION_EXPECTED);
        } catch(IllegalArgumentException ex) {
            Assertions.assertTrue(ex.getMessage().startsWith(ViewBase.ERR_INVALID_VALUE));
        }

        testObj.setTextSize(12);
        Assertions.assertEquals(12, testObj.getTextSize());
    }
}
