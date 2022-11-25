/* eslint-disable */

import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import axios from "axios";

// 모달 로그아웃 버튼
const L = styled.button`
  width: 100px;
  height: 100px;
  padding: 10px;
  color: white;
  font-size: 13px;
  background: rgb(88 101 242);
  border-radius: 10px;
`;

// 모달 실행시 배경이미지
const ModalBackdrop = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  background-color: rgba(0, 0, 0, 0.5);
  width: 100vw;
  height: 100vh;
`;

const BtnContainer = styled.div`
// 화면 중앙 고정
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  padding: 20px;
  width: 200px;
  height: 150px;
  background-color: #ffffff;
  border-radius: 10px;

  display: flex;
  align-items: center;
`;

const LogoutBtn = styled.button`
  width: 100%;
  height: 50%;
  padding: 10px;
  color: white;
  background: rgb(88 101 242);
  font-size: 13px;
  font-weight: 600;
  border: 0;
  border-radius: 10px;
  margin-right: 20px;

  display: flex;
  justify-content: center;
  align-items: center;

  &:hover {
    background: rgb(71 82 196)
  };
`;

const CancleBtn = styled.button`
  width: 100%;
  height: 50%;
  padding: 10px;
  color: rgb(88 101 242);
  background: white;
  font-size: 13px;
  font-weight: 600;
  border: 1px solid ;
  border-radius: 10px;

  display: flex;
  justify-content: center;
  align-items: center;

  &:hover {
    background: rgb(241, 242, 244);
  };
`;



const Container = styled.div`
  height: 100%;
  display: flex;
	flex-direction : column;
  align-items: center;
  justify-content: center;
`;

const Logout = () => {
  const navigate = useNavigate();
  const [isOpen, setIsOpen] = useState(false);
  const openModalHandler = () => {
    setIsOpen(!isOpen);
  };



  const logoutHandler = async () => {

    const url = "http://13.209.190.35:8080";

    const token = sessionStorage.getItem("jwt-token");
    // const res = await axios.post(`${url}/users/signup`)
    // const res = await axios.post(`${url}/users/signup`, {
    // 보내고자 하는 데이터
    //   email: data.email,
    //   nickname: data.nickname,
    //   password: data.password
    // });
    try {
      const res = await axios.delete(`${url}/logout`, {
        headers: {
          Authorization: `${token}`,
        },
      });

      // 세션에 있는 jwt 토큰 삭제
     
      if (res.status === 200) {

 sessionStorage.removeItem("jwt-token");
        // 정보 입력 완료하면 메인페이지로 이동 
        navigate("/login");
      }
      

    } catch (err) {
    // 응답 실패
      console.log(err);
      // console.log("로그아웃 실패")

      // 로그아웃 실패시 
      alert("로그아웃 실패");
    }




  };



  return (
    <>
      <div className="bg-d-dark w-full z-[1] h-12 max-w-lg justify-center text-lg px-10 font-medium fixed text-white border-b border-[#2C2F33] top-0  flex items-center">근로그</div>
      <div className="h-screen bg-d-lighter pt-14">
        <Container>
          <L onClick={openModalHandler}>logout</L>
          {isOpen ? (<ModalBackdrop onClick={openModalHandler}>
            <BtnContainer>
              {/* 로그인 버튼 클릭 시 홈으로 페이지 이동 수정하고 지우기*/}
              <LogoutBtn  
                onClick={logoutHandler} 
              >Logout</LogoutBtn>
              <CancleBtn >Cancle</CancleBtn>
            </BtnContainer>
          </ModalBackdrop>
          ) : null}
        </Container>
      </div>
    </>
  );
};
export default Logout;
