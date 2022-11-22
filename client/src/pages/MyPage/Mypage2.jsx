import styled from "styled-components/macro";
import React from "react";
import MyPageSecond from "../../components/MyPageSecond";

const MyPageContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`;


function Mypage() {
  return (
    <MyPageContainer>
      <MyPageSecond />
    </MyPageContainer>
  );
}

export default Mypage;
