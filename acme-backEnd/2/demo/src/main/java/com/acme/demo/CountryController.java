package com.acme.demo;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;


@RestController
@CrossOrigin(origins = "http://127.0.0.1:5173")
public class CountryController {
    private final LogEntryRepository logEntryRepository;

    public CountryController(LogEntryRepository logEntryRepository) {
        this.logEntryRepository = logEntryRepository;
    }

    @GetMapping("/pais/{name}")
    public String getCountryInfo(@PathVariable String name) {
        try {
            // Construir la URL de la solicitud GET
            String apiUrl = "https://restcountries.com/v3.1/name/" + name;
            URL url = new URL(apiUrl);

            // Realizar la solicitud GET al API
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Verificar si la solicitud fue exitosa
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parsear la respuesta JSON
                Gson gson = new Gson();
                JsonObject[] countryDataArray = gson.fromJson(response.toString(), JsonObject[].class);
                JsonObject countryData = countryDataArray[0];

                // Extraer la información básica del país
                String officialName = countryData.getAsJsonObject("name").get("official").getAsString();
                String region = countryData.get("region").getAsString();
                String subregion = countryData.get("subregion").getAsString();
                JsonObject languages = countryData.getAsJsonObject("languages");
                JsonObject currencies = countryData.getAsJsonObject("currencies");
                JsonObject coatOfArms = countryData.getAsJsonObject("coatOfArms");

                // Construir la respuesta JSON
                JsonObject countryInfo = new JsonObject();
                countryInfo.addProperty("officialName", officialName);
                countryInfo.addProperty("region", region);
                countryInfo.addProperty("subregion", subregion);
                countryInfo.add("languages", languages);
                countryInfo.add("currencies", currencies);
                countryInfo.add("coatOfArms", coatOfArms);

                // Guardar el registro de rastro/log en la base de datos
                LogEntry logEntry = new LogEntry(LocalDateTime.now(), name, countryInfo.toString());
                logEntryRepository.save(logEntry);

                // Devolver la respuesta JSON como cadena
                return countryInfo.toString();
            } else {
                return "“No se encontró resultado\n" +
                        "a la búsqueda”: " + responseCode;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error en el servidor";
        }
    }
}
