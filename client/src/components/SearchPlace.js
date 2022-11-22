import { useState } from "react";
import Map from "./Map";
import styled from "styled-components/macro";

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: auto;
`;

// const Input = styled.input`
//   width: 280px;
//   height: 28px;
//   border: 1px solid #babfc4;
//   border-radius: 4px;
//   font-size: 15px;
//   margin-left: 70px;
//   outline: none;
//   &:focus {
//     border: 1px solid #0995fd;
//     box-shadow: 0 0 0 3.3px #ddeaf7;
//     outline: none;
//   }
// `;

// const SerchButton = styled.button`
//   margin-bottom: 25px;
//   margin-left: 10px;
//   width: 3em;
//   background-color: #747bf2;
//   border: 2px solid #737bf2;
//   border-radius: 4px;
//   cursor: pointer;
//   :hover {
//     background-color: #4c53bf;
//     border: 2px solid #3c53bf;
//   }
// `;

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
            placeholder="근처 지역을 입력해주세요"
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
