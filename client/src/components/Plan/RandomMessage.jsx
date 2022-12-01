import React from "react";
import cls from "../../assets/utils";

const proverb = [
  "운동은 먹는 것보다 더 중요하다",
  "하루에 30분은 운동하라",
  "시작이 반이다",
  "항상 운동을 하라",
  "먹는 것보다 운동이 더 중요하다",
  "밥은 먹고 운동을 하라",
  "생각보다 운동이 더 중요하다",
  "깊게 숨을 들이마시고 힘을 내라",
  "결과를 위해 노력하라",
  "처음부터 끝까지 노력하라",
  "운동에 끝은 없다",
  "우선 운동을 하라",
  "일단 뛰고 봐라",
  "아침에 운동을 하라",
  "눈뜨면 운동을 하라",
  "가짜 배고픔에 빠지지 말라",
  "나약함을 극복하라",
  "게으름을 극복하라",
  "운동은 살기위한 발버둥이다",
  "운동은 삶의 기초다",
  "운동은 배신하지 않는다",
  "삶은 운동으로 이루어진다",
  "기초운동은 필수다",
  "필수적인 운동은 기초운동이다",
];

export default function RandomMessage({ large }) {
  const random = Math.floor(Math.random() * proverb.length);
  return (
    <div
      className={cls(
        " text-white font-medium shadow-sm text-center",
        large
          ? "text-[1em] text-d-point rounded-md bg-d-light p-1 px-2"
          : "text-[0.8em] rounded-lg bg-d-button px-2 "
      )}
    >
      {proverb[random]}
    </div>
  );
}
