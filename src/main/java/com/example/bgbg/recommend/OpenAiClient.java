package com.example.bgbg.recommend;

import com.example.bgbg.code.ErrorCode;
import com.example.bgbg.exception.GlobalException;
import com.example.bgbg.recommend.dto.request.GptRequest;
import com.example.bgbg.recommend.dto.request.Message;
import com.example.bgbg.recommend.dto.response.GptResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OpenAiClient {

    private final RestTemplate restTemplate;

    @Value("${openai.api.model}")
    private String model;

    @Value("${openai.api.url}")
    private String url;

    @Value("${openai.api.secret-key}")
    private String secretKey;

    // 사용자 질문을 모델에 전달하고 응답 받기
    public GptResponse getChatCompletion(String prompt) {

        // 요청 구성
        GptRequest request = getGptRequest(prompt);

        // 디버깅: 요청 내용 로그
        log.info("OpenAI 요청 - model: {}, messages size: {}", request.getModel(), request.getMessages().size());
        log.info("OpenAI URL: {}", url);

        // JSON 직렬화 확인을 위한 로그
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonRequest = mapper.writeValueAsString(request);
            log.info("실제 전송되는 JSON: {}", jsonRequest);
        } catch (Exception e) {
            log.error("JSON 직렬화 실패", e);
        }

        // HttpHeaders 명시적 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + secretKey);

        // HttpEntity로 요청 래핑
        HttpEntity<GptRequest> entity = new HttpEntity<>(request, headers);

        // RestTemplate을 통해 OpenAI API POST 요청 전송
        ResponseEntity<GptResponse> response = restTemplate.postForEntity(
            url,
            entity,
            GptResponse.class
        );

        // 응답 실패 처리
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            log.error("OpenAI API 요청 실패");
            throw new GlobalException(ErrorCode.OPENAI_API_FAILED);
        }

        // 성공 시 응답 본문 반환
        return response.getBody();

    }

    // OPENAI 요청 구성
    private GptRequest getGptRequest(String prompt) {

        // system 메세지 작성 : AI 역할 지시
        Message systemMessage = new Message(
            "system",
            "당신은 요리 재료 추천 전문가입니다. 사용자가 요리 메뉴를 입력하면, 그 메뉴를 만들기 위해 필요한 재료 목록을 제공해주세요. " +
            "각 재료는 다음 카테고리 중 어디에 속하는지 분류하여 답변해주세요: VEGETABLES_FRUITS, MEAT, SEAFOOD, EGGS_DAIRY, GRAINS_NUTS, SEASONINGS, FROZEN_FOOD, PROCESSED_FOOD, BEVERAGES_ALCOHOL, ETC. " +
            "위에서 정의한 카테고리 이외의 카테고리로 절대 분류하지 말아주세요. 만약 적당한 카테고리가 없다면 ETC로 분류해주세요." +
                "각 재료는 한줄로 간단명료하게 나열해 주세요: 계란: EGGS_DAIRY, 상추: VEGETABLES_FRUITS" +
            "선택사항인 재료들은 추천하지말고 필수 재료로만 추천해주세요." +
            "다른 코멘트 없이 위 응답 방식으로만 한줄로만 응답해주세요.");

        // user 메세지 작성 : 사용자 질문
        Message userMessage = new Message("user", prompt);

        // 메세 리스트에 system -> user 순으로 담기
        List<Message> messages = List.of(systemMessage, userMessage);

        // 모델 이름과 메세지를 포함한 요청 객체 생성
        return new GptRequest(model, messages);

    }


}
