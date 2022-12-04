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

  if (getaccessToken) {
    // 토큰 리코일에 저장
    setToken(`Bearer ${getaccessToken}`);
    // 로그인상태 true
    setIsLogin(true);
    // 구글로그인인지 체크
    setgoogle(true);
  }

  if (initialLogin === "false") {
    navigate("/startinginformation");
  } else navigate("/");
};
export default Oauth;
