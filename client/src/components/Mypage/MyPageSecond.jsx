/* eslint-disable */
import axios from "axios";
import React, { useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from "styled-components/macro";
import Toggle from "../Toggle";
import PwModal from "./PwModal";
import SecessionModal from "./SecessionModal.jsx";
import Logout from "../../pages/Logout";

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
  font-size: 15px;
  font-weight: 600;
  text-align: left;
  color: white;
  margin-bottom: 0.2em;
  margin-top: 1em;
`;
const DisplayText2 = styled.div`
  font-size: 20px;
  text-align: left;
  color: white;
  margin-top:-1.36em;
  margin-left: 3em;
`;
const DisplayText3= styled.div`
  font-size: 15px;
  font-weight: 600;
  text-align: left;
  color: white;
  margin-top:0.6em;
`;

const InputInfo = styled.div`
  display: flex;
  flex-direction: column;
  width: 262px;
  margin: 1px 0px 1px;
  color: black;
`;

const Input = styled.input`
  width: 130px;
  height: 25px;
  border: 1px solid #babfc4;
  border-radius: 5px;
  &:focus {
    border: 1px solid #0995fd;
    box-shadow: 0 0 0 3.3px #ddeaf7;
  }
 
`;
const Input2 = styled.input`
  width: 250px;
  height: 25px;
  border: 1px solid #babfc4;
  border-radius: 5px;
  &:focus {
    border: 1px solid #0995fd;
    box-shadow: 0 0 0 3.3px #ddeaf7;
  }
`;


const Input3 = styled.input`
  width: 200px;
  height: 26px;
  border: 1px solid #babfc4;
  border-radius: 5px 0px 0px 5px;
  &:focus {
    border: 1px solid #0995fd;
    box-shadow: 0 0 0 3.3px #ddeaf7;
  }
`;

const Input4 = styled.input`
  width: 55px;
  height: 25px;
  border: 1px solid #babfc4;
  border-radius: 5px;
  display: flex;
  &:focus {
    border: 1px solid #0995fd;
    box-shadow: 0 0 0 3.3px #ddeaf7;
  }
`;
const ToggleDiv = styled.div`
margin: -1.8em 0em 1.7em -2.1em;
`;

const MyPageButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 5em;
  height: 2em;
  padding:10px;
  font-size: 15px;
  border: 0.01px solid #43549f;
  margin-left: 9.5em;
  margin-top: -3em;
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
  font-size: 2px;
  margin-left: 275px;
  :hover {
    background-color: #4C53BF;
    border: 2px solid #3C53BF;
  }
  background-color: #747BF2;
  border: 2px solid #737BF2;
  border-radius: 4px;
  cursor: pointer;
`;
const MyPageButton3 = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 80px;
  height: 15px;
  padding:15px;
  font-size: 1px;
  color: black;
  margin-left: 275px;
  margin-top: 5em;
  margin-bottom: -20em;
  :hover {
    background-color: #2C53BF;
  }
  background-color: #547BF2;
  border-radius: 4px;
  cursor: pointer;
`;

const SerchButton = styled.button`
  margin-top: -1.64em;
  margin-left: 12.5em;
  width: 50px;
  background-color: #747BF2;
  border: 1px solid #737BF2;
  border-radius: 0px 5px 5px 0px;
  color: white;
  cursor: pointer;
  :hover {
    background-color: #4C53BF;
    border: 1px solid #3C53BF;
  }
`;
const PageText = styled.div`
display: flex;
align-items: center;
justify-content: center;
font-size: 1.2em;
width: 25rem;
margin-left: -1em;
font-weight: 400;
`;


const MyPageSecond = () => {

  const navigate = useNavigate();
  //인풋값들
  const heightInputRef = useRef();
  const weightInputRef = useRef();
  const nameInputRef = useRef();
  const ageInputRef = useRef();

  const [gender, setGender] = useState("W") //토글
//get해올 값들
  const [name, setName] = useState('');
  // const [sex, setSex] = useState('');
  const [age, setAge] = useState('');
  const [height, setHeight] = useState('');
  const [weight, setWeight] = useState('');

  axios
  .get('/users/mypages/info',{
      name:setName,
      age:setAge,
      height:setHeight,
      weight:setWeight
    })
    

  const handleOnClick = (event) => {
    event.preventDefault();
    const enteredName = nameInputRef.current.value;
    const enteredHeight= heightInputRef.current.value;
    const enteredWeight = weightInputRef.current.value;
    const enteredAge = ageInputRef.current.value;

    if (enteredName.length < 3) {
      alert('닉네임은 3자 이상으로 입력하세요!');
    }else
      axios
      .post(`http://localhost:8080/posts`,{
        nickname: enteredName,
        height: enteredHeight,
        weight: enteredWeight,
        age:enteredAge,
        gender:gender
      },
      {
        headers: {
          Authorization: `${localStorage.getItem('login-token')}`,
      },
      },
      )
      // .then(() => {
      //   if (res.ok) {
      //     return (
      //       alert(`${enteredName}님 정보수정 완료!`),
      //       navigate('/Mypage')
      //     );
      //   } else {
      //     return (
      //       alert('내 정보 수정을 실패하셨습니다. '),
      //       navigate('/')
      //   );
      // }
      // })
  };

  const handleOnClick2 = () => {
    event.preventDefault();
  };
 

  const [PwModalOn,setPwModalOn] = useState(false);
  const [SeModalOn,setSeModalOn] = useState(false);
  const [isOpen, setIsOpen] = useState(false);
  return (
    <>
    <form onSubmit={handleOnClick}>
      <MyPageForm>
        <PageText>정보수정</PageText>
        <MyPageButton2 type="button" onClick={() => setIsOpen(true)}
          className='modalButton'>Logout</MyPageButton2>
          <Logout open={isOpen} 
          onClose={() => setIsOpen(false)} />
        <MyPageButton3 type="button" onClick={() => setSeModalOn(true)}
          className='modalButton2'>회원탈퇴</MyPageButton3>
          <SecessionModal open={SeModalOn} 
          onClose={() => setSeModalOn(false)} />
        <InputInfo className="displayName">
          <DisplayText>Nickname</DisplayText>
          <Input type="text" id="displayname"  required ref={nameInputRef} />
        </InputInfo>
        <InputInfo className="displayMail">
          <DisplayText>Email</DisplayText>
          <Input2  type="mail" id="displayMail"  placeholder="이메일은 변경 불가능합니다." readOnly/>
        </InputInfo>
        <InputInfo className="password">
          <DisplayText>Password</DisplayText>
          <Input3 type="password" id="password" placeholder= "변경을 눌러주세요." readOnly />
          <SerchButton type="button" onClick={() => setPwModalOn(true)}
          className='modalButton'>변경</SerchButton>
          <PwModal open={PwModalOn} 
          onClose={() => setPwModalOn(false)} />
        </InputInfo>
        <InputInfo className="displayWidth">
          <DisplayText>Age</DisplayText>
          <Input type="date" id="birthday" name="birthday" required ref={ageInputRef} />
          </InputInfo>
          <InputInfo className="displayHeight">
          <DisplayText>Height</DisplayText>
          <Input4 type="number" id="displayHeight"  required ref={heightInputRef}  />
          <DisplayText2 className="h1">CM</DisplayText2>
        </InputInfo>
        <InputInfo className="displayWidth">
          <DisplayText>Weight</DisplayText>
          <Input4 type="number" id="displayweight" required ref={weightInputRef}/>
          <DisplayText2 className="h1">KG</DisplayText2>
          <DisplayText3>Sex</DisplayText3>
        </InputInfo>
        <ToggleDiv>
        <Toggle setGender={setGender}  />
        </ToggleDiv>
        <MyPageButton type="button" onClick={handleOnClick}>Save</MyPageButton>
      </MyPageForm>
      </form>
      </>
  )
}

export default MyPageSecond;