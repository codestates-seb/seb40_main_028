// import Layout from '../components/Layout';
import styled from 'styled-components';


import Logout from './Logout';

// import LoginContainer from '../components/LoginContainer';

const Container = styled.div`
  padding-top: 90px;
`;


const Login = () => {
  return (
    <>

    <Container>
      {/* 타이틀 배경색 */}
      <div className="bg-d-dark w-full z-[1] h-12 max-w-lg justify-center text-lg px-10 font-medium fixed text-white border-b border-[#2C2F33] top-0  flex items-center">근로그</div>
      <div className="text-gray-700 h-screen text-medium text-xl text-center mt-5">
        login Page
        
      <Logout/>
      
     </div>


    </Container>
     




    </>
  );
};
export default Login;
