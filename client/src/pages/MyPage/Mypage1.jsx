import MyPageFirst from '../../components/MyPageFirst';
import styled from 'styled-components/macro';
import MyPageText from "../../components/MyPageText";
// import { Link } from 'react-router-dom';

const MyPageContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  height: 75vh;
  min-width: 365px;
`;


const Mypage1 = () => {
  return (
    <div>
      <MyPageContainer>
      <MyPageFirst /> 
      <MyPageText />
      </MyPageContainer>
      </div>
  );
};

export default Mypage1;
