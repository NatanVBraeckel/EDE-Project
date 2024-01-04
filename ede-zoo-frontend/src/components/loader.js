import { Oval } from "react-loader-spinner";

function Loader({ show }) {
    return (
        <Oval
        visible={show}
        height="60"
        width="60"
        color="#634008"
        secondaryColor="#E8C386"
        ariaLabel="oval-loading"
        wrapperStyle={{}}
        wrapperClass=""
    />
    )
}

export default Loader;