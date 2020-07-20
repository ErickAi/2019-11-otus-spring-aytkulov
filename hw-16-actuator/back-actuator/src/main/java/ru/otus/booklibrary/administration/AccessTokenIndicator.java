package ru.otus.booklibrary.administration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.otus.booklibrary.config.properites.AppProperties;
import ru.otus.booklibrary.config.properites.AppUrls;
import ru.otus.booklibrary.dto.response.Jwt;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties({AppProperties.class, AppUrls.class})
public class AccessTokenIndicator implements HealthIndicator {

    private final AppProperties appProps;
    private final AppUrls appUrls;

    @Override
    public Health health() {
        Map<String, Object> details = new LinkedHashMap<>();
        String accessToken;
        boolean isCheckTokenEndpointAccessible;
        boolean isCheckTokenKeyAccessible;
        boolean isCheckUserInfoAccessible;
        try {
            accessToken = requestJwt().getAccessToken();
            isCheckTokenEndpointAccessible = checkAccessToken(accessToken);
            isCheckTokenKeyAccessible = checkTokenKey(accessToken);
            isCheckUserInfoAccessible = checkUserInfo(accessToken);
            log.info(accessToken);
        } catch (Exception e) {
            return Health.outOfService().withException(e).build();
        }

        details.put("/oauth/token", "ACCESSIBLE");
        details.put("     with user", appProps.getAdminLogin());
        details.put("/oauth/check_token", "ACCESSIBLE");
        details.put("/oauth/token_key", "ACCESSIBLE");
        details.put("/userinfo", "ACCESSIBLE");

        if (accessToken != null && isCheckTokenEndpointAccessible
                && isCheckTokenKeyAccessible && isCheckUserInfoAccessible) {
            return Health.up()
                    .withDetails(details).build();
        }
        return Health.outOfService()
                .withDetails(details).build();
    }

    private Jwt requestJwt() {
        RestTemplate template = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("client_id", appProps.getUiClientId());
        body.add("client_secret", appProps.getUiClientSecret());
        body.add("username", appProps.getAdminLogin());
        body.add("password", appProps.getAdminPassword());

        HttpEntity<?> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Jwt> responseEntity = template.exchange(appUrls.getOauthToken(),
                HttpMethod.POST,
                entity,
                Jwt.class
        );
        return responseEntity.getBody();
    }

    private boolean checkAccessToken(String accessToken) {
        RestTemplate template = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(appProps.getUiClientId(), appProps.getUiClientSecret());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(appUrls.getOauthCheckToken())
                .queryParam("token", accessToken);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = template.exchange(uriBuilder.toUriString(),
                HttpMethod.POST,
                entity,
                String.class
        );

        String body = responseEntity.getBody();
        return body != null && body.contains("client_id");
    }

    private boolean checkTokenKey(String accessToken) {
        RestTemplate template = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = template.exchange(appUrls.getOauthToken_key(),
                HttpMethod.GET,
                entity,
                String.class
        );
        String body = responseEntity.getBody();

        return body != null && body.contains("alg") && body.contains("value");
    }

    private boolean checkUserInfo(String accessToken) {
        RestTemplate template = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = template.exchange(appUrls.getUserInfo(),
                HttpMethod.GET,
                entity,
                String.class
        );
        String body = responseEntity.getBody();

        return body != null && body.contains("email");
    }
}
