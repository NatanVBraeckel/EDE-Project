import { useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

function Back() {
    const navigate = useNavigate();

    return (
        <button style={{ border: 'none', background: 'none', fontSize: "1.2rem" }} onClick={() => navigate(-1)}>
            <FontAwesomeIcon icon="chevron-left"></FontAwesomeIcon>
        </button>
    )
}

export default Back;