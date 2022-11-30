import DatePicker from "@hassanmojab/react-modern-calendar-datepicker";
import "@hassanmojab/react-modern-calendar-datepicker/lib/DatePicker.css";
import React from "react";

export default function PlanCalendar({ selectedDay, setSelectedDay }) {
  const renderCustomInput = ({ ref }) => (
    <label
      htmlFor="my-custom-input-class"
      className="flex items-center rounded-full  ease-out duration-150 hover:bg-d-light bg-d-dark text-white outline-none text-medium p-[0.3em] "
    >
      <svg
        xmlns="http://www.w3.org/2000/svg"
        fill="none"
        viewBox="0 0 24 24"
        strokeWidth={1.5}
        stroke="currentColor"
        className="w-6 h-6 ml-2"
      >
        <path
          strokeLinecap="round"
          strokeLinejoin="round"
          d="M6.75 3v2.25M17.25 3v2.25M3 18.75V7.5a2.25 2.25 0 012.25-2.25h13.5A2.25 2.25 0 0121 7.5v11.25m-18 0A2.25 2.25 0 005.25 21h13.5A2.25 2.25 0 0021 18.75m-18 0v-7.5A2.25 2.25 0 015.25 9h13.5A2.25 2.25 0 0121 11.25v7.5"
        />
      </svg>
      <input
        readOnly
        ref={ref}
        value={
          selectedDay
            ? `${selectedDay.year}년 ${selectedDay.month}월 ${selectedDay.day}일`
            : ""
        }
        id="my-custom-input-class"
        className="my-custom-input-class outline-none ml-2 mr-2 w-[8.2em] bg-transparent hover:bg-transparent"
      />
    </label>
  );

  return (
    <DatePicker
      value={selectedDay}
      onChange={setSelectedDay}
      colorPrimary="#5865f2"
      renderInput={renderCustomInput}
      calendarClassName="text-[0.8em]  text-black"
      shouldHighlightWeekends
    />
  );
}
