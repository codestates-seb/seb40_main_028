import styled from "styled-components/macro";


const Container = styled.div`
display: flex;
flex-direction: row;
justify-content: center;
width: 400px;
position: fixed;
margin-left: -28px;
flex-wrap: wrap;
height: 20px;
`;

const Text = styled.div`
display: flex;
justify-content: space-between;
width: 130px;
height: 100px;
margin: 35px 15px 0px 50px;
font-size: 15px;
color: white;
`
const TextForm = styled.form`
display: flex;
justify-content:center;
align-items: center;
background-color: #547BF2;
width: 90px;
height: 90px;
border-radius:90px;
margin: -55px 40px 0px 70px;
font-size: 15px;
color: white;
box-shadow: 0 0 3px 2px rgba(0, 0, 0, 0.2);
`

const MyPageText = () => {
  return (
    <>
      <Container >
        <Text>
            저번달 몸무게 차이
        </Text>
        <Text>
            저번달 운동 횟수 차이
        </Text>
        <TextForm>-12Kg</TextForm>
        <TextForm>+11</TextForm>
      </Container>
    </>
  )
}

export default MyPageText