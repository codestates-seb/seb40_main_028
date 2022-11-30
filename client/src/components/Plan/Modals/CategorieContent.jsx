import React from "react";
import { useRecoilState } from "recoil";
import { categorie } from "../../../state/states";
import Loading from "../../Loading";

const CategorieContent = ({ Modals, setModals, setSelectedCategory }) => {
  const [categories, setCategories] = useRecoilState(categorie);

  if (!categories)
    return (
      <div className="flex justify-center items-center h-[29em]">
        <Loading />
      </div>
    );
  return (
    <>
      <div className="flex flex-wrap items-center justify-center ">
        {categories.map((item, idx) => {
          return (
            <button
              type="button"
              key={idx}
              onClick={() => {
                setSelectedCategory(idx);
                setModals(Modals + 1);
              }}
              className="flex flex-col justify-center items-center w-[7em] h-[7em]"
            >
              <div className="w-16 h-16 hover:bg-d-button-hover bg-[#d9d9d9] rounded-lg ease-out duration-150" />
              <div className="font-medium text-[0.8em] mt-1">
                {item.name} 운동
              </div>
            </button>
          );
        })}
      </div>
    </>
  );
};

export default CategorieContent;
