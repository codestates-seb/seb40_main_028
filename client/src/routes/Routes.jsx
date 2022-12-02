import React from "react";
import { useRoutes, useNavigate } from "react-router-dom"; //
import { useRecoilValue } from "recoil"; //
import Main from "../pages/Main";
import Mappage from "../pages/Mappage";
import Plan from "../pages/Plan";
import Workout from "../pages/Workout";
import Mypage from "../pages/Mypage";
import NotFound from "../pages/NotFound";
import Login from "../pages/Login";
import Start from "../pages/Start";
import Register from "../pages/Register";
import Startinginformation from "../pages/Startinginformation";

import Oauth from "../pages/Oauth";
import { LoginState, TokenState, Googlelogin } from "../state/UserState";

export default function Router() {
  const navigate = useNavigate();

  // 로그인 상태
  const login = useRecoilValue(LoginState);

  const routes = useRoutes([
    {
      path: "/",
      element: login ? <Main /> : <Start />,
    },
    {
      path: "/mappage",
      element: login ? <Mappage /> : <Start />,
    },
    {
      path: "/workout",
      element: login ? <Workout /> : <Start />,
    },
    {
      path: "/plan",
      element: login ? <Plan /> : <Start />,
    },
    {
      path: "/mypage",
      element: login ? <Mypage /> : <Start />,
    },
    {
      path: "*",
      element: login ? <NotFound /> : <Start />,
    },
    {
      path: "/login",
      element: login ? <Main /> : <Login />,
    },
    {
      path: "/start",
      element: login ? <Main /> : <Start />,
    },
    {
      path: "/register",
      element: login ? <Register /> : <Start />,
    },
    {
      path: "/startinginformation",
      element: login ? <Startinginformation /> : <Start />,
    },
    {
      path: "/login/oauth",
      // element: <Oauth />,
      element: login ? <Main /> : <Oauth />,
    },
  ]);
  return routes;
}
