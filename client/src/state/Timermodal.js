import { atom } from "recoil";

const timermodalState = atom({
  key: "timermodalState",
  //0:모달창이 켜지는것에 대한 true false
  //1:타이머가 돌아가는 시간
  default: [false, 0]
});
export default timermodalState;

// 사용시 주의사항
// key 이름과 const로 명명하는 이름은 같은것이 좋음.
