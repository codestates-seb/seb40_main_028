/* eslint-disable */
/* eslint-disable react/jsx-props-no-spreading */

import React, { useState } from "react";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import axios from "axios";

import Toggle from "./Toggle";

import { useRecoilValue } from "recoil";
import { LoginState, TokenState, Googlelogin } from "../state/UserState";

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
  border: 2px solid rgb(88 101 242);

  // 화살표 제거
  // ::-webkit-outer-spin-button;
  ::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
  }
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
    background-color: rgb(71 82 196);
  }
`;

const MainContainer = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
`;

export default function InformationContainer() {
  const [error, setError] = useState("");

  const [gender, setGender] = useState("W");

  const navigate = useNavigate();

  // 구글로그인 체크
  const googlelogincheck = useRecoilValue(Googlelogin);

  // 로그인 상태
  const login = useRecoilValue(LoginState);

  // 토큰
  const token = useRecoilValue(TokenState);

  Googlelogin;
  const {
    register,
    formState: { errors },
    handleSubmit,
  } = useForm({ mode: onchange });

  // const onInformation = async (data) => {
  //   console.log(data);
  //   // 회원가입 api 자리
  //   // try {
  //   //   axios.post("#", { ...data });
  //   // } catch (err) {
  //   //   setError(err);
  //   // }
  // };

  // const [account, setAccount] = useState({
  //   height: "",
  //   // weight: "",
  //   // age: "",
  // });

  //input에 입력될 때마다 account state값 변경되게 하는 함수
  // const onChangeAccount = (e) => {
  //   setAccount({
  //     ...account,
  //     [e.target.name]: e.target.value,
  //   });
  // };
  // // console.log(account);

  // 성별체크
  // console.log(gender, "성별!!!!!")

  const onInformation = async (data) => {
    // console.log(data);
    // console.log(data.email+": 이메일");
    // console.log(data.password+": 이메일");

    // 키랑 몸무게는 number타입
    // console.log(`height: ${Number(data.height)}`)
    // console.log(`age: ${data.age}`)
    // console.log(`gender: ${gender}`)
    // console.log(`weight: ${Number(data.weight)}`)

    // 세션에 저장된 토큰 가져오기
    // let token = sessionStorage.getItem("jwt-token");
    // console.log(`토큰: ${token}`);

    // const url = "http://13.209.190.35:8080";
    // const url = "https://guenlog.shop";
    const URL = process.env.REACT_APP_BASE_URL;
    // https://guenlog.shop/users/info

    // 로그인상태가 아닌경우 login 페이지로 이동
    if (login === false) {
      alert("로그인이 안 되어 있습니다.");
      navigate("/login");
    }

    try {
      // 응답 성공
      // const res = await axios.post("http://13.209.190.35:8080/users/info",
      const res = await axios.patch(
        `${URL}/users/info`,
        {
          // 보내고자 하는 데이터
          // 키 몸무게만 숫자형으로
          height: data.height,
          age: data.age,
          gender: gender,
          weight: data.weight,
        },
        {
          // 헤더에 토큰값 넣어서 보내기
          headers: {
            Authorization: `${token}`,
            // Authorization: (`${token}` || `Bearer ${token}` ),
          },
        }
      );

      // let a = {
      //   height: data.height,
      //   age: data.age,
      //   gender: gender,
      //   weight: data.weight,
      // };
      // console.log(a, "정보입력 성공! 데이터값~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

      // status가 200이면 세션스토리지에 jwt-token 저장
      if (res.status === 200) {
        // 정보 입력 완료하면 메인페이지로 이동
        alert("정보 입력에 성공하셨습니다!");
        navigate("/");

        // console.log(res);
        // console.log("성공 로그인 ~~~~~~~~~~~~~~~~");

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
      // console.log(err);
      // console.log(err.response);
      // console.log(err.response.request);
      // console.log(err.response.request.status);
      // console.log("로그인 실패")

      // 세션에 토큰이 없을경우??
      // if(err.response.request.status === 403) {
      //   alert("먼저 로그인을 해주세요.");
      // }

      // 로그인 실패시
      alert("정보 입력에 실패하였습니다.");
      // console.log(`${token}`)
    }
  };

  return (
    <MainContainer>
      <div>
        <Container>
          <Form onSubmit={handleSubmit(onInformation)}>
            {/* onChange={onChangeAccount} */}
            <InputContainer>
              <Label htmlFor="age">age(생년월일)</Label>
              <Input
                type="text"
                id="age"
                placeholder="2000-01-01"
                {...register("age", {
                  required: true,
                  pattern: /\d{4}-\d{2}-\d{2}/,
                })}
              />
              {errors.age && errors.age.type === "required" && (
                <Errormsg>나이를 입력해주세요.</Errormsg>
              )}
              {errors.age && errors.age.type === "pattern" && (
                <Errormsg>2000-01-01 형식을 맞춰주세요</Errormsg>
              )}
            </InputContainer>
            <InputContainer>
              <Label htmlFor="height">height(키)</Label>
              <Input
                type="number"
                id="height"
                placeholder="165"
                {...register("height", {
                  required: true,
                  min: 50,
                  max: 250,
                })}
              />
              {errors.height && errors.height.type === "required" && (
                <Errormsg>키를 입력해주세요.</Errormsg>
              )}
              {errors.height && errors.height.type === "min" && (
                <Errormsg>올바른 키를 입력하세요</Errormsg>
              )}
              {errors.height && errors.height.type === "max" && (
                <Errormsg>올바른 키를 입력하세요</Errormsg>
              )}
            </InputContainer>
            <InputContainer>
              <Label htmlFor="weight">weight(몸무게)</Label>
              <Input
                type="number"
                id="weight"
                placeholder="65"
                {...register("weight", {
                  required: true,
                  min: 1,
                  max: 700,
                })}
              />
              {errors.weight && errors.weight.type === "required" && (
                <Errormsg>몸무게를 입력해주세요.</Errormsg>
              )}
              {errors.weight && errors.weight.type === "min" && (
                <Errormsg>올바른 몸무게를 입력하세요</Errormsg>
              )}
              {errors.weight && errors.weight.type === "max" && (
                <Errormsg>올바른 몸무게를 입력하세요</Errormsg>
              )}
            </InputContainer>

            <Toggle setGender={setGender} />
            {/* 완료 버튼 */}
            {/* 완료 클릭 시 메인 페이지로 이동 */}
            <SubmitBtn
              // onClick={() => navigate('/')}
              type="submit"
              value="완료"
              className="ease-out duration-150
              "
            />
            {error && <Errormsg>{error}</Errormsg>}
          </Form>
        </Container>
      </div>
    </MainContainer>
  );
}
