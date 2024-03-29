import { useEffect, useState } from "react";
import AnimalApi from "../api/animal-api";
import AnimalCard from "./animal-card";
import { Link } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { jwtState } from "../store";
import Loader from "./loader";

function Animal() {
    const [animals, setAnimals] = useState([]);
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

    const getAllAnimals = async () => {
        try {
            const result = await AnimalApi.getAllAnimals();
            console.log("Result animal:", result.data);
            setAnimals(result.data);
            setLoading(false);
        } catch {
            console.warn("Something went wrong with the all animals call");
        }
    }

    useEffect(() => {
        document.title = "Animals"
        getAllAnimals();
    }, [])

    const output = animals.map((animal, i) => {
        return (
            <AnimalCard animal={animal} afterApiRequest={getAllAnimals} key={i}></AnimalCard>
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
            <Loader show={loading} />
            { animals.length === 0 && !loading &&
                <p>No animals</p>
            }
            <div style={style.list}>
                { output }
            </div>
        </div>
    )
}

export default Animal;