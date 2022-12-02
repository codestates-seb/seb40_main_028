/* eslint-disable */
import React, {useState} from "react";
import "./Toggle.css";

const Toggle = (props) => { 
  const [isOpen, setIsOpen] = useState(false);
  const openToggleHandler = () => {
    setIsOpen(!isOpen);
    // isOpen이 true면 "M", flase면 "W"
    props.setGender(isOpen ? "W" : "M")
  };
  


  return(
    <>
      <input type="checkbox" id="toggle" hidden/> 
      <label htmlFor="toggle" className="toggleSwitch" onClick={openToggleHandler}>
        <span className="toggleButton" >{isOpen ? "남자" : "여자"}</span>
      </label>
    </>
  );
};
export default Toggle;

