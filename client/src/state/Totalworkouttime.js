import { atom } from "recoil";
import { recoilPersist } from 'recoil-persist'

const { persistAtom } = recoilPersist();

const totaltimeState = atom({
  key: "totaltimeState",
  //총운동시간
  default: 0,
  effects_UNSTABLE:[persistAtom],
});
export default totaltimeState;

// 사용시 주의사항
// key 이름과 const로 명명하는 이름은 같은것이 좋음.
