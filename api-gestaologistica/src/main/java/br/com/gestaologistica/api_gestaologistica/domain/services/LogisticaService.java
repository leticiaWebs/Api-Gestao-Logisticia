package br.com.gestaologistica.api_gestaologistica.domain.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogisticaService {

    @Autowired
    private GoogleMapsService googleMapsService;

    private static final String GOOGLE_API_KEY = "GOOGLE_API_KEY";

    public double calcularDistanciaEntreCidades(String origem, String destino) throws Exception {
        String response = googleMapsService.calcularDistancia(origem, destino, GOOGLE_API_KEY);

        // Parse da resposta do Google Maps API
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response);
        JsonNode distanceNode = root.path("rows").get(0).path("elements").get(0).path("distance").path("value");

        return distanceNode.asDouble() / 1000.0;
    }

    public double calcularTempoEstimado(double distancia) {
        final double VELOCIDADE_MEDIA = 60.0;
        return distancia / VELOCIDADE_MEDIA;
    }
}
