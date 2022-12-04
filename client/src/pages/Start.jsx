import styled from "styled-components";
import React from "react";
import { Link } from "react-router-dom";

import Logo from "../assets/img/Logo.png";
import Main from "../assets/img/Main.gif";
import Workout from "../assets/img/Workout.gif";

const Container = styled.div`
  // 화면 중앙 고정
  // position: fixed;
  // position: absolute;
  // top: 50%;
  // left: 50%;
  // transform: translate(10%, 10%);

  margin-top: -100px;
  // width: 400px;
  // height: 600px;
  border-radius: 10px;

  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  .title {
    margin-bottom: 30px;
    font-weight: bold;
  }
  color: white;
  font-size: 1em;
`;

// 로고 이미지
const LogoPng = styled.img`
  height: 500px;
  width: 500px;
  // margin-bottom: 1px;
`;

// 예시 gif
const Gif = styled.img`
  height: 700px;
  width: 350px;
  margin-top: 20px;
  margin-bottom: 10px;
`;

const Bt = styled.button`
  width: 5em;
  height: 50px;
  font-size: 1.5em;
  font-weight: 600;
  color: white;
  background-color: rgb(88 102 242);
  border: 0;
  border-radius: 5px;
  padding: 0;
  margin-bottom: 5px;

  &:hover {
    background-color: rgb(71 82 196);
`;

const Example = styled.div`
  // margin-top: 100px;
  padding-top: 10px;
  font-size: 1em;
  color: white;
  background-color: rgb(88 102 242);
  width: 100%;
  // height: 100vh;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

const Start = () => {
  return (
    <>
      <div className="bg-d-dark w-full z-[1] h-12 max-w-lg justify-center text-lg px-10 font-medium fixed text-white border-b border-[#2C2F33] top-0  flex items-center">
        근로그
      </div>
      {/* <div className="h-screen bg-d-lighter pt-14" /> */}
      <Container>
        <LogoPng src={Logo} />
        당신의 성장과 함께할 운동 기록 서비스
        <span className="title" />
        <Link to="/login">
          <Bt>login</Bt>
        </Link>
      </Container>
      <Example>
        근로그 이용 예시를 보려면 아래로 스크롤 하세요
        <Gif src={Main} />
        <Gif src={Workout} />
      </Example>
    </>
  );
};

export default Start;
