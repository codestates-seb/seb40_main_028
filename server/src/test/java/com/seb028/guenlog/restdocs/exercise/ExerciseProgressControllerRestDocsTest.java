package com.seb028.guenlog.restdocs.exercise;

import com.google.gson.Gson;
import com.seb028.guenlog.config.SecurityConfig;
import com.seb028.guenlog.config.auth.JwtTokenizer;
import com.seb028.guenlog.config.auth.jwt.filter.JwtAuthenticationFilter;
import com.seb028.guenlog.config.auth.jwt.filter.JwtVerificationFilter;
import com.seb028.guenlog.exercise.controller.ExerciseProgressController;
import com.seb028.guenlog.exercise.dto.ExercisePlanRequestDto;
import com.seb028.guenlog.exercise.dto.ExerciseProgressPatchDto;
import com.seb028.guenlog.exercise.dto.ExerciseProgressResponseDto;
import com.seb028.guenlog.exercise.entity.Record;
import com.seb028.guenlog.exercise.entity.Today;
import com.seb028.guenlog.exercise.mapper.ExerciseProgressMapper;
import com.seb028.guenlog.exercise.service.ExerciseProgressService;
import com.seb028.guenlog.exercise.util.ExerciseProgress;
import com.seb028.guenlog.member.entity.Member;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    public void get_exercise_progress_test() throws Exception {
        // <<< given >>>
        // ??? ?????? ????????? ?????? ??????, ??????, ?????? ??????, ?????? ?????? ?????? ????????? ??????
        ExercisePlanRequestDto.EachRecords eachRecord = new ExercisePlanRequestDto.EachRecords();
        eachRecord.setWeight(50);
        eachRecord.setCount(12);
        eachRecord.setTimer(60);
        eachRecord.setEachCompleted(false);

        // ??? ????????? ?????? ??? ?????? ????????? ????????? ??????
        List<ExercisePlanRequestDto.EachRecords> eachRecords = new ArrayList<ExercisePlanRequestDto.EachRecords>();
        eachRecords.add(eachRecord);

        // ?????? ?????? ?????? ????????? ??????
        ExerciseProgressResponseDto.TodayExerciseDto todayExerciseDto = ExerciseProgressResponseDto.TodayExerciseDto.builder()
                .exerciseName("????????? ")      // ?????? ??????
                .exerciseId(1L)                  // ?????? ID
                .imageUrl("s3.somewhere")         // ??????????????? URL
                .isCompleted(false)            // ?????? ?????? ??????
                .eachRecords(eachRecords)                   // ??? ?????? ?????????
                .build();

        // ?????? ?????? ?????? ????????? ????????? ??????
        List<ExerciseProgressResponseDto.TodayExerciseDto> exercises = new ArrayList<ExerciseProgressResponseDto.TodayExerciseDto>();
        exercises.add(todayExerciseDto);

        // ?????? ?????? ????????? ??? ?????? DTO ??????
        ExerciseProgressResponseDto responseDto = ExerciseProgressResponseDto.builder()
                .todayId(1L)         // ?????? ?????? ????????? ID
                .totalTime(600)     // ??? ?????? ??????
                .exercises(exercises)         // ????????? ?????? ?????????
                .build();

        // ?????? ?????? ????????? ?????? ??????
        Today today = Today.builder()
                .id(1L)                     // ID
                .totalTime(600)     // ??? ?????? ??????
                .build();

        // ?????? ?????? ?????? ????????? ?????? ??????
        Record record = Record.builder()
                .today(today)                   // ?????? ?????? ????????? ?????? ??????
                .isCompleted(false)     // ?????? ?????? ??????
                .eachRecords(eachRecords)           // ??? ?????? ?????????
                .build();

        // ?????? ?????? ?????? ?????? ???????????? ?????? ??????
        List<Record> records = new ArrayList<Record>();
        records.add(record);

        // ?????? ????????? ?????? ??????
        ExerciseProgress exerciseProgress = ExerciseProgress.builder()
                .today(today)           // ?????? ?????? ??????
                .records(records)       // ?????? ?????? ????????? ??????
                .build();

        long memberId = 1L;     // ????????? ID

        String date = "2022-11-29";         // ?????? ????????? ??????
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();    // ?????? ????????????
        queryParams.add("date", date);

        // -- Stubbing --
        // memberService??? ????????? ID?????? ????????? Stubbing
        given(memberService.findMemberId(
                Mockito.any(HttpServletRequest.class)))
                .willReturn(memberId);

        // ?????? ?????? ??????????????? ?????? ExercisesProgress ?????? ?????? ????????? Stubbing
        given(exerciseProgressService.findTodayExerciseProgress(
                Mockito.any(LocalDate.class), Mockito.anyLong()))
                .willReturn(exerciseProgress);

        // ExerciseProgress ????????? ?????????DTO??? ???????????? ?????? Stubbing
        given(mapper.exerciseProgressToExerciseProgressResponseDto(
                Mockito.any(ExerciseProgress.class)))
                .willReturn(responseDto);

        // <<< when >>>
        ResultActions actions =
                mockMvc.perform(
                RestDocumentationRequestBuilders.get("/exercises/progress")     // GET ?????????
                        .header("Authorization", "Bearer {AccessToken}")                    // ????????? ?????? ??????
                                    .params(queryParams)                                                   // ?????? ???????????? (?????? ?????? ??????)
                                    .accept(MediaType.APPLICATION_JSON));

        // <<< then >>>
        MvcResult result =  actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.todayId").value(responseDto.getTodayId()))
                .andExpect(jsonPath("$.data.totalTime").value(responseDto.getTotalTime()))
                .andExpect(jsonPath("$.data.exercises").isArray())
                .andExpect(jsonPath("$.data.exercises[0].exerciseId")
                        .value(responseDto.getExercises().get(0).getExerciseId()))
                .andExpect(jsonPath("$.data.exercises[0].isCompleted")
                        .value(responseDto.getExercises().get(0).getIsCompleted()))
                .andExpect(jsonPath("$.data.exercises[0].exerciseName")
                        .value(responseDto.getExercises().get(0).getExerciseName()))
                .andExpect(jsonPath("$.data.exercises[0].imageUrl")
                        .value(responseDto.getExercises().get(0).getImageUrl()))
                .andExpect(jsonPath("$.data.exercises[0].eachRecords[0].weight")
                        .value(responseDto.getExercises().get(0).getEachRecords().get(0).getWeight()))
                .andExpect(jsonPath("$.data.exercises[0].eachRecords[0].count")
                        .value(responseDto.getExercises().get(0).getEachRecords().get(0).getCount()))
                .andExpect(jsonPath("$.data.exercises[0].eachRecords[0].timer")
                        .value(responseDto.getExercises().get(0).getEachRecords().get(0).getTimer()))
                .andExpect(jsonPath("$.data.exercises[0].eachRecords[0].eachCompleted")
                        .value(responseDto.getExercises().get(0).getEachRecords().get(0).getEachCompleted()))
                .andDo(
                document(       // rest doc ??????
                    "get-exercise-progress",
                                getRequestPreprocessor(),
                                getResponsePreProcessor(),
                    requestParameters(      // ?????? ????????????
                                        parameterWithName("date").description("?????? ?????? ?????? (??????)")
                                ),
                                requestHeaders(         // ??????
                                        headerWithName("Authorization").description("Bearer + {????????? ?????? Access ??????}")
                                ),
                                responseFields(         // ?????? ??????
                                        Arrays.asList(
                                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("?????? ?????? ??????"),
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                                fieldWithPath("data.todayId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????????"),
                                                fieldWithPath("data.totalTime").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ??? ??????"),
                                                fieldWithPath("data.exercises[]").type(JsonFieldType.ARRAY).description("?????? ?????? ?????? ??????"),
                                                fieldWithPath("data.exercises[].exerciseId").type(JsonFieldType.NUMBER).description("?????? ID"),
                                                fieldWithPath("data.exercises[].isCompleted").type(JsonFieldType.BOOLEAN).description("?????? ?????? ??????"),
                                                fieldWithPath("data.exercises[].exerciseName").type(JsonFieldType.STRING).description("?????? ??????"),
                                                fieldWithPath("data.exercises[].imageUrl").type(JsonFieldType.STRING).description("?????? ????????? URL"),
                                                fieldWithPath("data.exercises[].eachRecords").type(JsonFieldType.ARRAY).description("??? ??????"),
                                                fieldWithPath("data.exercises[].eachRecords[].weight").type(JsonFieldType.NUMBER).description("??????"),
                                                fieldWithPath("data.exercises[].eachRecords[].count").type(JsonFieldType.NUMBER).description("??????"),
                                                fieldWithPath("data.exercises[].eachRecords[].timer").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                                fieldWithPath("data.exercises[].eachRecords[].eachCompleted").type(JsonFieldType.BOOLEAN).description("?????? ?????? ??????")
                                        )
                                )
                        )
                )
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser()
    // ?????? ?????? ?????? ????????? ?????????
    public void patch_exercise_progress_test() throws Exception {
        // <<< given >>>
        // ??? ?????? ????????? ?????? ??????, ??????, ?????? ??????, ?????? ?????? ?????? ????????? ??????
        ExercisePlanRequestDto.EachRecords eachRecord = new ExercisePlanRequestDto.EachRecords();
        eachRecord.setWeight(50);           // ?????? ??????
        eachRecord.setCount(12);            // ?????? ??????
        eachRecord.setTimer(60);            // ?????? ?????? ??????
        eachRecord.setEachCompleted(true);  // ?????? ???????????? ?????? ??????

        // ??? ????????? ?????? ??? ?????? ????????? ????????? ??????
        List<ExercisePlanRequestDto.EachRecords> eachRecords = new ArrayList<ExercisePlanRequestDto.EachRecords>();
        eachRecords.add(eachRecord);

        // ?????? ?????? ??? ????????? ?????? ????????? ??????
        Long exerciseId = 1L;
        ExerciseProgressPatchDto.TodayExerciseDto todayExerciseDto = ExerciseProgressPatchDto.TodayExerciseDto.builder()
                .exerciseId(exerciseId)              // ?????? ID ??????
                .isCompleted(true)      // ?????????????????? ?????? ??????
                .eachRecords(eachRecords)            // ??? ?????? ??????
                .build();

        // ?????? ?????? ?????? ???????????? ?????? ????????? ??????
        List<ExerciseProgressPatchDto.TodayExerciseDto> exercises = new ArrayList<>();
        exercises.add(todayExerciseDto);

        Integer totalTime = 3600;       // ??? ?????? ??????

        // ?????? ?????? ????????? ?????? Patch DTO ??????
        ExerciseProgressPatchDto exerciseProgressPatchDto = ExerciseProgressPatchDto.builder()
                .totalTime(totalTime)       // ??? ???????????? ??????
                .exercises(exercises)       // ?????? ?????? ?????? ?????? ??????
                .build();

        // PatchDto ????????? JSON?????? ??????
        String content = gson.toJson(exerciseProgressPatchDto);

        // ????????? ??????
        long memberId = 1L;
        Member member = Member.builder()
                .id(memberId)
                .build();

        // ?????? ?????? ????????? ?????? ??????
        Long todayId = 1L;
        Today today = Today.builder()
                .id(todayId)                // ?????? ?????? ?????? ID
                .totalTime(3600)     // ??? ?????? ??????
                .member(member)                 // ?????????
                .build();

        // ?????? ?????? ?????? ????????? ?????? ??????
        Record record = Record.builder()
                .today(today)                   // ?????? ?????? ????????? ?????? ??????
                .isCompleted(true)     // ?????? ?????? ??????
                .eachRecords(eachRecords)           // ??? ?????? ?????????
                .build();

        // ?????? ?????? ?????? ?????? ???????????? ?????? ??????
        List<Record> records = new ArrayList<Record>();
        records.add(record);

        // ?????? ????????? ?????? ??????
        ExerciseProgress exerciseProgress = ExerciseProgress.builder()
                .today(today)           // ?????? ?????? ??????
                .records(records)       // ?????? ?????? ????????? ??????
                .build();

        // -- Stubbing --
        // memberService??? ????????? ID?????? ????????? Stubbing
        given(memberService.findMemberId(
                Mockito.any(HttpServletRequest.class)))
                .willReturn(memberId);
        // ?????? ?????? ?????? Patch DTO ????????? ExerciseProgress????????? ?????? ????????? Stubbing
        given(mapper.exerciseProgressPatchDtoToExerciseProgress(
                Mockito.any(ExerciseProgressPatchDto.class)))
                .willReturn(exerciseProgress);
        // ExerciseProgressService?????? ?????? ?????? ?????? ?????????????????? ????????? Stubbing
        given(exerciseProgressService.updateExerciseProgress(
                Mockito.anyLong(), Mockito.any(ExerciseProgress.class)))
                .willReturn(exerciseProgress);

        // <<< when >>>
        ResultActions actions =
                mockMvc.perform(
                RestDocumentationRequestBuilders
                                    .patch("/exercises/progress/{today-id}", todayId)   // PATCH ?????????
                                    .header("Authorization", "Bearer {AccessToken}")              // ????????? ?????? ??????
                                    .accept(MediaType.APPLICATION_JSON)                             // ?????????????????? JSON ?????? ??????
                                    .contentType(MediaType.APPLICATION_JSON)                        //  ??????????????? ??????????????? JSON?????? ??????
                                    .content(content)                                                            // request body ??????
                                    .with(csrf()));                                                // CSRF ??????

        // <<< then >>>
        MvcResult result = actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andDo(
                        document(   // Rest Docs ??????
                                "patch-exercises-progress",
                                getRequestPreprocessor(),
                                getResponsePreProcessor(),
                                pathParameters(        // Path Parameter
                                        Arrays.asList(parameterWithName("today-id").description("?????? ?????? ?????? ?????? ????????? ID"))
                                ),
                                requestHeaders(                 // ??????
                                        headerWithName("Authorization").description("Bearer + {????????? ?????? Access ??????}")
                                ),
                                requestFields(                  // ?????? ??????
                                        Arrays.asList(
                                                fieldWithPath("totalTime").type(JsonFieldType.NUMBER)
                                                        .description("?????? ?????? ?????? ??? ??????"),
                                                fieldWithPath("exercises[]").type(JsonFieldType.ARRAY)
                                                        .description("?????? ?????? ?????? ??????"),
                                                fieldWithPath("exercises[].exerciseId").type(JsonFieldType.NUMBER)
                                                        .description("?????? ID"),
                                                fieldWithPath("exercises[].isCompleted").type(JsonFieldType.BOOLEAN)
                                                        .description("?????? ?????? ??????"),
                                                fieldWithPath("exercises[].eachRecords").type(JsonFieldType.ARRAY)
                                                        .description("??? ??????"),
                                                fieldWithPath("exercises[].eachRecords[].weight").type(JsonFieldType.NUMBER)
                                                        .description("??????"),
                                                fieldWithPath("exercises[].eachRecords[].count").type(JsonFieldType.NUMBER)
                                                        .description("??????"),
                                                fieldWithPath("exercises[].eachRecords[].timer").type(JsonFieldType.NUMBER)
                                                        .description("?????? ??????"),
                                                fieldWithPath("exercises[].eachRecords[].eachCompleted").type(JsonFieldType.BOOLEAN)
                                                        .description("?????? ?????? ??????")
                                        )
                                ),
                                responseFields(                 // ?????? ??????
                                        Arrays.asList(
                                                fieldWithPath("success").type(JsonFieldType.BOOLEAN)
                                                        .description("?????? ?????? ??????"),
                                                fieldWithPath("data").type(JsonFieldType.NULL)
                                                        .description("?????? ?????????")
                                        )
                                )

                        )
                )
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }
}