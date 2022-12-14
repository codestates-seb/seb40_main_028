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
@DisplayName(value = "???????????? ?????????")
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
    @DisplayName("???????????? ????????? ?????? ?????????")
    @WithMockUser(username = "guenlog@test.com", roles = {"USER"}, password = "password!!")
    void postMemberTest() throws Exception {
        //given
        MemberDto.post memberPost = new MemberDto.post("guenlog@test.com", "guenlog", "password!!1");

        Member member = mapper.memberPostDtoToMember(memberPost);
        //memberController - post??? ?????????????????? ???????????? ???????????? stubbing
        given(mapper.memberPostDtoToMember(Mockito.any(MemberDto.post.class)))
                .willReturn(new Member());
        given(memberService.createMember(Mockito.any(Member.class)))
                .willReturn(new Member());
        //Json ???????????? ??????
        String content = gson.toJson(memberPost);

        //when
        ResultActions actions =
                mvc.perform(
                        post("/users/signup")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())  // Request??? Parameter??? _csrf ???????????? ?????? ????????????????????? ??????
                                .content(content));

        //then
        actions
                //????????? ?????? ?????????
                .andExpect(status().isCreated()) //http Status
                .andExpect(jsonPath("$.success").value(true)) //?????? ????????? ??????
                .andDo(document(
                        "post-member",  //API ????????? ?????????
                        getRequestPreprocessor(),   //json ?????? ??????
                        getResponsePreProcessor(),
                        requestFields(  //request body??? ????????? ?????????
                                List.of(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("????????????")
                                )
                        ),
                        responseFields(     //response body??? ????????? ?????????
                                List.of(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("??????"),
                                        fieldWithPath("data").type(JsonFieldType.NULL).description("?????????")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("?????? ????????? ?????? ?????? ??????")
    @WithMockUser(username = "guenlog@test.com", roles = {"USER"}, password = "password!!")
    void patchMemberTest() throws Exception {
        //given
        long memberId = 1L;
        MemberDto.patch memberPatch = new MemberDto.patch(memberId, "1999-09-09", "M", 170,
                70, true);

        Member member = mapper.memberPatchDtoToMember(memberPatch);
        //memberController - post??? ?????????????????? ???????????? ???????????? stubbing
        given(mapper.memberPatchDtoToMember(Mockito.any(MemberDto.patch.class)))
                .willReturn(new Member());
        given(memberService.findMemberId(Mockito.any(HttpServletRequest.class)))
                .willReturn(memberId);
        given(memberService.initialMember(Mockito.any(Member.class), Mockito.any(Integer.class)))
                .willReturn(new Member());
        //Json ???????????? ??????
        String content = gson.toJson(memberPatch);

        //when
        ResultActions actions =
                mvc.perform(
                        RestDocumentationRequestBuilders
                                .patch("/users/info")
                                .header("Authorization", "Bearer {AccessToken}")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())  // Request??? Parameter??? _csrf ???????????? ?????? ????????????????????? ??????
                                .content(content));

        //then
        actions
                //????????? ?????? ?????????
                .andExpect(status().isOk()) //http Status
                .andExpect(jsonPath("$.success").value(true)) //?????? ????????? ??????
                .andDo(document(
                        "initial-member",  //API ????????? ?????????
                        getRequestPreprocessor(),   //json ?????? ??????
                        getResponsePreProcessor(),
                        requestHeaders(    //request header
                                headerWithName("Authorization").description("Bearer + {????????? ?????? Access ??????}")
                        ),
                        requestFields(  //request body??? ????????? ?????????
                                List.of(
                                        fieldWithPath("Id").type(JsonFieldType.NUMBER).description("??????ID(Token?????? ??????)"),
                                        fieldWithPath("age").type(JsonFieldType.STRING).description("????????????"),
                                        fieldWithPath("height").type(JsonFieldType.NUMBER).description("???"),
                                        fieldWithPath("weight").type(JsonFieldType.NUMBER).description("?????????"),
                                        fieldWithPath("gender").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("initialLogin").type(JsonFieldType.BOOLEAN).description("?????? ????????? ??????")
                                )
                        ),
                        responseFields(     //response body??? ????????? ?????????
                                List.of(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("??????"),
                                        fieldWithPath("data").type(JsonFieldType.NULL).description("?????????")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("?????? ??????")
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

        //memberController - patch??? ?????????????????? ???????????? ???????????? stubbing
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
                                .with(csrf()))  // Request??? Parameter??? _csrf ???????????? ?????? ????????????????????? ??????
                ;

        //then
        actions
                //????????? ?????? ?????????
                .andExpect(status().isNoContent()) //http Status
                .andExpect(jsonPath("$.success").value(true)) //?????? ????????? ??????
                .andDo(document(
                        "delete-member",  //API ????????? ?????????
                        getRequestPreprocessor(),   //json ?????? ??????
                        getResponsePreProcessor(),
                        requestHeaders(    //request header
                                headerWithName("Authorization").description("Bearer + {????????? ?????? Access ??????}")
                        ),

                        responseFields(     //response body??? ????????? ?????????
                                List.of(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("??????"),
                                        fieldWithPath("data").type(JsonFieldType.NULL).description("?????????")
                                )
                        )
                ));
    }
}

