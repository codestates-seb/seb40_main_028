import React, { useEffect, useState } from "react";
import Modal from "react-modal";
import { useRecoilState } from "recoil";
import icons from "../../../assets/icons.png";
import { ModalNum, selectedDays } from "../../../state/states";
import CategorieContent from "./CategorieContent";
import DetailContent from "./DetailContent";
import DetailEdit from "./DetailEdit";
import DetailListModal from "./DetailListContent";

const PlanModal = ({
  isModalOpen,
  setIsModalOpen,
  data,
  setData,
  editData,
  setEditData,
  recordId,
  editRecord,
  setEditRecord,
  handleEdit,
}) => {
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
      height: "40.5em",
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
  const [selectedDay, setSelectedDay] = useRecoilState(selectedDays); //달력 저장 날짜
  const [Modals, setModals] = useRecoilState(ModalNum); //모달 위치 저장
  const [selectedCategory, setSelectedCategory] = useState(null); //선택한 카테고리 요기 저장
  const [selectedExercise, setSelectedExercise] = useState(null); //선택한 운동 요기 저장

  const [detailExercise, setDetailExercise] = useState([
    {
      weight: "",
      count: "",
      timer: "",
    },
  ]);
  const Title = () => {
    if (Modals === 0) {
      return "운동 카테고리";
    } else if (Modals === 1) {
      return "운동 선택";
    } else if (Modals === 2) {
      return "운동 세부 설정";
    } else if (Modals === 3) {
      return "운동 수정";
    }
  };

  return (
    <>
      <Modal
        overlayClassName="w-full max-w-lg mx-auto"
        preventScroll={false}
        isOpen={isModalOpen}
        ariaHideApp={false}
        onRequestClose={() => setIsModalOpen(false)}
        style={style}
      >
        <div className="flex flex-col">
          <div className="flex flex-col">
            <div className="h-9 px-1 bg-[#837f7f] border-2 border-[#837f7f] rounded-t-lg flex items-center justify-center text-white font-semibold">
              {Title()}
            </div>

            <img
              src={icons}
              alt="hi"
              className="w-12 h-12 bg-[#d9d9d9] rounded-2xl fixed mt-[0.9em] ml-[1em]"
            />

            <div className="text-white text-xs ml-[6em] mt-[0.4em]">
              {selectedDay.year}/{selectedDay.month}/{selectedDay.day}
            </div>
          </div>

          <div className="mt-[1.4em] px-[1em] text-white flex justify-center ">
            {Modals === 0 && (
              <CategorieContent
                Modals={Modals}
                setModals={setModals}
                setSelectedCategory={setSelectedCategory}
              />
            )}

            {Modals === 1 && (
              <DetailListModal
                Modals={Modals}
                setModals={setModals}
                setSelectedExercise={setSelectedExercise}
                selectedCategory={selectedCategory}
              />
            )}

            {Modals === 2 && (
              <DetailContent
                selectedCategory={selectedCategory}
                selectedExercise={selectedExercise}
                detailExercise={detailExercise}
                setDetailExercise={setDetailExercise}
                selectedDay={selectedDay}
                data={data}
                setData={setData}
              />
            )}

            {Modals === 3 && (
              <DetailEdit
                selectedCategory={selectedCategory}
                selectedExercise={selectedExercise}
                detailExercise={detailExercise}
                setDetailExercise={setDetailExercise}
                selectedDay={selectedDay}
                editData={editData}
                setEditData={setEditData}
                data={data}
                setData={setData}
                recordId={recordId}
                editRecord={editRecord}
                setEditRecord={setEditRecord}
                handleEdit={handleEdit}
              />
            )}
          </div>
        </div>
      </Modal>
    </>
  );
};

export default PlanModal;
