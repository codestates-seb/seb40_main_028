import { useSetRecoilState } from 'recoil';
import specificsetState from '../state/Workoutset';


export function Smallerbutton ({name, fn}) {
    return (
        <>
            <button className={`flex justify-center items-center rounded-lg w-28 h-[4vh] ml-10 bg-gradient-to-b from-[#b8b9bf] to-[#808185] drop-shadow-2xl text-[#4E525A] font-bold`} onClick={fn}>{name}</button>    
        </>  
    );
};

export function Smallbutton ({name, fn, ifnext}) {
    return (
        <>
            <button className={`flex justify-center items-center rounded-lg w-28 h-[6vh] ml-10 bg-gradient-to-b from-[#b8b9bf] to-[#808185] drop-shadow-2xl text-[#4E525A] font-bold ${ifnext}`} onClick={fn}>{name}</button>    
        </>  
    );
};

export function Setlistbutton ({x, idx}) {
    return(
        <div id={idx} className="flex cursor-default my-4 h-[6vh] mx-10 border-none  text-[3vh] bg-gradient-to-b from-[#4E525A] to-[#36393F] drop-shadow-2xl ease-out hover:h-[100px] hover:mx-5 border-2 rounded-lg justify-center items-center font-bold text-white overflow-x-hidden whitespace-nowrap" >
            <div className='basis-1/5 ml-[1vh] whitespace-nowrap'>{idx+1}세트</div>
            <div className='flex flex-row basis-3/5 max-h-[28] justify-center items-center'>
                <span className='flex-nowrap whitespace-nowrap text-clip basis-1/2 mx-[1vh]'>{x[0]}kg</span>
                <span className='flex-nowrap whitespace-nowrap text-clip basis-1/3'>{x[1]}회</span>
            </div>
            <div className='basis-1/5 justify-center items-center'>
                <button className='w-8 h-8' onClick={() => {console.log("clicked")}}>
                  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="4" stroke="green" className="w-12 h-12">
                    <path strokeLinecap="round" strokeLinejoin="round" d="M4.5 12.75l6 6 9-13.5" />
                  </svg>
                </button>
            </div>  
        </div>
    );
};

export function Specificsetlistbutton ({x, idx}) {
    const setSpecificset = useSetRecoilState(specificsetState);

    return(
        <div id={idx} className="flex justify-items-start cursor-default my-4 h-[6vh] mx-10 border-none bg-gradient-to-b from-[#4E525A] to-[#36393F] drop-shadow-2xl ease-out hover:h-[100px] hover:mx-5 border-2 rounded-lg justify-center items-center font-bold text-[28px] text-white overflow-x-hidden whitespace-nowrap" 
              onClick={()=> {setSpecificset(idx)}}>{x}</div>
    );
}

export function Timebutton ({color, time}) {
    return(
        <div className={`${color} font-extrabold text-[5.5vh]`}>
        {time}
    </div>
    );
};

export function Movingbutton () {
    return(
        <div className='flex-row w-[200px]'>
        <button className='mx-[5px]'>
          <svg 
            transform="scale(-1 -1)" 
            xmlns="http://www.w3.org/2000/svg" 
            fill="none" 
            viewBox="0 0 24 24" 
            strokeWidth="3" 
            stroke="#b8b9bf" 
            className="w-16 h-[5vh]"
          > 
            <path 
              strokeLinecap="round" 
              strokeLinejoin="round" 
              d="M5.25 5.653c0-.856.917-1.398 1.667-.986l11.54 6.348a1.125 1.125 0 010 1.971l-11.54 6.347a1.125 1.125 0 01-1.667-.985V5.653z" 
            />
          </svg>
        </button>
        <button className='mr-[1vh]'>
          <svg 
            xmlns="http://www.w3.org/2000/svg" 
            fill="none" 
            viewBox="0 0 24 24" 
            strokeWidth="3" 
            stroke="#b8b9bf" 
            className="w-16 h-[5vh] p-0 m-0 border-0"
          > 
            <path 
              strokeLinecap="round" 
              strokeLinejoin="round" 
              d="M5.25 5.653c0-.856.917-1.398 1.667-.986l11.54 6.348a1.125 1.125 0 010 1.971l-11.54 6.347a1.125 1.125 0 01-1.667-.985V5.653z" 
            />
          </svg>
        </button>
        </div>
    );
}