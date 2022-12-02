import React from "react";
import { useRoutes, Navigate } from "react-router-dom"; //
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
import { LoginState } from "../state/UserState";

export default function Router() {
  // const navigate = useNavigate();

  // 로그인 상태
  const login = useRecoilValue(LoginState);

  const routes = useRoutes([
    {
      path: "/",
      element: login ? <Main /> : <Navigate replace to="/start" />,
    },
    {
      path: "/mappage",
      element: login ? <Mappage /> : <Navigate replace to="/start" />,
    },
    {
      path: "/workout",
      element: login ? <Workout /> : <Navigate replace to="/start" />,
    },
    {
      path: "/plan",
      element: login ? <Plan /> : <Navigate replace to="/start" />,
    },
    {
      path: "/mypage",
      element: login ? <Mypage /> : <Navigate replace to="/start" />,
    },
    {
      path: "*",
      element: login ? <NotFound /> : <Navigate replace to="/start" />,
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
      element: login ? <Main /> : <Register />,
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
