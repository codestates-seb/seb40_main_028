export default function PlanList() {
  const data = [
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
      title: '윗몸일으키기',
    },
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
      title: '윗몸일으키기',
    },
  ];
  return (
    <div className="flex flex-col items-center text-gray-700 text-medium text-xl text-center mt-5 h-[18em] overflow-y-scroll">
      {data.map((item) => (
        <div
          className="flex justify-between hover:my-1 hover:scale-110 ease-out duration-150 p-4 border rounded-xl w-3/4 border-[#2C2F33] bg-[#4E525A] hover:bg-d-light text-white text-base text-medium"
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
