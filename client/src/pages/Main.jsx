import React from "react";
import Layout from "../components/Layout";
import MainCalendar from "../components/Main/MainCalendar";
import YouTubeContainer from "../components/Main/YouTubeContainer";
// import styled from "styled-components";
// import Loding from '../components/Loding';

const Main = () => {
  return (
    <Layout title="근로그" hasTabBar>
      <div className="text-gray-700 h-screen text-medium text-xl text-center bg-d-lighter flex flex-col items-center justify-start">
        <MainCalendar />
        <YouTubeContainer />
      </div>
    </Layout>
  );
};
export default Main;
