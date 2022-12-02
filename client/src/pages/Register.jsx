import React from "react";
import styled from "styled-components";
import RegisterContainer from "../components/RegisterContainer";
import Logo from "../assets/img/Logo.png";

const Container = styled.div`
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

const LogoPng = styled.img`
  height: 500px;
  width: 500px;
  margin-top: -5em;
  margin-bottom: -5em;
  border-radius: 20px;
`;

const Register = () => {
  return (
    <>
      <div className="bg-d-dark w-full z-[1] h-12 max-w-lg justify-center text-lg px-10 font-medium fixed text-white border-b border-[#2C2F33] top-0  flex items-center">
        근로그
      </div>
      <div className="h-screen bg-d-lighter pt-14">
        <Container>
          <LogoPng src={Logo} />
          <RegisterContainer />
        </Container>
      </div>
    </>
  );
};
export default Register;
