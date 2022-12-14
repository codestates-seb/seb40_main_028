package com.seb028.guenlog.restdocs.exercise;

import com.google.gson.Gson;
import com.seb028.guenlog.config.SecurityConfig;
import com.seb028.guenlog.config.auth.JwtTokenizer;
import com.seb028.guenlog.config.auth.jwt.filter.JwtAuthenticationFilter;
import com.seb028.guenlog.config.auth.jwt.filter.JwtVerificationFilter;
import com.seb028.guenlog.exercise.controller.ExerciseMainController;
import com.seb028.guenlog.exercise.dto.CalendarResponseDto;
import com.seb028.guenlog.exercise.dto.ExerciseMainResponseDto;
import com.seb028.guenlog.exercise.entity.Exercise;
import com.seb028.guenlog.exercise.entity.Record;
import com.seb028.guenlog.exercise.entity.Today;
import com.seb028.guenlog.exercise.mapper.ExerciseMainMapper;
import com.seb028.guenlog.exercise.mapper.ExercisePlanMapper;
import com.seb028.guenlog.exercise.repository.CategoryRepository;
import com.seb028.guenlog.exercise.repository.ExerciseRepository;
import com.seb028.guenlog.exercise.repository.RecordRepository;
import com.seb028.guenlog.exercise.repository.TodayRepository;
import com.seb028.guenlog.exercise.service.ExerciseMainService;
import com.seb028.guenlog.exercise.service.ExercisePlanService;
import com.seb028.guenlog.member.entity.Member;
import com.seb028.guenlog.member.entity.MemberWeight;
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
import java.sql.Date;
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
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ExerciseMainController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = {SecurityConfig.class, JwtTokenizer.class,
                                JwtAuthenticationFilter.class, JwtVerificationFilter.class}
                )})
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class ExerciseMainControllerRestDocsTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

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
    private ExerciseMainService exerciseMainService;

    @MockBean
    private ExerciseMainMapper exerciseMainMapper;

    @MockBean
    private MemberService memberService;

    class CalendarResponseImpl implements CalendarResponseDto {

        Long todayId;

        Date dueDate;

        Integer completed;

        public CalendarResponseImpl(Long todayId, Date dueDate, Integer completed) {
            this.todayId = todayId;
            this.dueDate = dueDate;
            this.completed = completed;
        }

        @Override
        public Long getTodayId() {
            return todayId;
        }

        @Override
        public Date getDueDate() {
            return dueDate;
        }

        @Override
        public Integer getCompleted() {
            return completed;
        }
    }

    @Test
    @WithMockUser
    public void getExerciseCalendarTest() throws Exception{

        Long memberId = 1L;
        String date = "2022-11";
        Date duedate = Date.valueOf("2022-11-10");
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();    // ?????? ????????????
        queryParams.add("date", date);

        String email = "example@example.com";       // ????????? ?????????
        String password = "expassword!123";         // ????????? ????????????
        String nickName = "???????????????";             // ????????? ?????????
        String age = "2000-11-11";                  // ????????? ????????????
        Integer height = 185;                       // ????????? ???
        Character gender = 'M';                     // ????????? ??????
        List<MemberWeight> weights = new ArrayList<>();     // ????????? ????????? ?????? ?????????

        // ????????? Member ??????
        Member member = Member.builder()
                .id(memberId)       // ????????? ??????
                .email(email)           // ????????? ??????
                .password(password)     // ???????????? ??????
                .nickname(nickName)     // ????????? ??????
                .age(LocalDate.parse(age))  // ???????????? ??????
                .gender(gender)         // ?????? ??????
                .weights(weights)       // ????????? ????????? ??????
                .build();

        Exercise exercise = Exercise.builder()
                .id(1L)
                .imageUrl("s3.png")
                .name("??? ????????????")
                .build();

        Today today = Today.builder()
                .id(1L)
                .dueDate(LocalDate.parse("2022-11-10"))
                .totalTime(300)
                .member(member)
                .build();

        Record record = Record.builder()
                .id(1L)
                .isCompleted(true)
                .exercise(exercise)
                .today(today)
                .build();

        ExerciseMainResponseDto.CalendarDto calendarResponseDto = new ExerciseMainResponseDto.CalendarDto(1L, duedate.toLocalDate(),1);

        List<ExerciseMainResponseDto.CalendarDto> calendarResponseDtos = new ArrayList<>();
        calendarResponseDtos.add(calendarResponseDto);





        given(memberService.findMemberId(
                Mockito.any(HttpServletRequest.class)))
                .willReturn(memberId);

        given(exerciseMainService.findCalendar(Mockito.any(String.class),Mockito.anyLong()))
                .willReturn(calendarResponseDtos);

        System.out.println(calendarResponseDtos.get(0).getDueDate()+"11");

        // <<< when >>>
        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/exercises/calendar")     // GET ?????????
                                .header("Authorization", "Bearer {AccessToken}")
                                .params(queryParams)   // ?????? ???????????? (?????? ?????? ??????)
                                .accept(MediaType.APPLICATION_JSON));

        // <<< then >>>
        MvcResult result =  actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].dueDate").value(calendarResponseDtos.get(0).getDueDate().toString()))
                .andExpect(jsonPath("$.data[0].completed").value(calendarResponseDtos.get(0).getCompleted()))
                .andExpect(jsonPath("$.data[0].todayId").value(calendarResponseDtos.get(0).getTodayId()))
                .andDo(
                        document(       // rest doc ??????
                                "get-exercise-calendar",
                                getRequestPreprocessor(),
                                getResponsePreProcessor(),
                                requestParameters(      // ?????? ????????????
                                        parameterWithName("date").description("?????? ?????? ???")
                                ),
                                requestHeaders(         // ??????
                                        headerWithName("Authorization").description("Bearer + {????????? ?????? Access ??????}")
                                ),
                                responseFields(         // ?????? ??????
                                        Arrays.asList(
                                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("?????? ?????? ??????"),
                                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("?????? ?????????"),
                                                fieldWithPath("data[].dueDate").type(JsonFieldType.STRING).description("??????"),
                                                fieldWithPath("data[].completed").type(JsonFieldType.NUMBER).description("????????????"),
                                                fieldWithPath("data[].todayId").type(JsonFieldType.NUMBER).description("????????? ?????????")
                                        )
                                )
                        )
                )
                .andReturn();


    }

    @Test
    @WithMockUser
    public void getExerciseDetailCalendarTest() throws Exception {
        Long todayId = 1L;
        Integer timer = 300;

        Today today = Today.builder()
                .id(1L)
                .totalTime(300)
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
                .build();

        List<Record> records = new ArrayList<>();
        records.add(record);

        ExerciseMainResponseDto.ExerciseDto exerciseDto = new ExerciseMainResponseDto.ExerciseDto();
        exerciseDto.setExerciseId(record.getExercise().getId());
        exerciseDto.setExerciseName(record.getExercise().getName());
        exerciseDto.setIsComleted(record.getIsCompleted());

        List<ExerciseMainResponseDto.ExerciseDto> exerciseDtos = new ArrayList<>();
        exerciseDtos.add(exerciseDto);



        ExerciseMainResponseDto.RecordDetailDto recordDetailDto = new ExerciseMainResponseDto.RecordDetailDto();
        recordDetailDto.setTotalTime(300);
        recordDetailDto.setExercises(exerciseDtos);


        given(exerciseMainService.findTimer(Mockito.anyLong()))
                .willReturn(timer);

        given(exerciseMainService.findExercise(Mockito.anyLong()))
                .willReturn(records);

        given(exerciseMainMapper.recordToExerciseDto(Mockito.anyList()))
                .willReturn(exerciseDtos);

        given(exerciseMainMapper.exercisesDtoToRecordDetailDto(Mockito.anyList(),Mockito.anyInt()))
                .willReturn(recordDetailDto);


        // <<< when >>>
        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/exercises/calendar/detail/{today-id}",todayId)
                                .header("Authorization", "Bearer {AccessToken}")
                                .accept(MediaType.APPLICATION_JSON)
                );



        // <<< then >>>
        MvcResult result =  actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.totalTime").value(recordDetailDto.getTotalTime()))
                .andExpect(jsonPath("$.data.exercises[0].exerciseId").value(recordDetailDto.getExercises().get(0).getExerciseId()))
                .andExpect(jsonPath("$.data.exercises[0].exerciseName").value(recordDetailDto.getExercises().get(0).getExerciseName()))
                .andExpect(jsonPath("$.data.exercises[0].isComleted").value(recordDetailDto.getExercises().get(0).getIsComleted()))
                .andDo(
                        document(       // rest doc ??????
                                "get-exercise-detail",
                                getRequestPreprocessor(),
                                getResponsePreProcessor(),
                                pathParameters(        // Path Parameter
                                        Arrays.asList(parameterWithName("today-id").description("?????? ?????? ?????? ID"))
                                ),
                                requestHeaders(         // ??????
                                        headerWithName("Authorization").description("Bearer + {????????? ?????? Access ??????}")
                                ),
                                responseFields(         // ?????? ??????
                                        Arrays.asList(
                                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("?????? ?????? ??????"),
                                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                                fieldWithPath("data.totalTime").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                                fieldWithPath("data.exercises[].exerciseId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                                fieldWithPath("data.exercises[].exerciseName").type(JsonFieldType.STRING).description("?????? ??????"),
                                                fieldWithPath("data.exercises[].isComleted").type(JsonFieldType.BOOLEAN).description("?????? ?????? ??????")
                                        )
                                )
                        )
                )
                .andReturn();


    }
}
