package com.alejandrobel.prograquest;


import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.alejandrobel.prograquest.Question; // Cambia por tu paquete real
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class JsonHelper {
    public static List<Question> loadQuestionsFromJson(Context context) {
        Gson gson = new Gson();
        InputStreamReader reader = new InputStreamReader(context.getResources().openRawResource(R.raw.preguntas_app));
        Type listType = new TypeToken<List<Question>>() {}.getType();
        return gson.fromJson(reader, listType);
    }
}
