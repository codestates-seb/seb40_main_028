// 운동진행리스트 state 업데이트 문제
// useref로 리스트 파악 문제

import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import useInterval from "../assets/Interval";
import { workoutlistState, worktimeState } from "../state/states";
import Congrats from "../components/Congrats";
import {Smallbutton, EachRecordbutton, Timebutton, Movingbutton, Worklistbutton} from "../components/exercise/ExerciseButton";
import expic from "../assets/11.png";
import Restmodal from "../components/exercise/Restmodal";


function Workout() {
  const [specificset, setSpecificset] = useState("list");
  const [stopped, setStopped] = useState(false);
  const [isrestmodalon, setIsrestmodalon] = useState([false,0]);
  const [workoutdone, setWorkoutdone] = useState(false);
  const [worktime, setWorktime] = useRecoilState(worktimeState);
  const [workoutdata, setWorkoutdata] = useRecoilState(workoutlistState);
  
  const navigate = useNavigate();
  // gohome에 axios 걸고 진행상황 넘겨주고 초기화해야함.
  const gohome = () => {if(window.confirm("정말 끝내시겠습니까?")) { navigate("/")}};
  const goback = () => setSpecificset("list");
  const gonext = () => setSpecificset(specificset+1);
  const finished = () => setWorkoutdone(true);
  setWorkoutdata(cur => cur);

  const timeonscreen = (time) => {
    const hours = Math.floor(time/3600).toLocaleString("en-US",{minimumIntegerDigits:2});
    const minutes = Math.floor((time%3600)/60).toLocaleString("en-US",{minimumIntegerDigits:2});
    const seconds = Math.floor((time%60)).toLocaleString("en-US",{minimumIntegerDigits:2});
    const result = `${hours}:${minutes}:${seconds}`;
    return result;
  }

  useInterval(() => setWorktime(prevState => 
    prevState+1),stopped)

  const pausefunction = () => {
    setStopped(!stopped);
  }

  const setclicked = async (timer, set) => {
    setIsrestmodalon([true,timer]);
    if(set !== "list") {
      setWorkoutdata(prevState => 
        ({success:true,
          data:{
            ...prevState.data, 
            exercises : [
              //이부분이없으면 기존의 데이터를 못가져옴, 있으면 데이터가 증식함
              ...prevState.data.exercises, 
              prevState.data.exercises[set] : {
                ...prevState.data.exercises[set],  
              // //   isCompleted : true,
              // // eachRecords : [
              // //   prevState.data.exercises[specificset][eachRecords],
              // // ],
              },
              
            ]
          }}));
    }
  };

  // 이부분은 layout 버튼부분에 눌렀을때 날짜가 전송되게 구현할때 필요한 날짜형식
  // 이 날짜는 처음 눌렀을 때 날짜를 저장해야겠다. recoil로 state 저장
  // let datenow = new Date();
  // let senddate = `${datenow.getFullYear()}-${datenow.getMonth()+1}-${datenow.getDate()}`

  // useHover: Hover되면 ref와 상태값을 반환한다.
  // function useHover() {
  //   const [value, setValue] = useState(false);
  //   const ref = useRef(null);
  //   const handleMouseOver = () => setValue(true);
  //   const handleMouseOut = () => setValue(false);
  //   useEffect(
  //     () => {
  //       const node = ref.current;
  //       if (node) {
  //         node.addEventListener("mouseover", handleMouseOver);
  //         node.addEventListener("mouseout", handleMouseOut);
  //         return () => {
  //           node.removeEventListener("mouseover", handleMouseOver);
  //           node.removeEventListener("mouseout", handleMouseOut);
  //         };
  //       }
  //     },
  //     [ref.current] // Recall only if ref changes
  //   );

  //   return [ref, value];
  // }
  // const [hoverRef, isHovered] = useHover();
  

  
  return (
    <div className="flex flex-col bg-d-light text-gray-700 max-w-lg h-screen text-center">
      {/* 시간이 흘러갈때는 초록색으로 표기하고 멈췄을때는 빨간색으로 표기하는것 적용 */}
      <div className='flex justify-between items-center border-transparent h-[3em] px-[1em] py-[0.1em] xs:py-[0.5em] text-[1.5em] font-bold text-white'>
        <span className='flex basis-1/2 px-[1em] xs:px-[1.2em] pt-[0.3em] xs:pt-[0.4em] xs:text-[1.5rem] whitespace-nowrap overflow-x-clip overflow-y-visible text-left'>
          {specificset==="list"? workoutdata.data.exercises[0].exerciseName:workoutdata.data.exercises[specificset].exerciseName}</span>
        {/* {specificset==="list"?null:<button className="justify-self-end itmes-end w-20 h-20 text-lg" onClick={(()=> setisrestmodalon([true,5]))}>타이머</button>} */}
        <button type="button" readOnly className='flex justify-center items-center cursor-pointer w-[10rem] xs:w-[24rem] h-[3rem] text-[1em] xs:text-[1.7em]    m-auto mx-[0.5em] rounded-xl bg-d-dark shadow-lg shadow-black transition duration-150 active:ml-[0.5em] active:shadow-none hover:bg-d-hover
        max-w-' 
        onClick={pausefunction} onKeyDown={pausefunction}>
          {stopped? 
            <Timebutton color="text-red-700" time={timeonscreen(worktime)} />
            :<Timebutton color="text-green-700" time={timeonscreen(worktime)} />
          } 
        </button>
      </div>
      {/* <hr className='flex items-center first-letter:mx-2 pb-[1vh]'></hr> */}
      <div className='flex h-[8rem] xs:h-[15rem] border-none mx-[2.4em] justify-center items-center text-white'> 
        
        <img src={expic} alt="사진이 없습니다." />
      </div>
      {/* 여기에서 onclick 이벤트로 setState 넣어두면 무한반복되어버림=>useRef사용예정 */}
      <div className='flex-col w-full h-[25rem] xs:h-[40rem] max-h-[58rem] mx-auto overflow-scroll' >
        {specificset === "list"?
          workoutdata.data.exercises.map((x, idx)  => <Worklistbutton key={`${x.exerciseId}`} id={x.exerciseId} name={x.exerciseName} idx={idx} setState={setSpecificset}/>)
          : 
          workoutdata.data.exercises[specificset].eachRecords.map((x,idx) => <EachRecordbutton 
            key={idx} 
            kg={x.weight} 
            count={x.count} 
            idx={idx}
            iscompleted={x.eachCompleted} 
            fn={()=>setclicked(x.eachTimer, specificset)} 
            restfn={setIsrestmodalon} />)
        }
      </div>
      <div className='flex justify-between items-center w-full mt-auto mb-0 h-[4em] overflow-clip'>
        {specificset === "list"? 
          <>
            <Smallbutton name="운동종료" fn={gohome}/>
            <Movingbutton fn={() => setIsrestmodalon([true,60])}/>
          </>
          :<>
            <Smallbutton name="이전" fn={goback}/>
            {workoutdata.data.exercises.length-2 < specificset? 
              <Smallbutton name="운동완료" fn={finished} ifnext="mr-[2.5em]"/>
              :<Smallbutton name="다음운동" fn={gonext} ifnext="mr-[2.5em]"/>}
          </>}
        {isrestmodalon[0]? <Restmodal data={isrestmodalon} fn={setIsrestmodalon} />:null} 
        {workoutdone? <Congrats workoutdone/>:null}  
      </div>

    </div>
  );
}

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