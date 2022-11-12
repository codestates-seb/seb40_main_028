import { useRoutes } from 'react-router-dom';
import Main from '../pages/Main';
import Map from '../pages/Map';
import Plan from '../pages/Plan';
import Workout from '../pages/Workout';
import Mypage from '../pages/Mypage';
import NotFound from '../pages/NotFound';

export default function Router() {
  const routes = useRoutes([
    {
      path: '/',
      element: <Main />,
    },
    {
      path: '/map',
      element: <Map />,
    },
    {
      path: '/workout',
      element: <Workout />,
    },
    {
      path: '/plan',
      element: <Plan />,
    },
    {
      path: '/mypage',
      element: <Mypage />,
    },
    {
      path: '*',
      element: <NotFound />,
    },
  ]);
  return routes;
}
