/* eslint-disable */

import axios from 'axios';
import Chart from 'chart.js/auto';
import React, { useState,useEffect } from 'react';
import { Line } from "react-chartjs-2";
import styled from 'styled-components/macro';
import MyPageText from './MyPageText';

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
margin-top: 3em;
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

  useEffect(()=>{

    axios
    .get('http://13.209.190.35:8080/users/mypages',
    { 'headers': {'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6W10sInVzZXJJZCI6MTEsInN1YiI6Imd1ZW5sb2dAdGVzdC5jb20iLCJpYXQiOjE2Njk2MTE2MzgsImV4cCI6MTY2OTYyNjAzOH0.JESpB2rhBuaqnIvbN5OjGZfbHFKTOtS0h1LSQEY8Uo-FdeENke4yz_AflclVvWeGIQl7iehSy_568Gwn1HHMQg',} })
    .then((res => {
      console.log(res.data.data)
      let a = []
      // for(let i =0; i<6; i++){
      //   if(res.data.data.monthlyWeights[i].weight)
      //   a.push(res.data.data.monthlyWeights[i].weight);
      // }
      a.push(res.data.data.monthlyWeights[0].weight);
      a.push(res.data.data.monthlyWeights[1].weight);
      a.push(res.data.data.monthlyWeights[2].weight);
      a.push(res.data.data.monthlyWeights[3].weight);
      a.push(res.data.data.monthlyWeights[4].weight);
      setKgData(a)
      
      let b=[]
      b.push(res.data.data.monthlyRecords[0].record);
      b.push(res.data.data.monthlyRecords[1].record);
      b.push(res.data.data.monthlyRecords[2].record);
      b.push(res.data.data.monthlyRecords[3].record);
      b.push(res.data.data.monthlyRecords[4].record);
      b.push(res.data.data.monthlyRecords[5].record);
      setGymCheck(b)

      let c=[]
      c.push(res.data.data.monthlyRecords[0].date);
      c.push(res.data.data.monthlyRecords[1].date);
      c.push(res.data.data.monthlyRecords[2].date);
      c.push(res.data.data.monthlyRecords[3].date);
      c.push(res.data.data.monthlyRecords[4].date);
      c.push(res.data.data.monthlyRecords[5].date);
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