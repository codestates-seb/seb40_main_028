import { FaDumbbell } from 'react-icons/fa';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import useInterval from '../assets/Interval';
import { useRecoilState } from 'recoil';
import {timermodalState, totaltimeState } from '../state/states';
import Timer from '../components/Timer';
import Congrats from '../components/Congrats';
import {Smallbutton, Setlistbutton, Timebutton, Movingbutton, Specificsetlistbutton} from '../components/ExerciseButton';

const Workout = () => {
  const [specificset, setSpecificset] = useState("list");
  const [stopped, setStopped] = useState(false);
  const [workedtime, setWorkedtime] = useRecoilState(totaltimeState);
  const [istimermodalon, setIstimermodalon] = useRecoilState(timermodalState);
  const [workoutdone, setWorkoutdone] = useState(false);
  
  const workoutlist = ["스트레칭준비", "말도안되게긴운동명을넣어보자이정도를넣으면어떻게될려나", "스쿼트", "데드리프트", "덤벨 컬", "바벨 컬", "크런치", "벤치프레스", "스쿼드", "데드리프트", "덤벨 컬", "바벨 컬", "크런치"];
  const setlist = [[110,20],[15,15],[20,10], [2021,1324210],[10,20],[15,15],[20,10],[10,20],[15,15],[20,10]]
  
  const navigate = useNavigate();
  const gohome = () => {if(window.confirm("정말 끝내시겠습니까?")) {setWorkedtime(0); navigate("/")}};
  const goback = () => setSpecificset("list");
  const gonext = () => setSpecificset(specificset+1);
  const finished = () => setWorkoutdone(true);

  const timeonscreen = (time) => {
    let hours = Math.floor(time/3600).toLocaleString('en-US',{minimumIntegerDigits:2});
    let minutes = Math.floor((time%3600)/60).toLocaleString('en-US',{minimumIntegerDigits:2});
    let seconds = Math.floor((time%60)).toLocaleString('en-US',{minimumIntegerDigits:2});
    let result = `${hours}:${minutes}:${seconds}`;
    return result;
  }

  useInterval(() => setWorkedtime(workedtime+1),stopped)

  const pausefunction = () => {
    setStopped(!stopped);
  }

  // 이부분은 layout 버튼부분에 눌렀을때 날짜가 전송되게 구현할때 필요한 날짜형식
  // 이 날짜는 처음 눌렀을 때 날짜를 저장해야겠다. recoil로 state 저장
  // let datenow = new Date();
  // let senddate = `${datenow.getFullYear()}-${datenow.getMonth()+1}-${datenow.getDate()}`

  
  

  
  return (
    <>
      <div className="relative flex-col text-gray-700 min-h-[100vh] pt-[1vh] first-letter text-medium text-xl text-center">
        {/* 시간이 흘러갈때는 초록색으로 표기하고 멈췄을때는 빨간색으로 표기하는것 적용 */}
        <div readOnly className='flex justify-center items-center cursor-pointer h-[7vh] max-w-md m-auto mx-[3vw] rounded-xl bg-d-dark shadow-lg shadow-black transition duration-150 active:ml-[3.5vw] active:shadow-none' onClick={pausefunction}>
          {stopped? 
            <Timebutton color="text-red-700" time={timeonscreen(workedtime)} />
            :<Timebutton color="text-green-700" time={timeonscreen(workedtime)} />
          } 
        </div>
        <div className='flex justify-start items-center mx-4 border-transparent overflow-x-hidden h-[6vh] text-[3vh] font-bold text-white'>
          <span className='flex basis-4/5 whitespace-nowrap overflow-x-scroll overflow-y-visible py-2 '>{specificset==="list"? workoutlist[0]:workoutlist[specificset]}</span>
          {specificset==="list"?null:<button className="justify-self-end itmes-end w-20 h-20 text-lg" onClick={(()=> setIstimermodalon([true,5]))}>타이머</button>}</div>
        <hr className='flex items-center first-letter:mx-2 pb-[1vh]'></hr>
        <div className='flex h-[29vh] border-2 mx-10 justify-center items-center text-[10px] text-white'> 
        <FaDumbbell size={240} />
        </div>
        {/* 여기에서 onclick 이벤트로 setState 넣어두면 무한반복되어버림=>수정필요 */}
        <div className='flex-col h-[50vh] mx-auto overflow-scroll mouse'>
        {specificset === "list"?
          workoutlist.map((x, idx)  => <Specificsetlistbutton key={`${x}${idx}`} id={`${x}${idx}`} x={x} idx={idx} setState={setSpecificset} />)
        : 
          setlist.map((x, idx)  => <Setlistbutton key={`${x}${idx}`} x={x} idx={idx} record={`${x}${idx}`} />)
        }
        </div>
        <div className='flex justify-between items-center max-w-m max-h-[80px] w-full mt-auto mb-0 h-[7vh] overflow-clip'>
          {specificset === "list"? 
            <>
              <Smallbutton name="운동종료" fn={gohome}/>
              <Movingbutton />
            </>
            :<>
              <Smallbutton name="이전" fn={goback}/>
              {workoutlist.length-2 < specificset? 
              <Smallbutton name="운동완료" fn={finished} ifnext="mr-10"/>
              :<Smallbutton name="다음운동" fn={gonext} ifnext="mr-10"/>}
            </>}
                
        </div>
        {istimermodalon[0]? <Timer />:null}  
        {workoutdone? <Congrats />:null}  
      </div>
    </>
  );
};

export default Workout;


/* 
운동사진 축하합니다 페이지에서 몸무게입력창있으면 좋을듯

axios( {
  method: "GET",
  url: "exercise/progress",
  headers: { "Content-Type": "application/json", authorization: token},
  body: JSON.stringify(senddate),
})

senddate 형식 : 연도-월-일
=> senddate url에 같이 보내드리는게 좋다는 의견이 있었으나, 토의하여 string으로 body에 보내는 것으로 의견종합

response 형식
=>종목과 각 세트에 대한 정보, 추가적으로 세트에 대한 id도 들어옴
id에대한 무게 횟수
ex)
id1: 벤치프레스 첫번째세트 10kg 15회
id2: 벤치프레스 두번째세트 10kg 12회

axios( {
  method: "PATCH",
  url: "exercise/progress",
  headers: { "Content-Type": "application/json", authorization: token},
  body: JSON.stringify(sendprogress),
})

sendprogress:
완료된 세트 id만 보내드리면 됨.(배열형식)
운동전체시간
ex) {00-12-57},[1,4,9,13] 자세한 형식은 다시 요청해서 샘플 받는것으로 결정

response 성공 => status ok도 괜찮음
/ 실패 => 고민해보아야함.

이후 축하합니다 페이지에서 보낼 수 있는 url과 method를 저에게 알려주세요(지환)
*/