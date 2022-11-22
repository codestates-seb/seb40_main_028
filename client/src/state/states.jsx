import { atom } from "recoil";
import { recoilPersist } from "recoil-persist"
import dayjs from "dayjs";

const { persistAtom } = recoilPersist();
const now = dayjs().format("YYYY-MM-DD");
const splitNow = now.split("-");

export const selectedDays = atom({
  key: "selectedDays",
  default: {
    day: Number(splitNow[2]),
    month: Number(splitNow[1]),
    year: Number(splitNow[0]),
  },
});

export const categorie = atom({
  key: "categorie",
  default: [],
});

export const ModalNum = atom({
  key: "ModalNum",
  default: 0,
});

export const isModal = atom({
  key: "isModal",
  default: false,
});

// RJH states
export const timermodalState = atom({
  key: "timermodalState",
  // 0:모달창이 켜지는것에 대한 true false
  // 1:타이머가 돌아가는 시간
  default: [false, 0]
});

export const doneState = atom({
  key: "doneState",
  // 총운동시간
  default: [],
  effects_UNSTABLE:[persistAtom],
});

export const workoutlistState = atom({
  key: "workoutlistState",
  // 운동리스트전체
  default: {
    "success" : true, 
    "data" : {
      "today_id" : 1, 
      "totalTime" : 0, 
      "exercises" : [
        { 
          "exerciseId" : 1, 
          "is_comleted" : false, 
          "exerciseName" : "벤치프레스", 
          "imageUrl" : "s3.somewhere.somewhere",
          "eachRecords" : [ 
            {  
              "weight" : 30, 
              "count" : 6,
              "eachTimer" : 30,
              "eachCompleted" : false, 
            }, 
            { 
              "weight" : 30, 
              "count" : 6, 
              "eachTimer" : 30,
              "eachCompleted" : false, 
            },   
            { 
              "weight" : 30, 
              "count" : 6, 
              "eachTimer" : 30,
              "eachCompleted" : false, 
            }		
          ]
        }, 
        { 
          "exerciseId" : 2, 
          "is_comleted" : false, 
          "exerciseName" : "스쿼트", 
          "imageUrl" : "s3.somewhere.somewhere",
          "eachRecords" : [ 
            {  
              "weight" : 30, 
              "count" : 6,
              "eachTimer" : 30,
              "eachCompleted" :false, 
            }, 
            { 
              "weight" : 30, 
              "count" : 6, 
              "eachTimer" : 30,
              "eachCompleted" : false, 
            }, 
            { 
              "weight" : 30, 
              "count" : 6, 
              "eachTimer" : 30,
              "eachCompleted" : false, 
            }		
          ]
        }
      ] 
    }
  },
  effects_UNSTABLE:[persistAtom],
});