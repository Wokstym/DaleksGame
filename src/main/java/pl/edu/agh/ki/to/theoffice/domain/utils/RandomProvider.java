package pl.edu.agh.ki.to.theoffice.domain.utils;

import java.util.Random;

public class RandomProvider {

    private static final Random r = new Random();

    public static int randomNextInt(int bound) {
        return r.nextInt(bound);
    }
}
