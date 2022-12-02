import React from "react";
import Button from "../components/Button";
import Layout from "../components/Layout";

const NotFound = () => {
  return (
    <Layout title="NotFound" hasTabBar canGoBack>
      <div className="h-screen flex flex-col items-center justify-center text-gray-700 text-medium text-xl text-center mt-[-4em]">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
          strokeWidth={1.5}
          stroke="currentColor"
          className="w-32 h-32 mx-auto text-[#DB6A6A]"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            d="M12 9v3.75m-9.303 3.376c-.866 1.5.217 3.374 1.948 3.374h14.71c1.73 0 2.813-1.874 1.948-3.374L13.949 3.378c-.866-1.5-3.032-1.5-3.898 0L2.697 16.126zM12 15.75h.007v.008H12v-.008z"
          />
        </svg>
        <div className="font-medium text-[0.8em] text-[#DB6A6A] mb-[2em]">
          존재하지 않는 페이지입니다.
        </div>
        <Button text="돌아가기" onGoMain large />
      </div>
    </Layout>
  );
};

export default NotFound;
