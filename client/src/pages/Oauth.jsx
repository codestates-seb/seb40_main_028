import { useNavigate } from "react-router-dom";

import {
  useRecoilState,
  // useSetRecoilState
} from "recoil";
import { LoginState, TokenState, Googlelogin } from "../state/UserState";

const Oauth = () => {
  const navigate = useNavigate();

  const [isLogin, setIsLogin] = useRecoilState(LoginState);
  const [isToken, setToken] = useRecoilState(TokenState);
  const [isgoogle, setgoogle] = useRecoilState(Googlelogin);

  // const setIsLogin = useSetRecoilState(LoginState);
  // const setToken = useSetRecoilState(TokenState);

  // 파리미터값 가져오기
  const query = window.location.search;
  const param = new URLSearchParams(query);
  const getaccessToken = param.get("access_token");
  const initialLogin = param.get("initial_login");

  // console.log("토큰값 : ", getaccessToken);
  // console.log("로그인 상태: ", initialLogin);

  if (getaccessToken) {
    // 토큰 리코일에 저장
    setToken(`Bearer ${getaccessToken}`);
    // 로그인상태 true
    setIsLogin(true);
    // 구글로그인인지 체크
    setgoogle(true);
  }

  // console.log("토큰값 : ", getaccessToken);
  // console.log("로그인 상태: ", initialLogin);

  // 이니셜 로그인 값이 flase면 초기정보 입력 페이지로
  // console.log(initialLogin, "로그인상태값~~~~~");
  // console.log(isgoogle, "구글로그인 맞음~~~~~");

  if (initialLogin === "false") {
    navigate("/startinginformation");
  } else navigate("/");

  // 이니셜로그인이 true면 메인페이지로
  // if (initialLogin === true){
  // }
  // navigate("/");
  // useEffect(() => {
  //   console.log("useEffect!!", isLogin);
  // });
};
export default Oauth;
