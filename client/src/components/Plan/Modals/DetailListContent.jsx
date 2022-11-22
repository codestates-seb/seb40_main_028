import React from "react";
import imgss from "../../../assets/1111.png";
import Button from "../../Button";

const data = [
  {
    id: 1,
    name: "윗몸 일으윗몸 일으윗몸 일으윗",
  },
  {
    id: 2,
    name: "윗몸 일으키기2",
  },
  {
    id: 3,
    name: "윗몸 일으키기3",
  },
  {
    id: 4,
    name: "윗몸 일으키기4",
  },
  {
    id: 5,
    name: "윗몸 일으키기5",
  },
  {
    id: 6,
    name: "윗몸 일으키기6",
  },
  {
    id: 7,
    name: "윗몸 일으키기7",
  },
  {
    id: 8,
    name: "윗몸 일으키기2",
  },
  {
    id: 9,
    name: "윗몸 일으키기3",
  },
  {
    id: 10,
    name: "윗몸 일으키기4",
  },
  {
    id: 11,
    name: "윗몸 일으키기5",
  },
  {
    id: 12,
    name: "윗몸 일으키기6",
  },
  {
    id: 13,
    name: "윗몸 일으키기7",
  },
];

const DetailListModal = ({ Modals, setModals, setSelectedExercise }) => {
  return (
    <>
      <div className="flex flex-wrap overflow-scroll h-[30em] items-center justify-center">
        {data.map((item, idx) => {
          return (
            <button
              type="button"
              key={idx}
              onClick={() => {
                setModals(Modals + 1);
                setSelectedExercise(item);
              }}
              className="flex flex-col mb-3 items-center justify-center w-[7em] h-[7em]"
            >
              <div className="w-16 h-16 hover:bg-d-button-hover pt-1 bg-[#d9d9d9] scale-[4em] rounded-lg ease-out duration-150">
                <img
                  src={imgss}
                  alt="hi"
                  className=" top-0 left-0 bg-left-top"
                />
              </div>
              <div className="font-medium text-[0.8em] mt-[0.6em] h-5">
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
