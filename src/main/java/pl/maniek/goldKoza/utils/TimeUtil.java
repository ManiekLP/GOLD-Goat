package pl.maniek.goldKoza.utils;

import lombok.NonNull;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TimeUtil
{
    public static String convertSeconds(final long seconds) {
        final long days = TimeUnit.SECONDS.toDays(seconds);
        final long hours = TimeUnit.SECONDS.toHours(seconds) - days * 24L;
        final long minutes = TimeUnit.SECONDS.toMinutes(seconds) - days * 24L * 60L - hours * 60L;
        final long secs = seconds - days * 24L * 60L * 60L - hours * 60L * 60L - minutes * 60L;
        if (days != 0L) {
            return days + "d " + hours + "h " + minutes + "min " + secs + "s";
        }
        if (hours != 0L) {
            return hours + "h " + minutes + "min " + secs + "s";
        }
        if (minutes != 0L) {
            return minutes + "min " + secs + "s";
        }
        return secs + "s";
    }

    public static String convertMills(final long milliseconds) {
        final long days = TimeUnit.MILLISECONDS.toDays(milliseconds);
        final long hours = TimeUnit.MILLISECONDS.toHours(milliseconds) - days * 24L;
        final long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) - days * 24L * 60L - hours * 60L;
        final long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) - days * 24L * 60L * 60L - hours * 60L * 60L - minutes * 60L;
        long millisecondsFinal = milliseconds - days * 24L * 60L * 60L * 1000L - hours * 60L * 60L * 1000L - minutes * 60L * 1000L - seconds * 1000L;
        if (millisecondsFinal == 1000L) {
            --millisecondsFinal;
        }
        if (days != 0L) {
            if (millisecondsFinal == 0L) {
                return days + "d " + hours + "h " + minutes + "min " + seconds + "s";
            }
            return days + "d " + hours + "h " + minutes + "min " + seconds + "s " + millisecondsFinal + "ms";
        }
        else if (hours != 0L) {
            if (millisecondsFinal == 0L) {
                return hours + "h " + minutes + "min " + seconds + "s";
            }
            return hours + "h " + minutes + "min " + seconds + "s " + millisecondsFinal + "ms";
        }
        else if (minutes != 0L) {
            if (millisecondsFinal == 0L) {
                return minutes + "min " + seconds + "s";
            }
            return minutes + "min " + seconds + "s " + millisecondsFinal + "ms";
        }
        else {
            if (seconds == 0L) {
                return millisecondsFinal + "ms";
            }
            if (millisecondsFinal == 0L) {
                return seconds + "s";
            }
            return seconds + "s " + millisecondsFinal + "ms";
        }
    }

    public static String convertDurationSeconds(@NonNull final Duration duration) {
        if (duration == null) {
            throw new NullPointerException("duration is marked non-null but is null");
        }
        return convertSeconds(duration.getSeconds());
    }

    public static String convertDurationMills(@NonNull final Duration duration) {
        if (duration == null) {
            throw new NullPointerException("duration is marked non-null but is null");
        }
        return convertMills(duration.toMillis());
    }

    public static int parseTimeToSeconds(String time) {
        int totalSeconds = 0;
        String regex = "(\\d+)([dhms]|min)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(time);

        while (matcher.find()) {
            int value = Integer.parseInt(matcher.group(1));
            String unit = matcher.group(2);

            switch (unit) {
                case "d":
                    totalSeconds += value * 86400; // dni na sekundy
                    break;
                case "h":
                    totalSeconds += value * 3600; // godziny na sekundy
                    break;
                case "m":
                case "min":
                    totalSeconds += value * 60; // minuty na sekundy
                    break;
                case "s":
                    totalSeconds += value; // sekundy
                    break;
                default:
                    throw new IllegalArgumentException("Nieznana jednostka czasu: " + unit);
            }
        }

        if (totalSeconds == 0) {
            throw new IllegalArgumentException("Czas nie może być zerowy lub niepoprawny format.");
        }

        return totalSeconds;
    }

    private TimeUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}