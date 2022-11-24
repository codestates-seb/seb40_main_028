import React from "react";
import icons from "../../assets/icons.png";

const CalendarContent = ({ todos }) => {
  return (
    <div className="flex-auto  p-2 rounded-xl bg-d-light">
      {todos[0].exercise.map((item) => (
        <li className="flex items-center px-2 py-2 pr-3 group rounded-lg bg-[#4E525A] mb-2 last:mb-0">
          <div className="flex justify-between items-center w-full">
            <img
              src={icons}
              alt="icons"
              className="w-10 h-10 bg-[#3c3f45] rounded-xl"
            />
            <h3 className="text-white font-semibold">{item.exerciseName}</h3>
            <p>
              {item.isComleted ? (
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  strokeWidth={1.5}
                  stroke="currentColor"
                  className="w-6 h-6 text-[#3ba55d]"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    d="M9 12.75L11.25 15 15 9.75M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
                  />
                </svg>
              ) : (
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  strokeWidth={1.5}
                  stroke="currentColor"
                  className="w-6 h-6 text-[#d83c3e]"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    d="M9.75 9.75l4.5 4.5m0-4.5l-4.5 4.5M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
                  />
                </svg>
              )}
            </p>
          </div>
        </li>
      ))}
    </div>
  );
};

export default CalendarContent;
