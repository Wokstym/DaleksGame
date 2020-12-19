package pl.edu.agh.ki.to.theoffice.common.formatter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnityFormatterTest {

    @Test
    public void testFormatPositive() {
        // given
        int value = 2;

        // when then
        assertEquals(1, UnityFormatter.format(value));
    }

    @Test
    public void testFormatNegative() {
        // given
        int value = -10;

        // when then
        assertEquals(-1, UnityFormatter.format(value));
    }

    @Test
    public void testFormatEqual() {
        // given
        int value = 0;

        // when then
        assertEquals(0, UnityFormatter.format(value));
    }
}