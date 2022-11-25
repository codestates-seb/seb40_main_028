/* eslint-disable */
import React from "react";
import styled from "styled-components/macro";

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
margin-bottom: 1em;
font-weight: 500;
`
const Text2 = styled.div`
display: flex;
justify-content: space-between;
width: 2.6em;
font-size: 18px;
color: white;
margin-left: 0.8em;
margin-right: 3.3em;
margin-bottom:-7.3em;
z-index: 10;
`

const TextForm = styled.div`
display: flex;
justify-content:center;
align-items: center;
width: 7em;
height: 7em;
margin-left: -1em;
margin-right: 2.2em;
font-size: 15px;
color: white;
margin-top: 1em;
border: solid 2px #36393F;
	border-top: solid 2px #1249ed;
	border-radius: 50%;  
	animation: spin 3s linear infinite;
	-webkit-animation: spin 2s linear infinite;
  animation-iteration-count :5;
  @keyframes spin {
	0% {
		transform: rotate(0deg);
	}
	100% {
		transform: rotate(360deg);
	}
}
@-webkit-keyframes spin {
	
}
`
const TextForm2 = styled.div`
display: flex;
justify-content:center;
align-items: center;
width: 7em;
height: 7em;
margin-left: -1em;
margin-right: 2.2em;
font-size: 15px;
margin-top: 1em;
border: solid 2px #36393F;
	border-top: solid 2px #97a4ff;
	border-radius: 50%;  
	animation: spin 5s linear infinite;
	-webkit-animation: spin 2s linear infinite;
  animation-iteration-count :5;
  @keyframes spin {
	0% {
		transform: rotate(0deg);
	}
	100% {
		transform: rotate(360deg);
	}
}
@-webkit-keyframes spin {
	
}
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
        <Text2>
           +2KG
        </Text2>
        <Text2>
            +11회
        </Text2>
        <TextForm2/>
        <TextForm/>
      </Container>
    </>
  )
}

export default MyPageText