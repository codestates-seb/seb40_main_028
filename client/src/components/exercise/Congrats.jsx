import React, { useState } from "react";
import Modal from "react-modal";
import Button from "../Button";

export default function Congrats({ workoutdone, reset, recordweight }) {
  const [weight, setWeight] = useState("");

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
  const onChange = (el) => {
    setWeight(el.target.value);
  };
  return (
    <Modal
      overlayClassName="w-full max-w-lg mx-auto"
      preventScroll={false}
      isOpen={workoutdone}
      ariaHideApp={false}
      onRequestClose={() => {}}
      style={style}
    >
      <div className="flex justify-center items-center">
        {/* <div className="flex fixed inset-0 bg-d-dark bg-opacity-90 h-full max-w-lg mx-auto z-40 justify-center items-center"> */}
        <form className="flex flex-col justify-center items-center">
          <div className="bg-d-light text-white h-[1em]" />
          <div className="text-white text-[3em] py-[1em]">축하합니다</div>
          <span className="flex justify-center text-white text-[1.8em]">
            Optional: 오늘의 몸무게
          </span>
          <span className="flex justify-center text-white text-[1.8em]">
            입력해주세요
          </span>
          <input
            placeholder="몸무게"
            className="flex rounded-xl h-[3.5rem] w-[10rem] my-[2rem] py-0 text-2xl placeholder:text-2xl placeholder:text-center text-center"
            onChange={onChange}
            value={weight}
          />
          <div className="flex justify-center items-center">
            <Button exercisedone={() => reset(weight)} text="홈으로" />
          </div>
        </form>
      </div>
    </Modal>
  );
}
