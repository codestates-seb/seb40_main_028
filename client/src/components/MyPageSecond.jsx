import styled from "styled-components/macro";
import React from "react";
// import {
//    useEffect,useState,useUserState,useUserDispatch,useInput,useCallback
// } from 'react';

const Container = styled.div`
display: flex;
justify-content: center;
align-items: center;
justify-content: center;
margin-top: 45px;
align-items:center;
`;


const MyPageForm = styled.form`
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 25rem;
  height: 40rem;
  border-radius: 8px;
  background-color: #2C2F33;
  border: none;
  box-shadow: 0 0 5px 2px rgba(0, 0, 0, 0.3);
  margin-top: 5px;
  padding: 24px;
  color: white;
  .h2{
    font-size: 18px;
    margin: -15px 0px 20px 1px;
 
  }
`;

const DisplayText = styled.div`
  font-size: 17px;
  font-weight: 600;
  margin-bottom: 6px;
  text-align: left;
  color: white;
`;

const InputInfo = styled.div`
  display: flex;
  flex-direction: column;
  height: 90px;
  width: 262px;
  margin: 5px 0px 1px;
  color: black;
`;

const Input = styled.input`
  width: 130px;
  height: 28px;
  border: 1px solid #babfc4;
  border-radius: 4px;
  &:focus {
    border: 1px solid #0995fd;
    box-shadow: 0 0 0 3.3px #ddeaf7;
  }
 
`;

const Input2 = styled.input`
  width: 260px;
  height: 28px;
  border: 1px solid #babfc4;
  border-radius: 4px;
  &:focus {
    border: 1px solid #0995fd;
    box-shadow: 0 0 0 3.3px #ddeaf7;
  }
`;

const Input3 = styled.input`
  width: 70px;
  height: 28px;
  border: 1px solid #babfc4;
  border-radius: 4px;
  &:focus {
    border: 1px solid #0995fd;
    box-shadow: 0 0 0 3.3px #ddeaf7;
  }
`;

const MyPageButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 60px;
  height: 10px;
  padding:15px;
  font-size: 15px;
  border: 0.01px solid #43549f;
  color: white;
  margin: 6px 0px 40px 140px;
  :hover {
    background-color: #4C53BF;
    border: 2px solid #3C53BF;
  }
  background-color: #747BF2;
  border: 2px solid #737BF2;
  border-radius: 4px;
  cursor: pointer;
`;
const MyPageButton2 = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 80px;
  height: 15px;
  padding:15px;
  font-size: 1px;
  color: white;
  margin: -50px 0px 30px 260px;
  :hover {
    background-color: #4C53BF;
  }
  background-color: #747BF2;
  border-radius: 4px;
  cursor: pointer;
`;
const SerchButton = styled.button`
  margin-top: -30px;
  margin-left: 270px;
  width: 70px;
  background-color: #747BF2;
  border: 2px solid #737BF2;
  border-radius: 4px;
  color: white;
  cursor: pointer;
  :hover {
    background-color: #4C53BF;
    border: 2px solid #3C53BF;
  }
`;


function MyPageSecond() {
  
  return (
    <Container>
      <MyPageForm>
        <div className='h2'>
        정보 수정</div>
        <InputInfo className="displayName">
          <DisplayText>닉네임</DisplayText>
          <Input type="text" id="displayname"  />
        </InputInfo>
        
        <InputInfo className="displayMail">
          <DisplayText>이메일</DisplayText>
          <Input2  type="mail" id="displayMail"/>
        </InputInfo>
        <InputInfo className="password">
          <DisplayText>패스워드</DisplayText>
          <Input2 type="password" id="password"  />
          <SerchButton>변경</SerchButton>
        </InputInfo>

        <InputInfo className="displayHeight">
          <DisplayText>신장</DisplayText>
          <Input3 type="name" id="displayHeight"  />
        </InputInfo>
        <InputInfo className="displayWidth">
          <DisplayText>몸무게</DisplayText>
          <Input3 type="name" id="displayWidth"  />
        </InputInfo>
        <InputInfo className="displayWidth">
          <DisplayText>나이</DisplayText>
          <Input3 type="age" id="displayAge"  />
        </InputInfo>
        <MyPageButton>저장</MyPageButton>
        <MyPageButton2>회원탈퇴</MyPageButton2>
      </MyPageForm>
    </Container>
  )
}

export default MyPageSecond;