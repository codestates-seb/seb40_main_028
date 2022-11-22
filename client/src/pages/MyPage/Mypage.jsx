import styled from "styled-components/macro";
import React,{ useState } from "react";
import Layout from "../../components/Layout";
import MyPage1 from "./Mypage1";
import MyPage2 from "./Mypage2";
// import { Link } from 'react-router-dom';

const MyPageContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  height: auto;
  width: 32rem;
  background-color: red;
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
  margin-top:-45px;
  position: absolute;
  :hover {
    background-color: #4C53BF;
    border: 2px solid #3C53BF;
  }
  background-color: #747BF2;
  border: 2px solid #737BF2;
  border-radius: 4px;
  cursor: pointer;
  margin-left: 190px;
`;

function Mypage() {
  const [page,setPage] = useState(false);

  return (
    <div className="h-screen" >
      <Layout title="마이페이지" hasTabBar>
        <MyPageContainer>
          { page === false ? <MyPage1 /> : <MyPage2/>}
        </MyPageContainer>
        <MyPageButton onClick={() => {setPage(!page);}}>
          {page===true ? "정보수정" : "운동그래프"}</MyPageButton>
      </Layout>
    </div>
  );
}

export default Mypage;
