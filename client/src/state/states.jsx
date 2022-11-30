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

export const TokenValue = atom({
  key: "TokenValue",
  default:
    "eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6W10sInVzZXJJZCI6MTEsInN1YiI6Imd1ZW5sb2dAdGVzdC5jb20iLCJpYXQiOjE2Njk4MjMyNTIsImV4cCI6MTY3MDQyODA1Mn0.OUCPCrfKwzAqYhtXRVDWfLX-IBI1t3b6cKr1XU-x5NpQDKUsHyLiFxp-7OLfPlpnA7LjmSpqqj6-B0pFnNTcFg",
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

// {
//   success: true,
//   data: {
//     today_id: 1,
//     totalTime: 0,
//     exercises: [
//       {
//         exerciseId: 1,
//         isCompleted: false,
//         exerciseName: "벤치프레스",
//         imageUrl: "s3.somewhere.somewhere1",
//         eachRecords: [
//           {
//             weight: 30,
//             count: 6,
//             timer: 13,
//             eachCompleted: false,
//           },
//           {
//             weight: 30,
//             count: 6,
//             timer: 23,
//             eachCompleted: false,
//           },
//           {
//             weight: 30,
//             count: 6,
//             timer: 33,
//             eachCompleted: false,
//           },
//           {
//             weight: 30,
//             count: 6,
//             timer: 33,
//             eachCompleted: false,
//           },
//           {
//             weight: 30,
//             count: 6,
//             timer: 33,
//             eachCompleted: false,
//           },
//           {
//             weight: 30,
//             count: 6,
//             timer: 33,
//             eachCompleted: false,
//           },
//         ],
//       },
//       {
//         exerciseId: 2,
//         isCompleted: false,
//         exerciseName: "스쿼트",
//         imageUrl: "s3.somewhere.somewhere2",
//         eachRecords: [
//           {
//             weight: 30,
//             count: 6,
//             timer: 30,
//             eachCompleted: false,
//           },
//           {
//             weight: 30,
//             count: 6,
//             timer: 30,
//             eachCompleted: false,
//           },
//           {
//             weight: 30,
//             count: 6,
//             timer: 30,
//             eachCompleted: false,
//           },
//         ],
//       },
//       {
//         exerciseId: 3,
//         isCompleted: false,
//         exerciseName: "스쿼트",
//         imageUrl: "s3.somewhere.somewhere3",
//         eachRecords: [
//           {
//             weight: 30,
//             count: 6,
//             timer: 30,
//             eachCompleted: false,
//           },
//           {
//             weight: 30,
//             count: 6,
//             timer: 30,
//             eachCompleted: false,
//           },
//           {
//             weight: 30,
//             count: 6,
//             timer: 30,
//             eachCompleted: false,
//           },
//         ],
//       },
//     ],
//   },
// },
