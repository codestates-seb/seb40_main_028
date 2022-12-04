//AXIOS 테스트완료. 커버페이지 붙이고 토큰이랑 API 수정하면 완료
//운동id 1번부터 40번까지 존재함

import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useRecoilState, useRecoilValue } from "recoil";
import axios from "axios";
import useInterval from "../assets/Interval";
import { workoutlistState, worktimeState } from "../state/states";
import { TokenState } from "../state/UserState";
import Congrats from "../components/exercise/Congrats";
import {
  Smallbutton,
  EachRecordbutton,
  Timebutton,
  Worklistbutton,
} from "../components/exercise/ExerciseButton";
import Restmodal from "../components/exercise/Restmodal";
import Layout from "../components/Layout";

function Workout() {
  const [specificset, setSpecificset] = useState("list");
  const [specificpic, setSpecificpic] = useState(null);
  const [specificname, setSpecificname] = useState("list");
  const [stopped, setStopped] = useState(false);
  const [isrestmodalon, setIsrestmodalon] = useState([false, 0]);
  const [workoutdone, setWorkoutdone] = useState(false);
  const [worktime, setWorktime] = useRecoilState(worktimeState);
  const [workoutdata, setWorkoutdata] = useRecoilState(workoutlistState);
  const token = useRecoilValue(TokenState);
  const navigate = useNavigate();
  const URL = process.env.REACT_APP_BASE_URL;

  async function getdata(today) {
    await axios
      .get(URL + `/exercises/progress?date=${today}`, {
        // test용
        // .get(URL + "exercises/progress?date=2022-11-05", {
        headers: {
          Authorization: token,
        },
      })
      .then((data) => {
        setWorkoutdata(data.data.data);
        if (worktime === 0) setWorktime(data.data.data.totalTime);
        setSpecificpic(data.data.data.exercises[0].imageUrl);
        setSpecificname(data.data.data.exercises[0].exerciseName);
      })
      .catch((data) => {
        if (data.response.status === 421 || data.response.status === 419)
          alert("계획된 운동이 없습니다");
        else alert("에러가 발생하였습니다. 메인페이지로 돌아갑니다.");
        navigate("/");
      });
  }

  useEffect(() => {
    const datecalc = new Date(
      Date.now() - new Date().getTimezoneOffset() * 60000
    );
    const today = datecalc.toISOString().split("T", 1)[0];
    // if(worktime === 0 || workoutdata === {}) {
    //   getdata();
    // }
    if (workoutdata !== {}) getdata(today);
  }, []);

  const pausefunction = () => {
    setStopped(!stopped);
  };
  const goback = () => setSpecificset("list");
  const gonext = () => setSpecificset(specificset + 1);
  const finished = () => {
    pausefunction();
    setWorkoutdone(true);
  };

  const timeonscreen = (time) => {
    const hours = Math.floor(time / 3600).toLocaleString("en-US", {
      minimumIntegerDigits: 2,
    });
    const minutes = Math.floor((time % 3600) / 60).toLocaleString("en-US", {
      minimumIntegerDigits: 2,
    });
    const seconds = Math.floor(time % 60).toLocaleString("en-US", {
      minimumIntegerDigits: 2,
    });
    const result = `${hours}:${minutes}:${seconds}`;
    return result;
  };

  useInterval(() => setWorktime((prevState) => prevState + 1), stopped);

  const setclicked = (timer, set, setnum) => {
    setIsrestmodalon([true, timer]);

    //state를 직접수정이 불가능하여 이렇게 작성
    //배열내의 객체가 나열된 배열의 경우 스프레더를 사용한 후 특정객체를 선택할 수 없기 때문에 깊은복사를 한 이후 수정하고 새로운 객체로 넣어줌.
    if (set !== "list") {
      const copydata = [];
      //1. exercises 배열 내의 각각의 객체를 새로운 배열에 push해서 깊은복사를 진행
      //객체 내부에 객체가 있을경우 ... 스프레더를 사용하면안됨! 깊은복사에서 스프레더는 가장 깊은곳에서만 사용가능
      workoutdata.exercises.forEach((x) => {
        const temp = {};
        temp.exerciseId = x.exerciseId;
        temp.isCompleted = x.isCompleted;
        temp.exerciseName = x.exerciseName;
        temp.imageUrl = x.imageUrl;
        temp.eachRecords = [];
        x.eachRecords.forEach((el) => temp.eachRecords.push({ ...el }));
        copydata.push(temp);
      });

      // 2. 깊은 복사를 진행한 객체는 수정이 가능하므로 여기서 수정 진행
      const adddata = copydata.map((x, idx) => {
        // copydata: exercises 배열 전체가 들어가있음
        // x: 운동종류별로 운동정보전체를 담은 객체
        // idx: exercises 배열에서 x의 인덱스를 담은 변수
        // set: exercises 내에서의 배열의
        // setnum: 세트의 인덱스(0부터시작함)
        const result = x;
        let setalldone = true;
        const arrlen = result.eachRecords.length;

        if (idx === set) {
          result.eachRecords[setnum].eachCompleted = true;
        }

        for (let i = 0; i < arrlen; i += 1) {
          if (result.eachRecords[i].eachCompleted === false) {
            setalldone = false;
          }
          if (i + 1 === arrlen && setalldone === true)
            result.isCompleted = true;
        }

        return result;
      });

      // 3. 수정된 부분을 state에 다시 넣어주기
      setWorkoutdata((prevState) => ({
        ...prevState,
        exercises: [...adddata],
      }));
    }
  };

  const setdone = () => {
    const liidx = workoutdata.exercises[specificset].eachRecords.length;
    for (let i = 0; i < liidx; i += 1) {
      if (
        workoutdata.exercises[specificset].eachRecords[i].eachCompleted ===
        false
      ) {
        setclicked(
          workoutdata.exercises[specificset].eachRecords[i].timer,
          specificset,
          i
        );
        return;
      }
    }
  };

  const reset = async (weight) => {
    if (weight <= 10 || weight > 1000) {
      alert("입력 가능한 몸무게의 범위는 10-1000입니다.");
      setWorkoutdone(false);
      return setStopped(false);
    }

    await axios
      .patch(
        URL + `/exercises/progress/${workoutdata.todayId}`,
        {
          totalTime: worktime,
          exercises: workoutdata.exercises,
        },
        {
          headers: {
            Authorization: token,
          },
        }
      )
      .then(
        axios.patch(
          URL + "/users/mypages/info",
          {
            weight,
          },
          {
            headers: {
              Authorization: token,
            },
          }
        )
      )
      .then(() => {
        setWorkoutdata({});
        setWorktime(0);
        navigate("/");
      })
      // .then((data) => console.log(data).promise())
      .catch((data) => {
        alert(data);
      });
  };

  const gohome = () => {
    if (window.confirm("정말 끝내시겠습니까?")) {
      pausefunction();
      reset();
    }
  };

  return (
    <Layout title="운동시작">
      <div className="flex flex-col bg-d-lighter text-gray-700 max-w-lg max-h-[85vh] text-center">
        {/* 시간이 흘러갈때는 초록색으로 표기하고 멈췄을때는 빨간색으로 표기하는것 적용 */}
        {workoutdata.exercises ? (
          <>
            <div className="relative flex justify-between items-center border-transparent h-[3em] px-[1em] py-[0.1em] xs:py-[0.5em] text-[1.5em] font-bold text-white">
              <span className="flex w-[17rem] px-[1em] xs:px-[1.2em] pt-[0.3em] xs:pt-[0.4em] xs:text-[1.5rem] break-keep overflow-x-clip text-left">
                {/* <span className="flex basis-1/2 px-[1em] xs:px-[1.2em] pt-[0.3em] xs:pt-[0.4em] xs:text-[1.5rem] whitespace-nowrap overflow-x-clip overflow-y-visible text-left"> */}
                {specificname === "list"
                  ? workoutdata.exercises[0].exerciseName
                  : specificname}
              </span>
              <button
                type="button"
                readOnly
                className="flex justify-center items-center cursor-pointer w-[10rem] xs:w-[24rem] h-[3rem] text-[1em] xs:text-[1.7em]    m-auto mx-[0.5em] rounded-xl bg-d-dark shadow-lg shadow-black transition duration-150 active:ml-[0.5em] active:shadow-none hover:bg-d-hover max-w-"
                onClick={pausefunction}
                onKeyDown={pausefunction}
              >
                {stopped ? (
                  <Timebutton
                    color="text-red-700"
                    time={timeonscreen(worktime)}
                  />
                ) : (
                  <Timebutton
                    color="text-green-700"
                    time={timeonscreen(worktime)}
                  />
                )}
              </button>
              {/* <hr className='flex items-center first-letter:mx-2 pb-[1vh]'></hr> */}
            </div>
            <div className="flex max-w:100% my-[5rem] h-[6rem] xs:h-[12rem] border-none mx-[2.4em] justify-center items-center text-white">
              <img src={specificpic} alt={workoutdata.exercises[0].imageUrl} />
            </div>
            {/* 여기에서 onclick 이벤트로 setState 넣어두면 무한반복되어버림=>useRef사용예정 */}
            <div className="flex-col w-full h-[40rem] xs:h-[40rem] max-h-[58rem] mx-auto overflow-scroll">
              {specificset === "list"
                ? workoutdata.exercises.map((x, idx) => (
                    <Worklistbutton
                      key={`${x.exerciseId}${idx}`}
                      id={x.exerciseId}
                      name={x.exerciseName}
                      idx={idx}
                      iscompleted={x.isCompleted}
                      setState={setSpecificset}
                      hoverchangepic={x.imageUrl}
                      setpic={setSpecificpic}
                      setNamestate={setSpecificname}
                    />
                  ))
                : workoutdata.exercises[specificset].eachRecords.map(
                    (x, idx) => (
                      <EachRecordbutton
                        key={idx}
                        kg={x.weight}
                        count={x.count}
                        idx={idx}
                        iscompleted={x.eachCompleted}
                        fn={() => setclicked(x.timer, specificset, idx)}
                        restfn={setIsrestmodalon}
                      />
                    )
                  )}
            </div>
          </>
        ) : null}
        <div className="bg-d-dark max-w-lg z-[9995] text-white border-none fixed bottom-0 w-full pl-10 pb-3 pt-3 flex justify-between">
          {/* <div className="relative z-[9995] justify-between items-center w-full mt-auto mb-0 h-[4em] pb-24 overflow-clip"> */}
          {specificset === "list" ? (
            <>
              <Smallbutton name="운동종료" fn={gohome} />
              {/* <Movingbutton fn={() => setIsrestmodalon([true,60])}/> */}
            </>
          ) : (
            <>
              <Smallbutton name="이전" fn={goback} />
              {/* 첫분기: 완료표시안된 운동이 있습니까 -> 두번째분기: 뒤에 더 운동이 없습니까 */}

              {!workoutdata.exercises[specificset].isCompleted ? (
                <Smallbutton name="세트완료" ifnext="mr-[2.5em]" fn={setdone} />
              ) : workoutdata.exercises.length - 2 < specificset ? (
                <Smallbutton
                  name="운동완료"
                  fn={finished}
                  ifnext="mr-[2.5em]"
                />
              ) : (
                <Smallbutton name="다음운동" fn={gonext} ifnext="mr-[2.5em]" />
              )}
            </>
          )}
          {isrestmodalon[0] ? (
            <Restmodal data={isrestmodalon} fn={setIsrestmodalon} />
          ) : null}
          {workoutdone ? (
            <Congrats workoutdone reset={reset} recordweight={recordweight} />
          ) : null}
        </div>
      </div>
    </Layout>
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
