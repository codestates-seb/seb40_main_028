import styled from "styled-components";
import React from "react";
import { Link } from "react-router-dom";

import Logo from "../assets/img/Logo.png";

const Container = styled.div`
  // 화면 중앙 고정
  // position: fixed;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  padding: 20px;
  width: 400px;
  height: 600px;
  border-radius: 10px;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  // 당신의 성장과 함께할 운동 기록 서비스 글자 색 변경
  //color: white;

  .title {
    margin-bottom: 30px;
    font-weight: bold;
  }
  color: white;
  font-size: 1em;
`;

// 로고 이미지
const LogoPng = styled.img`
  margin-top: -1em;
  margin-bottom: -5em;
  border-radius: 20px;
`;

// const Example = styled.div`
//   margin-top: -40px;
//   padding-top: 10px;
//   font-size: 1em;
//   color: white;
//   background-color: rgb(88 102 242);
//   width: 100%;
//   height: 800px;
// `;

const Start = () => {
  return (
    <>
      <div className="bg-d-dark w-full z-[1] h-12 max-w-lg justify-center text-lg px-10 font-medium fixed text-white border-b border-[#2C2F33] top-0  flex items-center">
        근로그
      </div>
      <div className="h-screen bg-d-lighter pt-14" />
      <Container>
        <LogoPng src={Logo} />
        <div className="mt-6 mb-16 text-sm text-slate-200">
          {" "}
          당신의 성장과 함께할 운동 기록 서비스
        </div>

        <span className="title" />
        <Link to="/login">
          <div className="bg-d-button hover:bg-d-button-hover text-lg font-semibold w-[10em] h-[2.5em] flex justify-center items-center text-center rounded-md ">
            로그인
          </div>
        </Link>
      </Container>
      {/* <Example>아래로 스크롤하면 예시페이지 나타남</Example> */}
    </>
  );
};

export default Start;
