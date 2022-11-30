/* eslint-disable */

import React, { useState } from "react";
import styled from "styled-components";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import axios from "axios";

// import { Flag } from "heroicons-react";

import { useRecoilState } from "recoil";
import { LoginState, TokenState } from "../state/UserState";

const Container = styled.div`
  width: 300px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: white;
  border-radius: 10px;
`;

// sing up + 구글 로그인 버튼 들어있는 태그
// 회원가입 + 구글 로그인 버튼 가로 사이즈 조정
const BtnContainer = styled.div`
  padding: 20px;
  width: 210px;
  height: 200px;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

// sign up 버튼
const SignUp = styled.span`
  width: 100%;
  height: 30%;
  padding: 10px;
  color: rgb(88 101 242);
  background: white;
  font-size: 13px;
  font-weight: 600;
  border: 2px solid;
  border-radius: 5px;
  margin-bottom: 20px;

  display: flex;
  justify-content: center;
  align-items: center;

  &:hover {
    background-color: rgb(71 82 196);
    color: white;
  }
`;

// 구글 로그인
// GoogleLogin
const GoogleLogin = styled.span`
  width: 100%;
  height: 30%;
  padding: 10px;
  color: rgb(88 101 242);
  background: white;
  font-size: 13px;
  font-weight: 600;
  border: 2px solid;
  border-radius: 5px;

  display: flex;
  justify-content: center;
  align-items: center;

  &:hover {
    background-color: rgb(71 82 196);
    color: white;
  }
`;

// 로그인 form
const Form = styled.form`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-evenly;
  height: 100%;
  width: 100%;
  padding: 10px;
`;

const InputContainer = styled.div`
  margin: 10px 0;
  display: flex;
  flex-direction: column;
  align-self: center;
`;

// 입력에러 메세지 ex) 이메일을 입력해주세요
const Errormsg = styled.p`
  color: #bf1650;
  margin: 3px;
  font-size: 13px;
`;

const Label = styled.label`
  font-size: 15px;
  font-weight: 500;
  margin-bottom: 3px;
`;

// 이메일 + 패스워드 입력 창
const Input = styled.input`
  width: 260px;
  height: 35px;
  padding: 0;
  border: 2px solid rgb(88 101 242);
`;

// login 버튼
const SubmitBtn = styled.input`
  width: 260px;
  height: 35px;
  font-size: 13.6px;
  font-weight: 600;
  color: white;
  background-color: rgb(88 102 242);
  border: 0;
  border-radius: 3px;
  padding: 0;
  margin-bottom: 5px;
  &:hover {
    background-color: rgb(71 82 196);
  }
