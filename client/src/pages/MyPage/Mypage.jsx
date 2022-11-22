import styled from "styled-components/macro";
import React,{ useState } from "react";
import Layout from "../../components/Layout";
import MyPageFirst from "../../components/MyPageFirst";
import MyPageSecont from "../../components/MyPageSecond";
// import { Link } from 'react-router-dom';

const MyPageContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;

`;
const MyPageButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 130px;
  height: 10px;
  padding:15px;
  font-size: 15px;
  color: white;
  :hover {
    background-color: #4C53BF;
    border: 2px solid #3C53BF;
  }
  background-color: #747BF2;
  border: 2px solid #737BF2;
  border-radius: 4px;
  cursor: pointer;
  margin-left: 13em;
  margin-top: 2em;

  `;

function Mypage() {
  const [page,setPage] = useState(false);

  return (
    <div className="w-full h-screen bg-d-lighter" >
      <Layout title="마이페이지" hasTabBar>
        <MyPageContainer>
          { page === false ? <MyPageFirst /> : <MyPageSecont/>}
        </MyPageContainer>
        <MyPageButton onClick={() => {setPage(!page);}}>
          {page===true ? "운동그래프" : "정보수정"}</MyPageButton>
      </Layout>
    </div>
  );
}

export default Mypage;
