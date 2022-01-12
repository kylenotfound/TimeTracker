package me.kyleevangelisto.timetracker;

import java.time.Instant;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Helpers for getting the difference between two time objects.
 */
@SuppressWarnings("UseOfObsoleteDateTimeApi")
public enum Duration {;
    private static final String[] NAMES_SHORT = {"y", "mo", "d", "h", "m", "s" };
    private static final String[] NAMES_FULL = { " year", " month", " day", " hour", " minute", " second" };

    /**
     * Gets the difference between two {@link Instant}s in a shortened human-readable format.
     *
     * <dl>
     *     <dt>Examples</dt>
     *     <dd>{@code toHuman(oneHourTwentyMinutesAgo, now) == "1h 20m";}</dd>
     *     <dd>{@code toHuman(now, now) == "now";}</dd>
     * </dl>
     *
     * @param first The early instant
     * @param second The later instant
     * @return the human-readable difference string
     * @see #toHumanFull(Instant, Instant)
     * @see #toHuman(Calendar, Calendar)
     * @see #toHumanFull(Calendar, Calendar)
     * @see #toHuman(Calendar, Calendar, String[], boolean)
     */

    public static String toHuman( Instant first, Instant second) {
        Calendar cFirst = new GregorianCalendar();
        Calendar cSecond = new GregorianCalendar();

        cFirst.setTimeInMillis(first.toEpochMilli());
        cSecond.setTimeInMillis(second.toEpochMilli());

        return toHuman(cFirst, cSecond);
    }

    /**
     * Gets the difference between two {@link Instant}s in a full-length human-readable format.
     *
     * <dl>
     *     <dt>Examples</dt>
     *     <dd>{@code toHuman(oneHourTwentyMinutesAgo, now) == "1 hour 20 minutes";}</dd>
     *     <dd>{@code toHuman(now, now) == "now";}</dd>
     * </dl>
     *
     * @param first The early instant
     * @param second The later instant
     * @return the human-readable difference string
     * @see #toHuman(Instant, Instant)
     * @see #toHuman(Calendar, Calendar)
     * @see #toHumanFull(Calendar, Calendar)
     * @see #toHuman(Calendar, Calendar, String[], boolean)
     */
    public static String toHumanFull( Instant first,  Instant second) {
        Calendar cFirst = new GregorianCalendar();
        Calendar cSecond = new GregorianCalendar();

        cFirst.setTimeInMillis(first.toEpochMilli());
        cSecond.setTimeInMillis(second.toEpochMilli());

        return toHumanFull(cFirst, cSecond);
    }

    /**
     * Gets the difference between two {@link Instant}s in a shortened human-readable format.
     *
     * <dl>
     *     <dt>Examples</dt>
     *     <dd>{@code toHuman(oneHourTwentyMinutesAgo, now) == "1h 20m";}</dd>
     *     <dd>{@code toHuman(now, now) == "now";}</dd>
     * </dl>
     *
     * @param first The early calendar
     * @param second The later calendar
     * @return the human-readable difference string
     * @see #toHuman(Instant, Instant)
     * @see #toHumanFull(Instant, Instant)
     * @see #toHumanFull(Calendar, Calendar)
     * @see #toHuman(Calendar, Calendar, String[], boolean)
     */
    public static String toHuman( Calendar first, Calendar second) {
        return toHuman(first, second, NAMES_SHORT, false);
    }

    /**
     * Gets the difference between two {@link Calendar}s in a full-length human-readable format.
     *
     * <dl>
     *     <dt>Examples</dt>
     *     <dd>{@code toHuman(oneHourTwentyMinutesAgo, now) == "1 hour 20 minutes";}</dd>
     *     <dd>{@code toHuman(now, now) == "now";}</dd>
     * </dl>
     *
     * @param first The early calendar
     * @param second The later calendar
     * @return the human-readable difference string
     * @see #toHuman(Instant, Instant)
     * @see #toHumanFull(Instant, Instant)
     * @see #toHuman(Calendar, Calendar)
     * @see #toHuman(Calendar, Calendar, String[], boolean)
     */
    public static String toHumanFull( Calendar first,  Calendar second) {
        return toHuman(first, second, NAMES_FULL, true);
    }

    /**
     * Gets the difference between two {@link Calendar}s in a customized human-readable format.
     *
     * <dl>
     *     <dt>Examples</dt>
     *     <dd>{@code String[] names = new String[] { "yr", "mo", "day", "hr", "min", "sec" };}</dd>
     *     <dd>{@code toHuman(oneHourTwentyMinutesAgo, now, names, false) == "1hr 20min";}</dd>
     *     <dd>{@code toHuman(oneHourTwentyMinutesAgo, now, names, true) == "1hr 20mins";}</dd>
     *     <dd>{@code toHuman(now, now, names, false) == "now";}</dd>
     *     <dd>{@code toHuman(now, now, names, true) == "now";}</dd>
     * </dl>
     *
     * @param first The early calendar
     * @param second The later calendar
     * @param names The array of names for each of year, month, day of month, hour of day, minute, and second.
     * @param plural Whether or not to append an {@code s} to the end of fragments that are anything but {@code 1}
     * @return the human-readable difference string
     * @see #toHuman(Instant, Instant)
     * @see #toHumanFull(Instant, Instant)
     * @see #toHuman(Calendar, Calendar)
     * @see #toHumanFull(Calendar, Calendar)
     */
    public static String toHuman( Calendar first,  Calendar second,  String[] names, boolean plural) {
        boolean future = false;

        if (second.equals(first)) {
            return "now";
        }

        if (second.after(first)) {
            future = true;
        }

        int accuracy = 0;
        StringBuilder result = new StringBuilder();
        int[] units = { Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND };
        names = names.length == units.length ? names : NAMES_SHORT;

        for (int i = 0; i < units.length; i++) {
            if (accuracy > 2) {
                break;
            }

            int diff = diff(units[i], first, second, future);

            if (diff > 0) {
                accuracy++;

                result.append(' ').append(diff).append(names[i]);

                if (plural && diff != 1) {
                    result.append('s');
                }
            }
        }

        if (result.length() == 0) {
            return "now";
        }

        return result.toString().trim();
    }

    private static int diff(int unit, Calendar first,  Calendar second, boolean future) {
        int diff = 0;
        long savedDate = first.getTimeInMillis();

        while ((future && first.before(second)) || (!future && first.after(second))) {
            savedDate = first.getTimeInMillis();

            first.add(unit, future ? 1 : -1);

            diff++;
        }

        diff--;

        first.setTimeInMillis(savedDate);

        return diff;
    }
}