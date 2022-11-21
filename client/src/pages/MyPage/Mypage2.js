import MyPageSecond from '../../components/MyPageSecond';
import styled from 'styled-components/macro';

const MyPageContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  height: 75vh;
  min-width: 365px;
`;

const Mypage = () => {
  return (
    <div>
      <MyPageContainer>
      <MyPageSecond />
      </MyPageContainer>
      </div>
  );
};

export default Mypage;
