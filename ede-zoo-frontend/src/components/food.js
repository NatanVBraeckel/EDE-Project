import { useEffect } from "react";
import FoodApi from "../api/food-api";

function Food() {
    useEffect(() => {
        const getAllFood = async () => {
            try {
                const result = await FoodApi.getAllFood();
                console.log(result.data);
            } catch {
                console.warn("Something went wrong with the all food call");
            }
        }
        getAllFood();
    }, [])

    return (
        <div>
            <h2>Food</h2>
        </div>
    )
}

export default Food;