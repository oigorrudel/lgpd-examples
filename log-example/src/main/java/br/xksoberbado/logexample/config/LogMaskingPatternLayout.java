package br.xksoberbado.logexample.config;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LogMaskingPatternLayout extends PatternLayout {

    private Pattern multilinePattern;
    private List<String> fieldPatterns = new LinkedList<>();

    public void addFieldPattern(final String fieldPattern) {
        fieldPatterns.add(fieldPattern);
        multilinePattern = Pattern.compile(
            fieldPatterns.stream().collect(Collectors.joining("|")),
            Pattern.MULTILINE
        );
    }

    @Override
    public String doLayout(final ILoggingEvent event) {
        return maskMessage(super.doLayout(event));
    }

    private String maskMessage(final String message) {
        if (multilinePattern == null) {
            return message;
        }

        final var sb = new StringBuilder(message);
        final var matcher = multilinePattern.matcher(sb);

        while (matcher.find()) {
            IntStream.rangeClosed(1, matcher.groupCount())
                .forEach(group -> {
                    if (matcher.group(group) != null) {
                        IntStream.range(matcher.start(group), matcher.end(group)).forEach(i -> sb.setCharAt(i, '*'));
                    }
                });
        }
        return sb.toString();
    }
}
