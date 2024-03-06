package com.hprtech.ywddk.util;

import com.hprtech.ywddk.contants.Constant;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ReplaceStringInFiles {
    public static void main(String[] args) {

    }

    private static String replaceString(String content, String stringToReplace, String replacement) {
        return content.replace(stringToReplace, replacement);
    }
}
