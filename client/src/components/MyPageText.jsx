import styled from "styled-components/macro";
import React from "react";


const Container = styled.div`
display: flex;
align-items: center;
justify-content: space-between;
flex-direction: row;
width: 20em;
flex-wrap: wrap;
height: 20px;
margin-top: 2em;
`;

const Text = styled.div`
display: flex;
justify-content: space-between;
width: 10em;
font-size: 15px;
color: white;
margin-left: -1em;

`
const TextForm = styled.div`
display: flex;
justify-content:center;
align-items: center;
background-color: #547BF2;
width: 7em;
height: 7em;
margin-left: -1em;
margin-right: 2em;
border-radius:90px;
font-size: 15px;
color: white;
box-shadow: 0 0 3px 2px rgba(0, 0, 0, 0.2);
margin-top: 1em;
`

function MyPageText() {
  return (
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
  )
}

export default MyPageText