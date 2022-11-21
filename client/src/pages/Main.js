import Layout from "../components/Layout";
import MainCalendar from "../components/MainCalendar";
import styled from "styled-components";
// import Loding from '../components/Loding';

const Main = () => {
  return (
    <Layout title="근로그" hasTabBar>
      <div className="text-gray-700 h-screen text-medium text-xl text-center mt-5 bg-d-lighter flex items-start justify-center">
        {/* <Loding /> */}
        <MainCalendar />
      </div>
    </Layout>
  );
};
export default Main;
