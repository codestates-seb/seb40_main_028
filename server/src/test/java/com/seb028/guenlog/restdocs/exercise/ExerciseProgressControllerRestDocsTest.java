package com.seb028.guenlog.restdocs.exercise;

import com.google.gson.Gson;
import com.seb028.guenlog.GuenLogApplication;
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
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExerciseProgressController.class)
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
    public void getExerciseProgressTest() throws Exception {
        // given
        // 테스트 데이터
        ExercisePlanRequestDto.EachRecords eachRecord = new ExercisePlanRequestDto.EachRecords();
        eachRecord.setWeight(50);
        eachRecord.setCount(12);
        eachRecord.setTimer(60);
        eachRecord.setEachCompleted(false);

        List<ExercisePlanRequestDto.EachRecords> eachRecords = new ArrayList<ExercisePlanRequestDto.EachRecords>();

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

        SingleResponseDto response = new SingleResponseDto(responseDto);

        Today today = Today.builder()
                .id(1L)
                .totalTime(60)
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

        // when
        ResultActions actions =
                mockMvc.perform(
                RestDocumentationRequestBuilders.get("/exercises/progress")
                                    .params(queryParams)
                                    .accept(MediaType.APPLICATION_JSON));

        // then
        actions
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "get-exerciseProgress",
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        Arrays.asList(
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터").optional(),
                                                fieldWithPath("data.todayUd").type(JsonFieldType.NUMBER).description("오늘 하루 운동 식별자"),
                                                fieldWithPath("data.totalTime").type(JsonFieldType.NUMBER).description("오늘 하루 운동 총 시간"),
                                                fieldWithPath("data.exercises").type(JsonFieldType.OBJECT).description("오늘 하루 운동 종류")
                                        )
                                )

                        )
                )
                .andReturn();
    }
}
