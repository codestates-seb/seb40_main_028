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
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
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
}
