import Layout from '../components/Layout';
import { FaDumbbell } from 'react-icons/fa';
import { useState } from 'react';

const Workout = () => {
  const workoutlist = ["스트레칭", "벤치프레스", "스쿼드", "데드리프트", "덤벨 컬", "바벨 컬", "크런치"];
  const itemscss = "flex my-4 h-[80px] mx-10 border-none bg-[#4E525A] ease-out hover:h-[100px] hover:mx-5 border-2 rounded-lg justify-center items-center font-bold text-[28px] text-white";
  let datenow = new Date();
  let senddate = `${datenow.getFullYear()}-${datenow.getMonth()+1}-${datenow.getDate()}`

  return (
    <Layout title="운동시작" hasTabBar>
      <div className="flex-col items-center justify-items-end margin-[1px] text-gray-700 h-screen text-medium text-xl text-center mt-5">
        <div className='flex justify-start mx-4 mb-1 h-[3vh] text-[40px] font-bold text-white'>날짜예시 {senddate}</div>
        <hr className='mx-2 h-1 my-2 border-'></hr>
        <div className='flex h-[30vh] border-2 mx-14 justify-center items-center text-[10px] text-white'> 
        <FaDumbbell size={240} /></div>
        <ul className='flex-col mx-auto'>
          {workoutlist.map(x => <li id={x} className={itemscss}>{x}</li>) }
        </ul>
      </div>
    </Layout>
  );
};

export default Workout;


/* 
운동사진 축하합니다 페이지에서 몸무게입력창있으면 좋을듯

axios( {
  method: "GET",
  url: "exercise/progress",
  headers: { "Content-Type": "application/json", authorization: token},
  body: JSON.stringify(senddate),
})

senddate 형식 : 연도-월-일
=> senddate url에 같이 보내드리는게 좋다는 의견이 있었으나, 토의하여 string으로 body에 보내는 것으로 의견종합

response 형식
=>종목과 각 세트에 대한 정보, 추가적으로 세트에 대한 id도 들어옴
id에대한 무게 횟수
ex)
id1: 벤치프레스 첫번째세트 10kg 15회
id2: 벤치프레스 두번째세트 10kg 12회

axios( {
  method: "PATCH",
  url: "exercise/progress",
  headers: { "Content-Type": "application/json", authorization: token},
  body: JSON.stringify(sendprogress),
})

sendprogress:
완료된 세트 id만 보내드리면 됨.(배열형식)
운동전체시간
ex) {00-12-57},[1,4,9,13] 자세한 형식은 다시 요청해서 샘플 받는것으로 결정

response 성공 => status ok도 괜찮음
/ 실패 => 고민해보아야함.

이후 축하합니다 페이지에서 보낼 수 있는 url과 method를 저에게 알려주세요(지환)
*/