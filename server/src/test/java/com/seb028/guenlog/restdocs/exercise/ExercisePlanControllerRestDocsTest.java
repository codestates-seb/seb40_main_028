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
                .name("덤벨 라잉 익스텐션")
                .imageUrl("s3.png")
                .build();


        List<Exercise> exercises = new ArrayList<>();
        exercises.add(exercise);


        Category category = Category.builder()
                .id(1l)
                .name("상체")
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
                        RestDocumentationRequestBuilders.get("/exercises/category")     // GET 요청시
                                .header("Authorization", "Bearer {AccessToken}")                                                      // 쿼리 파라미터 (운동 진행 날짜)
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
                        document(       // rest doc 생성
                                "get-exercise-category",
                                getRequestPreprocessor(),
                                getResponsePreProcessor(),
                                requestHeaders(         // 헤더
                                        headerWithName("Authorization").description("Bearer + {로그인 요청 Access 토큰}")
                                ),
                                responseFields(         // 응답 필드
                                        Arrays.asList(
                                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("응답 성공 여부"),
                                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("응답 데이터"),
                                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("카테고리 아이디"),
                                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("카테고리 명"),
                                                fieldWithPath("data[].imageUrl").type(JsonFieldType.STRING).description("카테고리 이미지"),
                                                fieldWithPath("data[].exercises[].id").type(JsonFieldType.NUMBER).description("운동 아이디"),
                                                fieldWithPath("data[].exercises[].name").type(JsonFieldType.STRING).description("운동 이름"),
                                                fieldWithPath("data[].exercises[].imageUrl").type(JsonFieldType.STRING).description("운동 이미지")
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
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();    // 쿼리 파라미터
        queryParams.add("date", dateDto);

        // 한 운동 세트에 대한 무게, 횟수, 휴식 시간, 세트 완료 여부 응답용 객체
        ExercisePlanRequestDto.EachRecords eachRecord = new ExercisePlanRequestDto.EachRecords();
        eachRecord.setWeight(50);
        eachRecord.setCount(12);
        eachRecord.setTimer(60);
        eachRecord.setEachCompleted(false);

        // 한 운동에 대한 각 세트 리스트 응답용 객체
        List<ExercisePlanRequestDto.EachRecords> eachRecords = new ArrayList<ExercisePlanRequestDto.EachRecords>();
        eachRecords.add(eachRecord);

        Today today = Today.builder()
                .id(1L)
                .build();

        Exercise exercise = Exercise.builder()
                .name("러시안 트위스트")
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
        responseBaseDto.setName("러시안 트위스트");


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
                        RestDocumentationRequestBuilders.get("/exercises/records")     // GET 요청시
                                .header("Authorization", "Bearer {AccessToken}")
                                .params(queryParams)       // 쿼리 파라미터 (운동 진행 날짜)
                                .accept(MediaType.APPLICATION_JSON));

        // <<< then >>>
        MvcResult result =  actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].recordId").value(responseBaseDto.getRecordId()))
                .andExpect(jsonPath("$.data[0].name").value(responseBaseDto.getName()))
                .andDo(
                        document(       // rest doc 생성
                                "get-exercise-todo",
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
                                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("응답 데이터"),
                                                fieldWithPath("data[].recordId").type(JsonFieldType.NUMBER).description("카테고리 아이디"),
                                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("카테고리 명")
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
                .name("백 익스텐션")
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
                        RestDocumentationRequestBuilders.get("/exercises/records/{record-id}",recordId)     // GET 요청시
                                .header("Authorization", "Bearer {AccessToken}")    // 쿼리 파라미터 (운동 진행 날짜)
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
                        document(       // rest doc 생성
                                "get-exercise-record",
                                getRequestPreprocessor(),
                                getResponsePreProcessor(),
                                pathParameters(        // Path Parameter
                                        Arrays.asList(parameterWithName("record-id").description("오늘 운동 계획 선택 ID"))
                                ),
                                requestHeaders(         // 헤더
                                        headerWithName("Authorization").description("Bearer + {로그인 요청 Access 토큰}")
                                ),
                                responseFields(         // 응답 필드
                                        Arrays.asList(
                                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("응답 성공 여부"),
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                                fieldWithPath("data.exerciseId").type(JsonFieldType.NUMBER).description("운동 아이디"),
                                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("운동 이름"),
                                                fieldWithPath("data.images").type(JsonFieldType.STRING).description("운동 이미지"),
                                                fieldWithPath("data.records[].weight").type(JsonFieldType.NUMBER).description("운동 무게"),
                                                fieldWithPath("data.records[].count").type(JsonFieldType.NUMBER).description("운동 횟수"),
                                                fieldWithPath("data.records[].timer").type(JsonFieldType.NUMBER).description("운동 휴식시간"),
                                                fieldWithPath("data.records[].eachCompleted").type(JsonFieldType.BOOLEAN).description("완료 여부")
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
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();    // 쿼리 파라미터
        queryParams.add("date", dateDto);

        // 한 운동 세트에 대한 무게, 횟수, 휴식 시간, 세트 완료 여부 응답용 객체
        ExercisePlanRequestDto.EachRecords eachRecord = new ExercisePlanRequestDto.EachRecords();
        eachRecord.setWeight(50);
        eachRecord.setCount(12);
        eachRecord.setTimer(60);
        eachRecord.setEachCompleted(false);

        // 한 운동에 대한 각 세트 리스트 응답용 객체
        List<ExercisePlanRequestDto.EachRecords> eachRecords = new ArrayList<ExercisePlanRequestDto.EachRecords>();
        eachRecords.add(eachRecord);

        Today today = Today.builder()
                .id(1L)
                .build();

        Exercise exercise = Exercise.builder()
                .id(1L)
                .name("러시안 트위스트")
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
        responseDto.setName("러시안 트위스트");
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
                        document(       // rest doc 생성
                                "post-exercise-record",
                                getRequestPreprocessor(),
                                getResponsePreProcessor(),
                                requestParameters(      // 쿼리 파라미터
                                        parameterWithName("date").description("운동 진행 날짜 (오늘)"),
                                        parameterWithName("_csrf").description("csrf").ignored()
                                ),
                                requestHeaders(         // 헤더
                                        headerWithName("Authorization").description("Bearer + {로그인 요청 Access 토큰}")
                                ),
                                requestFields(                  // 요청 필드
                                        Arrays.asList(
                                                fieldWithPath("exerciseId").type(JsonFieldType.NUMBER).description("운동 아이디"),
                                                fieldWithPath("eachRecords").type(JsonFieldType.ARRAY).description("각 세트"),
                                                fieldWithPath("eachRecords[].weight").type(JsonFieldType.NUMBER).description("무게"),
                                                fieldWithPath("eachRecords[].count").type(JsonFieldType.NUMBER).description("횟수"),
                                                fieldWithPath("eachRecords[].timer").type(JsonFieldType.NUMBER).description("휴식 시간"),
                                                fieldWithPath("eachRecords[].eachCompleted").type(JsonFieldType.BOOLEAN).description("세트 완료 여부")
                                        )
                                ),
                                responseFields(         // 응답 필드
                                        Arrays.asList(
                                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("응답 성공 여부"),
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                                fieldWithPath("data.recordId").type(JsonFieldType.NUMBER).description("기록 아이디"),
                                                fieldWithPath("data.exerciseId").type(JsonFieldType.NUMBER).description("운동 아이디"),
                                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("운동 이름"),
                                                fieldWithPath("data.records[].weight").type(JsonFieldType.NUMBER).description("운동 무게"),
                                                fieldWithPath("data.records[].count").type(JsonFieldType.NUMBER).description("운동 횟수"),
                                                fieldWithPath("data.records[].timer").type(JsonFieldType.NUMBER).description("운동 휴식시간"),
                                                fieldWithPath("data.records[].eachCompleted").type(JsonFieldType.BOOLEAN).description("완료 여부")
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

        // 한 운동 세트에 대한 무게, 횟수, 휴식 시간, 세트 완료 여부 응답용 객체
        ExercisePlanRequestDto.EachRecords eachRecord = new ExercisePlanRequestDto.EachRecords();
        eachRecord.setWeight(50);
        eachRecord.setCount(12);
        eachRecord.setTimer(60);
        eachRecord.setEachCompleted(false);

        // 한 운동에 대한 각 세트 리스트 응답용 객체
        List<ExercisePlanRequestDto.EachRecords> eachRecords = new ArrayList<ExercisePlanRequestDto.EachRecords>();
        eachRecords.add(eachRecord);

        Today today = Today.builder()
                .id(1L)
                .build();

        Exercise exercise = Exercise.builder()
                .id(1L)
                .name("러시안 트위스트")
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
        responseDto.setName("러시안 트위스트");
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
                        document(       // rest doc 생성
                                "patch-exercise-record",
                                getRequestPreprocessor(),
                                getResponsePreProcessor(),
                                pathParameters(        // Path Parameter
                                        Arrays.asList(parameterWithName("record-id").description("오늘 운동 계획 선택 ID"))
                                ),
                                requestParameters(      // 쿼리 파라미터
                                        parameterWithName("_csrf").description("csrf").ignored()
                                ),
                                requestHeaders(         // 헤더
                                        headerWithName("Authorization").description("Bearer + {로그인 요청 Access 토큰}")
                                ),
                                requestFields(                  // 요청 필드
                                        Arrays.asList(
                                                fieldWithPath("exerciseId").type(JsonFieldType.NUMBER).description("운동 아이디"),
                                                fieldWithPath("eachRecords").type(JsonFieldType.ARRAY).description("각 세트"),
                                                fieldWithPath("eachRecords[].weight").type(JsonFieldType.NUMBER).description("무게"),
                                                fieldWithPath("eachRecords[].count").type(JsonFieldType.NUMBER).description("횟수"),
                                                fieldWithPath("eachRecords[].timer").type(JsonFieldType.NUMBER).description("휴식 시간"),
                                                fieldWithPath("eachRecords[].eachCompleted").type(JsonFieldType.BOOLEAN).description("세트 완료 여부")
                                        )
                                ),
                                responseFields(         // 응답 필드
                                        Arrays.asList(
                                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("응답 성공 여부"),
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                                fieldWithPath("data.recordId").type(JsonFieldType.NUMBER).description("기록 아이디"),
                                                fieldWithPath("data.exerciseId").type(JsonFieldType.NUMBER).description("운동 아이디"),
                                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("운동 이름"),
                                                fieldWithPath("data.records[].weight").type(JsonFieldType.NUMBER).description("운동 무게"),
                                                fieldWithPath("data.records[].count").type(JsonFieldType.NUMBER).description("운동 횟수"),
                                                fieldWithPath("data.records[].timer").type(JsonFieldType.NUMBER).description("운동 휴식시간"),
                                                fieldWithPath("data.records[].eachCompleted").type(JsonFieldType.BOOLEAN).description("완료 여부")
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
                                        headerWithName("Authorization").description("Bearer + {로그인 요청 Access 토큰}")
                                ),
                                pathParameters(              // (1)
                                        parameterWithName("record-id").description("기록 아이디")
                                ),
                                responseFields(     //response body에 포함된 데이터
                                        List.of(
                                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("결과"),
                                                fieldWithPath("data").type(JsonFieldType.STRING).description("데이터")
                                        )
                                )
                        )
                );


    }
}
