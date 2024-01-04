import { useEffect, useState } from "react";
import FoodApi from "../api/food-api";
import FoodCard from './food-card';
import { Link } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { jwtState } from "../store";
import Loader from "./loader";

function Food() {
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

    const [food, setFood] = useState([]);

    const getAllFood = async () => {
        try {
            const result = await FoodApi.getAllFood();
            console.log("Result food:", result.data);
            setFood(result.data);
            setLoading(false);
        } catch {
            console.warn("Something went wrong with the all food call");
        }
    }

    useEffect(() => {
        getAllFood();
    }, []);

    const output = food.map((food, i) => {
        return (
            <FoodCard food={food} afterApiRequest={getAllFood} key={i} style={style.foodCard}></FoodCard>
        )
    })

    return (
        <div style={style.container}>
            <h2>Food</h2>
            { jwtToken !== '' &&
                <Link to={'/food/0'}>
                    <button style={{ marginBottom: '1rem' }}>
                        New food
                    </button>
                </Link>
            }
            <Loader show={loading} />
            <div style={style.list}>{ output }</div>
        </div>
    )
}

export default Food;