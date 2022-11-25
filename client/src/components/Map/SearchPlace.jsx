/* eslint-disable */
import React, { useState } from "react";
import styled from "styled-components/macro";
import Map from "./Map";

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const SearchPlace = () => {
  const [inputText, setInputText] = useState("");
  const [place, setPlace] = useState("");

  const onChange = (e) => {
    setInputText(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setPlace(inputText);
    setInputText("");
  };

  return (
    <>
      <Container>
        <div className="text-[1.1em] font-medium text-white mt-3 mb-2">
          동네 헬스장 찾기
        </div>
        <form className="inputForm flex" onSubmit={handleSubmit}>
          <input
            className="w-72 h-10 mb-3 border-2 border-d-light  border-r-0 text-center rounded-tl-md rounded-bl-md pl-2 outline-none"
            placeholder="근처 역 또는 지역을 입력해주세요"
            onChange={onChange}
            value={inputText}
          />
          <button
            type="submit"
            className="border-2 border-l-0 border-d-light rounded-tr-md bg-d-button hover:bg-d-button-hover rounded-br-md text-center text-white w-12 h-10 "
          >
            검색
          </button>
        </form>
        <Map searchPlace={place} />
      </Container>
    </>
  );
};

export default SearchPlace;
