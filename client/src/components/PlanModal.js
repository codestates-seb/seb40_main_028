import { useRecoilState } from 'recoil';
import { selectedDays, categorie, ModalNum } from '../state/states';
import { useEffect, useState } from 'react';
import Button from './Button';
import React from 'react';
import Modal from 'react-modal';
import axios from 'axios';
import icons from '../assets/icons.png';
import imgss from '../assets/1111.png';
import imgsss from '../assets/11.png';
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
    },
    content: {
      position: 'absolute',
      display: 'flex',
      top: '50%',
      left: '50%',
      width: '23em',
      minWidth: '23em',
      height: '37em',
      minHeight: '37em',
      marginBottom: '10em',
      marginTop: '-21em',
      marginLeft: '-11.5em',
      border: '1px solid #ccc',
      background: 'gray',
      overflow: 'auto',
      backgroundColor: '#303030',
      WebkitOverflowScrolling: 'touch',
      borderRadius: '0.7em',
      borderColor: '#36393F',
      outline: 'none',
      padding: '10px',
      zIndex: 9999,
      boxShadow:
        'rgba(0, 0, 0, 0.25) 0px 54px 55px, rgba(0, 0, 0, 0.12) 0px -12px 30px, rgba(0, 0, 0, 0.12) 0px 4px 6px, rgba(0, 0, 0, 0.17) 0px 12px 13px, rgba(0, 0, 0, 0.09) 0px -3px 5px',
    },
  };
  const [selectedDay, setSelectedDay] = useRecoilState(selectedDays); //달력 저장 날짜
  const [categories, setCategories] = useRecoilState(categorie); //API 카테고리 호출후 저장
  const [Modals, setModals] = useRecoilState(ModalNum); //모달 위치 저장
  const [selectedCategory, setSelectedCategory] = useState(null); //선택한 카테고리 요기 저장
  const [selectedExercise, setSelectedExercise] = useState(null); //선택한 운동 요기 저장
  const [detailExercise, setDetailExercise] = useState([
    {
      weight: '',
      count: '',
      timer: '',
    },
  ]); //선택한 운동 상세 요기 저장
  const [finalExercise, setFinalExercise] = useState([]); //서버에 보낼 최종 저장 운동 요기 저장
  // const handleCategory = async (category) => {
  //   // axios.get(`/api/exercise/${category}`).then((res) => {
  //   // setCategories(res.data);
  //   // });
  // };
  const data = [
    {
      id: 1,
      name: '윗몸 일으윗몸 일으윗몸 일으윗',
    },
    {
      id: 2,
      name: '윗몸 일으키기2',
    },
    {
      id: 3,
      name: '윗몸 일으키기3',
    },
    {
      id: 4,
      name: '윗몸 일으키기4',
    },
    {
      id: 5,
      name: '윗몸 일으키기5',
    },
    {
      id: 6,
      name: '윗몸 일으키기6',
    },
    {
      id: 7,
      name: '윗몸 일으키기7',
    },
    {
      id: 8,
      name: '윗몸 일으키기2',
    },
    {
      id: 9,
      name: '윗몸 일으키기3',
    },
    {
      id: 10,
      name: '윗몸 일으키기4',
    },
    {
      id: 11,
      name: '윗몸 일으키기5',
    },
    {
      id: 12,
      name: '윗몸 일으키기6',
    },
    {
      id: 13,
      name: '윗몸 일으키기7',
    },
  ];

  useEffect(() => {
    setFinalExercise([
      ...detailExercise,
      selectedDay,
      selectedCategory,
      selectedExercise,
    ]);
    console.log(finalExercise);
  }, [detailExercise, selectedDay, selectedCategory, selectedExercise]);

  const onChange = (e, idx) => {
    const newObject = [...detailExercise];
    newObject[idx][e.target.name] = e.target.value;
    setDetailExercise(newObject);
  };

  return (
    <>
      <Modal
        isOpen={isModalOpen}
        ariaHideApp={false}
        onRequestClose={() => setIsModalOpen(false)}
        style={style}
      >
        <div className="flex flex-col fixed mt-[-21em] w-[23em] top-[50%] left-[50%] ml-[-11.5em]">
          <div>
            <div className="w-full h-9 bg-[#837f7f] rounded-t-lg flex items-center justify-center text-white font-semibold">
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
              className="w-6 h-6 text-[#d9d9d9] fixed mt-[-1.9em] ml-[21em] cursor-pointer hover:text-red-600"
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

          <div className="mt-[1.4em] px-[1em] flex items-center justify-center text-white ">
            {Modals === 0 ? (
              // 카테고리 설정 Modal

              <div className="flex flex-wrap items-center justify-start">
                {categories.map((item, idx) => {
                  return (
                    <div
                      key={idx}
                      onClick={() => {
                        setModals(Modals + 1);
                        setSelectedCategory(item);
                      }}
                      className="flex flex-col mb-3 items-center justify-center w-[7em] h-[7em]"
                    >
                      <div className="w-16 h-16 hover:bg-d-button-hover bg-[#d9d9d9] rounded-lg"></div>
                      <div className="font-medium text-[0.8em] mt-[0.5em]">
                        {item}
                      </div>
                    </div>
                  );
                })}
              </div>
            ) : Modals === 1 ? (
              // 상세 운동 목록 Modal
              <div className="flex flex-wrap items-center justify-start h-[27em] overflow-scroll">
                {data.map((item, idx) => {
                  return (
                    <div
                      key={idx}
                      onClick={() => {
                        setModals(Modals + 1);
                        setSelectedExercise(item);
                      }}
                      className="flex flex-col mb-3 ml-[0.5]  items-center justify-center w-[7em] h-[7em]"
                    >
                      <div className="w-16 h-16 hover:bg-d-button-hover pt-1 bg-[#d9d9d9] scale-[4em] rounded-lg">
                        <img
                          src={imgss}
                          alt="hi"
                          className=" top-0 left-0 bg-left-top"
                        />
                      </div>
                      <div className="font-medium text-[0.8em] mt-[0.6em]  h-5">
                        {item.name}
                      </div>
                    </div>
                  );
                })}
              </div>
            ) : (
              // 상세 운동 설정 Modal
              <div>
                <div className="flex flex-col items-end mt-[-1.15em]">
                  <div className="text-[0.9em] text-d-point font-semibold">
                    {selectedCategory}
                  </div>
                  <div className="text-[0.75em] font-light">
                    {selectedExercise.name}
                  </div>
                </div>

                <img src={imgsss} />

                {/* 세트 표 상단 */}
                <div className="w-full h-[3em] rounded-lg px-2 flex justify-between items-center">
                  <div className="text-white font-semibold text-[0.8em]">
                    세트
                  </div>
                  <div className="text-white font-semibold text-[0.8em]">
                    무게
                  </div>
                  <div className="text-white font-semibold text-[0.8em]">
                    횟수
                  </div>
                  <div className="text-white font-semibold text-[0.8em]">
                    타이머
                  </div>
                  <div className="text-white font-semibold text-[0.8em]">
                    삭제
                  </div>
                </div>

                <div className="h-[14.55em] p-1 w-full flex flex-col justify-center items-center overflow-scroll">
                  <div className="w-full h-[3em]  mt-[-12em]   justify-center items-center">
                    {detailExercise.map((item, idx) => {
                      return (
                        <div
                          key={idx}
                          className="w-full  h-[3em] rounded-lg px-4 flex justify-between items-center"
                        >
                          <div className="text-white  w-[1em] font-semibold text-[0.8em] text-right">
                            {idx + 1}
                          </div>

                          <div className="text-black font-semibold text-[0.8em]">
                            <input
                              className="w-[3em] rounded-[0.2em] text-center"
                              onChange={(e) => onChange(e, idx)}
                              name="weight"
                              value={item.weight || ''}
                              placeholder={`${item.weight}kg`}
                              autoComplete="off"
                            />
                          </div>
                          <div className="text-black  font-semibold text-[0.8em]">
                            <input
                              className="w-[3em] rounded-[0.2em] text-center"
                              onChange={(e) => onChange(e, idx)}
                              name="count"
                              value={item.count || ''}
                              placeholder={`${item.count}회`}
                              autoComplete="off"
                            />
                          </div>
                          <div className="text-black font-semibold text-[0.8em]">
                            <input
                              className="w-[3em] rounded-[0.2em] text-center"
                              onChange={(e) => onChange(e, idx)}
                              value={item.timer || ''}
                              name="timer"
                              placeholder={`${item.timer}초`}
                              autoComplete="off"
                            />
                          </div>

                          <svg
                            xmlns="http://www.w3.org/2000/svg"
                            fill="none"
                            viewBox="0 0 24 24"
                            strokeWidth={1.5}
                            stroke="currentColor"
                            className="w-6 h-6 text-[#d9d9d9] cursor-pointer hover:text-red-600"
                            onClick={() => {
                              if (detailExercise.length === 1) {
                                alert('최소 1개의 세트가 필요합니다.');
                                return;
                              }
                              setDetailExercise(
                                detailExercise.filter((item, index) => {
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
                        onClick={() => {
                          setDetailExercise([
                            ...detailExercise,
                            {
                              weight: '',
                              count: '',
                              timer: '',
                            },
                          ]);
                        }}
                        className="flex w-[20%] items-center p-1 mt-[0.5em] justify-center font-medium rounded-lg bg-d-button hover:bg-d-button-hover text-[0.8em]"
                      >
                        세트 추가
                      </button>
                      <div className="mt-[0.5em] h-[1em]" />
                    </div>
                  </div>
                </div>
              </div>
            )}
          </div>
          {Modals !== 0 ? (
            <div className="rounded-2xl w-full mx-auto h-[0.3em] flex items-center justify-center bg-white" />
          ) : null}
          <div className=" h-[7em] pb-[2em] flex items-center justify-center">
            {Modals === 1 ? (
              <div className="flex w-[23em] justify-center h-16 items-center">
                <Button text={'뒤로가기'} beforeModal />
              </div>
            ) : Modals === 2 ? (
              <div className="flex w-[23em] justify-center space-x-10 h-16 items-center">
                <div
                  onClick={() => {
                    setDetailExercise([
                      {
                        weight: '',
                        count: '',
                        timer: '',
                      },
                    ]);
                  }}
                >
                  <Button
                    text={'뒤로가기'}
                    beforeModal
                    resetDetails
                    setDetailExercise={setDetailExercise}
                    detailExercise={detailExercise}
                  />
                </div>
                <div
                  onClick={() => {
                    setModals(0);
                    setIsModalOpen(false);
                    setDetailExercise([
                      {
                        weight: '',
                        count: '',
                        timer: '',
                      },
                    ]);
                  }}
                >
                  <Button text={'저장하기'} />
                </div>
              </div>
            ) : null}
          </div>
        </div>
      </Modal>
    </>
  );
};

export default PlanModal;
