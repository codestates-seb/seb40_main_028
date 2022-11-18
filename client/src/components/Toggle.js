import React, {useState} from 'react';
// import styled from "styled-components";

import "./Toggle.css";


const Toggle = () => {


  //   const ToggleSwitch = styled.label`
  //     width: 100px;
  //     margin: 30px;
  //     height: 50px;
  //     display: block;
  //     position: relative;
  //     border-radius: 30px;
  //     background-color: #fff;
  //     box-shadow: 0 0 16px 3px rgba(0 0 0 / 15%);
  //     cursor: pointer;
  //     `;
  
  //   /* 토글 버튼 */
  //   const ToggleButton = styled.span`
  //     /* 버튼은 토글보다 작아야함  */
  //     width: 40px;
  //     height: 40px;
  //     position: absolute;
  //     top: 50%;
  //     left: 4px;
  //     transform: translateY(-50%);
  //     border-radius: 50%;
  //     background: #f03d3d;
  
  //     `;
  
  const [isOpen, setIsOpen] = useState(false);
  const openModalHandler = () => {
    setIsOpen(!isOpen);
  };



  return(
    <>
    
      <input type="checkbox" id="toggle" hidden/> 

      <label htmlFor="toggle" className="toggleSwitch" onClick={openModalHandler}>
        <span className="toggleButton" >{isOpen ? "남자" : "여자"}</span>
      </label>

      


    </>

  );

};

export default Toggle;

