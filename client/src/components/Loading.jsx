import React from "react";
import styled from "styled-components";
import { BallTriangle } from "react-loader-spinner";

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Loading = ({ small }) => {
  return (
    <Container>
      {small ? (
        <div>
          <BallTriangle
            height="50"
            width="50"
            color="#747BF2"
            ariaLabel="Loding"
          />
        </div>
      ) : (
        <div>
          <BallTriangle
            height="100"
            width="100"
            color="#747BF2"
            ariaLabel="Loding"
          />
        </div>
      )}
    </Container>
  );
};

export default Loading;
