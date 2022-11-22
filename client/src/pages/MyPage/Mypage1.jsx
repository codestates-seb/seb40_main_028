import styled from "styled-components/macro";
import React from "react";
import MyPageFirst from "../../components/MyPageFirst";
import MyPageText from "../../components/MyPageText";
// import { Link } from 'react-router-dom';

const MyPageContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`;


function Mypage1() {
  return (
    <MyPageContainer>
      <MyPageFirst /> 
      <MyPageText />
    </MyPageContainer>
  );
}

export default Mypage1;
