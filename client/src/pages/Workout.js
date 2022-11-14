import Layout from '../components/Layout';
import { useState } from 'react';

const Workout = () => {
  const workoutlist = ["스트레칭", "벤치프레스", "스쿼드", "데드리프트", "덤벨 컬", "바벨 컬", "크런치"];
  const itemscss = "flex my-4 h-[80px] mx-10 hover:h-[100px] hover:mx-5 border-2 border-dashed rounded-lg border-black justify-center items-center font-bold text-[28px]";
  
  return (
    <Layout title="운동시작" hasTabBar>
      <div className="flex-col items-center justify-items-end margin-[1px] text-gray-700 h-screen text-medium text-xl text-center mt-5">
        <div className='flex justify-start mx-4 mb-1 h-[3vh] text-[40px] font-bold'>운동이름</div>
        <hr className='mx-2 h-1 bg-black border-'></hr>
        <div className='flex h-[30vh] border-2 mx-14 justify-center items-center text-[120px]'>운동사진</div>
        <ul className='flex-col mx-auto'>
          {workoutlist.map(x => <li id={x} className={itemscss}>{x}</li>) }
        </ul>
      </div>
    </Layout>
  );
};

export default Workout;
