/* eslint-disable */
import axios from "axios";
import React, { useRef } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import { LoginState, TokenState } from "../../state/UserState";
import { useRecoilValue } from "recoil";

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 25em;
  color: black;
 
.overlay {
  background-color: rgba(0, 0, 0, 0.5);
  position: fixed;
  width: 120em;
  height: 120em;
  z-index: 100000;;
}

.modalContainer {
  max-width: 600px;
  width: 20em;
  position: fixed;
  top: 43%;
  left: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  background-color: #ffffff;
  box-shadow: 0px 0px 18px 0px rgba(0, 0, 0, 0.75);
  border-radius: 8px;
}
.closeBtn{
  margin-left: 18em;
  margin-top: 0.5em;
  color: black;
  cursor: pointer;
}
`;
const Input = styled.input`
  width: 200px;
  height: 28px;
  border: 1px solid #babfc4;
  border-radius: 5px 0px 0px 5px;
  &:focus {
    border: 1px solid #0995fd;
    box-shadow: 0 0 0 3.3px #ddeaf7;
  }
`;
const DisplayText = styled.div`
  font-size: 17px;
  font-weight: 600;
  text-align: left;
  color: black;
  margin-bottom: 0.2em;
  margin-top: 0.2em;
`;

const InputInfo = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 262px;
  color: black;
  margin-left: 2em;
`;

const MyPageButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 7em;
  height: 2em;
  padding:12px;
  font-size: 12px;
  border: 0.01px solid #43549f;
  color: white;
  margin-left: 9.5em;
  margin-bottom: 2em;
  margin-top: 2em;
  :hover {
    background-color: #4C53BF;
    border: 2px solid #3C53BF;
  }
  background-color: #747BF2;
  border: 2px solid #737BF2;
  border-radius: 4px;
  cursor: pointer;
`;

const PwModal = ({ open, onClose }) => {
  const navigate = useNavigate();
  const passwordInputRef = useRef();
  const newPasswordInpitRef = useRef();
  const newPassword2InpitRef = useRef();

  const login = useRecoilValue(LoginState);
  // 토큰
  const token = useRecoilValue(TokenState);
  // url주소
  const url = "http://13.209.190.35:8080";
  if (login === false) {
    alert("로그인이 안 되어 있습니다.");
    navigate("/login");
  }
  if (!open) return null;
  const handleOnClick = (event) => {
    event.preventDefault();
    const enteredPassword = passwordInputRef.current.value;
    const enteredNewPassword = newPasswordInpitRef.current.value;
    const enteredNewPassword2 = newPassword2InpitRef.current.value;
    let regPass = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=+-])(?=.*[0-9]).{9,25}$/;
    if(
      !regPass.test(enteredNewPassword)
    ){
      alert("최소길이는 10자이상, 특수문자,영문대소문자,숫자를 포함하세요")
    }
    else if (!(enteredNewPassword === enteredNewPassword2)) {
      alert("새로운 패스워드와 패스워드 확인이 일치하지 않습니다.");
    } else if (enteredPassword === enteredNewPassword) {
      alert("새로운 패스워드와 현재 패스워드가 일치합니다.");
    }
    else{
     axios
     .patch(`${url}/users/mypages/password`,
        {
          password: enteredPassword,
          newPassword: enteredNewPassword
          // returnSecureToken: true,
        },
        {
        headers: { Authorization : `${token}`},
      }
      )
      .then(() => {
            alert("패스워드 변경완료"),
            navigate("/");

        // else {
        //   return (
        //     alert("비밀번호 변경을 실패하셨습니다. "),
        //     res.JSON().then((data) => {
        //       console.log(data), navigate("/");
        //     })
        //   );
        // }
      })
      .catch((data) => {
        alert("현재 패스워드가 일치하지 않습니다.")
        console.log(data);
      });
  };
}


  return (
    <Container>
      <div onClick={onClose} className='overlay'>
        <div
          onClick={(e) => {
            e.stopPropagation();
          }}
          className='modalContainer'
        >
          <div className='modalRight'>
            <p className='closeBtn' onClick={onClose}>
            X
            </p>
          
            <InputInfo className="password">
              <DisplayText>Current Password</DisplayText>
              <Input type="password" id="password" required ref={passwordInputRef} autoComplete="off" />
              <DisplayText>New Password</DisplayText>
              <Input type="password" id="newpassword" required ref={newPasswordInpitRef} autoComplete="off" />
              <DisplayText>Confirm New Password</DisplayText>
              <Input type="password" id="newpassword2"  required ref={newPassword2InpitRef} autoComplete="off" />
            </InputInfo>
            <MyPageButton className='btnContainer' onClick={handleOnClick} >Save</MyPageButton>
         
         
            
          </div>
        </div>
      </div>
    </Container>
  );
};

export default PwModal;