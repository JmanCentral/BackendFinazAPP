package com.example.FinanzApp.Servicios;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.stereotype.Service;


@Service
@Data
@AllArgsConstructor
public class ServicioConsejos {

    /*
    private final RestTemplate restTemplate;
    private final DeepSeekConfig deepSeekConfig;


    public ConsejosDTO getFinancialAdvice() {
        String endpoint = "https://api.deepseek.com/v1/chat/completions";

        // Crear el cuerpo de la solicitud
        DeepSeekRequest request = new DeepSeekRequest();
        request.setModel("deepseek-chat");
        request.setMessages(List.of(new DeepSeekRequest.Message("user", "Dame 10 consejos para controlar las finanzas personales.")));
        request.setMax_tokens(500);
        request.setTemperature(0.7);

        // Configurar los headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + deepSeekConfig.getApiKey());
        headers.set("Content-Type", "application/json");

        // Crear la entidad HTTP
        HttpEntity<DeepSeekRequest> entity = new HttpEntity<>(request, headers);

        // Hacer la solicitud a la API de DeepSeek
        ResponseEntity<DeepSeekResponse> response = restTemplate.exchange(
                endpoint,
                HttpMethod.POST,
                entity,
                DeepSeekResponse.class
        );

        // Procesar la respuesta y mapearla al DTO
        DeepSeekResponse.Choice.Message message = response.getBody().getChoices().get(0).getMessage();
        String[] consejos = message.getContent().split("\n");

        ConsejosDTO consejosDTO = new ConsejosDTO();
        consejosDTO.setConsejo1(consejos[0]);
        consejosDTO.setConsejo2(consejos[1]);
        consejosDTO.setConsejo3(consejos[2]);
        consejosDTO.setConsejo4(consejos[3]);
        consejosDTO.setConsejo5(consejos[4]);
        consejosDTO.setConsejo6(consejos[5]);
        consejosDTO.setConsejo7(consejos[6]);
        consejosDTO.setConsejo8(consejos[7]);
        consejosDTO.setConsejo9(consejos[8]);
        consejosDTO.setConsejo10(consejos[9]);

        return consejosDTO;
    }

     */
}
