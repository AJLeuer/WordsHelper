package com.dev.ajl.wordshelper;

import java.util.* ;
import java.io.* ;

/**
 * Created by Adam on 8/17/15.
 */
public class Util {

    /**
     *
     * @param firstIn The first String to compare
     * @param secondIn The second String to compare
     *
     * @return True if the two Strings are equal length and character-for-character identical.
     *         Also returns true if both are of equal length and one string, or the other, or
     *         both have (a) blank space(s) in any position(s) instead of (a) character(s),
     *         but otherwise match characters at each position. For example, "m ybe" and " a be"
     *         will return true, but "sleep" and "sheep" will return false.
     */
    static boolean compareIgnoringSpaces(String firstIn, String secondIn) {

        String first = firstIn.toLowerCase() ;
        String second = secondIn.toLowerCase() ;

        if (first.length() != second.length()) {
            return false ;
        }

        for (int i = 0 ; i < first.length() ; i++) {

            char a = first.charAt(i) ;
            char b = second.charAt(i) ;

            if ((a == ' ') || (b == ' ') || (a == b)) {
                continue ;
            }
            else {
                return false ;
            }
        }
        return true ;
    }

    static String convertToString(ArrayList<String> stringList) {
        StringBuilder builder = new StringBuilder() ;
        for (String string: stringList) {
            builder.append(string + '\n');
        }
        return builder.toString() ;
    }

    static ArrayList<String> convertToStringArray(InputStream inputStream) {

        Scanner wordScanner = new Scanner(inputStream) ;
        ArrayList<String> stringArray = new ArrayList<>() ;

        while (wordScanner.hasNextLine()) {
            stringArray.add(wordScanner.nextLine());
        }
        return stringArray ;
    }

    /**
     * Identical to the above method, but takes an ArrayList<String> out parameter which is modified,
     * instead of returning one
     * 
     * @param outArray
     * @param inputStream
     */
    static void convertToStringArray(List<String> outArray, InputStream inputStream) {

        Scanner wordScanner = new Scanner(inputStream) ;

        while (wordScanner.hasNextLine()) {
            outArray.add(wordScanner.nextLine());
        }

        int i = 0 ;//debug code, delete
    }
}

class Pointer<T> {
    public T value ;

    public Pointer(T t) {
        this.value = t ;
    }
}

















