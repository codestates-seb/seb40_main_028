import React from "react";
import { Pagination } from "swiper";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/swiper-bundle.min.css";
import "swiper/swiper.min.css";

import icons from "../../assets/icons.png";

const CalendarContent = ({ todos }) => {
  return (
    <div className="flex-auto p-2 pt-0 rounded-xl">
      <div className="w-full text-center">
        <div className="text-sm mb-3 flex justify-center items-center">
          오늘은&nbsp;
          <div className="text-white"> {todos[0].totalTime}분</div>&nbsp;운동을
          하셨습니다!
        </div>
      </div>
      <div className="w-full h-[10em] flex flex-col">
        <Swiper
          direction="vertical"
          slidesPerView={2}
          spaceBetween={0}
          speed={400}
          pagination={{ clickable: true }}
          modules={[Pagination]}
          className="mySwipers"
        >
          {todos[0].exercise.map((item, idx) => (
            <SwiperSlide key={idx}>
              <div className="flex min-w-[19em] min-h-[4em] mr-8 justify-between items-center px-2 group rounded-lg bg-[#4E525A] shadow-lg">
                <img
                  src={icons}
                  alt="icons"
                  className="w-10 h-10 bg-[#3c3f45] rounded-xl"
                />
                <h3 className="text-white font-semibold">
                  {item.exerciseName}
                </h3>
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
            </SwiperSlide>
          ))}
        </Swiper>
      </div>
    </div>
  );
};

export default CalendarContent;
