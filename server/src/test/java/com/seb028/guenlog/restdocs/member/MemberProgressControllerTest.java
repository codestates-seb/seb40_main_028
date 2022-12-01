package com.seb028.guenlog.restdocs.member;

import com.google.gson.Gson;
import com.seb028.guenlog.config.SecurityConfig;
import com.seb028.guenlog.config.auth.JwtTokenizer;
import com.seb028.guenlog.config.auth.jwt.filter.JwtAuthenticationFilter;
import com.seb028.guenlog.config.auth.jwt.filter.JwtVerificationFilter;
import com.seb028.guenlog.member.controller.MemberController;
import com.seb028.guenlog.member.dto.MemberDto;
import com.seb028.guenlog.member.entity.Member;
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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.seb028.util.ApiDocumentUtils.getRequestPreprocessor;
import static com.seb028.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
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
}
