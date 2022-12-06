package com.seb028.util;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

// 문서 스니핏 생성 전 request, response에 해당하는 문서 영역 전처리하는 클래스
public interface ApiDocumentUtils {
    // 문서에 표시되는 JSON 포맷의 request body를 예쁘게 표현
    static OperationRequestPreprocessor getRequestPreprocessor() {
        return preprocessRequest(prettyPrint());
    }

    // 문서에 표시되는 JSON 포맷의 response body를 예쁘게 표현
    static OperationResponsePreprocessor getResponsePreProcessor() {
        return preprocessResponse(prettyPrint());
    }

}