`;

const MainContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

export default function LoginContainer() {
  const navigate = useNavigate();
  const [error, setError] = useState("");

  const [isLogin, setIsLogin] = useRecoilState(LoginState);
  const [isToken, setToken] = useRecoilState(TokenState);

  /////////////////////////////////////////////
  console.log(isLogin, "처음 이즈로그인 값");

  const {
    register,
    formState: { errors },
    handleSubmit,
  } = useForm({ mode: onchange });

  const onLogin = async (data) => {
    const url = "http://13.209.190.35:8080";

    console.log(data);
    // console.log(data.email+": 이메일");
    // console.log(data.password+": 이메일");

    try {
      // 응답 성공
      // const res = await axios.post("http://13.209.190.35:8080/users/login", {
      const res = await axios.post(`${url}/users/login`, {
        // 보내고자 하는 데이터
        username: data.email,
        password: data.password,
      });

      // status가 200일때
      if (res.status === 200) {
        // 세션스토리지에 jwt-token 저장
        // sessionStorage.setItem("jwt-token", res.headers.authorization);

        const token = res.headers.authorization;

        console.log(isToken, "토큰저장 전~~~~~~~");

        // 토큰이 있으면
        if (token) {
          // 토큰 저장
          setToken(token);
          // 로그인 상태 true
          setIsLogin(true);
          console.log("토큰 확인됐음 로컬에 토큰 저장");
        }

        // console.log(isLogin, "로그인 성공 시 트루로 바꾼 값~~~");

        // console.log("성공 로그인 ~~~~~~~~~~~~~~~~");
        // console.log(res);
        // console.log("바디데이터 ", res.data);
        // console.log("바디 이니셜로그인 값 확인 ", res.data.initialLogin)

        // 이니셜로그인 false면 초기정보 입력 페이지로
        if (res.data.initialLogin === false) {
          navigate("/startinginformation");
        }
        // 이니셜로그인이 true면 메인페이지로
        else {
          navigate("/");
        }

        // console.log("응답 전체",res)
        // console.log("응답.headers", res.headers.InitialLogin)
        // console.log("응답.headers", res.headers.initialLogin)
        // console.log("응답.headers", res.headers.initiallogin)

        // InitialLogin이 false면 처음 로그인으로
        // if(res.InitialLogin === false){
        //   navigate("/startinginformation");
        //   sessionStorage.setItem('jwt-token', res.headers.authorization);
        //   // InitialLogin이 true면 main 페이지로
        // } else if(res.InitialLogin === ture){
        //   navigate("/");
        // }
      }
    } catch (err) {
      // 응답 실패
      console.log(err);
      // console.log("로그인 실패")

      // 로그인 실패시
      alert("Email or Password를 확인하세요");
    }
  };

  const googleLogin = () => {
    // 구글로그인 주소
    const googleurl = "http://guenlog.shop/oauth2/authorization/google";

    window.location.href = `${googleurl}`;
  };

  return (
    <MainContainer>
      <Container>
        <Form onSubmit={handleSubmit(onLogin)}>
          <InputContainer>
            <Label htmlFor="Email">Email</Label>
            <Input
              type="email"
              id="Email"
              {...register("email", {
                required: true,
                pattern: /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/,
                maxLength: 50,
              })}
            />
            {errors.email && errors.email.type === "required" && (
              <Errormsg>⚠ 이메일을 입력해주세요.</Errormsg>
            )}
            {errors.email && errors.email.type === "pattern" && (
              <Errormsg>⚠ 이메일 형식이여야 합니다.</Errormsg>
            )}
            {errors.email && errors.email.type === "maxLength" && (
              <Errormsg>⚠ 최대 길이는 50자 이하여야 합니다</Errormsg>
            )}
          </InputContainer>
          <InputContainer>
            <Label htmlFor="password">Password</Label>
            <Input
              type="password"
              id="password"
              {...register("password", {
                required: true,
                minLength: 10,
              })}
            />
            {errors.password && errors.password.type === "required" && (
              <Errormsg>⚠ 패스워드를 입력해주세요</Errormsg>
            )}
            {errors.password && errors.password.type === "minLength" && (
              <Errormsg>⚠ 최소 길이는 10자 이상이여야 합니다</Errormsg>
            )}
          </InputContainer>

          {/* 로그인 버튼 */}
          <SubmitBtn type="submit" value="Login" />
          {error && <Errormsg>⚠ {error}</Errormsg>}
        </Form>
      </Container>
      <BtnContainer>
        {/* 회원가입 */}
        <SignUp onClick={() => navigate("/register")}>Sign up</SignUp>
        <GoogleLogin onClick={googleLogin}>
          <svg
            aria-hidden="true"
            className="native svg-icon iconGoogle"
            width="18"
            height="18"
            viewBox="0 0 18 18"
          >
            <path
              d="M16.51 8H8.98v3h4.3c-.18 1-.74 1.48-1.6 2.04v2.01h2.6a7.8 7.8 0 0 0 2.38-5.88c0-.57-.05-.66-.15-1.18Z"
              fill="#4285F4"
            />
            <path
              d="M8.98 17c2.16 0 3.97-.72 5.3-1.94l-2.6-2a4.8 4.8 0 0 1-7.18-2.54H1.83v2.07A8 8 0 0 0 8.98 17Z"
              fill="#34A853"
            />
            <path
              d="M4.5 10.52a4.8 4.8 0 0 1 0-3.04V5.41H1.83a8 8 0 0 0 0 7.18l2.67-2.07Z"
              fill="#FBBC05"
            />
            <path
              d="M8.98 4.18c1.17 0 2.23.4 3.06 1.2l2.3-2.3A8 8 0 0 0 1.83 5.4L4.5 7.49a4.77 4.77 0 0 1 4.48-3.3Z"
              fill="#EA4335"
            />
          </svg>
          Sign up with Google
        </GoogleLogin>
      </BtnContainer>
    </MainContainer>
  );
}
