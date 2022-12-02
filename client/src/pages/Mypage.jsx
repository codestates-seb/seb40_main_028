/* eslint-disable */
import React, { useState } from "react";
import styled from 'styled-components/macro';
import Layout from "../components/Layout";
import MyPageFirst from "../components/Mypage/MyPageFirst";
import MyPageSecont from "../components/Mypage/MyPageSecond";
// import { Link } from 'react-router-dom';

const MyPageContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`;
const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`;

const MyPageButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 10em;
  height: 10px;
  padding:15px;
  font-size: 15px;
  color: white;
  :hover {
    background-color: #4C53BF;
  }
  background-color: #747BF2;
  border-radius: 4px;
  cursor: pointer;
  margin-top: 2em;
  `;

const Mypage = () => {
  const [page,setPage] = useState(false);

  return (
    <div className="w-full h-screen bg-d-lighter" >
    <Layout title="마이페이지" hasTabBar>
      <MyPageContainer>
        { page === false ? <MyPageFirst /> : <MyPageSecont/>}
      </MyPageContainer>
      <Container>
      <MyPageButton onClick={() => {setPage(!page);}}>
        {page===true ? "운동그래프" : "정보수정"}</MyPageButton>
        </Container>
    </Layout>
    </div>
  );
};

export default Mypage;
