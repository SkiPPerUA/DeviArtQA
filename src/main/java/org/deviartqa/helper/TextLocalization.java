package org.deviartqa.helper;

import org.deviartqa.TestScenario;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public interface TextLocalization {

    static String get(String key, String localization){
        String text = "";
        File file = new File("src/main/resources/text.xml");
        Properties properties = new Properties();
        try {
            properties.loadFromXML(new FileInputStream(file));
        }catch (Throwable e){
            throw new RuntimeException("Файл с ресурсами не найден");
        }
        char [] arrValue;
        try {
            arrValue = properties.get(key).toString().toCharArray();
        }catch (NullPointerException e){
            throw new RuntimeException(String.format("Ключь {%s} НЕ найден",key));
        }


        if (arrValue[0] == '{' && arrValue[arrValue.length-1] == '}'){
            try {
                text = new JSONObject(properties.get(key).toString()).getString(localization);
            }catch (JSONException e){
                throw new RuntimeException(String.format("Ключь {%s} для локализации {%s} НЕ найден",key,localization));
            }
        }else {
            text = properties.get(key).toString();
        }
        return text;
    }
    static String get(String key){
        return get(key,TestScenario.local);
    }
}
