import { Oval } from "react-loader-spinner";

function Loader({ show }) {
    return (
        <Oval
        visible={show}
        height="60"
        width="60"
        color="#5A9CFF"
        secondaryColor="#A2C7FF"
        ariaLabel="oval-loading"
        wrapperStyle={{}}
        wrapperClass=""
    />
    )
}

export default Loader;