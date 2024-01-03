import { Outlet, Routes, Route, BrowserRouter, NavLink } from 'react-router-dom';
import './App.css';
import Home from './components/home';
import Food from './components/food';
import FoodDetail from './components/food-detail';
import { useEffect } from 'react';
import ConfigData from './config.json';
import { RecoilRoot, useRecoilState, useSetRecoilState } from 'recoil';
import { jwtDecode } from 'jwt-decode';
import { jwtState, userState } from './store';
import Enclosure from './components/enclosure';
import Animal from './components/animal';

//fontawesome
import { library } from '@fortawesome/fontawesome-svg-core'
// import { fab } from '@fortawesome/free-brands-svg-icons'
import { faPenToSquare, faXmark, faTrashCan, faCubesStacked } from '@fortawesome/free-solid-svg-icons'

library.add(faPenToSquare, faXmark, faTrashCan, faCubesStacked)

function NavBar() {

  const setJwt = useSetRecoilState(jwtState);
  const [user, setUser] = useRecoilState(userState);

  function handleCallbackResponse(response) {
    console.log("Encoded JWT ID token:", response.credential);
    setJwt(response.credential);

    var userObject = jwtDecode(response.credential);
    console.log(userObject);
    setUser(userObject);
    document.getElementById('signInDiv').hidden = true;
  }

  function handleSignOut(event) {
    setJwt('');
    setUser({});
    document.getElementById('signInDiv').hidden = false;
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
        <div className='links'>
          <li><NavLink to={"/"} className={"nav-link"}>Home</NavLink></li>
          <li><NavLink to={"/food"} className={"nav-link"}>Food</NavLink></li>
          <li><NavLink to={"/animal"} className={"nav-link"}>Animals</NavLink></li>
          <li><NavLink to={"/enclosure"} className={"nav-link"}>Enclosures</NavLink></li>
        </div>
        <div>
          { Object.keys(user).length !== 0 &&
            <div className='user-logged-in'>
              <li>
                <div className='user-info'>
                  <img src={user.picture}></img>
                  <p>{ user.name }</p>
                </div>
              </li>
              <li><button onClick={ (e) => handleSignOut(e) }>Sign out</button></li>
            </div>
          }
          <li><div id='signInDiv'></div></li>
        </div>
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
      <Route path={'/animal'} element={<Outlet />}>
        <Route index element={<Animal />}/>
        {/* <Route path={':id'} element={<AnimalDetail />}/> */}
      </Route>
      <Route path={'/enclosure'} element={<Outlet />}>
        <Route index element={<Enclosure />}/>
        {/* <Route path={':id'} element={<EnclosureDetail />}/> */}
      </Route>
    </Routes>
  </div>
  )
}

function App() {
  return (
    <RecoilRoot>
      <BrowserRouter>
        <NavBar></NavBar>
        <Main></Main>
      </BrowserRouter>
    </RecoilRoot>
  );
}

export default App;
