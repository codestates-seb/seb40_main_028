/* eslint-disable */
import React, {useState} from "react";
import "./Toggle.css";

const Toggle = () => { 
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

