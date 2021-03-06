package com.danga.squeezer;

import java.util.Formatter;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

public class Util {
    private Util() {}
    
    public static String nonNullString(AtomicReference<String> ref) {
        String string = ref.get();
        return string == null ? "" : string;
    }
    
    // Update target and return true iff it's null or different from newValue. 
    public static boolean atomicStringUpdated(AtomicReference<String> target,
            String newValue) {
        String currentValue = target.get();
        if (currentValue == null || !currentValue.equals(newValue)) {
            target.set(newValue);
            return true;
        }
        return false;
    }

    public static int parseDecimalIntOrZero(String value) {
        int decimalPoint = value.indexOf('.');
        if (decimalPoint != -1) value = value.substring(0, decimalPoint);
        if (value.length() == 0) return 0;
        try {
            int intValue = Integer.parseInt(value);
            return intValue;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    private static StringBuilder sFormatBuilder = new StringBuilder();
    private static Formatter sFormatter = new Formatter(sFormatBuilder, Locale.getDefault());
    private static final Object[] sTimeArgs = new Object[5];

    public synchronized static String makeTimeString(long secs) {
        /* Provide multiple arguments so the format can be changed easily
         * by modifying the xml.
         */
        sFormatBuilder.setLength(0);

        final Object[] timeArgs = sTimeArgs;
        timeArgs[0] = secs / 3600;
        timeArgs[1] = secs / 60;
        timeArgs[2] = (secs / 60) % 60;
        timeArgs[3] = secs;
        timeArgs[4] = secs % 60;
        return sFormatter.format("%2$d:%5$02d", timeArgs).toString();
    }
       
}
