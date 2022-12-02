import { useEffect, useRef } from "react";

export default function useInterval(callback, delay) {
  const savedCallback = useRef(); // 최근에 들어온 callback을 저장할 ref를 하나 만든다.
  let delaytime = delay;
  if(delaytime) delaytime = null
  else delaytime = 1000;

  useEffect(() => {
    savedCallback.current = callback; // callback이 바뀔 때마다 ref를 업데이트 해준다.
  }, [callback]);

  useEffect(() => {
    function tick() {
      savedCallback.current(); // tick이 실행되면 callback 함수를 실행시킨다.
    }
    if (delaytime !== null) { // 만약 delaytime가 null이 아니라면 
      const id = setInterval(tick, delaytime); // delaytime에 맞추어 interval을 새로 실행시킨다.
      return () => clearInterval(id); // unmount될 때 clearInterval을 해준다.
    }
    
    return undefined;
  }, [delaytime]); // delay가 바뀔 때마다 새로 실행된다.
}