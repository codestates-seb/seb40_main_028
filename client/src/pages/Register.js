
import RegisterContainer from '../components/RegisterContainer';

import styled from "styled-components";

const Container = styled.div`
  // padding-top: 90px;
  // width: 100vw;

  height: 100%;

  display: flex;
	flex-direction : column;
  align-items: center;
  justify-content: center;
  

  // 확인하고 지우기
  // padding-top: 60px;

`;

const Register = () => {
  return (
    <>
      <div className="bg-d-dark w-full z-[1] h-12 max-w-lg justify-center text-lg px-10 font-medium fixed text-white border-b border-[#2C2F33] top-0  flex items-center">근로그</div>
      <div className="h-screen bg-d-lighter pt-14">
        
        <Container>
          
        
          <RegisterContainer></RegisterContainer>
        </Container>


      </div>
  

    </>
  );
};
export default Register;
