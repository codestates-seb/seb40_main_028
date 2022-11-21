import { useRoutes } from "react-router-dom";
import Main from "../pages/Main";
import Map from "../pages/Map";
import Plan from "../pages/Plan";
import Workout from "../pages/Workout";
import Mypage from "../pages/Mypage";
import NotFound from "../pages/NotFound";

import Login from "../pages/Login";
import Start from "../pages/Start";
import Register from "../pages/Register";
import Startinginformation from "../pages/Startinginformation";
import Logout from "../pages/Logout";



export default function Router() {
  const routes = useRoutes([
    {
      path: "/",
      element: <Main />,
    },
    {
      path: "/map",
      element: <Map />,
    },
    {
      path: "/workout",
      element: <Workout />,
    },
    {
      path: "/plan",
      element: <Plan />,
    },
    {
      path: "/mypage",
      element: <Mypage />,
    },
    {
      path: "*",
      element: <NotFound />,
    },
    {
      path: "/login",
      element: <Login />,
    },
    {
      path: "/start",
      element: <Start />,
    },
    {
      path: "/logout",
      element: <Logout />,
    },
    {
      path: "/register",
      element: <Register />,
    },
    {
      path: "/startinginformation",
      element: <Startinginformation />,
    },      
  ]);
  return routes;
}
