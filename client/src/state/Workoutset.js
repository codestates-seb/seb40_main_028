import { atom } from "recoil";

const specificsetState = atom({
  key: "specificsetState",
  default: "list",
});
export default specificsetState;

// 사용시 주의사항
// key 이름과 const로 명명하는 이름은 같은것이 좋음.
