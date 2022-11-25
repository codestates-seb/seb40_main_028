import React from "react";
import styled from "styled-components";
import { BallTriangle } from "react-loader-spinner";

const Container = styled.div`
  display: flex;
  padding-top: 2em;
  justify-content: center;
  align-items: center;
`;

const Loding = () => {
  return (
    <Container>
      <div>
        <BallTriangle
          height="150"
          width="150"
          color="#747BF2"
          ariaLabel="Loding"
        />
      </div>
    </Container>
  );
};

export default Loding;
