package com.lucas.picpay.service;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AuthTransacaoServiceTest {

    @InjectMocks
    private AuthTransacaoService authTransacaoService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Testa quando a API retorna HTTP 200 e autorização true
    @Test
    public void testValidacaoTransicao_Authorized() {
        // Prepara o corpo da resposta
        Map<String, Object> data = new HashMap<>();
        data.put("authorization", true);

        Map<String, Object> body = new HashMap<>();
        body.put("data", data);

        ResponseEntity<Map> response = new ResponseEntity<>(body, HttpStatus.OK);

        // Configura o mock para retornar o response simulado
        when(restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class))
            .thenReturn(response);

        // Usa spy para substituir a criação do RestTemplate dentro do método
        AuthTransacaoService serviceSpy = Mockito.spy(authTransacaoService);
        doReturn(restTemplate).when(serviceSpy);

        Boolean result = serviceSpy.validacaoTransicao();

        assertThat(result);
    }

    // Testa quando a API retorna HTTP 200 e autorização false
    @Test
    public void testValidacaoTransicao_NotAuthorized() {
        Map<String, Object> data = new HashMap<>();
        data.put("authorization", false);

        Map<String, Object> body = new HashMap<>();
        body.put("data", data);

        ResponseEntity<Map> response = new ResponseEntity<>(body, HttpStatus.OK);

        when(restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class))
            .thenReturn(response);

        AuthTransacaoService serviceSpy = Mockito.spy(authTransacaoService);
        doReturn(restTemplate).when(serviceSpy);

        Boolean result = serviceSpy.validacaoTransicao();

        assert(result);
    }

    // Testa quando a API retorna erro HTTP (ex: 400)
    @Test
    public void testValidacaoTransicao_HttpError() {
        ResponseEntity<Map> response = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        when(restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class))
            .thenReturn(response);

        AuthTransacaoService serviceSpy = Mockito.spy(authTransacaoService);
        doReturn(restTemplate).when(serviceSpy);

        Boolean result = serviceSpy.validacaoTransicao();

        assertFalse(result);
    }

    // Testa quando ocorre exceção na chamada HTTP
    @Test
    public void testValidacaoTransicao_Exception() {
        when(restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class))
            .thenThrow(new RuntimeException("Erro na chamada"));

        AuthTransacaoService serviceSpy = Mockito.spy(authTransacaoService);
        doReturn(restTemplate).when(serviceSpy);

        Boolean result = serviceSpy.validacaoTransicao();

        assertFalse(result);
    }
}
