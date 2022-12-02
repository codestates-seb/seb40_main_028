import React from "react";
import { useRecoilState } from "recoil";
import icons from "../../assets/icons.png";
import { isModal, ModalNum } from "../../state/states";
import Loading from "../Loading";
import RandomMessage from "./RandomMessage";

export default function PlanList({ data, setData, deletePlan, handleEdit }) {
  const [isModalOpen, setIsModalOpen] = useRecoilState(isModal);
  const [Modals, setModals] = useRecoilState(ModalNum);
  return (
    <div className="flex flex-col bg-d-lighter items-center w-full text-gray-700 text-medium text-xl text-center mt-5">
      {data.map((item, idx) => (
        <div
          onClick={() => {
            handleEdit(item.recordId);
            setIsModalOpen(true);
            setModals(3);
          }}
          key={idx}
          className="flex relative shadow-sm justify-between py-4  ease-out duration-150 p-1 rounded-lg mb-2
    w-[80%] border-[#2C2F33] bg-[#4E525A] hover:border-b-d-light hover:bg-[#2f3136] hover:rounded-lg  text-white text-base text-medium"
        >
          {icons ? (
            <img
              src={icons}
              alt="icons"
              className="w-12 h-12 bg-[#3c3f45] rounded-2xl absolute ml-3 mt-1"
            />
          ) : (
            <Loading small />
          )}

          <div className="flex flex-col text-left ml-[5.5em]  space-y-2">
            <div className="font-semibold text-[1em] text-[#dcddde]">
              {item.name}
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
              onClick={(e) => {
                e.stopPropagation();
                if (window.confirm("삭제하시겠습니까?")) {
                  deletePlan(item.recordId);
                }
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
