import React from "react";
import { useState } from "react";
import RandomMessage from "./RandomMessage";
import icons from "../../assets/icons.png";
export default function PlanList({ data, setData }) {
  return (
    <div className="flex flex-col items-center w-full text-gray-700 text-medium text-xl text-center mt-5">
      {data.map((item) => (
        <div
          className="flex relative shadow-sm justify-between py-4  ease-out duration-150 p-1 rounded-lg mb-2
    w-[80%] border-[#2C2F33] bg-[#4E525A] hover:border-b-d-light hover:bg-[#2f3136] hover:rounded-lg  text-white text-base text-medium"
          key={item.id}
        >
          <img
            src={icons}
            alt="image"
            className="w-12 h-12 bg-[#3c3f45] rounded-2xl absolute ml-3 mt-1"
          />
          <div className="flex flex-col text-left ml-[5.5em]  space-y-2">
            <div className="font-semibold text-[1em] text-[#dcddde]">
              {item.title}
            </div>
            <RandomMessage />
          </div>
          <div className="mt-[-0.7em]">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              strokeWidth="1.5"
              stroke="currentColor"
              className="w-6 h-6 text-red-400 hover:text-red-600 hover:scale-125 ease-out duration-150"
              onClick={() => {
                //alert 로 삭제할지 물어보고 삭제해줘
                if (window.confirm("삭제하시겠습니까?")) {
                  setData(data.filter((data) => data.id !== item.id));
                  // axios delete 요청
                }
                return;
              }}
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M6 18L18 6M6 6l12 12"
              />
            </svg>
          </div>
        </div>
      ))}
    </div>
  );
}
