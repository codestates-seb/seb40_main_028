import Layout from '../components/Layout';
import Map from '../components/Map';
import styled from 'styled-components/macro';
import SearchPlace from '../components/SearchPlace';
// import Loading from '../components/Loding';
// import { useState } from 'react';

const Container = styled.div`
  display: flex;
  align-items: center;
  height: 100vh;
`;

const MapContainer = styled.div`
  margin-top: -310px;
`;





const Mappage = () => {
  return (
    <Container>
    <Layout title="지도" hasTabBar>
       <MapContainer>
       <SearchPlace />
       <Map />
       </MapContainer>
    </Layout>
    </Container>
  );
};

export default Mappage;
