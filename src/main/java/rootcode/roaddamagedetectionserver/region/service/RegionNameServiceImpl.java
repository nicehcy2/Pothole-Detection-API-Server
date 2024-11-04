package rootcode.roaddamagedetectionserver.region.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

// TODO: 포트홀을 저장하는 로직이 테스트 하고 싶은거면 카카오맵 호출해서 지역받아오는건 부차적인거니, 그냥 가짜 서비스
// TODO: 정말 카카오 이 클래스 테스트 하고 싶은거면
// TODO: 스프링의 value로 받아오는 거기때문에 테스트가 힘들다
@Service
@RequiredArgsConstructor
public class RegionNameServiceImpl implements RegionNameService {
    private final RestTemplate restTemplate;

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    @Override
    public String getAddressFromCoordinates(double latitude, double longitude) {

        String url = buildRequestUrl(latitude, longitude);
        HttpHeaders headers = createHttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            return extractAddressFromResponse(response).orElse("Address not found");
        } catch (Exception e) {
            return "Address not found";
        }
    }

    @Override
    public String extractRegionBeforeSecondSpace(String fullRegionName) {

        String[] parts = fullRegionName.split("\\s+"); // 최대 3개의 부분으로 분리
        if (parts.length < 2) {
            throw new IllegalArgumentException();
        }

        return parts[0] + " " + parts[1];
    }

    public static String extractRegionFirstPart(String fullRegionName) {

        String[] parts = fullRegionName.split("\\s+"); // 최대 3개의 부분으로 분리
        if (parts.length < 2) {
            throw new IllegalArgumentException();
        }
        return parts[0];
    }

    public static String extractRegionSecondPart(String fullRegionName) {

        String[] parts = fullRegionName.split("\\s+"); // 최대 3개의 부분으로 분리
        if (parts.length < 2) {
            throw new IllegalArgumentException();
        }
        return parts[1];
    }


    // URL 생성 메소드
    private String buildRequestUrl(double latitude, double longitude) {

        return String.format("https://dapi.kakao.com/v2/local/geo/coord2address.json?x=%s&y=%s", longitude, latitude);
    }

    // HTTP 헤더 생성 메소드
    private HttpHeaders createHttpHeaders() {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoApiKey);
        return headers;
    }

    // API 응답에서 주소 추출 메소드
    private Optional<String> extractAddressFromResponse(ResponseEntity<Map> response) {

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            Map<String, Object> responseBody = response.getBody();
            List<Map<String, Object>> documents = (List<Map<String, Object>>) responseBody.get("documents");

            if (!documents.isEmpty()) {
                Map<String, Object> addressData = (Map<String, Object>) documents.get(0).get("address");
                return Optional.ofNullable((String) addressData.get("address_name"));
            }
        }
        return Optional.empty();
    }
}
