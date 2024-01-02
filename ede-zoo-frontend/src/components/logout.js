import { GoogleLogout } from "react-google-login";
import ConfigData from '../config.json';

function Logout() {

    const onSuccess = () => {
        console.log("Log out successfull!");
    }

    return (
        <div id="signOutButton">
            <GoogleLogout
                clientId={ConfigData.clientId}
                buttonText={"Logout"}
                onLogoutSuccess={onSuccess}
            />
        </div>
    )
}

export default Logout;