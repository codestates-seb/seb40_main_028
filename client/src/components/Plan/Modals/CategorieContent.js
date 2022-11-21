import React from "react";

const categories = [
  "상체운동",
  "하체운동",
  "전신운동",
  "유산소운동",
  "근력운동",
];

const CategorieContent = ({ Modals, setModals, setSelectedCategory }) => {
  return (
    <>
      <div className="flex flex-wrap items-center justify-center ">
        {categories.map((item, idx) => {
          return (
            <div
              key={idx}
              onClick={() => {
                setModals(Modals + 1);
                setSelectedCategory(item);
              }}
              className="flex flex-col justify-center items-center w-[7em] h-[7em]"
            >
              <div className="w-16 h-16 hover:bg-d-button-hover bg-[#d9d9d9] rounded-lg"></div>
              <div className="font-medium text-[0.8em] mt-1">{item}</div>
            </div>
          );
        })}
      </div>
    </>
  );
};

export default CategorieContent;
