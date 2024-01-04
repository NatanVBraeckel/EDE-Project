import { useEffect, useState } from "react";
import EnclosureApi from "../api/enclosure-api";
import EnclosureCard from "./enclosure-card";
import { Link } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { jwtState } from "../store";
import Loader from "./loader";

function Enclosure() {
    const [enclosures, setEnclosures] = useState([]);
    const jwtToken = useRecoilValue(jwtState);
    const [loading, setLoading] = useState(true);

    const style = {
        container: {
            margin: '2rem',
        },
        list: {
            display: 'flex',
            flexWrap: 'wrap',
            gap: '2rem'
        },
    }

    const getAllEnclosures = async () => {
        try {
            const result = await EnclosureApi.getAllEnclosures();
            console.log("Result enclosure:", result.data);
            setEnclosures(result.data);
            setLoading(false);
        } catch {
            console.warn("Something went wrong with the all enclosures call");
        }
    }

    useEffect(() => {
        getAllEnclosures();
    }, []);

    const output = enclosures.map((enclosure, i) => {
        return (
            <EnclosureCard key={i} enclosure={enclosure} afterApiRequest={getAllEnclosures}></EnclosureCard>
        )
    })

    return (
        <div style={style.container}>
            <h2>Enclosures</h2>
            { jwtToken !== '' &&
                <Link to={'/enclosure/0'}>
                    <button style={{ marginBottom: '1rem' }}>
                        New enclosure
                    </button>
                </Link>
            }
            <Loader show={loading} />
            <div style={style.list}>
                { output }
            </div>
        </div>
    )
}

export default Enclosure;