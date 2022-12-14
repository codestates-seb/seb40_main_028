package com.seb028.guenlog.restdocs.exercise;

import com.google.gson.Gson;
import com.seb028.guenlog.config.SecurityConfig;
import com.seb028.guenlog.config.auth.JwtTokenizer;
import com.seb028.guenlog.config.auth.jwt.filter.JwtAuthenticationFilter;
import com.seb028.guenlog.config.auth.jwt.filter.JwtVerificationFilter;
import com.seb028.guenlog.exercise.controller.ExercisePlanController;
import com.seb028.guenlog.exercise.dto.ExercisePlanRequestDto;
import com.seb028.guenlog.exercise.dto.ExercisePlanResponseDto;
import com.seb028.guenlog.exercise.entity.Category;
import com.seb028.guenlog.exercise.entity.Exercise;
import com.seb028.guenlog.exercise.entity.Record;
import com.seb028.guenlog.exercise.entity.Today;
import com.seb028.guenlog.exercise.mapper.ExercisePlanMapper;
import com.seb028.guenlog.exercise.repository.CategoryRepository;
import com.seb028.guenlog.exercise.repository.ExerciseRepository;
import com.seb028.guenlog.exercise.repository.RecordRepository;
import com.seb028.guenlog.exercise.repository.TodayRepository;
import com.seb028.guenlog.exercise.service.ExercisePlanService;
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
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ExercisePlanController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = {SecurityConfig.class, JwtTokenizer.class,
                                JwtAuthenticationFilter.class, JwtVerificationFilter.class}
                )})
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class ExercisePlanControllerRestDocsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExercisePlanService exercisePlanService;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private ExercisePlanMapper exercisePlanMapper;

    @MockBean
    private ExerciseRepository exerciseRepository;

    @MockBean
    private RecordRepository recordRepository;

    @MockBean
    private TodayRepository todayRepository;

    @MockBean
    private MemberService memberService;

    @Autowired
    private Gson gson;

    @Test
    @WithMockUser
    public void getExerciseCategoryTest() throws Exception{


        //given
        Exercise exercise = Exercise.builder()
                .id(7l)
                .name("?????? ?????? ????????????")
                .imageUrl("s3.png")
                .build();


        List<Exercise> exercises = new ArrayList<>();
        exercises.add(exercise);


        Category category = Category.builder()
                .id(1l)
                .name("??????")
                .imageUrl("s3.png")
                .exercises(exercises)
                .build();



        List<Category> categorys = new ArrayList<>();
        categorys.add(category);


        given(exercisePlanService.findCategory())
                .willReturn(categorys);



        // <<< when >>>
        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/exercises/category")     // GET ?????????
                                .header("Authorization", "Bearer {AccessToken}")                                                      // ?????? ???????????? (?????? ?????? ??????)
                                .accept(MediaType.APPLICATION_JSON));

        // <<< then >>>
        MvcResult result =  actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].id").value(categorys.get(0).getId()))
                .andExpect(jsonPath("$.data[0].name").value(categorys.get(0).getName()))
                .andExpect(jsonPath("$.data[0].imageUrl").value(categorys.get(0).getImageUrl()))
                .andExpect(jsonPath("$.data[0].exercises[0].id").value(categorys.get(0).getExercises().get(0).getId()))
                .andExpect(jsonPath("$.data[0].exercises[0].name").value((categorys.get(0).getExercises().get(0).getName())))
                .andExpect(jsonPath("$.data[0].exercises[0].imageUrl").value((categorys.get(0).getExercises().get(0).getImageUrl())))
                .andDo(
                        document(       // rest doc ??????
                                "get-exercise-category",
                                getRequestPreprocessor(),
                                getResponsePreProcessor(),
                                requestHeaders(         // ??????
                                        headerWithName("Authorization").description("Bearer + {????????? ?????? Access ??????}")
                                ),
                                responseFields(         // ?????? ??????
                                        Arrays.asList(
                                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("?????? ?????? ??????"),
                                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("?????? ?????????"),
                                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("???????????? ?????????"),
                                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("???????????? ???"),
                                                fieldWithPath("data[].imageUrl").type(JsonFieldType.STRING).description("???????????? ?????????"),
                                                fieldWithPath("data[].exercises[].id").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                                fieldWithPath("data[].exercises[].name").type(JsonFieldType.STRING).description("?????? ??????"),
                                                fieldWithPath("data[].exercises[].imageUrl").type(JsonFieldType.STRING).description("?????? ?????????")
                                        )
                                )
                        )
                )
                .andReturn();

    }

    @Test
    @WithMockUser
    public void getExerciseTodoTest() throws Exception {
        Long memberId = 1L;
        String dateDto = "2022-11-30";
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();    // ?????? ????????????
        queryParams.add("date", dateDto);

        // ??? ?????? ????????? ?????? ??????, ??????, ?????? ??????, ?????? ?????? ?????? ????????? ??????
        ExercisePlanRequestDto.EachRecords eachRecord = new ExercisePlanRequestDto.EachRecords();
        eachRecord.setWeight(50);
        eachRecord.setCount(12);
        eachRecord.setTimer(60);
        eachRecord.setEachCompleted(false);

        // ??? ????????? ?????? ??? ?????? ????????? ????????? ??????
        List<ExercisePlanRequestDto.EachRecords> eachRecords = new ArrayList<ExercisePlanRequestDto.EachRecords>();
        eachRecords.add(eachRecord);

        Today today = Today.builder()
                .id(1L)
                .build();

        Exercise exercise = Exercise.builder()
                .name("????????? ????????????")
                .imageUrl("s3.png")
                .build();

        Long recordId = 1L;
        Record record = Record.builder()
                .exercise(exercise)
                .today(today)
                .isCompleted(false)
                .eachRecords(eachRecords)
                .build();

        List<Record> records = new ArrayList<>();
        records.add(record);

        ExercisePlanResponseDto.RecordBaseResponseDto responseBaseDto = new ExercisePlanResponseDto.RecordBaseResponseDto();
        responseBaseDto.setRecordId(1L);
        responseBaseDto.setName("????????? ????????????");


        List<ExercisePlanResponseDto.RecordBaseResponseDto> responseDto = new ArrayList<>();
        responseDto.add(responseBaseDto);


        given(memberService.findMemberId(
                Mockito.any(HttpServletRequest.class)))
                .willReturn(memberId);

        given(exercisePlanService.findTodo(Mockito.any(LocalDate.class), Mockito.anyLong()))
                .willReturn(records);

        given(exercisePlanMapper.recordToRecordBaseResponseDto(
                Mockito.anyList()))
                .willReturn(responseDto);



        // <<< when >>>
        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/exercises/records")     // GET ?????????
                                .header("Authorization", "Bearer {AccessToken}")
                                .params(queryParams)       // ?????? ???????????? (?????? ?????? ??????)
                                .accept(MediaType.APPLICATION_JSON));

        // <<< then >>>
        MvcResult result =  actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].recordId").value(responseBaseDto.getRecordId()))
                .andExpect(jsonPath("$.data[0].name").value(responseBaseDto.getName()))
                .andDo(
                        document(       // rest doc ??????
                                "get-exercise-todo",
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
                                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("?????? ?????????"),
                                                fieldWithPath("data[].recordId").type(JsonFieldType.NUMBER).description("???????????? ?????????"),
                                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("???????????? ???")
                                        )
                                )
                        )
                )
                .andReturn();

    }

    @Test
    @WithMockUser
    public void getExerciseRecordTest() throws Exception {

        Long recordId = 1L;

        ExercisePlanRequestDto.EachRecords eachRecord = new ExercisePlanRequestDto.EachRecords();
        eachRecord.setWeight(50);
        eachRecord.setCount(12);
        eachRecord.setTimer(60);
        eachRecord.setEachCompleted(false);

        List<ExercisePlanRequestDto.EachRecords> eachRecords = new ArrayList<ExercisePlanRequestDto.EachRecords>();
        eachRecords.add(eachRecord);

        Exercise exercise = Exercise.builder()
                .id(1L)
                .imageUrl("s3.png")
                .name("??? ????????????")
                .build();


        Record records = Record.builder()
                .id(1L)
                .eachRecords(eachRecords)
                .exercise(exercise)
                .build();

        ExercisePlanResponseDto.RecordDetailResponseDto responseDto = new ExercisePlanResponseDto.RecordDetailResponseDto();
        responseDto.setExerciseId(records.getExercise().getId());
        responseDto.setName(records.getExercise().getName());
        responseDto.setImages(records.getExercise().getImageUrl());
        responseDto.setRecords(records.getEachRecords());

        given(exercisePlanService.findDetail(Mockito.anyLong()))
                .willReturn(records);

        given(exercisePlanMapper.recordToRecordDetailNameResponseDto(
                Mockito.any(Record.class)))
                .willReturn(responseDto);

        // <<< when >>>
        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/exercises/records/{record-id}",recordId)     // GET ?????????
                                .header("Authorization", "Bearer {AccessToken}")    // ?????? ???????????? (?????? ?????? ??????)
                                .accept(MediaType.APPLICATION_JSON));

        // <<< then >>>
        MvcResult result =  actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.exerciseId").value(responseDto.getExerciseId()))
                .andExpect(jsonPath("$.data.name").value(responseDto.getName()))
                .andExpect(jsonPath("$.data.images").value(responseDto.getImages()))
                .andExpect(jsonPath("$.data.records[0].weight").value(responseDto.getRecords().get(0).getWeight()))
                .andExpect(jsonPath("$.data.records[0].count").value(responseDto.getRecords().get(0).getCount()))
                .andExpect(jsonPath("$.data.records[0].timer").value(responseDto.getRecords().get(0).getTimer()))
                .andExpect(jsonPath("$.data.records[0].eachCompleted").value(responseDto.getRecords().get(0).getEachCompleted()))
                .andDo(
                        document(       // rest doc ??????
                                "get-exercise-record",
                                getRequestPreprocessor(),
                                getResponsePreProcessor(),
                                pathParameters(        // Path Parameter
                                        Arrays.asList(parameterWithName("record-id").description("?????? ?????? ?????? ?????? ID"))
                                ),
                                requestHeaders(         // ??????
                                        headerWithName("Authorization").description("Bearer + {????????? ?????? Access ??????}")
                                ),
                                responseFields(         // ?????? ??????
                                        Arrays.asList(
                                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("?????? ?????? ??????"),
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                                fieldWithPath("data.exerciseId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("?????? ??????"),
                                                fieldWithPath("data.images").type(JsonFieldType.STRING).description("?????? ?????????"),
                                                fieldWithPath("data.records[].weight").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                                fieldWithPath("data.records[].count").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                                fieldWithPath("data.records[].timer").type(JsonFieldType.NUMBER).description("?????? ????????????"),
                                                fieldWithPath("data.records[].eachCompleted").type(JsonFieldType.BOOLEAN).description("?????? ??????")
                                        )
                                )
                        )
                )
                .andReturn();

    }

    @Test
    @WithMockUser
    public void postExercisePlanTest() throws Exception {
        Long memberId = 1L;
        String dateDto = "2022-11-30";
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();    // ?????? ????????????
        queryParams.add("date", dateDto);

        // ??? ?????? ????????? ?????? ??????, ??????, ?????? ??????, ?????? ?????? ?????? ????????? ??????
        ExercisePlanRequestDto.EachRecords eachRecord = new ExercisePlanRequestDto.EachRecords();
        eachRecord.setWeight(50);
        eachRecord.setCount(12);
        eachRecord.setTimer(60);
        eachRecord.setEachCompleted(false);

        // ??? ????????? ?????? ??? ?????? ????????? ????????? ??????
        List<ExercisePlanRequestDto.EachRecords> eachRecords = new ArrayList<ExercisePlanRequestDto.EachRecords>();
        eachRecords.add(eachRecord);

        Today today = Today.builder()
                .id(1L)
                .build();

        Exercise exercise = Exercise.builder()
                .id(1L)
                .name("????????? ????????????")
                .imageUrl("s3.png")
                .build();

        Long recordId = 1L;
        Record record = Record.builder()
                .exercise(exercise)
                .today(today)
                .isCompleted(false)
                .eachRecords(eachRecords)
                .build();

        ExercisePlanResponseDto.RecordPostResponseDto  responseDto= new  ExercisePlanResponseDto.RecordPostResponseDto();
        responseDto.setRecordId(recordId);
        responseDto.setExerciseId(1L);
        responseDto.setName("????????? ????????????");
        responseDto.setRecords(eachRecords);


        ExercisePlanRequestDto.TodoPostDto todoPostDto = new ExercisePlanRequestDto.TodoPostDto();
        todoPostDto.setExerciseId(1L);
        todoPostDto.setEachRecords(eachRecords);

        String content = gson.toJson(todoPostDto);


        given(memberService.findMemberId(
                Mockito.any(HttpServletRequest.class)))
                .willReturn(memberId);

        given(exercisePlanService.createRecord(Mockito.any(LocalDate.class),Mockito.anyLong(),Mockito.any(ExercisePlanRequestDto.TodoPostDto.class)))
                .willReturn(record);

        given(exercisePlanMapper.recordToRecordPostResponseDto(
                Mockito.any(Record.class)))
                .willReturn(responseDto);

        // <<< when >>>
        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/exercises/records")
                                .header("Authorization", "Bearer {AccessToken}")
                                .params(queryParams)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                                .with(csrf())
                );



        // <<< then >>>
        MvcResult result =  actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.recordId").value(responseDto.getRecordId()))
                .andExpect(jsonPath("$.data.exerciseId").value(responseDto.getRecordId()))
                .andExpect(jsonPath("$.data.name").value(responseDto.getName()))
                .andExpect(jsonPath("$.data.records[0].weight").value(responseDto.getRecords().get(0).getWeight()))
                .andExpect(jsonPath("$.data.records[0].count").value(responseDto.getRecords().get(0).getCount()))
                .andExpect(jsonPath("$.data.records[0].timer").value(responseDto.getRecords().get(0).getTimer()))
                .andExpect(jsonPath("$.data.records[0].eachCompleted").value(responseDto.getRecords().get(0).getEachCompleted()))
                .andDo(
                        document(       // rest doc ??????
                                "post-exercise-record",
                                getRequestPreprocessor(),
                                getResponsePreProcessor(),
                                requestParameters(      // ?????? ????????????
                                        parameterWithName("date").description("?????? ?????? ?????? (??????)"),
                                        parameterWithName("_csrf").description("csrf").ignored()
                                ),
                                requestHeaders(         // ??????
                                        headerWithName("Authorization").description("Bearer + {????????? ?????? Access ??????}")
                                ),
                                requestFields(                  // ?????? ??????
                                        Arrays.asList(
                                                fieldWithPath("exerciseId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                                fieldWithPath("eachRecords").type(JsonFieldType.ARRAY).description("??? ??????"),
                                                fieldWithPath("eachRecords[].weight").type(JsonFieldType.NUMBER).description("??????"),
                                                fieldWithPath("eachRecords[].count").type(JsonFieldType.NUMBER).description("??????"),
                                                fieldWithPath("eachRecords[].timer").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                                fieldWithPath("eachRecords[].eachCompleted").type(JsonFieldType.BOOLEAN).description("?????? ?????? ??????")
                                        )
                                ),
                                responseFields(         // ?????? ??????
                                        Arrays.asList(
                                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("?????? ?????? ??????"),
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                                fieldWithPath("data.recordId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                                fieldWithPath("data.exerciseId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("?????? ??????"),
                                                fieldWithPath("data.records[].weight").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                                fieldWithPath("data.records[].count").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                                fieldWithPath("data.records[].timer").type(JsonFieldType.NUMBER).description("?????? ????????????"),
                                                fieldWithPath("data.records[].eachCompleted").type(JsonFieldType.BOOLEAN).description("?????? ??????")
                                        )
                                )
                        )
                )
                .andReturn();

    }
    @Test
    @WithMockUser
    public void patchExercisePlanTest() throws Exception {
        Long recordId = 1L;

        // ??? ?????? ????????? ?????? ??????, ??????, ?????? ??????, ?????? ?????? ?????? ????????? ??????
        ExercisePlanRequestDto.EachRecords eachRecord = new ExercisePlanRequestDto.EachRecords();
        eachRecord.setWeight(50);
        eachRecord.setCount(12);
        eachRecord.setTimer(60);
        eachRecord.setEachCompleted(false);

        // ??? ????????? ?????? ??? ?????? ????????? ????????? ??????
        List<ExercisePlanRequestDto.EachRecords> eachRecords = new ArrayList<ExercisePlanRequestDto.EachRecords>();
        eachRecords.add(eachRecord);

        Today today = Today.builder()
                .id(1L)
                .build();

        Exercise exercise = Exercise.builder()
                .id(1L)
                .name("????????? ????????????")
                .imageUrl("s3.png")
                .build();

        Record record = Record.builder()
                .exercise(exercise)
                .today(today)
                .isCompleted(false)
                .eachRecords(eachRecords)
                .build();

        ExercisePlanResponseDto.RecordPostResponseDto  responseDto= new  ExercisePlanResponseDto.RecordPostResponseDto();
        responseDto.setRecordId(recordId);
        responseDto.setExerciseId(1L);
        responseDto.setName("????????? ????????????");
        responseDto.setRecords(eachRecords);


        ExercisePlanRequestDto.TodoPostDto todoPostDto = new ExercisePlanRequestDto.TodoPostDto();
        todoPostDto.setExerciseId(1L);
        todoPostDto.setEachRecords(eachRecords);

        String content = gson.toJson(todoPostDto);

        given(exercisePlanService.updateRecord(Mockito.anyLong(),Mockito.any(ExercisePlanRequestDto.TodoPostDto.class)))
                .willReturn(record);

        given(exercisePlanMapper.recordToRecordPostResponseDto(
                Mockito.any(Record.class)))
                .willReturn(responseDto);

        // <<< when >>>
        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.patch("/exercises/records/{record-id}",recordId)
                                .header("Authorization", "Bearer {AccessToken}")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                                .with(csrf())
                );


        // <<< then >>>
        MvcResult result =  actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.recordId").value(responseDto.getRecordId()))
                .andExpect(jsonPath("$.data.exerciseId").value(responseDto.getRecordId()))
                .andExpect(jsonPath("$.data.name").value(responseDto.getName()))
                .andExpect(jsonPath("$.data.records[0].weight").value(responseDto.getRecords().get(0).getWeight()))
                .andExpect(jsonPath("$.data.records[0].count").value(responseDto.getRecords().get(0).getCount()))
                .andExpect(jsonPath("$.data.records[0].timer").value(responseDto.getRecords().get(0).getTimer()))
                .andExpect(jsonPath("$.data.records[0].eachCompleted").value(responseDto.getRecords().get(0).getEachCompleted()))
                .andDo(
                        document(       // rest doc ??????
                                "patch-exercise-record",
                                getRequestPreprocessor(),
                                getResponsePreProcessor(),
                                pathParameters(        // Path Parameter
                                        Arrays.asList(parameterWithName("record-id").description("?????? ?????? ?????? ?????? ID"))
                                ),
                                requestParameters(      // ?????? ????????????
                                        parameterWithName("_csrf").description("csrf").ignored()
                                ),
                                requestHeaders(         // ??????
                                        headerWithName("Authorization").description("Bearer + {????????? ?????? Access ??????}")
                                ),
                                requestFields(                  // ?????? ??????
                                        Arrays.asList(
                                                fieldWithPath("exerciseId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                                fieldWithPath("eachRecords").type(JsonFieldType.ARRAY).description("??? ??????"),
                                                fieldWithPath("eachRecords[].weight").type(JsonFieldType.NUMBER).description("??????"),
                                                fieldWithPath("eachRecords[].count").type(JsonFieldType.NUMBER).description("??????"),
                                                fieldWithPath("eachRecords[].timer").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                                fieldWithPath("eachRecords[].eachCompleted").type(JsonFieldType.BOOLEAN).description("?????? ?????? ??????")
                                        )
                                ),
                                responseFields(         // ?????? ??????
                                        Arrays.asList(
                                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("?????? ?????? ??????"),
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                                fieldWithPath("data.recordId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                                fieldWithPath("data.exerciseId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("?????? ??????"),
                                                fieldWithPath("data.records[].weight").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                                fieldWithPath("data.records[].count").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                                fieldWithPath("data.records[].timer").type(JsonFieldType.NUMBER).description("?????? ????????????"),
                                                fieldWithPath("data.records[].eachCompleted").type(JsonFieldType.BOOLEAN).description("?????? ??????")
                                        )
                                )
                        )
                )
                .andReturn();



    }



    @Test
    @WithMockUser
    public void deleteExercisePlanTest() throws Exception {
        long recordId = 1L;



        doNothing().when(exercisePlanService).deleteTodo(Mockito.anyLong());

        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.delete("/exercises/records/{record-id}",recordId)
                                .header("Authorization", "Bearer {AccessToken}")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                );


        // then
        actions.andExpect(status().isNoContent())
                .andExpect(jsonPath("$.success").value(true))
                .andDo(document(       // (9)
                                "delete-exercise-record",     // (9-1)
                                getRequestPreprocessor(),
                                getResponsePreProcessor(),
                                requestHeaders(    //request header
                                        headerWithName("Authorization").description("Bearer + {????????? ?????? Access ??????}")
                                ),
                                pathParameters(              // (1)
                                        parameterWithName("record-id").description("?????? ?????????")
                                ),
                                responseFields(     //response body??? ????????? ?????????
                                        List.of(
                                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("??????"),
                                                fieldWithPath("data").type(JsonFieldType.STRING).description("?????????")
                                        )
                                )
                        )
                );


    }
}
