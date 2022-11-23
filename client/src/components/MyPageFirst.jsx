import styled from "styled-components/macro";
import { Line } from "react-chartjs-2";
import Chart from "chart.js/auto";
// import axios from "axios";
import React from "react";
import MyPageText from "./MyPageText";

const MyPageForm = styled.form`
  display: flex;
  width: 25em;
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
width: 25rem;
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
font-size: 1.5em;
width: 25rem;
`;



const Month = ["1월","2월","3월", "4월", "5월","6월"]
const KgData = ["87", "88", 91,105, 90, 110]
const GymCheck = [10, 15, 20, 15, 17, 10]
Chart.register();
const data = {
  responsive: false,
  labels: Month,
  datasets: [
    {
      label: "몸무게",
      data: KgData,
      fill: true,
      borderColor: ["rgba(82, 209, 229, 0.2)"],
      backgroundColor: ["rgba(82, 209, 229, 0.2)"],
      pointBackgroundColor: ["rgba(82, 208, 255, 1)"],
      pointBorderColor: ["rgba(83, 85, 255, 0.2)"]
    },
    {
      label: "운동횟수",
      data: GymCheck,
      fill: true,
      borderColor: ["rgba(24, 206, 83, 0.6)"],
      backgroundColor: ["rgba(24, 206, 83, 0.6)"],
      pointBackgroundColor: ["rgba(24, 206, 83, 1)"],
      pointBorderColor: ["rgba(24, 206, 83, 1)"]
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
  // axios({
  //   method: "get", 
  //   url: "", 
  // })
  //   .then((data) => {
  //     for (let i = 0; i < 6; i++) {
  //       Month.push(Month.data);
  //       KgData.push(KgData.data);
  //       GymCheck.push(GymCheck.data);
  //       console.log(data)
  //     }
  //   },
  //   null
  //   );
      
 


  return (
    <MyPageForm>
      <PageText>운동그래프</PageText>
      <Container>
        <Line data={data} options={options}/>
      </Container>
      <Container2>
        <MyPageText />
      </Container2>
    </MyPageForm>
  )
}

export default MyPageFirst