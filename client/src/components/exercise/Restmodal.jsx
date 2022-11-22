import React, { useState } from "react";
import { RecoilState, useRecoilState } from "recoil";
import useInterval from "../../assets/Interval";
import {timermodalState} from "../../state/states";
import { Smallerbutton } from "./ExerciseButton";

const categories = [
  "30",
  "60",
  "120",
  "180",
];

function Restmodal({ time }) {
  const [timermodal, setTimermodal] = useRecoilState(timermodalState);
    
  const timeonscreen = (time) => {
    const hours = Math.floor(time/3600).toLocaleString("en-US",{minimumIntegerDigits:2});
    const minutes = Math.floor((time%3600)/60).toLocaleString("en-US",{minimumIntegerDigits:2});
    const seconds = Math.floor((time%60)).toLocaleString("en-US",{minimumIntegerDigits:2});
    const result = `${hours}:${minutes}:${seconds}`;
    return result;
  }

  useInterval(() => {
    console.log("ticking")
    if(timermodal[1] > 0) return (setTimermodal([true,timermodal[1]-1]),false)
    return (null,false)
  })

  return (
    <div className="flex fixed inset-0 bg-d-dark bg-opacity-90 h-full max-w-lg mx-auto z-40 justify-center items-center">
      <div className="bg-d-light text-white h-[3vh]" />
      
      <div className="flex-col z-50 justify-center items-center h-[40vh] w-2/3 max-w-lg bg-d-lighter rounded-2xl overflow-hidden">
        <div className="flex flex-wrap items-center justify-center ">
          {categories.map((item, idx) => (
            <div
              key={idx}
              onClick={() => {
                setTimermodal([true, item])
              }}
              className="flex flex-col justify-center items-center w-[7em] h-[7em]"
            >
              <div className="w-16 h-16 hover:bg-d-button-hover bg-[#d9d9d9] rounded-lg" />
              <div className="font-medium text-[0.8em] mt-1">{item}</div>
            </div>
          ))}
        </div>

        
        <div className={`top-[7vh] ${timermodal[1]!==0? "text-green-700":"text-red-700"}`}>{timeonscreen(timermodal[1])}</div>
        <div className="relative top-[10vh] flex justify-center items-center my-[1vh]">
          <Smallerbutton name="00:30"/>
          <Smallerbutton name="01:00"/>
        </div>
        <div className="relative top-[11vh] flex justify-center items-center my-[1vh]">
          <Smallerbutton name="02:00"/>
          <Smallerbutton name="03:00"/>
        </div>
                
        <button className="w-12 h-12 bg-white" onClick={(()=> setTimermodal(false,timermodal[1]))}>뒤로가기</button>
        <div className="relative top-[20vh] flex justify-center items-center" />
      </div>
    </div>

    
  );
}

export default Restmodal;
