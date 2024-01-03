import { useEffect } from "react";
import AnimalApi from "../api/animal-api";

function Animal() {
    useEffect(() => {
        const getAllAnimals = async () => {
            try {
                const result = await AnimalApi.getAllAnimals();
                console.log("Result:", result.data);
            } catch {
                console.warn("Something went wrong with the all animals call");
            }
        }
        getAllAnimals();
    }, [])

    return (
        <div>
            <h2>Animals</h2>
        </div>
    )
}

export default Animal;