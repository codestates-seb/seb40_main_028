/* eslint-disable */
import React from "react";
import styled from "styled-components/macro";

const Container = styled.div`
display: flex;
justify-content: space-between;
flex-direction: row;
width: 30em;
margin-left: 2.5em;
`;

const Text = styled.div`
display: flex;
font-size: 15px;
color: white;
margin-bottom: 1em;
font-weight: 500;
margin-left: 3.2em;
`
const Text2 = styled.div`
display: flex;
width: 10em;
font-size: 15px;
color: white;
margin-bottom: 1em;
font-weight: 500;
margin-right:4.4em;

`


const MyPageText = () => {
 
  return (
    <>
      <Container >
        <Text>
            직전 달 평균 체중 차이
        </Text>
        <Text2>
            직전 달 운동 횟수 차이
        </Text2>
      </Container>
    </>
  )
}

export default MyPageText