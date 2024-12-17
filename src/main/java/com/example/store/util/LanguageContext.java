package com.example.store.util;

public class LanguageContext {

    private static final ThreadLocal<String> currentLanguage = new ThreadLocal<>();

    public static void setLanguage(String languageCode) {
        currentLanguage.set(languageCode);
    }

    public static String getLanguage() {
        return currentLanguage.get();
    }

    public static void clear() {
        currentLanguage.remove();
    }
}
