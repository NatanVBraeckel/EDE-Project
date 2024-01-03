import { useEffect, useState } from "react";
import AnimalApi from "../api/animal-api";

function Animal() {
    const [animals, setAnimals] = useState([]);

    useEffect(() => {
        const getAllAnimals = async () => {
            try {
                const result = await AnimalApi.getAllAnimals();
                console.log("Result animal:", result.data);
                setAnimals(result.data);
            } catch {
                console.warn("Something went wrong with the all animals call");
            }
        }
        getAllAnimals();
    }, [])

    const output = animals.map((animal, i) => {
        return (
            <div key={i}>
                { animal.name }
            </div>
        )
    })

    return (
        <div>
            <h2>Animals</h2>
            <div>
                { output }
            </div>
        </div>
    )
}

export default Animal;