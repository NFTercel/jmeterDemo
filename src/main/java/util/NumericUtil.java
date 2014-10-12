package util;

/**
 * Created by winnie on 2014-08-23 .
 */
public class NumericUtil {

    public static int parseInt(String value, int defaultValue) {

        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return defaultValue;
    }
}
