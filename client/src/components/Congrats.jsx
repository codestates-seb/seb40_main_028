import Button from "./Button";

export default function Congrats() {
    
  return(
    <div className="flex fixed inset-0 bg-d-dark bg-opacity-90 h-full max-w-lg mx-auto z-40 justify-center items-center">
      <form className="flex-col z-50 justify-center items-center h-[40vh] w-2/3 max-w-lg bg-d-lighter rounded-2xl overflow-hidden">
        <div className="bg-d-light text-white h-[3vh]" />
        <div className="relative top-[5vh] text-white text-[3vh]">축하합니다</div>
        <span className="relative flex justify-center top-[10vh] text-white mx-[2vh] text-[1.8vh]">Optional: 오늘의 몸무게를</span>
        <span className="relative flex justify-center top-[10vh] text-white mx-[2vh] text-[1.8vh]">입력해주세요</span>
        <input className="relative top-[12vh] rounded-xl h-[4vh]" />
        <div className="relative top-[20vh] flex justify-center items-center">
          <Button workoutodone text="홈으로" />
        </div>
      </form>
    </div>
  );
    
};


