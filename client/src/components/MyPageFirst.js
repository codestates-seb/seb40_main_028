
import styled from 'styled-components/macro';
import { Link } from 'react-router-dom';
import { Line } from "react-chartjs-2";
import Chart from 'chart.js/auto';



const MyPageForm = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 390px;
  height: 850px;
  border-radius: 8px;
  background-color: #2C2F33;
  border: none;
  box-shadow: 0 0 5px 2px rgba(0, 0, 0, 0.3);
  margin-top: 15px;
  padding: 24px;
  color: white;
  .h2{
    font-size: 18px;
    margin: -15px 0px 20px 0px;
 
  }
`;

const Container = styled.form`
display: flex;
justify-content: center;
position: fixed;
margin-top: 70px;
margin-left: -14px;
width: 370px;

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
      borderColor: ['rgba(82, 209, 229, 0.2)'],
      backgroundColor: ['rgba(82, 209, 229, 0.2)'],
      pointBackgroundColor: ['rgba(82, 208, 255, 1)'],
      pointBorderColor: ['rgba(83, 85, 255, 0.2)']
    },
    {
      label: "운동횟수",
      data: GymCheck,
      fill: true,
      borderColor: ['rgba(24, 206, 83, 0.6)'],
      backgroundColor: ['rgba(24, 206, 83, 0.6)'],
      pointBackgroundColor: ['rgba(24, 206, 83, 1)'],
      pointBorderColor: ['rgba(24, 206, 83, 1)']
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

const MyPageFirst = () => {
  return (
    
    <MyPageForm>
        <div className='h2'>
        운동그래프</div>
        <Container>
        <Line data={data} options={options}/>
        </Container>
        <Link to="/mypage2">
        </Link>
        </MyPageForm>
    
  )
}

export default MyPageFirst