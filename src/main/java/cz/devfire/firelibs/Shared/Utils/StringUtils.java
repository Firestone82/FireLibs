package cz.devfire.firelibs.Shared.Utils;

import com.google.common.collect.Lists;
import cz.devfire.firelibs.Spigot.Utils.ServerUtils;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private StringUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    private static final Pattern hexPattern = Pattern.compile("&#(\\w{5}[0-9a-f])");

    /**
     *
     * @param textToTranslate
     * @return
     */
    public static String translateHexCodes(String textToTranslate) {
        if (ServerUtils.getServerVersionID() >= 16) {
            Matcher matcher = hexPattern.matcher(textToTranslate);
            StringBuffer buffer = new StringBuffer();

            while (matcher.find()) {
                matcher.appendReplacement(buffer,ChatColor.valueOf("#" + matcher.group(1)).toString());
            }

            return cc(matcher.appendTail(buffer).toString());
        }

        return cc(textToTranslate);
    }

    /**
     *
     * @param list
     * @return
     */
    public static String listToString(List<String> list) {
        return String.join("\n&r",list);
    }

    /**
     *
     * @param millis
     * @return
     */
    public static String getTimerString(Long millis) {
        String s = "";
        Long amount = 0L;

        amount /= 60000L;
        millis %= 60000L;
        s = s + ((amount < 10L) ? "0" : "") + amount + ":";

        amount = millis / 1000L;
        millis = millis % 1000L;
        s = s + ((amount < 10L) ? "0" : "") + amount + ":";

        s = s + ((millis < 100L) ? ("0" + ((millis < 10L) ? "0" : "")) : "") + millis;
        return s;
    }

    /**
     *
     * @param string
     * @return
     */
    public static String stripBrackets(String string) {
        if (string.startsWith("[")) {
            string = string.substring(1);
        }

        if (string.startsWith("{")) {
            string = string.substring(1);
        }

        if (string.endsWith("]")) {
            string = string.substring(0,string.length()-1);
        }

        if (string.endsWith("}")) {
            string = string.substring(0,string.length()-1);
        }

        return string;
    }

    /**
     *
     * @param string
     * @return
     */
    public static String stripColors(String string) {
        return stripColors(string,true);
    }

    /**
     *
     * @param string
     * @param translated
     * @return
     */
    public static String stripColors(String string, Boolean translated) {
        for (String ch : Arrays.asList("0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f","i","k","l","m","n","o","r","x")) {
            string = string.replace("&"+ ch,"");
        }

        if (translated) {
            return ChatColor.stripColor(string);
        } else {
            return string;
        }
    }

    /**
     *
     * @param string
     * @return
     */
    public static Boolean containsColorArgs(String string) {
        return containsColorArgs(string,false);
    }

    /**
     *
     * @param string
     * @return
     */
    public static boolean isColored(String string) {
        return containsColorArgs(string,true);
    }

    private static Boolean containsColorArgs(String string, Boolean translated) {
        if (string.length() <= 2) {
            return false;
        }

        for (Integer i = 0; i <= 9; i++) {
            if (string.contains("&"+ i)) return true;
            if (translated && string.contains("§"+ i)) return true;
        }

        for (String ch : Arrays.asList("a","b","c","d","e","f","i","k","l","m","n","o","r","x")) {
            if (string.toLowerCase().contains("&"+ ch)) return true;
            if (translated && string.toLowerCase().contains("§"+ ch)) return true;
        }

        return false;
    }

    /**
     *
     * @param string
     * @return
     */
    public static String cc(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    /**
     *
     * @param stringList
     * @return
     */
    public static List<String> ccl(List<String> stringList) {
        List<String> list = Lists.newArrayList();

        for (String s : stringList) {
            list.add(cc(s));
        }

        return list;
    }

    /**
     *
     * @param string
     * @param args
     * @return
     */
    public static String parseArgs(String string, String... args) {
        Integer i = 0;

        for (String arg : args) {
            string = string.replace("{"+ i +"}", arg);
            i++;
        }

        return string;
    }

    /**
     *
     * @param string
     * @param args
     * @return
     */
    public static String parseArgs(String string, List<String> args) {
        Integer i = 0;

        for (String arg : args) {
            string = string.replace("{"+ i +"}", arg);
            i++;
        }

        return string;
    }

    /**
     *
     * @param stringList
     * @param args
     * @return
     */
    public static List<String> parseArgs(List<String> stringList, String... args) {
        List<String> a = Lists.newArrayList();

        for (String string : stringList) {
            Integer i = 0;

            for (String arg : args) {
                string = string.replace("{" + i + "}", arg);
                i++;
            }

            a.add(string);
        }

        return a;
    }

    /**
     *
     * @param stringList
     * @param args
     * @return
     */
    public static List<String> parseArgs(List<String> stringList, List<String> args) {
        List<String> a = Lists.newArrayList();

        for (String string : stringList) {
            Integer i = 0;

            for (String arg : args) {
                string = string.replace("{" + i + "}", arg);
                i++;
            }

            a.add(string);
        }

        return a;
    }

    /**
     *
     * @param n
     * @return
     */
    public static String getAlphaNumericString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }

    /**
     *
     * @param word
     * @param withMatch
     * @return
     */
    public static ArrayList<String> copyMatches(String word, List<String> withMatch) {
        ArrayList<String> a = new ArrayList<String>();

        for (String string : withMatch) {
            if (string.length() < word.length()) {
                continue;
            }

            if (string.regionMatches(true,0,word,0,word.length())) {
                a.add(string);
            }
        }

        return a;
    }

    /**
     *
     * @param text
     * @param string
     * @return
     */
    public static Boolean containsIgnoreCase(String text, String string) {
        if (text == null || string == null) return false;

        final Integer length = string.length();

        if (length == 0) {
            return true;
        }

        for (int i = text.length() - length; i >= 0; i--) {
            if (text.regionMatches(true, i, string,0, length)) {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @param list
     * @param string
     * @return
     */
    public static Boolean containsIgnoreCase(Collection<String> list, String string) {
        for (String current : list) {
            if (current.equalsIgnoreCase(string)) {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @param seconds
     * @return
     */
    public static String getTimeString(Long seconds) {
        Integer days = ((int) Math.floor(seconds / 86400));
        String d = days == 0 ? "" : days +"d ";
        seconds = seconds % 86400;

        Integer hours = ((int) Math.floor(seconds / 3600));
        String h = hours == 0 ? "" : hours +"h ";
        seconds = seconds % 3600;

        Integer minutes = ((int) Math.floor(seconds / 60));
        String m = minutes == 0 ? "" : minutes + "m ";
        seconds = seconds % 60;

        String s = seconds == 0 ? "" : seconds + "s";

        return d + h + m + s;
    }

    /**
     *
     * @param string
     * @return
     */
    public static Boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     *
     * @param string
     * @return
     */
    public static Boolean isDouble(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     *
     * @param string
     * @return
     */
    public static Boolean isFloat(String string) {
        try {
            Float.parseFloat(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     *
     * @param string
     * @return
     */
    public static Boolean isLong(String string) {
        try {
            Long.parseLong(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     *
     * @param string
     * @return
     */
    public static Boolean isShort(String string) {
        try {
            Short.parseShort(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     *
     * @param stringList
     * @param target
     * @param replacement
     * @return
     */
    public static Object replaceList(List<String> stringList, String target, String replacement) {
        ArrayList<String> newList = Lists.newArrayList();

        for (String line : stringList) {
            newList.add(line.replace(target,replacement));
        }

        return newList;
    }
}
