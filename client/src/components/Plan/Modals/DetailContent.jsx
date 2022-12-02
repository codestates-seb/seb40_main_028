import axios from "axios";
import dayjs from "dayjs";
import React from "react";
import { useRecoilState } from "recoil";
import { categorie, isModal } from "../../../state/states";
import { TokenState } from "../../../state/UserState";
import Button from "../../Button";
import Loading from "../../Loading";

const DetailContent = ({
  selectedCategory,
  selectedExercise,
  detailExercise,
  setDetailExercise,
  selectedDay,
  data,
  setData,
}) => {
  const URL = process.env.REACT_APP_BASE_URL;
  const [isModalOpen, setIsModalOpen] = useRecoilState(isModal);
  const [token, setToken] = useRecoilState(TokenState);
  const [categories, setCategories] = useRecoilState(categorie);

  const List = categories[selectedCategory].exercises[selectedExercise];
  const date = dayjs(
    `${selectedDay.year}-${selectedDay.month}-${selectedDay.day}`
  ).format("YYYY-MM-DD");

  const ExercisesRecords = [
    // 운동 세부 기록 데이터 배열화
    detailExercise.map((item) => ({
      ...item,
      eachCompleted: false,
    })),
  ];

  const result = {
    // 운동 세부 기록과 아이디를 서버에 보낼 수 있게 데이터 객체화
    exerciseId: Number(List.id),
    eachRecords: [...ExercisesRecords[0]],
  };

  const handleSubmit = async (e) => {
    const isValid = detailExercise.every((item) =>
      Object.values(item).every((value) => value.trim().length)
    );
    if (!isValid) {
      alert("빈칸을 전부 채워주세요!");
    } else {
      await axios
        .post(URL + `/exercises/records?date=${date}`, result, {
          headers: {
            Authorization: token,
          },
        })
        .then((res) => {
          const newData = {
            recordId: res.data.data.recordId,
            name: List.name,
          };
          if (res.data.data.recordId && data) {
            setData([...data, newData]);
          } else {
            setData([newData]);
          }
        });

      setIsModalOpen(false);
    }
  };

  const onChange = (e, idx) => {
    const newObject = [...detailExercise];
    newObject[idx][e.target.name] = e.target.value;
    setDetailExercise(newObject);
  };

  if (!List)
    return (
      <div className="flex justify-center items-center h-[29em]">
        <Loading />
      </div>
    );
  return (
    <>
      <div>
        <div className="flex flex-col items-end mt-[-1.15em]">
          <div className="text-[0.9em] text-d-point font-semibold">
            {categories[selectedCategory].name} 운동
          </div>
          <div className="text-[0.75em] font-light">{List.name}</div>
        </div>
        <div className="min-h-[11em] max-h-[11em] mt-5 mb-5 flex items-center justify-center object-contain aspect-video">
          {List.imageUrl ? (
            <img src={List.imageUrl} alt="icons" />
          ) : (
            <Loading />
          )}
        </div>

        {/* 세트 표 상단 */}
        <div className="w-full h-[3em] rounded-lg px-4 flex justify-between items-center">
          <div className="text-white font-semibold text-[0.8em]">세트</div>
          <div className="text-white font-semibold text-[0.8em]">무게</div>
          <div className="text-white font-semibold text-[0.8em]">횟수</div>
          <div className="text-white font-semibold text-[0.8em]">타이머</div>
          <div className="text-white font-semibold text-[0.8em]">삭제</div>
        </div>

        <div className="h-[13rem] w-full  flex flex-col justify-center items-center overflow-scroll">
          <div className="w-full h-[3em]  mt-[-11em]  justify-center items-center ">
            {detailExercise.map((item, idx) => {
              return (
                <div
                  key={idx}
                  className="w-full h-[3em] rounded-lg px-4 flex justify-between items-center"
                >
                  <div className="text-white  w-[1em] font-semibold text-[0.8em] text-right">
                    {idx + 1}
                  </div>

                  <div className="text-[#dcddde] font-semibold text-[0.8em]">
                    <input
                      className="noappearance w-[3em]] text-center rounded-md"
                      onChange={(e) => onChange(e, idx)}
                      name="weight"
                      value={item.weight || ""}
                      placeholder={`${item.weight}kg`}
                      autoComplete="off"
                      type="number"
                    />
                  </div>
                  <div className="text-[#dcddde]  font-semibold text-[0.8em]">
                    <input
                      className="noappearance w-[3em] text-center rounded-md"
                      onChange={(e) => onChange(e, idx)}
                      name="count"
                      value={item.count || ""}
                      placeholder={`${item.count}회`}
                      autoComplete="off"
                      type="number"
                    />
                  </div>
                  <div className="text-[#dcddde] font-semibold text-[0.8em] ">
                    <input
                      className="noappearance w-[3em] text-center rounded-md"
                      onChange={(e) => onChange(e, idx)}
                      value={item.timer || ""}
                      name="timer"
                      placeholder={`${item.timer}초`}
                      autoComplete="off"
                      type="number"
                    />
                  </div>

                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                    strokeWidth={1.5}
                    stroke="currentColor"
                    className="w-6 h-6 text-[#d9d9d9] cursor-pointer ease-out duration-150 hover:text-red-600"
                    onClick={() => {
                      if (detailExercise.length === 1) {
                        alert("최소 1개의 세트가 필요합니다.");
                        return;
                      }
                      setDetailExercise(
                        detailExercise.filter((items, index) => {
                          return index !== idx;
                        })
                      );
                    }}
                  >
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      d="M6 18L18 6M6 6l12 12"
                    />
                  </svg>
                </div>
              );
            })}
            <div className="flex flex-col items-center justify-center">
              <button
                type="button"
                onClick={() => {
                  setDetailExercise([
                    ...detailExercise,
                    {
                      weight: "",
                      count: "",
                      timer: "",
                    },
                  ]);
                }}
                className="flex w-[20%] ease-out duration-150 whitespace-nowrap items-center p-1 mt-[0.5em] justify-center font-medium rounded-lg bg-d-button hover:bg-d-button-hover text-[0.8em]"
              >
                세트 추가
              </button>
              <div className="mt-[0.5em] h-[1em]" />
            </div>
          </div>
        </div>
      </div>
      <div className="bottom-[0rem] px-3 h-[5rem] absolute left-[-0.65rem]  items-center flex flex-col justify-center space-y-3 bg-[#303030]">
        <div className="flex items-center justify-center">
          <div className="flex justify-center ml-[2rem] space-x-10 items-center whitespace-nowrap">
            <div
              onClick={() => {
                setDetailExercise([
                  {
                    weight: "",
                    count: "",
                    timer: "",
                  },
                ]);
              }}
            >
              <Button
                text="뒤로가기"
                beforeModal
                resetDetails
                setDetailExercise={setDetailExercise}
                detailExercise={detailExercise}
              />
            </div>
            <Button text="저장하기" onClick={handleSubmit} />
          </div>
        </div>
      </div>
    </>
  );
};

export default DetailContent;
