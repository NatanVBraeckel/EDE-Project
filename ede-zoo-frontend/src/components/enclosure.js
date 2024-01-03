import { useEffect, useState } from "react";
import EnclosureApi from "../api/enclosure-api";

function Enclosure() {
    const [enclosures, setEnclosures] = useState([]);

    useEffect(() => {
        const getAllEnclosures = async () => {
            try {
                const result = await EnclosureApi.getAllEnclosures();
                console.log("Result enclosure:", result.data);
                setEnclosures(result.data);
            } catch {
                console.warn("Something went wrong with the all enclosures call");
            }
        }
        getAllEnclosures();
    }, []);

    const output = enclosures.map((enclosure, i) => {
        return (
            <div key={i}>
                { enclosure.name }
            </div>
        )
    })

    return (
        <div>
            <h2>Enclosures</h2>
            <div>{ output }</div>
        </div>
    )
}

export default Enclosure;