import React from "react";
import { useRecoilState } from "recoil";
import { categorie } from "../../../state/states";
import Button from "../../Button";
import Loading from "../../Loading";

const DetailListModal = ({
  Modals,
  setModals,
  selectedExercise,
  setSelectedExercise,
  selectedCategory,
}) => {
  const [categories, setCategories] = useRecoilState(categorie);
  const List = categories[selectedCategory].exercises;

  if (!List)
    return (
      <div className="flex justify-center items-center h-[29em]">
        <Loading />
      </div>
    );
  return (
    <>
      <div className="flex flex-wrap overflow-scroll justify-center max-h-[30em]">
        {List.map((item, idx) => {
          return (
            <button
              type="button"
              key={idx}
              onClick={() => {
                setSelectedExercise(idx);
                setModals(Modals + 1);
              }}
              className="flex flex-col mb-3 items-center justify-center w-[7em] h-[7em]"
            >
              <div className="w-16 h-16 hover:bg-d-button-hover pt-1 bg-[#d9d9d9] scale-[4em] rounded-lg ease-out duration-150 flex items-center justify-center">
                {item.imageUrl ? (
                  <img
                    src={item.imageUrl}
                    alt="ExercisesImage"
                    className=" top-0 left-0 bg-left-top"
                  />
                ) : (
                  <div className="mb-3">
                    <Loading small />
                  </div>
                )}
              </div>
              <div className="font-medium text-[0.8em] mt-[0.6em] h-5 w-[7em] break-keep">
                {item.name}
              </div>
            </button>
          );
        })}
      </div>

      <div className="bottom-[0rem] px-3 h-[5rem] absolute left-[0.25rem]  items-center flex flex-col justify-center space-y-3 bg-[#303030]">
        <div className="flex items-center justify-center">
          <div className="flex w-[23em] justify-center items-center">
            <Button text="뒤로가기" beforeModal />
          </div>
        </div>
      </div>
    </>
  );
};

export default DetailListModal;
