import Layout from "../components/Layout";
import Map from "../components/Map";
import styled from "styled-components/macro";
import SearchPlace from "../components/SearchPlace";
// import Loading from '../components/Loding';
// import { useState } from 'react';

// const Container = styled.div`
//   display: flex;
//   align-items: center;
//   justify-content: center;
//   height: 100vh;
// `;

const MapContainer = styled.div`
  margin-top: 1.5em;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: white;
  width: 100%;
`;

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
