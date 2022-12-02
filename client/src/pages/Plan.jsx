import axios from "axios";
import dayjs from "dayjs";
import React, { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import Layout from "../components/Layout";
import Loading from "../components/Loading";
import PlanModal from "../components/Plan/Modals/PlanModal";
import PlanAdd from "../components/Plan/PlanAdd";
import PlanCalendar from "../components/Plan/PlanCalendar";
import PlanList from "../components/Plan/PlanList";
import RandomMessage from "../components/Plan/RandomMessage";
import { isModal, ModalNum, selectedDays } from "../state/states";
import { TokenState } from "../state/UserState";

const Plan = () => {
  const [isModalOpen, setIsModalOpen] = useRecoilState(isModal);
  const [categories, setCategories] = useState(null);
  const [Modals, setModals] = useRecoilState(ModalNum);
  const [token, setToken] = useRecoilState(TokenState);
  const [selectedDay, setSelectedDay] = useRecoilState(selectedDays);
  const [data, setData] = useState(undefined);
  const [editData, setEditData] = useState(undefined);
  const [recordId, setRecordId] = useState(undefined);
  const [editRecord, setEditRecord] = useState(undefined); //수정할 운동 요기 저장
  const URL = process.env.REACT_APP_BASE_URL;

  const dayFormat = dayjs(
    `${selectedDay.year}-${selectedDay.month}-${selectedDay.day}`
  ).format("YYYY-MM-DD");

  const deletePlan = async (id) => {
    await axios
      .delete(URL + `/exercises/records/${id}`, {
        headers: {
          Authorization: token,
        },
      })
      .then((res) => {
        setData(data.filter((item) => item.recordId !== id));
        if (data.length === 1) {
          setData(undefined);
        }
      });
  };

  const handleEdit = async (id) => {
    await axios
      .get(URL + `/exercises/records/${id}`, {
        headers: {
          Authorization: token,
        },
      })
      .then((res) => {
        setEditData(res.data.data);
        setEditRecord(res.data.data.records);
        setRecordId(id);
        setModals(3);
      });
  };

  useEffect(() => {
    setData(undefined);

    axios
      .get(URL + `/exercises/records?date=${dayFormat}`, {
        headers: {
          Authorization: token,
        },
      })
      .then((res) => {
        if (res.data?.data?.length === 0 || !res.data?.data) {
          setData([]);
        } else {
          setData(res.data.data);
        }
      });
  }, [selectedDay]);

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

  useEffect(() => {
    // DetailEdit 이전 사진 기록 삭제
    if (Modals === 0) {
      setEditRecord(false);
      setEditData(false);
    }
  }, [Modals]);

  return (
    <div className="h-screen bg-d-lighter relative">
      <Layout title="계획작성" hasTabBar>
        <div className="flex flex-col h-full items-center mt-5">
          <PlanCalendar
            selectedDay={selectedDay}
            setSelectedDay={setSelectedDay}
          />
        </div>
        <div className="flex justify-center">
          {data === undefined && (
            <div className="flex flex-col items-center justify-center mt-[16em]">
              <Loading />
            </div>
          )}
          {Boolean(data && data.length) && (
            <PlanList
              data={data}
              setData={setData}
              deletePlan={deletePlan}
              handleEdit={handleEdit}
            />
          )}
          {Boolean(data && !data.length) && (
            <div className="flex flex-col items-center justify-center mt-[12em]">
              <Loading />
              <div className="text-[#cccccc] font-semibold text-[1.7em] rounded-lg mt-[2em]">
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
            data={data}
            setData={setData}
            editData={editData}
            setEditData={setEditData}
            recordId={recordId}
            editRecord={editRecord}
            setEditRecord={setEditRecord}
            handleEdit={handleEdit}
          />
        )}
      </Layout>
    </div>
  );
};

export default Plan;
