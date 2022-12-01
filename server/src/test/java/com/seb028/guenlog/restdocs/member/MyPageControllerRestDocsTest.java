package com.seb028.guenlog.restdocs.member;

import com.google.gson.Gson;
import com.seb028.guenlog.config.SecurityConfig;
import com.seb028.guenlog.config.auth.JwtTokenizer;
import com.seb028.guenlog.config.auth.jwt.filter.JwtAuthenticationFilter;
import com.seb028.guenlog.config.auth.jwt.filter.JwtVerificationFilter;
import com.seb028.guenlog.member.controller.MyPageController;
import com.seb028.guenlog.member.dto.MyPageInfoDto;
import com.seb028.guenlog.member.dto.MyPageResponseDto;
import com.seb028.guenlog.member.dto.PasswordPatchDto;
import com.seb028.guenlog.member.entity.Member;
import com.seb028.guenlog.member.entity.MemberWeight;
import com.seb028.guenlog.member.mapper.MyPageInfoMapper;
import com.seb028.guenlog.member.mapper.MyPageMapper;
import com.seb028.guenlog.member.service.MemberService;
import com.seb028.guenlog.member.service.MemberWeightService;
import com.seb028.guenlog.member.service.MyPageService;
import com.seb028.guenlog.member.util.MonthlyRecord;
import com.seb028.guenlog.member.util.MonthlyWeight;
import com.seb028.guenlog.member.util.MyPage;
import com.seb028.guenlog.member.util.MyPageInfo;
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

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.seb028.util.ApiDocumentUtils.getRequestPreprocessor;
import static com.seb028.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MyPageController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = {SecurityConfig.class, JwtTokenizer.class,
                                JwtAuthenticationFilter.class, JwtVerificationFilter.class}
                )})
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class MyPageControllerRestDocsTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MyPageService myPageService;

    @MockBean
    private MemberWeightService memberWeightService;

    @MockBean
    private MyPageInfoMapper myPageInfoMapper;

    @MockBean
    private MyPageMapper myPageMapper;

    // 인터페이스 MonthlyRecord를 구현한 MonthlyRecordImpl 클래스
    class MonthlyRecordImpl implements MonthlyRecord {
        String dates;
        Integer record;

        public MonthlyRecordImpl(String dates, Integer record) {
            this.dates = dates;
            this.record = record;
        }

        public String getDates() {
            return dates;
        }

        public Integer getRecord() {
            return record;
        }
    }

    // 인터페이스 MonthlyWeight를 구현한 MonthlyWeightImpl 클래스
    class MonthlyWeightImpl implements MonthlyWeight {
        String dates;
        Double weight;

        public MonthlyWeightImpl(String dates, Double weight) {
            this.dates = dates;
            this.weight = weight;
        }

        public String getDates() {
            return dates;
        }

        public Double getWeight() {
            return weight;
        }
    }


    @Test
    @WithMockUser
    public void get_my_page_test() throws Exception{
        // <<< given >>>
        // -- MyPageService에서 리턴하는 운동 기록, 몸무게 기록이 저장된 MyPage객체
        String dates = "2022-11-30";        // 조회하고자 하는 월
        Integer record = 30;                // 해당 월 운동 횟수
        Double weight = 75.8;               // 해당 월 몸무게 평균

        // 해당 월에 대한 운동 횟수가 담긴 월별 기록 객체 생성
        MonthlyRecord monthlyRecord = new MonthlyRecordImpl(dates, record);

        // 월별 운동 횟수가 담긴 월별 기록 객체 리스트 생성
        List<MonthlyRecord> monthlyRecords = new ArrayList<>();
        monthlyRecords.add(monthlyRecord);

        // 해당 월에 대한 몸무게 평균이 담긴 월별 몸무게 기록 객체 생성
        MonthlyWeight monthlyWeight = new MonthlyWeightImpl(dates, weight);

        // 월별 몸무게 평균 기록이 담긴 월별 몸무게 기록 객체 리스트 생성
        List<MonthlyWeight> monthlyWeights = new ArrayList<>();
        monthlyWeights.add(monthlyWeight);

        // MyPage 객체 생성
        MyPage myPage = MyPage.builder()
                .monthlyRecords(monthlyRecords)     // 월별 운동 횟수 기록 리스트 설정
                .monthlyWeights(monthlyWeights)     // 월별 몸무게 평균 기록 리스트 설정
                .build();


        // MyPage객체를 통해 월별 기록들 반환하는 ResponseDTO 객체
        String dateDto = "2022-11-30";          // 조회하고자 하는 월
        Integer recordDto = 30;                 // 해당 월 운동횟수
        Double weightDto = 75.8;                // 해당 월 몸무게 평균

        // 해당 월에 대한 운동 횟수가 담긴 월별 기록 DTO 객체 생성
        MyPageResponseDto.MonthlyRecordDto monthlyRecordDto = MyPageResponseDto.MonthlyRecordDto.builder()
                .date(dateDto)
                .record(recordDto)
                .build();

        // 월별 운동 횟수가 담긴 월별 기록 DTO 객체 리스트 생성
        List<MyPageResponseDto.MonthlyRecordDto> monthlyRecordDtos = new ArrayList<>();
        monthlyRecordDtos.add(monthlyRecordDto);

        // 해당 월에 대한 몸무게 평균이 담긴 월별 몸무게 기록 DTO 객체 생성
        MyPageResponseDto.MonthlyWeightDto monthlyWeightDto = MyPageResponseDto.MonthlyWeightDto.builder()
                .date(dateDto)
                .weight(weightDto)
                .build();

        // 월별 몸무게 평균 기록이 담긴 월별 몸무게 기록 DTO 객체 리스트 생성
        List<MyPageResponseDto.MonthlyWeightDto> monthlyWeightDtos = new ArrayList<>();
        monthlyWeightDtos.add(monthlyWeightDto);

        // MyPage객체에 대한 ResponseDTO객체 생성
        MyPageResponseDto myPageResponseDto = MyPageResponseDto.builder()
                .monthlyRecords(monthlyRecordDtos)
                .monthlyWeights(monthlyWeightDtos)
                .build();

        long memberId = 1L;     // 사용자 ID

        // -- Stubbing --
        // memberService로 사용자 ID찾는 메서드 Stubbing
        given(memberService.findMemberId(
                Mockito.any(HttpServletRequest.class)))
                .willReturn(memberId);

        // myPageService에서 사용자의 마이페이지 기록 조회 메서드 Stubbing
        given(myPageService.findMyPage(
                Mockito.anyLong()))
                .willReturn(myPage);

        // MyPage객체를 ResponseDTO로 변환해주는 Mapper의 메서드 Stubbing
        given(myPageMapper.myPageToMyPageResponseDto(
                Mockito.any(MyPage.class))).
                willReturn(myPageResponseDto);

        // <<< when >>>
        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/users/mypages")      // GET 요청시
                                .header("Authorization", "Bearer {AccessToken}")                // 헤더에 토큰 추가
                                .accept(MediaType.APPLICATION_JSON));

        // <<< then >>>
        MvcResult result = actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.monthlyRecords[0].date")
                        .value(myPageResponseDto.getMonthlyRecords().get(0).getDate()))
                .andExpect(jsonPath("$.data.monthlyRecords[0].record")
                        .value(myPageResponseDto.getMonthlyRecords().get(0).getRecord()))
                .andExpect(jsonPath("$.data.monthlyWeights[0].date")
                        .value(myPageResponseDto.getMonthlyWeights().get(0).getDate()))
                .andExpect(jsonPath("$.data.monthlyWeights[0].weight")
                        .value(myPageResponseDto.getMonthlyWeights().get(0).getWeight()))
                .andDo(
                        document(       // rest doc 생성
                                "get-mypages",
                                getRequestPreprocessor(),
                                getResponsePreProcessor(),
                                requestHeaders(         // 헤더
                                        headerWithName("Authorization").description("Bearer + {로그인 요청 Access 토큰}")
                                ),
                                responseFields(
                                        Arrays.asList(
                                                fieldWithPath("success").type(JsonFieldType.BOOLEAN)
                                                        .description("응답 성공 여부"),
                                                fieldWithPath("data").type(JsonFieldType.OBJECT)
                                                        .description("응답 데이터"),
                                                fieldWithPath("data.monthlyRecords[]").type(JsonFieldType.ARRAY)
                                                        .description("월별 운동 기록"),
                                                fieldWithPath("data.monthlyRecords[].date").type(JsonFieldType.STRING)
                                                        .description("해당 월"),
                                                fieldWithPath("data.monthlyRecords[].record").type(JsonFieldType.NUMBER)
                                                        .description("해당 월 운동 총 횟수"),
                                                fieldWithPath("data.monthlyWeights[]").type(JsonFieldType.ARRAY)
                                                        .description("월별 몸무게 평균 기록"),
                                                fieldWithPath("data.monthlyWeights[].date").type(JsonFieldType.STRING)
                                                        .description("해당 월"),
                                                fieldWithPath("data.monthlyWeights[].weight").type(JsonFieldType.NUMBER)
                                                        .description("해당 월 몸무게 평균")
                                        )
                                )
                        )
                )
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());

    }

    @Test
    @WithMockUser
    public void get_my_page_info() throws Exception{

        // <<< given >>>
        // Service에서 사용하는 엔티티 객체
        Long memberId = 1L;                         // 사용자 아이디
        String email = "example@example.com";       // 사용자 이메일
        String password = "expassword!123";         // 사용자 비밀번호
        String nickName = "닉네임예시";             // 사용자 닉네임
        String age = "2000-11-11";                  // 사용자 생년월일
        Integer height = 185;                       // 사용자 키
        Character gender = 'M';                     // 사용자 성별
        List<MemberWeight> weights = new ArrayList<>();     // 사용자 몸무게 기록 리스트

        // 사용자 Member 객체
        Member member = Member.builder()
                .id(memberId)       // 아이디 설정
                .email(email)           // 이메일 설정
                .password(password)     // 비밀번호 설정
                .nickname(nickName)     // 닉네임 설정
                .age(LocalDate.parse(age))  // 생년월일 설정
                .gender(gender)         // 성별 설정
                .weights(weights)       // 몸무게 리스트 설정
                .build();

        Long weightId = 1L;         // 몸무게 기록 객체 아이디
        Integer weight = 70;        // 몸무게
        // 사용자 몸무게 기록 MemberWeight 객체
        MemberWeight memberWeight = MemberWeight.builder()
                .id(weightId)       // 몸무게 기록 아이디 설정
                .weight(weight)         // 몸무게 설정
                .member(member)         // 사용자 설정
                .build();

        // Member객체에 몸무게 기록 객체 추가
        member.getWeights().add(memberWeight);

        // MyPageService에서 반환하는 사용자 개인정보 MyPageInfo 객체 생성
        MyPageInfo myPageInfo = MyPageInfo.builder()
                .member(member)                 // 사용자 member 설정
                .memberWeight(memberWeight)     // 사용자 몸무게 설정
                .build();

        // 사용자 개인정보 객체에 대한 응답용 Response DTO 객체에 필요한 변수들
        String nickNameDto = "닉네임예시";           // 사용자 닉네임
        String emailDto = "example@example.com";     // 사용자 이메일
        String ageDto = "2000-11-11";                // 사용자 생년월일
        Character genderDto = 'M';                   // 사용자 성별
        Integer heightDto = 185;                     // 사용자 키
        Integer weightDto = 70;                      // 사용자 몸무게

        // 사용자 개인정보 MyPageInfo 객체에 대한 응답용 Response DTO 객체
        MyPageInfoDto.Response myPageInfoResponseDto = MyPageInfoDto.Response.builder()
                .nickname(nickNameDto)      // 닉네임 설정
                .email(emailDto)            // 이메일 설정
                .age(ageDto)                // 생년월일 설정
                .gender(genderDto)          // 성별 설정
                .height(heightDto)          // 키 설정
                .weight(weightDto)          // 몸무게 설정
                .build();

        // -- Stubbing --
        // memberService로 사용자 ID찾는 메서드 Stubbing
        given(memberService.findMemberId(
                Mockito.any(HttpServletRequest.class)))
                .willReturn(memberId);

        // MyPageService에서 사용자 개인정보 조회하는 메서드 Stubbing
        given(myPageService.getMyPageInfo(
                Mockito.anyLong()))
                .willReturn(myPageInfo);

        // MyPageInfoMapper가 MyPageInfo객체를 응답용 DTO 객체로 변환하는 메서드 Stubbing
        given(myPageInfoMapper.myPageInfoToMyPageInfoResponseDto(
                Mockito.any(MyPageInfo.class)))
                .willReturn(myPageInfoResponseDto);

        // <<< when >>>
        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/users/mypages/info") // GET 요청시
                            .header("Authorization", "Bearer {AccessToken}")                // 헤더에 토큰 추가
                            .accept(MediaType.APPLICATION_JSON));

        // <<< then >>>
        MvcResult result = actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.nickname")
                        .value(myPageInfoResponseDto.getNickname()))
                .andExpect(jsonPath("$.data.email")
                        .value(myPageInfoResponseDto.getEmail()))
                .andExpect(jsonPath("$.data.age")
                        .value(myPageInfoResponseDto.getAge()))
                .andExpect(jsonPath("$.data.gender")
                        .value(String.valueOf(myPageInfoResponseDto.getGender())))
                .andExpect(jsonPath("$.data.height")
                        .value(myPageInfoResponseDto.getHeight()))
                .andExpect(jsonPath("$.data.weight")
                        .value(myPageInfoResponseDto.getWeight()))
                .andDo(
                        document(
                                "get-mypages-info",
                                getRequestPreprocessor(),
                                getResponsePreProcessor(),
                                requestHeaders(
                                        headerWithName("Authorization")
                                                .description("Bearer + {로그인 요청 Access 토큰}")
                                ),
                                responseFields(
                                        Arrays.asList(
                                                fieldWithPath("success").type(JsonFieldType.BOOLEAN)
                                                        .description("응답 성공 여부"),
                                                fieldWithPath("data").type(JsonFieldType.OBJECT)
                                                        .description("응답 데이터"),
                                                fieldWithPath("data.nickname").type(JsonFieldType.STRING)
                                                        .description("사용자 닉네임"),
                                                fieldWithPath("data.email").type(JsonFieldType.STRING)
                                                        .description("사용자 이메일"),
                                                fieldWithPath("data.age").type(JsonFieldType.STRING)
                                                        .description("사용자 생년월일"),
                                                fieldWithPath("data.gender").type(JsonFieldType.STRING)
                                                        .description("사용자 성별"),
                                                fieldWithPath("data.height").type(JsonFieldType.NUMBER)
                                                        .description("사용자 키"),
                                                fieldWithPath("data.weight").type(JsonFieldType.NUMBER)
                                                        .description("사용자 몸무게")
                                        )
                                )
                        )
                )
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser
    public void patch_my_page_info() throws Exception {
        // <<< given >>>
        // 사용자 개인정보 수정 Patch DTO 객체에 필요한 변수들 구성
        String nickNamePatchDto = "닉네임변경";           // 변경할 사용자 닉네임
        String agePatchDto = "2022-11-11";                // 변경할 사용자 생년월일
        Character genderPatchDto = 'W';                   // 변경할 사용자 성별
        Integer heightPatchDto = 160;                     // 변경할 사용자 키
        Integer weightPatchDto = 55;                      // 변경할 사용자 몸무게

        // 사용자 개인정보 수정 Patch DTO 객체 생성
        MyPageInfoDto.Patch myPageInfoPatchDto = MyPageInfoDto.Patch.builder()
                .nickname(nickNamePatchDto)         // 닉네임 설정
                .age(agePatchDto)                   // 나이 설정
                .gender(genderPatchDto)             // 성별 설정
                .height(heightPatchDto)             // 키 설정
                .weight(weightPatchDto)             // 몸무게 설정
                .build();

        // MyPageInfoPatchDTO객체를 JSON으로 변환
        String content = gson.toJson(myPageInfoPatchDto);

        // MyPageInfo 객체에 필요한 변수 구성
        long memberId = 1L;                     // 사용자 ID
        String email = "example@example.com";   // 사용자 이메일
        // Member 객체 생성
        Member member = Member.builder()
                .id(memberId)
                .email(email)
                .password("임의의 패스워드")
                .nickname("기존 닉네임")
                .build();

        // MyPageInfo 객체 생성
        MyPageInfo myPageInfo = MyPageInfo.builder()
                .member(member)
                .memberWeight(new MemberWeight())
                .build();

        // --- MyPageInfoResponseDTO 구성 ---
        // 수정된 사용자 개인정보 MyPageInfo 객체에 대한 응답용 Response DTO 객체에 필요한 변수 구성
        String nickNameResponseDto = "닉네임변경";           // 수정된 사용자 닉네임
        String emailResponseDto = "example@example.com";     // 사용자 이메일
        String ageResponseDto = "2022-11-11";                // 수정된 사용자 생년월일
        Character genderResponseDto = 'W';                   // 수정된 사용자 성별
        Integer heightResponseDto = 160;                     // 수정된 사용자 키
        Integer weightResponseDto = 55;                      // 수정된 사용자 몸무게

        // 수정된 사용자 개인정보 MyPageInfo 객체에 대한 응답용 Response DTO 객체
        MyPageInfoDto.Response myPageInfoResponseDto = MyPageInfoDto.Response.builder()
                .nickname(nickNameResponseDto)      // 닉네임 설정
                .email(emailResponseDto)            // 이메일 설정
                .age(ageResponseDto)                // 생년월일 설정
                .gender(genderResponseDto)          // 성별 설정
                .height(heightResponseDto)          // 키 설정
                .weight(weightResponseDto)          // 몸무게 설정
                .build();

        // memberService로 사용자 ID찾는 메서드 Stubbing
        given(memberService.findMemberId(
                Mockito.any(HttpServletRequest.class)))
                .willReturn(memberId);

        // MyPageInfoPatchDTO 객체를 MyPageInfo 객체로 변환하는 매퍼 클래스의 메서드 Stubbing
        given(myPageInfoMapper.myPageInfoPatchDtoToMyPageInfo(
                Mockito.any(MyPageInfoDto.Patch.class)))
                .willReturn(myPageInfo);

        // myPageService에서 수정 원하는 MyPageInfo 객체 받아서 업데이트하는 메서드 Stubbing
        given(myPageService.updateMyPageInfo(
                Mockito.any(MyPageInfo.class)))
                .willReturn(myPageInfo);

        // MyPageInfoMapper가 MyPageInfo객체를 응답용 DTO 객체로 변환하는 메서드 Stubbing
        given(myPageInfoMapper.myPageInfoToMyPageInfoResponseDto(
                Mockito.any(MyPageInfo.class)))
                .willReturn(myPageInfoResponseDto);

        // <<< when >>>
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .patch("/users/mypages/info")
                        .header("Authorization", "Bearer {AccessToken}")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .with(csrf()));

        // <<< then >>>
        MvcResult result = actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.nickname").value(myPageInfoPatchDto.getNickname()))
                .andExpect(jsonPath("$.data.age").value(myPageInfoPatchDto.getAge()))
                .andExpect(jsonPath("$.data.gender").value(String.valueOf(myPageInfoPatchDto.getGender())))
                .andExpect(jsonPath("$.data.height").value(myPageInfoPatchDto.getHeight()))
                .andExpect(jsonPath("$.data.weight").value(myPageInfoPatchDto.getWeight()))
                .andDo(
                        document(
                                "patch-mypages-info",
                                getRequestPreprocessor(),
                                getResponsePreProcessor(),
                                requestHeaders(
                                        headerWithName("Authorization")
                                                .description("Bearer + {로그인 요청 Access 토큰}")
                                ),
                                requestFields(
                                        List.of(
                                                fieldWithPath("nickname").type(JsonFieldType.STRING)
                                                        .description("수정할 사용자 닉네임").optional(),
                                                fieldWithPath("age").type(JsonFieldType.STRING)
                                                        .description("수정할 사용자 생년월일").optional(),
                                                fieldWithPath("gender").type(JsonFieldType.STRING)
                                                        .description("수정할 사용자 성별").optional(),
                                                fieldWithPath("height").type(JsonFieldType.NUMBER)
                                                        .description("수정할 사용자 키").optional(),
                                                fieldWithPath("weight").type(JsonFieldType.NUMBER)
                                                        .description("수정할 사용자 몸무게").optional()
                                        )
                                ),
                                responseFields(
                                        Arrays.asList(
                                                fieldWithPath("success").type(JsonFieldType.BOOLEAN)
                                                        .description("응답 성공 여부"),
                                                fieldWithPath("data").type(JsonFieldType.OBJECT)
                                                        .description("응답 데이터"),
                                                fieldWithPath("data.nickname").type(JsonFieldType.STRING)
                                                        .description("수정된 사용자 닉네임"),
                                                fieldWithPath("data.email").type(JsonFieldType.STRING)
                                                        .description("사용자 이메일"),
                                                fieldWithPath("data.age").type(JsonFieldType.STRING)
                                                        .description("수정된 사용자 생년월일"),
                                                fieldWithPath("data.gender").type(JsonFieldType.STRING)
                                                        .description("수정된 사용자 성별"),
                                                fieldWithPath("data.height").type(JsonFieldType.NUMBER)
                                                        .description("수정된 사용자 키"),
                                                fieldWithPath("data.weight").type(JsonFieldType.NUMBER)
                                                        .description("수정된 사용자 몸무게")
                                        )
                                )
                        )
                )
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser
    public void patch_password_test() throws Exception{

        // <<< given >>>
        String password = "example!123";        // 기존 비밀번호
        String newPassword = "testtest@456";    // 변경할 비밀번호

        // 비밀번호 수정용 PatchDto 객체 생성
        PasswordPatchDto passwordPatchDto = PasswordPatchDto.builder()
                .password(password)             // 기존 비밀번호 설정
                .newPassword(newPassword)       // 변경할 비밀번호 설정
                .build();

        // 비밀번호 수정용 PatchDto객체를 JSON으로 변환
        String content = gson.toJson(passwordPatchDto);

        long memberId = 1L;     // 사용자 ID

        // --- Stubbing ---
        // memberService로 사용자 ID찾는 메서드 Stubbing
        given(memberService.findMemberId(
                Mockito.any(HttpServletRequest.class)))
                .willReturn(memberId);

        // mypageService에서 비밀번호 수정하는 메서드 Stubbing
        willDoNothing().given(myPageService).updatePassword(passwordPatchDto, memberId);

        // <<< when >>>
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .patch("/users/mypages/password")
                        .header("Authorization", "Bearer {AccessToken}")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .with(csrf()));

        // <<< then >>>
        MvcResult result = actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value("true"))
                .andDo(
                        document(
                                "patch-mypages-password",
                                getRequestPreprocessor(),
                                getResponsePreProcessor(),
                                requestHeaders(
                                        headerWithName("Authorization")
                                                .description("Bearer + {로그인 요청 Access 토큰}")
                                ),
                                requestFields(
                                        List.of(
                                                fieldWithPath("password").type(JsonFieldType.STRING)
                                                        .description("기존 비밀번호"),
                                                fieldWithPath("newPassword").type(JsonFieldType.STRING)
                                                        .description("변경할 비밀번호")
                                        )
                                ),
                                responseFields(
                                        Arrays.asList(
                                                fieldWithPath("success").type(JsonFieldType.BOOLEAN)
                                                        .description("응답 성공 여부"),
                                                fieldWithPath("data").type(JsonFieldType.NULL)
                                                        .description("응답 데이터")
                                        )
                                )
                        )
                )
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }
}
