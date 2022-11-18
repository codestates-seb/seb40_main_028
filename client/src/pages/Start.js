
import styled from "styled-components";

import { Link } from "react-router-dom";

import Logo from '../assets/img/Logo.png';


const Container = styled.div`
// 화면 중앙 고정
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  padding: 20px;
  width: 400px;
  height: 600px;
  
  // background-color: #ffffff;
  border-radius: 10px;

  //버튼 가운데 정렬
  // display: flex;
  // align-items: center;

  display: flex;

  // 요소들을 가로로 정렬
  flex-direction: column;

  // 가로 가운데 정렬
  align-items: center;
  // 세로 가운데 정렬
  justify-content: center;


  // 당신의 성장과 함께할 운동 기록 서비스 글자 색 변경
  //color: white;


  .title {
    margin-bottom: 30px;
    // font-size: px;
    font-weight: bold;
  }


`;

// 로고 이미지
const LogoPng = styled.img`
  // height: 37px;
  // width: 32px;
  // margin-bottom: 20px;

  height: 200px;
  width: 200px;
  margin-bottom: 10px;
  

  
  
`;


const Bt = styled.button`
  // width: 68px;
  // height: 100px;
  // padding: 10px;
  // color: white;
  // font-size: 13px;
  // background: rgb(88 101 242);


  width: 150px;
  height: 50px;
  font-size: 13.6px;
  font-weight: 600;
  color: white;
  background-color: rgb(88 102 242);
  border: 0;
  border-radius: 5px;
  padding: 0;
  margin-bottom: 5px;

  // 마진값이 들어간 영역을 클릭해도 온클릭 이벤트가 실행됨 주의하기!
  // margin-top: 30px;

  &:hover {
    background-color: rgb(71 82 196);
`;




const Start = () => {
  return (
    <>
      <div className="bg-d-dark w-full z-[1] h-12 max-w-lg justify-center text-lg px-10 font-medium fixed text-white border-b border-[#2C2F33] top-0  flex items-center">근로그</div>
      <div className="h-screen bg-d-lighter pt-14"></div>
      <Container>

        <LogoPng src={Logo} />
        당신의 성장과 함께할 운동 기록 서비스
        <span className="title">근로그</span>
        <Link to="/login">
          
          <Bt>login</Bt>
        </Link>
      </Container>
    </>
  );
};

export default Start;






