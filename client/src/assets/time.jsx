export default function time(seconds) {
  const hour =
    parseInt(seconds / 3600) < 10
      ? "0" + parseInt(seconds / 3600)
      : parseInt(seconds / 3600);
  const min =
    parseInt((seconds % 3600) / 60) < 10
      ? "0" + parseInt((seconds % 3600) / 60)
      : parseInt((seconds % 3600) / 60);
  const sec = seconds % 60 < 10 ? "0" + (seconds % 60) : seconds % 60;
  return hour + "시간 " + min + "분 " + sec + "초";
}
