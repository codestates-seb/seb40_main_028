/* eslint-disable import/prefer-default-export */
import { atom } from "recoil";
import { recoilPersist } from "recoil-persist";

const { persistAtom } = recoilPersist();

export const LoginState = atom({
  key: "LoginState",
  default: false,
  effects_UNSTABLE: [persistAtom],
});

export const TokenState = atom({
  key: "TokenState",
  default: null,
  effects_UNSTABLE: [persistAtom],
});