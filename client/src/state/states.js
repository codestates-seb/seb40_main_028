import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist'
import dayjs from 'dayjs';

const { persistAtom } = recoilPersist();
const now = dayjs().format('YYYY-MM-DD');
let splitNow = now.split('-');

export const selectedDays = atom({
  key: 'selectedDays',
  default: {
    day: Number(splitNow[2]),
    month: Number(splitNow[1]),
    year: Number(splitNow[0]),
  },
});

export const categorie = atom({
  key: 'categorie',
  default: [],
});

export const ModalNum = atom({
  key: 'ModalNum',
  default: 0,
});

export const isModal = atom({
  key: 'isModal',
  default: false,
});

export const timermodalState = atom({
  key: "timermodalState",
  //0:모달창이 켜지는것에 대한 true false
  //1:타이머가 돌아가는 시간
  default: [false, 0]
});

export const totaltimeState = atom({
  key: "totaltimeState",
  //총운동시간
  default: 0,
  effects_UNSTABLE:[persistAtom],
});

export const doneState = atom({
  key: "doneState",
  //총운동시간
  default: [],
  effects_UNSTABLE:[persistAtom],
});
