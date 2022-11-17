
import Layout from "../components/Layout";
import styled from "styled-components";

import { Link, useNavigate } from "react-router-dom";

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
  
  background-color: #ffffff;
  border-radius: 10px;

  //버튼 가운데 정렬
  // display: flex;
  // align-items: center;

  display: flex;

  // 가로로 정렬
  flex-direction: column;

  // 가로 가운데 정렬
  align-items: center;
  // 세로 가운데 정렬
  justify-content: center;

`;

// 로고 이미지
const LogoPng = styled.img`
  // height: 37px;
  // width: 32px;
  // margin-bottom: 20px;

  height: 200px;
  width: 200px;
  margin-bottom: 20px;
  
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
  border-radius: 3px;
  padding: 0;
  margin-bottom: 5px;
  &:hover {
    background-color: rgb(71 82 196);
`;





const Start = () => {
  return (
    
    <Layout title="근로그" hasTabBar>
      <div className="text-gray-700 h-screen text-medium text-xl text-center mt-5">
        <Container>

          <LogoPng src={Logo} />

          <Link to="/login">
            <Bt>login으로 이동</Bt>
          </Link>
        </Container>
      </div>
    </Layout>
  );
};

export default Start;






