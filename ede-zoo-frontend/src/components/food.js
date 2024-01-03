import { useEffect, useState } from "react";
import FoodApi from "../api/food-api";

function Food() {
    const [food, setFood] = useState([]);

    useEffect(() => {
        const getAllFood = async () => {
            try {
                const result = await FoodApi.getAllFood();
                console.log("Result food:", result.data);
                setFood(result.data);
            } catch {
                console.warn("Something went wrong with the all food call");
            }
        }
        getAllFood();
    }, []);

    const output = food.map((food, i) => {
        return (
            <div key={i}>
                { food.name }
            </div>
        )
    })

    return (
        <div>
            <h2>Food</h2>
            <div>{ output }</div>
        </div>
    )
}

export default Food;