import React from "react";
import Modal from "react-modal";
import useInterval from "../../assets/Interval";
import { Smallerbutton, Exitbutton } from "./ExerciseButton";

const categories = [
  "30",
  "60",
  "120",
  "180",
];

function Restmodal({data, fn}) {
  const timeonscreen = (time, specific) => {
    const hours = Math.floor(time/3600).toLocaleString("en-US",{minimumIntegerDigits:2});
    const minutes = Math.floor((time%3600)/60).toLocaleString("en-US",{minimumIntegerDigits:2});
    const seconds = Math.floor((time%60)).toLocaleString("en-US",{minimumIntegerDigits:2});
    let result = `${hours}:${minutes}:${seconds}`;
    if(specific === "minutes") {result = `${minutes}:${seconds}`};
    return result;
  }

  useInterval(() => {
    if(data[1] > 0) fn([true,data[1]-1]);
    return null;
  },!data[0])
  
  const style = {
    overlay: {
      position: "fixed",
      top: 0,
      left: 0,
      right: 0,
      bottom: 0,
      backgroundColor: "rgba(15, 15, 15, 0.49)",
      zIndex: 9997,
      display: "flex",
      justifyContent: "center",
      alignItems: "center",
    },
    content: {
      position: "relative",
      width: "24rem",
      height: "32.5em",
      border: "1px solid #ccc",
      background: "gray",
      overflow: "auto",
      backgroundColor: "#303030",
      borderRadius: "0.8em",
      borderColor: "#303030",
      outline: "none",
      padding: 0,
      zIndex: 9998,
      inset: 0,
      boxShadow:
        "rgba(0, 0, 0, 0.25) 0px 54px 55px, rgba(0, 0, 0, 0.12) 0px -12px 30px, rgba(0, 0, 0, 0.12) 0px 4px 6px, rgba(0, 0, 0, 0.17) 0px 12px 13px, rgba(0, 0, 0, 0.09) 0px -3px 5px",
    },
  };

  return (
    <Modal
      overlayClassName="w-full max-w-lg mx-auto"
      preventScroll={false}
      isOpen={data[0]}
      ariaHideApp={false}
      onRequestClose={() =>{fn(false,data[1]);}}
      style={style}
    >
      <div className="flex flex-col justify-center items-center">
        <div className="flex pt-[1rem] text-[2rem] text-white">휴식시간측정기</div>
        <div className={`flex justify-center items-center text-[4rem] ${data[1]!==0? "text-green-700":"text-red-700"}`}>{timeonscreen(data[1])}</div>
        {categories.map((item) => (
          <div className="flex flex-wrap basis-1/2 justify-center items-center m-[0.5em]">
            <Smallerbutton key={item} name={timeonscreen(item, "minutes")} fn={() => {fn([true, item])}}/>
          </div>
        ))}
        <div className="flex pt-[2rem]">
          <Exitbutton name="뒤로가기" fn={(()=> fn(false,data[1]))} />  
        </div>
        
      </div>
      
    </Modal>

    
    
    
  );
}

export default Restmodal;
