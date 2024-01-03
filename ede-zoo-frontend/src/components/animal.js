import { useEffect, useState } from "react";
import AnimalApi from "../api/animal-api";
import AnimalCard from "./animal-card";
import { Link } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { jwtState } from "../store";

function Animal() {
    const [animals, setAnimals] = useState([]);
    const jwtToken = useRecoilValue(jwtState);

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

    const getAllAnimals = async () => {
        try {
            const result = await AnimalApi.getAllAnimals();
            console.log("Result animal:", result.data);
            setAnimals(result.data);
        } catch {
            console.warn("Something went wrong with the all animals call");
        }
    }

    useEffect(() => {
        getAllAnimals();
    }, [])

    async function deleteAnimal(id, jwtToken) {
        console.log("deleting animal with id:", id);
        try {
            await AnimalApi.deleteAnimal(id, jwtToken);
        } catch {
            console.warn("Something went wrong with animal delete");
        }
        getAllAnimals();
    }

    const output = animals.map((animal, i) => {
        return (
            <AnimalCard animal={animal} deleteAnimal={deleteAnimal} key={i}></AnimalCard>
        )
    })

    return (
        <div style={style.container}>
            <h2>Animals</h2>
            { jwtToken !== '' &&
                <Link to={'/animal/0'}>
                    <button style={{ marginBottom: '1rem' }}>
                        New animal
                    </button>
                </Link>
            }
            <div style={style.list}>
                { output }
            </div>
        </div>
    )
}

export default Animal;