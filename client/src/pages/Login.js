// import Layout from '../components/Layout';
import styled from "styled-components";

import LoginContainer from '../components/LoginContainer';

const Container = styled.div`
  padding-top: 90px;
  // width: 100vw;
  height: 100%;

  display: flex;
  align-items: center;
  justify-content: center;
  

  // 확인하고 지우기
  padding-top: 200px;

`;


const Login = () => {
  return (
    <>

      <Container>
        {/* 타이틀 배경색 */}
        <div className="bg-d-dark w-full z-[1] h-12 max-w-lg justify-center text-lg px-10 font-medium fixed text-white border-b border-[#2C2F33] top-0  flex items-center">근로그</div>
        <div className="text-gray-700 h-screen text-medium text-xl text-center mt-5">
          <LoginContainer></LoginContainer>
        </div>


      </Container>
     




    </>
  );
};
export default Login;
