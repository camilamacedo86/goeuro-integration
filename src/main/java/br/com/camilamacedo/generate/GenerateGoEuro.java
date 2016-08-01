package br.com.camilamacedo.generate;

import br.com.camilamacedo.Parameters;
import br.com.camilamacedo.ParametersGoEuro;
import br.com.camilamacedo.context.GoEuroResources;
import br.com.camilamacedo.model.Place;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GenerateGoEuro implements Generate {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private static final String URL_SERVICE = "http://api.goeuro.com/api/v2/position/suggest/en/";


    @Override
    public String generate(Parameters parameters) throws Exception {
        ParametersGoEuro parametersGoEuro = (ParametersGoEuro) parameters;
        if (parametersGoEuro == null || parametersGoEuro.getCity() == null) {
            throw new Exception(GoEuroResources.getMessage("goeuro-integration.city.empty"));
        }
        String url = URL_SERVICE + parametersGoEuro.getCity();
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet(url);
            request.addHeader("content-type", "application/json");
            CloseableHttpResponse response = httpClient.execute(request);
            if ( response.getStatusLine().getStatusCode() == 200){
                List<Place> placeList = transformResultJsonToPlaceClass(response);
                validateData(placeList);
                return writeFile(parametersGoEuro, placeList);
            }
        } catch (IOException ex) {
            throw new Exception(GoEuroResources.getMessage("goeuro-integration.erro.file", ex.getMessage()));
        }
        return null;
    }

    /**
     * Make all necessary validations in data
     * @param placeList
     * @throws Exception
     */
    private void validateData(List<Place> placeList) throws Exception {
        if (isEmptyDataResult(placeList)){
            throw new Exception(GoEuroResources.getMessage("goeuro-integration.city.empty.data"));
        }
    }

    /**
     * Transform JSON in Place Objects
     * @param response
     * @return
     * @throws IOException
     */
    private List<Place> transformResultJsonToPlaceClass(CloseableHttpResponse response) throws IOException {
        Gson gson = new GsonBuilder().create();
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        TypeToken<List<Place>> token = new TypeToken<List<Place>>(){};
        return gson.fromJson(rd, token.getType());
    }

    /**
     * Validate if has result
     * @param placeList
     * @return
     */
    private boolean isEmptyDataResult(List<Place> placeList) {
        return placeList.size() < 1;
    }

    /**
     * Write a file
     * @param parametersGoEuro
     * @param placeList
     * @return
     * @throws IOException
     */
    private String writeFile(ParametersGoEuro parametersGoEuro, List<Place> placeList) throws IOException {
        long time = System.currentTimeMillis();
        String path = parametersGoEuro.getCity() + "_" + time + ".csv";
        List<String> lines = new ArrayList<String>(placeList.size());
        String csvHeader = "ID;NAME;TYPE;LATITUDE;LONGITUDE";
        lines.add(csvHeader);
        placeList.forEach(p->{
            lines.add(p.toString());
        });
        Files.write(Paths.get(path), lines);
        return path;
    }


}