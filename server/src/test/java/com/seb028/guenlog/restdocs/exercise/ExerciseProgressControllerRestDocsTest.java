package com.seb028.guenlog.restdocs.exercise;

import com.google.gson.Gson;
import com.seb028.guenlog.config.SecurityConfig;
import com.seb028.guenlog.config.auth.JwtTokenizer;
import com.seb028.guenlog.config.auth.jwt.filter.JwtAuthenticationFilter;
import com.seb028.guenlog.config.auth.jwt.filter.JwtVerificationFilter;
import com.seb028.guenlog.exercise.controller.ExerciseProgressController;
import com.seb028.guenlog.exercise.dto.ExercisePlanRequestDto;
import com.seb028.guenlog.exercise.dto.ExerciseProgressResponseDto;
import com.seb028.guenlog.exercise.entity.Record;
import com.seb028.guenlog.exercise.entity.Today;
import com.seb028.guenlog.exercise.mapper.ExerciseProgressMapper;
import com.seb028.guenlog.exercise.service.ExerciseProgressService;
import com.seb028.guenlog.exercise.util.ExerciseProgress;
import com.seb028.guenlog.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.seb028.util.ApiDocumentUtils.getRequestPreprocessor;
import static com.seb028.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ExerciseProgressController.class,
    excludeFilters = {
        @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {SecurityConfig.class, JwtTokenizer.class,
                        JwtAuthenticationFilter.class, JwtVerificationFilter.class}
        )})
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class ExerciseProgressControllerRestDocsTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExerciseProgressService exerciseProgressService;

    @MockBean
    private MemberService memberService;

    @MockBean
    private ExerciseProgressMapper mapper;

    @Autowired
    private Gson gson;

    @Test
    @WithMockUser()
    public void getExerciseProgressTest() throws Exception {
        // <<< given >>>
        // 한 운동 세트에 대한 무게, 횟수, 휴식 시간, 세트 완료 여부 응답용 객체
        ExercisePlanRequestDto.EachRecords eachRecord = new ExercisePlanRequestDto.EachRecords();
        eachRecord.setWeight(50);
        eachRecord.setCount(12);
        eachRecord.setTimer(60);
        eachRecord.setEachCompleted(false);

        // 한 운동에 대한 각 세트 리스트 응답용 객체
        List<ExercisePlanRequestDto.EachRecords> eachRecords = new ArrayList<ExercisePlanRequestDto.EachRecords>();
        eachRecords.add(eachRecord);

        // 오늘 하루 운동 응답용 객체
        ExerciseProgressResponseDto.TodayExerciseDto todayExerciseDto = ExerciseProgressResponseDto.TodayExerciseDto.builder()
                .exerciseName("스쿼트 ")      // 운동 이름
                .exerciseId(1L)                  // 운동 ID
                .imageUrl("s3.somewhere")         // 운동이미지 URL
                .isCompleted(false)            // 운동 완료 여부
                .eachRecords(eachRecords)                   // 각 세트 리스트
                .build();

        // 오늘 하루 운동 리스트 응답용 객체
        List<ExerciseProgressResponseDto.TodayExerciseDto> exercises = new ArrayList<ExerciseProgressResponseDto.TodayExerciseDto>();
        exercises.add(todayExerciseDto);

        // 운동 진행 조회할 때 응답 DTO 객체
        ExerciseProgressResponseDto responseDto = ExerciseProgressResponseDto.builder()
                .todayId(1L)         // 오늘 하루 운동의 ID
                .totalTime(600)     // 총 운동 시간
                .exercises(exercises)         // 운동들 목록 리스트
                .build();

        // 오늘 하루 운동에 대한 객체
        Today today = Today.builder()
                .id(1L)                     // ID
                .totalTime(600)     // 총 운동 시간
                .build();

        // 오늘 하루 운동 기록에 대한 객체
        Record record = Record.builder()
                .today(today)                   // 오늘 하루 운동에 대한 객체
                .isCompleted(false)     // 운동 완료 여부
                .eachRecords(eachRecords)           // 각 세트 리스트
                .build();

        // 오늘 하루 운동 기록 리스트에 대한 객체
        List<Record> records = new ArrayList<Record>();
        records.add(record);

        // 운동 진행에 대한 객체
        ExerciseProgress exerciseProgress = ExerciseProgress.builder()
                .today(today)           // 오늘 하루 객체
                .records(records)       // 운동 기록 리스트 객체
                .build();

        long memberId = 1L;     // 사용자 ID

        String date = "2022-11-29";         // 운동 진행할 날짜
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();    // 쿼리 파라미터
        queryParams.add("date", date);

        // -- Stubbing --
        // memberService로 사용자 ID찾는 메서드 Stubbing
        given(memberService.findMemberId(
                Mockito.any(HttpServletRequest.class)))
                .willReturn(memberId);

        // 오늘 하루 운동진행에 대한 ExercisesProgress 객체 조회 메서드 Stubbing
        given(exerciseProgressService.findTodayExerciseProgress(
                Mockito.any(LocalDate.class), Mockito.anyLong()))
                .willReturn(exerciseProgress);

        // ExerciseProgress 객체를 응답용DTO로 변환하는 매퍼 Stubbing
        given(mapper.exerciseProgressToExerciseProgressResponseDto(
                Mockito.any(ExerciseProgress.class)))
                .willReturn(responseDto);

        // <<< when >>>
        ResultActions actions =
                mockMvc.perform(
                RestDocumentationRequestBuilders.get("/exercises/progress")     // GET 요청시
                        .header("Authorization", "Bearer {AccessToken}")                    // 헤더에 토큰 추가
                                    .params(queryParams)                                                   // 쿼리 파라미터 (운동 진행 날짜)
                                    .accept(MediaType.APPLICATION_JSON));

        // <<< then >>>
        MvcResult result =  actions
                .andExpect(status().isOk())
                .andDo(
                document(       // rest doc 생성
                    "get-exerciseProgress",
                                getRequestPreprocessor(),
                                getResponsePreProcessor(),
                    requestParameters(      // 쿼리 파라미터
                                        parameterWithName("date").description("운동 진행 날짜 (오늘)")
                                ),
                                requestHeaders(         // 헤더
                                        headerWithName("Authorization").description("Bearer + {로그인 요청 Access 토큰}")
                                ),
                                responseFields(         // 응답 필드
                                        Arrays.asList(
                                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("응답 성공 여부"),
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                                fieldWithPath("data.todayId").type(JsonFieldType.NUMBER).description("오늘 하루 운동 식별자"),
                                                fieldWithPath("data.totalTime").type(JsonFieldType.NUMBER).description("오늘 하루 운동 총 시간"),
                                                fieldWithPath("data.exercises[]").type(JsonFieldType.ARRAY).description("오늘 하루 운동 목록"),
                                                fieldWithPath("data.exercises[].exerciseId").type(JsonFieldType.NUMBER).description("운동 ID"),
                                                fieldWithPath("data.exercises[].isCompleted").type(JsonFieldType.BOOLEAN).description("운동 완료 여부"),
                                                fieldWithPath("data.exercises[].exerciseName").type(JsonFieldType.STRING).description("운동 이름"),
                                                fieldWithPath("data.exercises[].imageUrl").type(JsonFieldType.STRING).description("운동 이미지 URL"),
                                                fieldWithPath("data.exercises[].eachRecords").type(JsonFieldType.ARRAY).description("각 세트"),
                                                fieldWithPath("data.exercises[].eachRecords[].weight").type(JsonFieldType.NUMBER).description("무게"),
                                                fieldWithPath("data.exercises[].eachRecords[].count").type(JsonFieldType.NUMBER).description("횟수"),
                                                fieldWithPath("data.exercises[].eachRecords[].timer").type(JsonFieldType.NUMBER).description("휴식 시간"),
                                                fieldWithPath("data.exercises[].eachRecords[].eachCompleted").type(JsonFieldType.BOOLEAN).description("세트 완료 여부")
                                        )
                                )
                        )
                )
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }
}