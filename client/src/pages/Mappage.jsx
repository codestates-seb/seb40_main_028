import React from "react";
import Layout from "../components/Layout";
import SearchPlace from "../components/Map/SearchPlace";
// import Loading from '../components/Loding';
// import { useState } from 'react';

// const Container = styled.div`
//   display: flex;
//   align-items: center;
//   justify-content: center;
//   height: 100vh;
// `;

const Mappage = () => {
  return (
    <div className="h-screen">
      <Layout title="지도" hasTabBar>
        <SearchPlace />
      </Layout>
    </div>
  );
};

export default Mappage;
