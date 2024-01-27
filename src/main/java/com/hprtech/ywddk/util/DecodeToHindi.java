package com.hprtech.ywddk.util;

public class DecodeToHindi {
    private static String decodeUnicode(String input) {
        StringBuilder sb = new StringBuilder();
        StringBuilder currentToken = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);

            if (currentChar == '\\' && i < input.length() - 1 && input.charAt(i + 1) == 'u') {
                // Start of a Unicode escape sequence
                if (currentToken.length() > 0) {
                    sb.append(currentToken);
                    currentToken.setLength(0);
                }

                // Extract the Unicode escape sequence
                String unicodeSequence = input.substring(i + 2, i + 6);

                // Handle Devanagari script
                int codepoint = Integer.parseInt(unicodeSequence, 16);
                sb.append((char) codepoint);

                // Skip the processed characters
                i += 5;
            } else {
                // Regular character or number
                currentToken.append(currentChar);
            }
        }

        if (currentToken.length() > 0) {
            sb.append(currentToken);
        }

        return sb.toString();
    }
}
