package utils;

public final class ConsoleColors {

    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";

    private static final String CYAN = "\u001B[36m";
    private static final String BLUE = "\u001B[34m";
    private static final String WHITE = "\u001B[37m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";

    private ConsoleColors() {
    }

    private static String colorize(String color, String text) {
        return color + text + RESET;
    }

    public static String title(String text) {
        return colorize(BOLD + CYAN, text);
    }

    public static String menu(String text) {
        return colorize(BLUE, text);
    }

    public static String info(String text) {
        return colorize(WHITE, text);
    }

    public static String prompt(String text) {
        return colorize(YELLOW, text);
    }

    public static String success(String text) {
        return colorize(GREEN, text);
    }

    public static String warning(String text) {
        return colorize(YELLOW, text);
    }

    public static String error(String text) {
        return colorize(RED, text);
    }
}
