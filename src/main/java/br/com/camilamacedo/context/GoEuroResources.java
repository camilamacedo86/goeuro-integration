package br.com.camilamacedo.context;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;


public class GoEuroResources {
    private static final ResourceBundle MESSAGENS = ResourceBundle.getBundle("goeuro_messages", Locale.getDefault());

    private GoEuroResources() {

    }

    public static final String getMessage(String key, Object... params) {
        return MessageFormat.format(MESSAGENS.getString(key), params);
    }

}