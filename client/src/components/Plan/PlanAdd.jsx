import axios from "axios";
import React from "react";
import { useRecoilState } from "recoil";
import { categorie, TokenValue } from "../../state/states";

export default function PlanAdd({ setIsModalOpen }) {
  const [categories, setCategories] = useRecoilState(categorie);
  const [token, setToken] = useRecoilState(TokenValue);
  const URL = process.env.REACT_APP_BASE_URL;

  const getApi = async () => {
    await axios
      .get(URL + `/exercises/category`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => {
        setCategories(res.data.data);
      });
  };

  const openModal = () => {
    getApi();
    setIsModalOpen(true);
  };

  return (
    <div className="w-full max-w-lg fixed flex justify-end pr-6 bottom-[6em]">
      <button
        type="button"
        onClick={openModal}
        className=" hover:bg-d-button-hover aspect-square border-transparent 
      transition-colors cursor-pointer shadow-xl bg-d-button rounded-full w-14
      flex items-center justify-center text-white  min-h-[56px] min-w-[56px] "
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
      </button>
    </div>
  );
}
