import { useState } from "react";
import Map from "./Map";
import styled from "styled-components/macro";

const Container = styled.div`
width: 500px;
height: auto;
`
const Input = styled.input`
  width: 280px;
  height: 28px;
  border: 1px solid #babfc4;
  border-radius: 4px;
  font-size: 15px;
  margin-left: 70px;
  &:focus {
    border: 1px solid #0995fd;
    box-shadow: 0 0 0 3.3px #ddeaf7;
  }
`;

const SerchButton = styled.button`
  margin-bottom: 25px;
  margin-left: 10px;
  width: 70px;
  background-color: #747BF2;
  border: 2px solid #737BF2;
  border-radius: 4px;
  cursor: pointer;
  :hover {
    background-color: #4C53BF;
    border: 2px solid #3C53BF;
  }
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
  const MapText = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 15px;
  margin: 0px 20px 20px 40px;
  
  `;

  return (
    <>
      <Container>
        <MapText>우리 동네 헬스장 찾아보기</MapText>
        <form className="inputForm" onSubmit={handleSubmit}>
          <Input
            placeholder="사시는 지역 또는 근처역을 입력해주세요."
            onChange={onChange}
            value={inputText}
          />
          <SerchButton type="submit">검색</SerchButton>
        </form>
        <Map searchPlace={place} />
      </Container>
    </>
  );
};

export default SearchPlace;