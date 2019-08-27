package com.ishita.doordash.utils;

import android.os.Build;
import android.util.Log;

public class LogUtils {

    // Debug logs for user debug build only
    public static final boolean DEBUG = isUserDebugBuild();

    public static final boolean INFO = true;
    public static final boolean WARN = true;
    public static final boolean ERROR = true;

    private LogUtils() {
    }

    /**
     * Writes a debug log.
     *
     * @param tag
     * @param args
     */
    public static final void d(String tag, Object... args) {
        if (isLoggable(tag, Log.DEBUG)) {
            Log.d(tag, getLoggableString(args));
        }
    }

    /**
     * Writes a info log.
     *
     * @param tag
     * @param args
     */
    public static final void i(String tag, Object... args) {
        if (isLoggable(tag, Log.INFO)) {
            Log.i(tag, getLoggableString(args));
        }
    }

    /**
     * Writes a warning log. When the last argument is a {@link Throwable}, then
     * a stack trace is logged as well.
     *
     * @param tag
     * @param args
     */
    public static final void w(String tag, Object... args) {
        if (isLoggable(tag, Log.WARN)) {
            final Throwable throwable = getThrowable(args);
            if (throwable == null) {
                Log.w(tag, getLoggableString(args));
            } else {
                Log.w(tag, getLoggableString(args), throwable);
            }
        }
    }

    /**
     * Writes an error log. When the last argument is a {@link Throwable}, then
     * a stack trace is logged as well.
     *
     * @param tag
     * @param args
     */
    public static final void e(String tag, Object... args) {
        if (isLoggable(tag, Log.ERROR)) {
            final Throwable throwable = getThrowable(args);
            if (throwable == null) {
                Log.e(tag, getLoggableString(args));
            } else {
                Log.e(tag, getLoggableString(args), throwable);
            }
        }
    }

    private static final boolean isLoggable(String tag, int level) {
        return (Log.isLoggable(tag, level));
    }

    /**
     * Returns the Throwable if it is the last argument.
     *
     * @param args
     * @return
     */
    private static final Throwable getThrowable(Object... args) {
        if ((args == null) || (args.length == 0)) {
            return null;
        }
        final Object lastArg = args[args.length - 1];
        if (lastArg instanceof Throwable) {
            return (Throwable) lastArg;
        }
        return null;
    }

    private static final String getLoggableString(Object... args) {
        final StringBuilder outputBuilder = new StringBuilder();

        for (Object obj : args) {
            outputBuilder.append(obj);
        }
        return outputBuilder.toString();
    }

    private static final boolean isUserDebugBuild() {
        return Constants.USER_DEBUG.equalsIgnoreCase(Build.TYPE);
    }
}


