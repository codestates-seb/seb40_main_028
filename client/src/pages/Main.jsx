import React from "react";
import Layout from "../components/Layout";
import MainCalendar from "../components/Main/MainCalendar";
import YouTubeContainer from "../components/Main/YouTubeContainer";
// import styled from "styled-components";
// import Loding from '../components/Loding';

const Main = () => {
  return (
    <Layout title="근로그" hasTabBar>
      <div className="text-gray-700 text-medium text-xl text-center bg-d-lighter flex flex-col items-center mb-[-2em]">
        <MainCalendar />

        <div className="mb-[-1.75em]">
          <div className="w-full h-1 rounded-md bg-gray-200 mt-12 mb-2" />
          <h2 className="text-white text-center font-semibold mb-1">
            추천 운동 영상
          </h2>
          <p className="whitespace-nowrap text-sm leading-6 text-gray-500">
            클릭시 유튜브로 이동합니다.
          </p>
        </div>
        <YouTubeContainer />
      </div>
    </Layout>
  );
};
export default Main;
