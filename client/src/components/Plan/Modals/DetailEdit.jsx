import axios from "axios";
import dayjs from "dayjs";
import React, { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import { categorie, isModal } from "../../../state/states";
import { TokenState } from "../../../state/UserState";
import Button from "../../Button";
import Loading from "../../Loading";

const DetailEdit = ({
  selectedDay,
  editData,
  setData,
  setEditData,
  data,
  recordId,
  editRecord,
  setEditRecord,
  handleEdit,
}) => {
  const URL = process.env.REACT_APP_BASE_URL;
  const [isModalOpen, setIsModalOpen] = useRecoilState(isModal);
  const [token, setToken] = useRecoilState(TokenState);
  const [result, setResult] = useState(undefined);

  // 배열의 객체의 값이 모두 존재하는지 확인하는 함수
  const isAllValue = (arr) => {
    return arr.every((item) => {
      return Object.values(item).every((value) => value.trim().length);
    });
  };

  // 배열의 객체의 값들중 Number 타입의 값들과 boolean 타입의 값들을 ''로 감싸주는 함수
  const wrapValue = (arr) => {
    return arr.map((item) => {
      return Object.entries(item).reduce((acc, [key, value]) => {
        if (typeof value === "number" || typeof value === "boolean") {
          return { ...acc, [key]: `'${value}'` };
        }
        return { ...acc, [key]: value };
      }, {});
    });
  };

  const handleDelete = (id) => {
    setEditRecord(
      editRecord.filter((items, index) => {
        return index !== id;
      })
    );

    //삭제된 값 setResult에 반영하기
    setResult({
      ...result,
      eachRecords: wrapValue(
        editRecord.filter((items, index) => {
          return index !== id;
        })
      ),
    });
  };

  const handleSubmit = async (e) => {
    if (result) {
      const isValid = isAllValue(wrapValue(result.eachRecords));
      if (!isValid) {
        alert("빈칸을 전부 채워주세요!");
      } else {
        await axios
          .patch(
            URL + `/exercises/records/${recordId}`,
            {
              exerciseId: editData.exerciseId,
              eachRecords: [...editRecord],
            },
            {
              headers: {
                Authorization: token,
              },
            }
          )
          .then((res) => {
            handleEdit(recordId);
            setEditData(res.data.data);
            setEditRecord(res.data.data);
          });
      }
    }
    setIsModalOpen(false);
  };

  const onChange = (e, idx) => {
    setResult({
      exerciseId: editData.exerciseId,
      eachRecords: [...editRecord],
    });
    const newObject = [...editRecord];
    newObject[idx][e.target.name] = e.target.value;
    setEditRecord(newObject);
  };

  const handleAdd = () => {
    setEditRecord([
      ...editRecord,
      { weight: "", count: "", timer: "", eachCompleted: false },
    ]);

    // 추가된 값 setResult에 반영하기
    setResult({
      ...result,
      eachRecords: wrapValue([
        ...editRecord,
        { weight: "", count: "", timer: "", eachCompleted: false },
      ]),
    });
  };

  if (!editRecord) {
    return (
      <div className="flex justify-center items-center h-[29em]">
        <Loading />
      </div>
    );
  }
  return (
    <>
      <div>
        <div className="flex flex-col items-end mt-[-1.15em]">
          <div className="text-[0.9em] text-d-point font-semibold">
            운동이름
          </div>
          <div className="text-[0.75em] font-light">{editData.name}</div>
        </div>
        <div className="min-h-[11em] max-h-[11em] mt-5 mb-5 flex items-center justify-center object-contain aspect-video">
          {editData.images ? (
            <img src={editData.images} alt="icons" />
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
            {editRecord.map((item, idx) => {
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
                      if (editRecord.length === 1) {
                        alert("최소 1개의 세트가 필요합니다.");
                        return;
                      }
                      handleDelete(idx);
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
                  //생성하기 눌렀을때 결과 result에 반영
                  handleAdd();
                  //세트 추가
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
      <div className="bottom-[0rem] px-3 h-[5rem] absolute left-[27.5%] items-center flex flex-col justify-center space-y-3 bg-[#303030]">
        <Button text="수정하기" onClick={handleSubmit} />
      </div>
    </>
  );
};

export default DetailEdit;
