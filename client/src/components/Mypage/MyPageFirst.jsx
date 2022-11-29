/* eslint-disable */
import axios from 'axios';
import Chart from 'chart.js/auto';
import React, { useState,useEffect } from 'react';
import { Line } from "react-chartjs-2";
import styled from 'styled-components/macro';
import MyPageText from './MyPageText';
import { LoginState, TokenState } from "../../state/UserState";
import { useRecoilValue } from "recoil";

const MyPageForm = styled.form`
  display: flex;
  height: 40em;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: white;
`;

const Container = styled.div`
display: flex;
align-items: center;
justify-content: center;
max-width: 27em;
width: 25em;
height: 15em;
margin-bottom: 2em;
margin-top: 2em;
`;

const Container2 = styled.div`
display: flex;
align-items: center;
justify-content: center;
width: 25rem;
margin-bottom: 12em;
margin-left: 3em;
`;

const PageText = styled.div`
display: flex;
align-items: center;
justify-content: center;
font-size: 1.2em;
width: 25rem;
font-weight: 400;
`;




// function minMaxData() {
//   ajaxCall(
//       "GET",
//       "url",
//       null,
//       function (data) {
//           for (let i = 0; i < 6; i++) {
//              set.push(set.data);
//               KgData.push(KgData.data);
//               Gym.push(Gym.data);

//           }
//       },
//       null,
//       null
//   );
// }



const MyPageFirst = () => {
  const [KgData, setKgData] = useState('');
  const [GymData, setGymCheck] = useState('');
  const [month,setMonth]=useState([]);
  const login = useRecoilValue(LoginState);
  // 토큰
  const token = useRecoilValue(TokenState);
  // url주소
  const url = "http://13.209.190.35:8080";
  if (login === false) {
    alert("로그인이 안 되어 있습니다.");
    navigate("/login");
  }

  useEffect(()=>{

    axios
    .get(`${url}/users/mypages`,
    { headers: {Authorization: `${token}`,}, 
   })
    .then((res => {
      let a = []
      for(let i = 0; i<6; i++){
      if(res.data.data.monthlyWeights[i]){
      a.push(res.data.data.monthlyWeights[i].weight);
      }  
    }
      setKgData(a)
      
      let b = []
      for(let i = 0; i<6; i++){
      if(res.data.data.monthlyRecords[i]){
      b.push(res.data.data.monthlyRecords[i].record);
      }  
    }
      setGymCheck(b)

      let c = []
      for(let i = 0; i<6; i++){
      if(res.data.data.monthlyRecords[i]){
      c.push(res.data.data.monthlyRecords[i].date);
      }  
    }
      setMonth(c)

      }))
    },[])

    const Kg = KgData;
    const GymCheck = GymData;
    const GymMonth = month

Chart.register();
const data = {
  responsive: false,
  labels: GymMonth,
  datasets: [
    {
      label: "몸무게",
      data: Kg,
      fill: true,
      borderColor: ['rgba(151, 164, 255, 0.3)'],
      backgroundColor: ['rgba(151, 164, 255, 0.3)'],
      pointBackgroundColor: ['#ffffff'],
      pointBorderColor: ['#ffffff']
    },
    {
      label: "운동횟수",
      data: GymCheck,
      fill: true,
      borderColor: ['rgba(18, 73, 237, 1)'],
      backgroundColor: ['rgba(18, 73, 237, 1)'],
      pointBackgroundColor: ['#ffffff'],
      pointBorderColor: ['#ffffff']
    }
  ]
};
const options = {
  scales: {
    y: {
      min: 0,
      ticks: {
        stepSize: 5,
      },
    },
  },
};

 


  // .then((response) => response.json())
  // .then((data) => console.log(data));

  // axios.
  // get("http://13.209.190.35:8080/users/mypages"),
  // {
  //   headers: {
  //     Authorization: `${localStorage.getItem('login-token')}`,
  // }
  // .then((res) => {
  //   console.log(res)
    
  //   })
  //   .catch((err)=>{
  //     console.log(err);
  //   })
  // };

  // const chart = res.data;
  //   chart.forEach(data=> {
  //     for (let i = 0; i < 6; i++){
  //     Month.push(monthlyRecords.date);
  //     KgData.push(monthlyWeights.weight);
  //     GymCheck.push(monthlyRecords.record);
  //     console.log(data)
  //     }
      
  //     });
  //     setGymCheck(GymCheck)
  //     setKgData(KgData)

  return (
    <MyPageForm>
      <PageText>나만의 운동 일지</PageText>
        <Container>
        <Line data={data} options={options}/>
        </Container>
        <Container2>
        <MyPageText setGymCheck={GymData} setKgData={KgData} />
        </Container2>
        </MyPageForm>
  )
}

export default MyPageFirst