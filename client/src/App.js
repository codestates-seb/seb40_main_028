import { BrowserRouter, Routes, Route } from "react-router-dom";
import Main from "./pages/Main";
import Map from "./pages/Map";
import Workout from "./pages/Workout";
import Plan from "./pages/Plan";
import Mypage from "./pages/Mypage";

function App() {
  return (
    <div className="w-full h-[1500px] max-w-xl mx-auto bg-black">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Main />}></Route>
          <Route path="/map" element={<Map />}></Route>
          <Route path="/workout" element={<Workout />}></Route>
          <Route path="/plan" element={<Plan />}></Route>
          <Route path="/mypage" element={<Mypage />}></Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
