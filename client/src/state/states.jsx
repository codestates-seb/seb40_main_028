import { atom } from "recoil";
import { recoilPersist } from "recoil-persist";
import dayjs from "dayjs";

const { persistAtom } = recoilPersist();
const now = dayjs().format("YYYY-MM-DD");
const splitNow = now.split("-");

export const selectedDays = atom({
  key: "selectedDays",
  default: {
    day: Number(splitNow[2]),
    month: Number(splitNow[1]),
    year: Number(splitNow[0]),
  },
});

export const categorie = atom({
  key: "categorie",
  default: [],
});

export const ModalNum = atom({
  key: "ModalNum",
  default: 0,
});

export const isModal = atom({
  key: "isModal",
  default: false,
});

// RJH states
export const worktimeState = atom({
  key: "worktimeState",
  // 운동시간
  default: 0,
  effects_UNSTABLE: [persistAtom],
});

export const workoutlistState = atom({
  key: "workoutlistState",
  // 운동리스트전체
  default: {},
  effects_UNSTABLE: [persistAtom],
});
