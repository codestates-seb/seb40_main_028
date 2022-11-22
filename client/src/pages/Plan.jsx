import React, { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import icons from "../assets/icons.png";
import Layout from "../components/Layout";
import PlanModal from "../components/Plan/Modals/PlanModal";
import PlanAdd from "../components/Plan/PlanAdd";
import PlanCalendar from "../components/Plan/PlanCalendar";
import PlanList from "../components/Plan/PlanList";
import RandomMessage from "../components/Plan/RandomMessage";
import { isModal, ModalNum } from "../state/states";

const Plan = () => {
  const [isModalOpen, setIsModalOpen] = useRecoilState(isModal);
  const [categories, setCategories] = useState(null);
  const [Modals, setModals] = useRecoilState(ModalNum);

  const [data, setData] = useState(null);

  useEffect(() => {
    setData([
      {
        id: 1,
        title: "팔굽혀펴기",
      },
      {
        id: 2,
        title: "숄더프레스",
      },
      {
        id: 3,
        title: "팔굽혀펴기",
      },
      {
        id: 4,
        title: "숄더프레스",
      },
      {
        id: 5,
        title: "팔굽혀펴기",
      },
      {
        id: 6,
        title: "숄더프레스",
      },
    ]);
  }, []);

  useEffect(() => {
    if (!isModalOpen) {
      setModals(0);
    }
  }, [isModalOpen]);

  useEffect(() => {
    // 모달창 열리면 스크롤 방지
    if (isModalOpen) {
      document.body.style.overflow = "hidden";
    } else {
      document.body.style.overflow = "unset";
    }
  }, [isModalOpen]);

  return (
    <div className="h-screen bg-d-lighter relative">
      <Layout title="계획작성" hasTabBar>
        <div className="flex flex-col h-full items-center mt-5">
          <PlanCalendar />
        </div>
        <div className="flex justify-center">
          {data ? (
            <PlanList data={data} setData={setData} />
          ) : (
            <div className="flex flex-col items-center justify-center mt-[9em]">
              <img src={icons} alt="icons" className="w-48 h-48 rounded-2xl" />
              <div className="text-[#cccccc] font-semibold text-[1.7em] rounded-lg mt-[-1.5em]">
                오늘 할일이 아직 없습니다
              </div>
              <div className="mt-2">
                <RandomMessage large />
              </div>
            </div>
          )}
        </div>
        <PlanAdd
          setIsModalOpen={setIsModalOpen}
          setCategories={setCategories}
        />

        {isModalOpen && (
          <PlanModal
            isModalOpen={isModalOpen}
            setIsModalOpen={setIsModalOpen}
          />
        )}
      </Layout>
    </div>
  );
};

export default Plan;
