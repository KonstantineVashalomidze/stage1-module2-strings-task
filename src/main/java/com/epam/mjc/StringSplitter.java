package com.epam.mjc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

public class StringSplitter {

    /**
     * Splits given string applying all delimeters to it. Keeps order of result substrings as in source string.
     *
     * @param source source string
     * @param delimiters collection of delimiter strings
     * @return List of substrings
     */
    public List<String> splitByDelimiters(String source, Collection<String> delimiters) {
        if (source == null || source.isEmpty() || delimiters == null || delimiters.isEmpty()) {
            List<String> result = new ArrayList<>();
            if (source != null && !source.isEmpty()) {
                result.add(source);
            }
            return result;
        }

        List<String> result = new ArrayList<>();
        result.add(source);

        for (String delimiter : delimiters) {
            if (delimiter == null || delimiter.isEmpty()) {
                continue;
            }

            List<String> newResult = new ArrayList<>();
            for (String part : result) {
                String[] splitParts = part.split(delimiter, -1);
                for (String splitPart : splitParts) {
                    newResult.add(splitPart);
                }
            }
            result = newResult;
        }

        // Remove empty strings from the result
        result.removeIf(String::isEmpty);

        return result;
    }
}
