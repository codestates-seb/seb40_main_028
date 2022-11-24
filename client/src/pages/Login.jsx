import React from "react";
import styled from "styled-components";
import LoginContainer from "../components/LoginContainer";
import Logo from "../assets/img/Logo.png";

const Container = styled.div`
  height: 100%;
  display: flex;
	flex-direction : column;
  align-items: center;
  justify-content: center;
`;

const LogoPng = styled.img`
  height: 200px;
  width: 200px;
  margin-bottom: 40px;
  border-radius: 20px;
`;

const Login = () => {
  return (
    <>
      <div className="bg-d-dark w-full z-[1] h-12 max-w-lg justify-center text-lg px-10 font-medium fixed text-white border-b border-[#2C2F33] top-0  flex items-center">근로그</div>
      <div className="h-screen bg-d-lighter pt-14">
        <Container>
          {/* 로고 */}
          <LogoPng src={Logo} />
          <LoginContainer />
        </Container>
      </div>
    </>
  );
};
export default Login;
