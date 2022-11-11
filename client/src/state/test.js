import { atom } from "recoil";

const tokenState = atom({
  key: "tokenState",
  default: 1111,
});
export default tokenState;

// 사용시 주의사항
// key 이름과 const로 명명하는 이름은 같은것이 좋음.
