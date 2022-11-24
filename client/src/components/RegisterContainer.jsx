import React, { useState, useRef } from "react";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import axios from "axios";




const Container = styled.div`
  width: 300px;
  margin-bottom: 40px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: white;
  box-shadow: rgba(0, 0, 0, 0.05) 0px 10px 24px 0px,
    rgba(0, 0, 0, 0.05) 0px 20px 48px 0px, rgba(0, 0, 0, 0.1) 0px 1px 4px 0px;
  border-radius: 10px;
`;

const MentDiv = styled.div`
  font-size: ${({ fontSize }) => fontSize || "13px"};
  margin-bottom: 10px;
  padding: ${({ padding }) => padding || 0};
  margin-top: ${({ mt }) => mt || 0};
  text-align: center;
  
`;

const MentSpan = styled.span`
  font-size: 13px;
  cursor: pointer;
  // & a {
  //   text-decoration: none;
  //   color: #0074cc;
  // }
  color: rgb(88 101 242);
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-evenly;
  height: 100%;
  width: 100%;
  padding: 20px 10px 10px 10px;
`;

const InputContainer = styled.div`
  margin: 0 0 5px 0;
  display: flex;
  flex-direction: column;
  align-self: center;
`;

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

const Input = styled.input`
  width: 260px;
  height: 35px;
  padding: 0;
  border: 2px solid rgb(88 101 242) ;
`;

const SubmitBtn = styled.input`
  width: 260px;
  height: 35px;
  font-size: 13.6px;
  font-weight: 600;
  color: white;
  background-color: rgb(88 101 242);

  border: 0;
  border-radius: 3px;
  padding: 0;
  margin-bottom: 5px;
  &:hover {
    background-color: #0074cc;
  }
`;

const RegisterMent = styled.div`
  padding: 0 18px;
  margin-bottom: 16px;
  color: #6a737c;
  font-size: 12px;
`;

const MainContainer = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
`;


export default function RegisterContainer() {
  const [error, setError] = useState("");

  const navigate = useNavigate();

 
  const {
    register,
    formState: { errors },
    handleSubmit,
    watch,
  } = useForm({ mode: onchange });
  
  const password = useRef();
  password.current = watch("password");

  // 실시간 입력 값 확인
  // console.log(watch("email"));
  
  const onRegister = async (data) => {

    console.log("data", data)
    console.log("이메일", data.email)
    console.log("닉네임", data.Nickname)
    console.log("패스워드", data.password)

    /*
    api
    {
		  "email" : "test@test.com", 
	    "nickname" : "testnick", 
		  "password" : "test123!@#"	
    }
    */

    // let userData = {
    //   email: data.email,
    //   nickname: data.displayName,
    //   password: data.password,
    // };


    return axios
      .post('13.209.190.35:8080/users/info', {
        // 보내고자 하는 데이터
        email: data.email,
        nickname: data.displayName,
        password: data.password,
      })
      .then((res) => {
        navigate('/login');
      })
      .catch((err) => {
        console.log(err);

        // setError(err.response.data.message);
        // setTimeout(() => {
        //   setError('');
        // }, 2000);
      });



        







  };

  return (
    <MainContainer>
      {/* 왼쪽 내용 */}
      {/* <Description /> */}
      <div>
        <Container>
          <Form onSubmit={handleSubmit(onRegister)}>
            <InputContainer>
              <Label htmlFor="Nickname">Nickname</Label>
              <Input
                type="text"
                id="Nickname"
                {...register("Nickname", {
                  required: true,
                  minLength: 2,
                  maxLength: 10,
                  // 공백없는 숫자와 대소문자만 가능 * 숫자만도 가능해서 수정해야될것 같음
                  // pattern: /^[a-zA-Z0-9]*$/,
                  // pattern: /^([a-zA-Z가-힣]){1,8}$/,
                  pattern: /^([a-zA-Z0-9가-힣])*$/,
                })}
              />
              {errors.Nickname && errors.Nickname.type === "required" && (
                <Errormsg>닉네임을 입력해주세요.</Errormsg>
              )}
              {errors.Nickname && errors.Nickname.type === "pattern" && (
                <Errormsg>한글, 영문, 숫자을 사용하세요.</Errormsg>
              )}
              {errors.Nickname && errors.Nickname.type === "minLength" && (
                <Errormsg>최소 길이는 2자 이상여야 합니다.</Errormsg>
              )}
              {errors.Nickname && errors.Nickname.type === "maxLength" && (
                <Errormsg>최대 길이는 10자 이하여야 합니다.</Errormsg>
              )}
            </InputContainer>

            <InputContainer>
              <Label htmlFor="email">Email</Label>
              <Input
                type="email"
                id="email"
                {...register("email", {
                  required: true,
                  pattern: /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/,
                  maxLength: 50,
                })}
              />
              {errors.email && errors.email.type === "required" && (
                <Errormsg>이메일을 입력해주세요.</Errormsg>
              )}
              {errors.email && errors.email.type === "pattern" && (
                <Errormsg>이메일 형식이여야 합니다.</Errormsg>
              )}
              {errors.email && errors.email.type === "maxLength" && (
                <Errormsg>최대 길이는 50자 이하여야 합니다.</Errormsg>
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
                  // 최소 10자리 이상 영문 대소문자, 숫자, 특수문자가 각각 1개 이상
                  pattern:/^(?=.*?[a-zA-Z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{10,}$/,
                  // pattern:/^(?=.*?[a-zA-Z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).$/,
                })}
              />
              {errors.password && errors.password.type === "required" && (
                <Errormsg>패스워드를 입력해주세요</Errormsg>
              )}
              {errors.password && errors.password.type === "minLength" && (
                <Errormsg> 최소 길이는 10자 이상이어야 합니다</Errormsg>
              )}
                {errors.password &&
                errors.password.type === "pattern" && (
                <Errormsg>영문, 특수문자, 숫자 포함하세요</Errormsg>
              )}
            </InputContainer>
            <InputContainer>
              <Label htmlFor="passwordConfirm">passwordConfirm</Label>
              <Input
                // type를 패스워드로 입력 시 화면에 안보임 어떤 값을 입력하는지
                type="password"
                id="passwordConfirm"
                {...register("passwordConfirm", {
                  required: true,
                  validate: (value) =>
                    value === password.current
                })}
              />
              {errors.passwordConfirm && errors.passwordConfirm.type === "required"
                      && <Errormsg>패스워드를 입력해주세요.</Errormsg>}
              {errors.passwordConfirm && errors.passwordConfirm.type === "validate"
                      && <Errormsg>패스워드가 일치하지 않습니다. </Errormsg>}          
            </InputContainer>
            {/* 비밀번호 안내문구 */}
            <RegisterMent>
              비밀번호는 10자 이상의 영문대소문자, 특수문자, 숫자를 각각1개 이상 포함해야합니다. 
            </RegisterMent>
            {/* 회원가입 버튼 */}
            <SubmitBtn type="submit" value="Sign up" />
            {error && <Errormsg>{error}</Errormsg>}
          </Form>
        </Container>
        <MentDiv>
          이미 계정이 있습니까?
          <MentSpan onClick={() => navigate("/login")}> Log in</MentSpan>
        </MentDiv>
      </div>
    </MainContainer>
  );
}
