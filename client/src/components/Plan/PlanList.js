import { useState } from 'react';
export default function PlanList() {
  const [data, setData] = useState([
    {
      id: 1,
      title: '팔굽혀펴기',
    },
    {
      id: 2,
      title: '숄더프레스',
    },
    {
      id: 3,
      title: '팔굽혀펴기',
    },
    {
      id: 4,
      title: '숄더프레스',
    },
    {
      id: 5,
      title: '팔굽혀펴기',
    },
    {
      id: 6,
      title: '숄더프레스',
    },
  ]);

  return (
    <div className="flex flex-col items-center w-full text-gray-700 text-medium text-xl text-center mt-5">
      {data.map((item) => (
        <div
          className="flex justify-between py-3 hover:scale-110 ease-out duration-150 p-1 border-b-white last:border-b-0 last:rounded-b-xl border
          first:rounded-t-lg w-[65%] border-[#2C2F33] bg-[#4E525A] hover:border-b-d-light hover:bg-[#2f3136] hover:rounded-lg  text-white text-base text-medium"
          key={item.id}
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            strokeWidth={1.5}
            stroke="currentColor"
            className="w-6 h-6"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              d="M3.75 9h16.5m-16.5 6.75h16.5"
            />
          </svg>
          {item.title}
          <div className=" my-0 h-full">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              strokeWidth="1.5"
              stroke="currentColor"
              className="w-6 h-6 text-red-400 hover:text-red-600 hover:scale-125 ease-out duration-150"
              onClick={() => {
                //alert 로 삭제할지 물어보고 삭제해줘
                if (window.confirm('삭제하시겠습니까?')) {
                  setData(data.filter((data) => data.id !== item.id));
                  // axios delete 요청
                }
                return;
              }}
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M6 18L18 6M6 6l12 12"
              />
            </svg>
          </div>
        </div>
      ))}
    </div>
  );
}
