/* eslint-disable */
import styled from "styled-components";
import React,{ useRef} from "react";
import { useNavigate } from "react-router-dom";

const Container = styled.form`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 25em;
  color: black;
  .modalButton {
  position: fixed;
  top: 40%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 10000;
  padding: 12px 24px;
}
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
  top: 30%;
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
  margin-bottom: 0.5em;
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

  if (!open) return null;
  const handleOnClick = (event) => {
    event.preventDefault();
    const enteredPassword = passwordInputRef.current.value;
    const enteredNewPassword = newPasswordInpitRef.current.value;
    const enteredNewPassword2 = newPassword2InpitRef.current.value;

    if (!(enteredNewPassword === enteredNewPassword2)) {
      alert("새로운 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
    } else if (enteredPassword === enteredNewPassword) {
      alert("새로운 비밀번호와 현재 비밀번호가 일치합니다.");
    }
    else
      axios({
        method: "patch",
        url:"/users/mypages/password",
        data:{
          password: enteredPassword,
          newPassword: enteredNewPassword,
          // returnSecureToken: true,
        },
        headers: { "Authorization" : localStorage.getItem("access_token") },
        responseType:"json",
      }
      ).then((res) => {
        if (res.ok) {
          return (
            alert("변경완료"),
            navigate("/Mypage")
          );
        } else {
          return (
            alert("비밀번호 변경을 실패하셨습니다. "),
            res.JSON().then((data) => {
              console.log(data), navigate("/");
            })
          );
        }
      });
  };
 


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
              <DisplayText>현재 패스워드</DisplayText>
              <Input type="password" id="password" required ref={passwordInputRef} />
              <DisplayText>새로운 패스워드</DisplayText>
              <Input type="password" id="newpassword" required ref={newPasswordInpitRef} />
              <DisplayText>새로운 패스워드 확인</DisplayText>
              <Input type="password" id="newpassword2"  required ref={newPassword2InpitRef}/>
            </InputInfo>
            <MyPageButton className='btnContainer' onClick={handleOnClick} >변경완료</MyPageButton>
         
         
            
          </div>
        </div>
      </div>
    </Container>
  );
};

export default PwModal;