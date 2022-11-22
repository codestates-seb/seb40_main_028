/* eslint-disable */
import styled from "styled-components/macro";
import Toggle from "./Toggle";
import React,{ useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from "axios";

const MyPageForm = styled.div`
  display: flex;
  height: 40rem;
  flex-direction: column;
  justify-content: center;
  padding: 24px;
  color: white;
  width: 25em;
`;

const DisplayText = styled.div`
  font-size: 17px;
  font-weight: 600;
  text-align: left;
  color: white;
  margin-bottom: 0.6em;
`;
const DisplayText2 = styled.div`
  font-size: 20px;
  text-align: left;
  color: white;
  margin-top:-1.5em;
  margin-left: 3em;
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
  border-radius: 5px;
  &:focus {
    border: 1px solid #0995fd;
    box-shadow: 0 0 0 3.3px #ddeaf7;
  }
 
`;
const Input2 = styled.input`
  width: 250px;
  height: 28px;
  border: 1px solid #babfc4;
  border-radius: 5px;
  &:focus {
    border: 1px solid #0995fd;
    box-shadow: 0 0 0 3.3px #ddeaf7;
  }
`;


const Input3 = styled.input`
  width: 200px;
  height: 28px;
  border: 1px solid #babfc4;
  border-radius: 5px 0px 0px 5px;
  &:focus {
    border: 1px solid #0995fd;
    box-shadow: 0 0 0 3.3px #ddeaf7;
  }
`;

const Input4 = styled.input`
  width: 50px;
  height: 28px;
  border: 1px solid #babfc4;
  border-radius: 5px;
  display: flex;
  &:focus {
    border: 1px solid #0995fd;
    box-shadow: 0 0 0 3.3px #ddeaf7;
  }
`;
const ToggleDiv = styled.div`
margin: 0em 0em -0.2em -2em;
`;

const MyPageButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 5em;
  height: 2em;
  padding:12px;
  font-size: 15px;
  border: 0.01px solid #43549f;
  color: white;
  margin-left: 9.5em;
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
  color: black;
  margin-left: 270px;
  margin-top: 11em;
  :hover {
    background-color: #2C53BF;
  }
  background-color: #547BF2;
  border-radius: 4px;
  cursor: pointer;
`;
const SerchButton = styled.button`
  margin-top: -1.75em;
  margin-left: 12.5em;
  width: 50px;
  background-color: #747BF2;
  border: 2px solid #737BF2;
  border-radius: 0px 5px 5px 0px;
  color: white;
  cursor: pointer;
  :hover {
    background-color: #4C53BF;
    border: 2px solid #3C53BF;
  }
`;
const PageText = styled.div`
display: flex;
align-items: center;
justify-content: center;
font-size: 1.5em;
width: 25rem;
margin-top: 0.7em;
margin-left: -1em;
`;


const MyPageSecond = () => {
  const navigate = useNavigate();
  const heightInputRef = useRef();
  const weightInputRef = useRef();
  const nameInputRef = useRef();
  const ageInputRef = useRef();

  const handleOnClick = (event) => {
    event.preventDefault();
    const enteredName = nameInputRef.current.value;
    const enteredHeight= heightInputRef.current.value;
    const enteredWeight = weightInputRef.current.value;
    const enteredAge = ageInputRef.current.value;

    if (enteredName.length < 3) {
      alert('이름은 3자 이상으로 입력하세요!');
    }else
      axios({
          method: 'POST',
          url:``,
          data:{
            username: enteredName,
            height: enteredHeight,
            weight: enteredWeight,
            age:enteredAge
            // returnSecureToken: true,
          },
          headers: { "Authorization" : localStorage.getItem('access_token') },
          responseType:`json`,
        }
      ).then((res) => {
        if (res.ok) {
          return (
            alert(`${enteredName}님 수정완료.`),
            navigate('/Mypage')
          );
        } else {
          return (
            alert('내 정보 수정을 실패하셨습니다. '),
            res.JSON().then((data) => {
              console.log(data), navigate('/');
            })
          );
        }
      });
  };
  const handleOnClick2 = () => {
    
    
  }

  const handleOnClick3 = () => {
    axios.delete("").then(function(response){
      console.log(response);
        })
  
}

  
  return (
    <form onSubmit={handleOnClick}>
      <MyPageForm>
        <PageText>정보수정</PageText>
        <MyPageButton2 type="button" onClick={handleOnClick3}>회원탈퇴</MyPageButton2>
        <InputInfo className="displayName">
          <DisplayText>닉네임</DisplayText>
          <Input type="text" id="displayname"  required ref={nameInputRef} />
        </InputInfo>
        <InputInfo className="displayMail">
          <DisplayText>이메일</DisplayText>
          <Input2  type="mail" id="displayMail" readOnly/>
        </InputInfo>
        <InputInfo className="password">
          <DisplayText>패스워드</DisplayText>
          <Input3 type="password" id="password" readOnly />
          <SerchButton type="button" onClick={handleOnClick2}>변경</SerchButton>
        </InputInfo>
        <ToggleDiv>
        <Toggle />
        </ToggleDiv>
        <InputInfo className="displayHeight">
          <DisplayText>신장</DisplayText>
          <Input4 type="number" id="displayHeight" required ref={heightInputRef}  />
          <DisplayText2 className="h1">CM</DisplayText2>
        </InputInfo>
        <InputInfo className="displayWidth">
          <DisplayText>몸무게</DisplayText>
          <Input4 type="number" id="displayweight"  required ref={weightInputRef}/>
          <DisplayText2 className="h1">KG</DisplayText2>
        </InputInfo>
        <InputInfo className="displayWidth">
          <DisplayText>나이</DisplayText>
          <Input4 type="number" id="displayAge" required ref={ageInputRef} />
        </InputInfo>
        <MyPageButton type="button" onClick={handleOnClick}>저장</MyPageButton>
      </MyPageForm>
      </form>
  )
}

export default MyPageSecond;