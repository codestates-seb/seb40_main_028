package com.seb028.guenlog.restdocs.exercise;

import com.google.gson.Gson;
import com.seb028.guenlog.GuenLogApplication;
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
import com.seb028.guenlog.response.SingleResponseDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
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
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
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
        // given
        // 테스트 데이터
        ExercisePlanRequestDto.EachRecords eachRecord = new ExercisePlanRequestDto.EachRecords();
        eachRecord.setWeight(50);
        eachRecord.setCount(12);
        eachRecord.setTimer(60);
        eachRecord.setEachCompleted(false);

        List<ExercisePlanRequestDto.EachRecords> eachRecords = new ArrayList<ExercisePlanRequestDto.EachRecords>();
        eachRecords.add(eachRecord);

        ExerciseProgressResponseDto.TodayExerciseDto todayExerciseDto = ExerciseProgressResponseDto.TodayExerciseDto.builder()
                .exerciseName("스쿼트 ")
                .exerciseId(1L)
                .imageUrl("s3.somewhere")
                .isCompleted(false)
                .eachRecords(eachRecords)
                .build();

        List<ExerciseProgressResponseDto.TodayExerciseDto> exercises = new ArrayList<ExerciseProgressResponseDto.TodayExerciseDto>();
        exercises.add(todayExerciseDto);

        ExerciseProgressResponseDto responseDto = ExerciseProgressResponseDto.builder()
                .todayId(1L)
                .totalTime(600)
                .exercises(exercises)
                .build();

        Today today = Today.builder()
                .id(1L)
                .totalTime(600)
                .build();

        Record record = Record.builder()
                .today(today)
                .isCompleted(false)
                .eachRecords(eachRecords)
                .build();

        List<Record> records = new ArrayList<Record>();
        records.add(record);

        ExerciseProgress exerciseProgress = ExerciseProgress.builder()
                .today(today)
                .records(records)
                .build();

        long memberId = 1L;

        String date = "2022-11-29";
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("date", date);

        given(memberService.findMemberId(
                Mockito.any(HttpServletRequest.class)))
                .willReturn(memberId);

        given(exerciseProgressService.findTodayExerciseProgress(
                Mockito.any(LocalDate.class), Mockito.anyLong()))
                .willReturn(exerciseProgress);

        given(mapper.exerciseProgressToExerciseProgressResponseDto(
                Mockito.any(ExerciseProgress.class)))
                .willReturn(responseDto);

        // when
        ResultActions actions =
                mockMvc.perform(
                RestDocumentationRequestBuilders.get("/exercises/progress")
                                    .params(queryParams)
                                    .accept(MediaType.APPLICATION_JSON));

        // then
        MvcResult result =  actions
                .andExpect(status().isOk())
                .andDo(
                document(
                    "get-exerciseProgress",
                                getRequestPreprocessor(),
                                getResponsePreProcessor(),
                    requestParameters(
                                        parameterWithName("date").description("운동 진행 날짜 (오늘)")
                                ),
                                responseFields(
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