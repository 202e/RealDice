package io.github._202e.realdice;

import io.github._202e.realdice.exception.RollParseException;

import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiceRoll {
    private static final Pattern rollPattern = Pattern.compile("([+-]?)([\\dd]+)");

    private static final Random rand = new Random();

    private final int diceCount;
    private final int dieFaces;

    private DiceRoll(int diceCount, int dieFaces) {
        this.diceCount = diceCount;
        this.dieFaces = dieFaces;
    }

    private int roll() {
        // might be faster with gaussian randoms, but this is fast enough for less than of thousands of rolls at once
        int sum = 0;
        for (int i = 0; i < diceCount; i++) {
            sum += rand.nextInt(this.dieFaces) + 1;
        }
        return sum;
    }

    public static int[] rollFormat(String f) throws RollParseException {
        String[] parts = f.split("(?=[+-])");
        int[] values = new int[parts.length];
        System.out.println(Arrays.toString(parts));
        for (int i = 0; i < parts.length; i++) {
            Matcher m = rollPattern.matcher(parts[i]);
            if (m.matches()) {
                boolean negate = m.group(1).equals("-");
                String valueString = m.group(2).toLowerCase();
                int value;
                if (valueString.contains("d")) {
                    String[] specs = valueString.split("d");
                    if (specs.length != 2) {
                        throw new RollParseException(parts[i]);
                    }
                    int count = 1;
                    if (!specs[0].isEmpty()) {
                        count = Integer.parseInt(specs[0]);
                    }
                    if (specs[1].isEmpty()) {
                        throw new RollParseException(parts[i]);
                    }
                    int sides = Integer.parseInt(specs[1]);
                    value = new DiceRoll(count, sides).roll();
                } else {
                    value = Integer.parseInt(valueString);
                }
                if (negate) value *= -1;
                values[i] = value;
            } else {
                throw new RollParseException(parts[i]);
            }
        }
        return values;
    }

    private static String formatOutcome(int[] outcome, String formula) {
        StringBuilder out = new StringBuilder();
        out.append(" ← ").append(outcome[0]);
        int sum = outcome[0];
        for (int i = 1; i < outcome.length; i++) {
            sum += outcome[i];
            out.append(outcome[i] < 0 ? " - " : " + ");
            out.append(Math.abs(outcome[i]));
        }
        out.insert(0, sum).append(" ← ").append(formula);
        return out.toString();
    }
}
