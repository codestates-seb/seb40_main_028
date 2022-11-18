import { atom } from 'recoil';
import dayjs from 'dayjs';

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
