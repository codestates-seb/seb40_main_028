/* eslint-disable */
import React from "react";
import styled from "styled-components/macro";

const Container = styled.div`
display: flex;
align-items: center;
justify-content: space-between;
flex-direction: row;
width: 23em;
flex-wrap: wrap;
height: 20px;
margin-top: 1em;
`;

const Container2 = styled.div`
display: flex;
align-items: center;
justify-content: center;
flex-direction: row;
width: 8em;
flex-wrap: wrap;
`;

const Text = styled.div`
display: flex;
justify-content: space-between;
width: 10em;
margin-left: -0.2em;
margin-right: 0.7em;
font-size: 15px;
color: white;
margin-bottom: 1em;
font-weight: 500;
`
const Text2 = styled.div`
display: flex;
justify-content: space-between;
width: 5em;
font-size: 18px;
color: white;
margin-bottom:-11.2em;
margin-left: 0.6em;
z-index: 10;
`
const Text3 = styled.div`
display: flex;
justify-content: space-between;
width: 5em;
font-size: 18px;
margin-right: 0.8em;
color: white;
margin-bottom:-11.2em;
z-index: 10;
`

const TextForm = styled.div`
display: flex;
justify-content:center;
align-items: center;
width: 12em;
height: 12em;
font-size: 15px;
color: white;
margin-top: 1em;
margin-right: 0.3em;
border: solid 2px #36393F;
	border-top: solid 2px #1249ed;
	border-radius: 50%;  
	animation: spin 3s linear infinite;
	-webkit-animation: spin 2s linear infinite;
  animation-iteration-count :7;
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
width: 12em;
height: 12em;
margin-left: -2.6em;
font-size: 15px;
margin-top: 1em;
border: solid 2px #36393F;
	border-top: solid 2px #97a4ff;
	border-radius: 50%;  
	animation: spin 5s linear infinite;
	-webkit-animation: spin 2s linear infinite;
  animation-iteration-count :7;
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


const MyPageText = ({setGymCheck,setKgData}) => {
  const a = setGymCheck;
  const b = setKgData;
  let c = 0;
  let d = 0;
  if(a.length>1){
   c= a.at(-1)-a.at(-2)
  }
  else{
   c = a
  }
  if(b.length>1){
    d= b.at(-1)-b.at(-2)
    
   }
   else{
    d = b
   }
  
  const roundNum = Math.round(d * 10) / 10;
  d=roundNum;
  d >= 0 ? d= `+${d}`:d 
  c >= 0 ? c= `+${c}`:c 
  return (
    <>
      <Container >
        <Text>
            직전달 몸무게 차이
        </Text>
        <Text>
            직전달 운동 횟수 차이
        </Text>
        <Container2>
        <Text2>
           {d} KG
        </Text2>
        </Container2>
        <Container2>
        <Text3>
            {c} 번
        </Text3>
        </Container2>
        <TextForm2/>
        <TextForm/>
      </Container>
    </>
  )
}

export default MyPageText