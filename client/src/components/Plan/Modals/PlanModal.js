import { useRecoilState } from 'recoil';
import { selectedDays, categorie, ModalNum } from '../../../state/states';
import { useState } from 'react';
import React from 'react';
import Modal from 'react-modal';
import icons from '../../../assets/icons.png';
import DetailListModal from './DetailListContent';
import CategorieContent from './CategorieContent';
import DetailContent from './DetailContent';

const PlanModal = ({ isModalOpen, setIsModalOpen }) => {
  const style = {
    overlay: {
      position: 'fixed',
      top: 0,
      left: 0,
      right: 0,
      bottom: 0,
      backgroundColor: 'rgba(15, 15, 15, 0.49)',
      zIndex: 9998,
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
    },
    content: {
      position: 'relative',
      width: '25rem',
      height: '40.5em',
      border: '1px solid #ccc',
      background: 'gray',
      overflow: 'auto',
      backgroundColor: '#303030',
      WebkitOverflowScrolling: 'touch',
      borderRadius: '0.7em',
      borderColor: '#36393F',
      outline: 'none',
      padding: 0,
      zIndex: 9999,
      inset: 0,
      boxShadow:
        'rgba(0, 0, 0, 0.25) 0px 54px 55px, rgba(0, 0, 0, 0.12) 0px -12px 30px, rgba(0, 0, 0, 0.12) 0px 4px 6px, rgba(0, 0, 0, 0.17) 0px 12px 13px, rgba(0, 0, 0, 0.09) 0px -3px 5px',
    },
  };
  const [selectedDay, setSelectedDay] = useRecoilState(selectedDays); //달력 저장 날짜
  const [Modals, setModals] = useRecoilState(ModalNum); //모달 위치 저장
  const [selectedCategory, setSelectedCategory] = useState(null); //선택한 카테고리 요기 저장
  const [selectedExercise, setSelectedExercise] = useState(null); //선택한 운동 요기 저장
  const [detailExercise, setDetailExercise] = useState([
    {
      weight: '',
      count: '',
      timer: '',
    },
  ]);

  return (
    <>
      <Modal
        overlayClassName="w-full max-w-lg mx-auto"
        isOpen={isModalOpen}
        ariaHideApp={false}
        onRequestClose={() => setIsModalOpen(false)}
        style={style}
      >
        <div className="flex flex-col ">
          <div>
            <div className="h-9 mt-[-0.1em] bg-[#837f7f] rounded-t-lg flex items-center justify-center text-white font-semibold">
              {Modals === 0
                ? 'Categories'
                : Modals === 1
                ? '상세 운동 목록'
                : Modals === 2
                ? '상세 운동 설정'
                : null}
            </div>
            <img
              src={icons}
              alt="hi"
              className="w-12 h-12 bg-[#d9d9d9] rounded-2xl fixed mt-[-1.5em] ml-[1em]"
            />
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              strokeWidth={1.5}
              stroke="currentColor"
              className="w-6 h-6 text-[#d9d9d9] fixed mt-[-1.9em] ml-[22.9em] cursor-pointer hover:text-red-600"
              onClick={() => setIsModalOpen(false)}
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M6 18L18 6M6 6l12 12"
              />
            </svg>
            <div className="text-white text-xs ml-[6em] mt-[0.4em]">
              {selectedDay.year}/{selectedDay.month}/{selectedDay.day}
            </div>
          </div>

          <div className="mt-[1.4em] px-[1em] text-white flex justify-center ">
            {Modals === 0 ? (
              <CategorieContent
                Modals={Modals}
                setModals={setModals}
                setSelectedCategory={setSelectedCategory}
              />
            ) : Modals === 1 ? (
              <DetailListModal
                Modals={Modals}
                setModals={setModals}
                setSelectedExercise={setSelectedExercise}
              />
            ) : (
              // 상세 운동 설정 Modal
              <DetailContent
                selectedCategory={selectedCategory}
                selectedExercise={selectedExercise}
                detailExercise={detailExercise}
                setDetailExercise={setDetailExercise}
              />
            )}
          </div>
        </div>
      </Modal>
    </>
  );
};

export default PlanModal;
