import React, { useEffect } from 'react';
import { useRecoilState } from 'recoil';
import { categorie } from '../state/states';
import axios from 'axios';

export default function PlanAdd({ setIsModalOpen }) {
  const [categories, setCategories] = useRecoilState(categorie);

  useEffect(() => {
    setCategories([
      '상체운동',
      '하체운동',
      '전신운동',
      '유산소운동',
      '근력운동',
    ]);
  }, []);

  const openModal = () => {
    setIsModalOpen(true);
    // axios.get('/api/plan').then((res) => {
    //   setCategories(res.data);
    // });
  };
  return (
    <div
      onClick={openModal}
      className="fixed hover:bg-d-button-hover aspect-square border-transparent 
      transition-colors cursor-pointer bottom-[6em] shadow-xl bg-d-button rounded-full w-14 flex items-center justify-center text-white ml-[23.5em]"
    >
      <svg
        xmlns="http://www.w3.org/2000/svg"
        fill="none"
        viewBox="0 0 24 24"
        strokeWidth={1.5}
        stroke="currentColor"
        className="w-8 h-8 "
      >
        <path
          strokeLinecap="round"
          strokeLinejoin="round"
          d="M12 4.5v15m7.5-7.5h-15"
        />
      </svg>
    </div>
  );
}
