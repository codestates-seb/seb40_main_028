
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";


// 모달 로그아웃 버튼
const L = styled.button`
  width: 50px;
  height: 50px;
  padding: 10px;
  color: white;
  font-size: 13px;
  background: red;
`;

// 모달 실행시 배경이미지
const ModalBackdrop = styled.div`
  background-color: rgba(0, 0, 0, 0.5);
  width: 100%;
  height: 100%;  
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
  
  background-color: #ffffff;
  border-radius: 10px;

  //버튼 가운데 정렬
  display: flex;
  align-items: center;

`;

const LogoutBtn = styled.button`
  width: 100%;
  height: 50%;
  padding: 10px;
  color: white;
  background: rgb(88 101 242);
  font-size: 13px;
  box-shadow: rgba(255, 255, 255, 0.4) 0px 1px 0px 0px inset;
  font-weight: 600;
  border: 0;
  border-radius: 10px;

  display: flex;
  justify-content: center;
  align-items: center;

  margin-right: 20px;

  &:hover {
    background: rgb(71 82 196)
  };
`;

const CancleBtn = styled.button`
  width: 100%;
  height: 50%;
  padding: 10px;
  color: rgb(88 101 242);
  background: white;
  font-size: 13px;
  box-shadow: rgba(255, 255, 255, 0.4) 0px 1px 0px 0px inset;
  font-weight: 600;
  // border: 0px;
  border: 1px solid ;
  border-radius: 10px;

  display: flex;
  justify-content: center;
  align-items: center;

  &:hover {
    background: rgb(241, 242, 244);
  };
`;


const Logout = () => {
  const navigate = useNavigate();

  const [isOpen, setIsOpen] = useState(false);
  const openModalHandler = () => {
    setIsOpen(!isOpen);
  };

  return (
  
    <>
  
      <div className="text-gray-700 h-screen text-medium text-xl text-center mt-5">
          logout
        <L onClick={openModalHandler}>버튼</L>
 
        {isOpen ? (<ModalBackdrop onClick={openModalHandler}>
          <BtnContainer>
            {/* <LogoutBtn  onClick={openModalHandler} >Logout</LogoutBtn> */}
            {/* <LogoutBtn  onClick={navigate('/map')} >Logout</LogoutBtn> */}
            {/* 로그인 버튼 클릭 시 홈으로 페이지 이동 */}
            <LogoutBtn  onClick={() => navigate('/')} >Logout</LogoutBtn>
            <CancleBtn >Cancle</CancleBtn>
          </BtnContainer>
        </ModalBackdrop>
        ) : null}

      </div>
  

    </>
  );
};
export default Logout;
