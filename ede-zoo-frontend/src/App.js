import { Outlet, Routes, Route, BrowserRouter, Link, NavLink } from 'react-router-dom';
import './App.css';
import Home from './components/home';
import Food from './components/food';
import FoodDetail from './components/food-detail';
import { useEffect } from 'react';
import ConfigData from './config.json';

function NavBar() {

  function handleCallbackResponse(response) {
    console.log("Encoded JWT ID token:", response.credential);
  }

  useEffect(() => {
    /* global google */
    google.accounts.id.initialize({
      client_id: ConfigData.clientId,
      callback: handleCallbackResponse
    });

    google.accounts.id.renderButton(
      document.getElementById("signInDiv"),
      { theme: "outline", size: "large" }
    );
  }, []);

  return (
    <nav>
      <ul>
        <li><NavLink to={"/"} className={"nav-link"}>Home</NavLink></li>
        <li><NavLink to={"/food"} className={"nav-link"}>Food</NavLink></li>
        <li><NavLink to={"/animal"} className={"nav-link"}>Animals</NavLink></li>
        <li><NavLink to={"/enclosure"} className={"nav-link"}>Enclosures</NavLink></li>
        <li><div id='signInDiv'></div></li>
      </ul>
    </nav>
  )
}

function Main() {
  return (
    <div className="content">
    <Routes>
      <Route path={'/'} element={<Home/>} />
      <Route path={'/food'} element={<Outlet />}>
        <Route index element={<Food />}/>
        <Route path={':id'} element={<FoodDetail />}/>
      </Route>
    </Routes>
  </div>
  )
}

function App() {
  return (
    <BrowserRouter>
      <NavBar></NavBar>
      <Main></Main>
    </BrowserRouter>
  );
}

export default App;
