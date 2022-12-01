package com.seb028.guenlog.restdocs.member;

import com.google.gson.Gson;
import com.seb028.guenlog.config.SecurityConfig;
import com.seb028.guenlog.config.auth.JwtTokenizer;
import com.seb028.guenlog.config.auth.jwt.filter.JwtAuthenticationFilter;
import com.seb028.guenlog.config.auth.jwt.filter.JwtVerificationFilter;
import com.seb028.guenlog.member.controller.MemberController;
import com.seb028.guenlog.member.dto.MemberDto;
import com.seb028.guenlog.member.entity.Member;
import com.seb028.guenlog.member.entity.MemberWeight;
import com.seb028.guenlog.member.mapper.MemberMapper;
import com.seb028.guenlog.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
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
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.seb028.util.ApiDocumentUtils.getRequestPreprocessor;
import static com.seb028.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MemberController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = {SecurityConfig.class, JwtTokenizer.class, JwtAuthenticationFilter.class, JwtVerificationFilter.class}
                )
        })
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@DisplayName(value = "회원가입 테스트")
public class MemberProgressControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private Gson gson;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberMapper mapper;

    @Test
    @DisplayName("회원가입 메서드 로직 테스트")
    @WithMockUser(username = "guenlog@test.com", roles = {"USER"}, password = "password!!")
    void postMemberTest() throws Exception {
        //given
        MemberDto.post memberPost = new MemberDto.post("guenlog@test.com", "guenlog", "password!!1");

        Member member = mapper.memberPostDtoToMember(memberPost);
        //memberController - post를 사용하기위해 주입받은 메서드를 stubbing
        given(mapper.memberPostDtoToMember(Mockito.any(MemberDto.post.class)))
                .willReturn(new Member());
        given(memberService.createMember(Mockito.any(Member.class)))
                .willReturn(new Member());
        //Json 타입으로 변환
        String content = gson.toJson(memberPost);

        //when
        ResultActions actions =
                mvc.perform(
                        post("/users/signup")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())  // Request의 Parameter에 _csrf 속성으로 값이 담겨들어오도록 세팅
                                .content(content));

        //then
        actions
                //테스트 결과 기대값
                .andExpect(status().isCreated()) //http Status
                .andExpect(jsonPath("$.success").value(true)) //정상 응답시 출력
                .andDo(document(
                        "post-member",  //API 문서화 식별자
                        getRequestPreprocessor(),   //json 포멧 표현
                        getResponsePreProcessor(),
                        requestFields(  //request body에 포함된 데이터
                                List.of(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("패스워드")
                                )
                        ),
                        responseFields(     //response body에 포함된 데이터
                                List.of(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("결과"),
                                        fieldWithPath("data").type(JsonFieldType.NULL).description("데이터")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("최초 로그인 추가 정보 입력")
    @WithMockUser(username = "guenlog@test.com", roles = {"USER"}, password = "password!!")
    void patchMemberTest() throws Exception {
        //given
        long memberId = 1L;
        MemberDto.patch memberPatch = new MemberDto.patch(memberId, "1999-09-09", "M", 170,
                70, true);

        Member member = mapper.memberPatchDtoToMember(memberPatch);
        //memberController - post를 사용하기위해 주입받은 메서드를 stubbing
        given(mapper.memberPatchDtoToMember(Mockito.any(MemberDto.patch.class)))
                .willReturn(new Member());
        given(memberService.findMemberId(Mockito.any(HttpServletRequest.class)))
                .willReturn(memberId);
        given(memberService.initialMember(Mockito.any(Member.class), Mockito.any(Integer.class)))
                .willReturn(new Member());
        //Json 타입으로 변환
        String content = gson.toJson(memberPatch);

        //when
        ResultActions actions =
                mvc.perform(
                        RestDocumentationRequestBuilders
                                .patch("/users/info")
                                .header("Authorization", "Bearer {AccessToken}")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())  // Request의 Parameter에 _csrf 속성으로 값이 담겨들어오도록 세팅
                                .content(content));

        //then
        actions
                //테스트 결과 기대값
                .andExpect(status().isOk()) //http Status
                .andExpect(jsonPath("$.success").value(true)) //정상 응답시 출력
                .andDo(document(
                        "initial-member",  //API 문서화 식별자
                        getRequestPreprocessor(),   //json 포멧 표현
                        getResponsePreProcessor(),
                        requestHeaders(    //request header
                                headerWithName("Authorization").description("Bearer + {로그인 요청 Access 토큰}")
                        ),
                        requestFields(  //request body에 포함된 데이터
                                List.of(
                                        fieldWithPath("Id").type(JsonFieldType.NUMBER).description("회원ID(Token에서 추출)"),
                                        fieldWithPath("age").type(JsonFieldType.STRING).description("생년월일"),
                                        fieldWithPath("height").type(JsonFieldType.NUMBER).description("키"),
                                        fieldWithPath("weight").type(JsonFieldType.NUMBER).description("몸무게"),
                                        fieldWithPath("gender").type(JsonFieldType.STRING).description("성별"),
                                        fieldWithPath("initialLogin").type(JsonFieldType.BOOLEAN).description("최초 로그인 상태")
                                )
                        ),
                        responseFields(     //response body에 포함된 데이터
                                List.of(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("결과"),
                                        fieldWithPath("data").type(JsonFieldType.NULL).description("데이터")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("회원 탈퇴")
    @WithMockUser(username = "guenlog@test.com", roles = {"USER"}, password = "password!!")
    void deleteMemberTest() throws Exception {
        //given
        long memberId = 1L;
        LocalDate age = LocalDate.parse("1999-09-09");
        List<MemberWeight> weights = new ArrayList<>();

        MemberWeight weight = MemberWeight.builder()
                .member(new Member())
                .weight(70)
                .build();
        weights.add(weight);

        Member member = Member.builder()
                .id(1L)
                .email("guenlog@test.com")
                .nickname("guenlog")
                .password("password!!1")
                .age(age)
                .gender('M')
                .height(79)
                .weights(weights)
                .initialLogin(true)
                .build();

        //memberController - patch를 사용하기위해 주입받은 메서드를 stubbing
        given(mapper.memberPatchDtoToMember(Mockito.any(MemberDto.patch.class)))
                .willReturn(new Member());
        given(memberService.findMemberId(Mockito.any(HttpServletRequest.class)))
                .willReturn(memberId);
        doNothing().when(memberService).deleteMember(Mockito.anyLong());

        //when
        ResultActions actions =
                mvc.perform(
                        RestDocumentationRequestBuilders
                                .delete("/users")
                                .header("Authorization", "Bearer {AccessToken}")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf()))  // Request의 Parameter에 _csrf 속성으로 값이 담겨들어오도록 세팅
                ;

        //then
        actions
                //테스트 결과 기대값
                .andExpect(status().isNoContent()) //http Status
                .andExpect(jsonPath("$.success").value(true)) //정상 응답시 출력
                .andDo(document(
                        "delete-member",  //API 문서화 식별자
                        getRequestPreprocessor(),   //json 포멧 표현
                        getResponsePreProcessor(),
                        requestHeaders(    //request header
                                headerWithName("Authorization").description("Bearer + {로그인 요청 Access 토큰}")
                        ),

                        responseFields(     //response body에 포함된 데이터
                                List.of(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("결과"),
                                        fieldWithPath("data").type(JsonFieldType.NULL).description("데이터")
                                )
                        )
                ));
    }
}

