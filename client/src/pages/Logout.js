
import { useState } from "react";
import styled from 'styled-components';


// 모달 로그아웃 버튼
const L = styled.button`
  width: 68px;
  height: 100px;
  padding: 10px;
  color: white;
  font-size: 13px;
  background: red;
`;

// 모달 실행시 배경이미지
const ModalBackdrop = styled.div`
  background-color: rgba(0, 0, 0, 0.5);
  width: 100vw;
  height: 100vh;  
`;

const BtnContainer = styled.div`
// 화면 중앙 고정
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  padding: 20px;
  width: 200px;
  height: 150px;

  display: flex;
  align-items: center;
  
  background-color: #ffffff;
  border-radius: 10px;

`;

const LogoutBtn = styled.button`
  width: 100%;
  height: 50px;
  padding: 10px;
  color: white;
  background: rgb(88 101 242);
  font-size: 13px;
  box-shadow: rgba(255, 255, 255, 0.4) 0px 1px 0px 0px inset;
  font-weight: 600;
  border: 0;
  border-radius: 10px;
  &:hover {
    background: rgb(71 82 196);
`;

const CancleBtn = styled.button`
  width: 100%;
  height: 50px;
  padding: 10px;
  color: rgb(88 101 242);
  background: white;
  font-size: 13px;
  box-shadow: rgba(255, 255, 255, 0.4) 0px 1px 0px 0px inset;
  font-weight: 600;
  border: 0;
  border-radius: 10px;
`;







const Logout = () => {

  const [isOpen, setIsOpen] = useState(false);
    const openModalHandler = () => {
      setIsOpen(!isOpen);
    };

  return (
  
  <>
  {/* <div className="text-gray-700 h-screen text-medium text-xl text-center mt-5">
          logout
  </div> */}
  <L onClick={openModalHandler}>모달버튼</L>
  {isOpen ? (<ModalBackdrop onClick={openModalHandler}>
    <BtnContainer>
      <LogoutBtn >Logout</LogoutBtn>
      <CancleBtn >Cancle</CancleBtn>
    </BtnContainer>
  </ModalBackdrop>
  ) : null}

        
        

          

    
    </>
  );
};
export default Logout;
