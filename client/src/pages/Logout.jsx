/* eslint-disable */

import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import axios from "axios";

import { useRecoilState, useResetRecoilState } from "recoil";
import { LoginState, TokenState } from "../state/UserState";

// // 모달 로그아웃 버튼
// const L = styled.button`
//   width: 80px;
//   height: 15px;
//   padding: 10px;
//   color: white;
//   font-size: 13px;
//   background: rgb(88 101 242);
//   border-radius: 10px;
// `;

// // 모달 실행시 배경이미지
// const ModalBackdrop = styled.div`
//   position: fixed;
//   top: 0;
//   left: 0;
//   background-color: rgba(0, 0, 0, 0.5);
//   width: 100vw;
//   height: 100vh;
// `;

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
    background: rgb(71 82 196);
  }
`;

const CancleBtn = styled.button`
  width: 100%;
  height: 50%;
  padding: 10px;
  color: rgb(88 101 242);
  background: white;
  font-size: 13px;
  font-weight: 600;
  border: 1px solid;
  border-radius: 10px;

  display: flex;
  justify-content: center;
  align-items: center;

  &:hover {
    background: rgb(241, 242, 244);
  }
`;

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
    z-index: 100000;
  }

  .modalContainer {
    max-width: 600px;
    width: 20em;
    position: fixed;
    top: 40%;
    left: 50%;
    transform: translate(-50%, -50%);
    display: flex;
    background-color: #ffffff;
    box-shadow: 0px 0px 18px 0px rgba(0, 0, 0, 0.75);
    border-radius: 8px;
  }
`;

const Logout = ({ open, onClose }) => {
  if (!open) return null;
  const navigate = useNavigate();

  // const [isLogin, setIsLogin] = useRecoilState(LoginState);
  // const [isToken, setToken] = useRecoilState(TokenState);

  const logoutHandler = async () => {
    const url = "http://13.209.190.35:8080";

    // const token = sessionStorage.getItem("jwt-token");
    // const res = await axios.post(`${url}/users/signup`)
    // const res = await axios.post(`${url}/users/signup`, {
    // 보내고자 하는 데이터
    //   email: data.email,
    //   nickname: data.nickname,
    //   password: data.password
    // });

    // try {
    //   const res = await axios.delete(`${url}/logout`, {
    //     headers: {
    //       Authorization: `${token}`,
    //     },
    //   });

    //   // 세션에 있는 jwt 토큰 삭제

    //   if (res.status === 200) {
    //     sessionStorage.removeItem("jwt-token");
    //     // 정보 입력 완료하면 메인페이지로 이동
    //     navigate("/login");
    //   }
    // } catch (err) {
    //   // 응답 실패
    //   console.log(err);
    //   // console.log("로그아웃 실패")

    //   // 로그아웃 실패시
    //   alert("로그아웃 실패");
    // }

    useResetRecoilState(TokenState);
    console.log(useRecoilValue(TokenState), "토큰 리셋");
    if (TokenState === null) {
      useResetRecoilState(LoginState);
      console.log(useRecoilValue(LoginState), "로그인 리셋");
    }
  };

  return (
    <Container>
      <div onClick={onClose} className="overlay">
        <div
          onClick={(e) => {
            e.stopPropagation();
          }}
          className="modalContainer"
        >
          <div className="modalRight">
            <BtnContainer>
              {/* 로그인 버튼 클릭 시 홈으로 페이지 이동 수정하고 지우기*/}
              <LogoutBtn onClick={logoutHandler}>Logout</LogoutBtn>
              <CancleBtn onClick={onClose}>Cancle</CancleBtn>
            </BtnContainer>
          </div>
        </div>
      </div>
    </Container>
  );
};
export default Logout;
