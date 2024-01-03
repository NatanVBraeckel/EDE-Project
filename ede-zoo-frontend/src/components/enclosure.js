import { useEffect } from "react";
import EnclosureApi from "../api/enclosure-api";

function Enclosure() {
    useEffect(() => {
        const getAllEnclosures = async () => {
            try {
                const result = await EnclosureApi.getAllEnclosures();
                console.log("Result:", result.data);
            } catch {
                console.warn("Something went wrong with the all enclosures call");
            }
        }
        getAllEnclosures();
    }, [])

    return (
        <div>
            <h2>Enclosures</h2>
        </div>
    )
}

export default Enclosure;