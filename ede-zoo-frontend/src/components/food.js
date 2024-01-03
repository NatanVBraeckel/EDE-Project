import { useEffect, useState } from "react";
import FoodApi from "../api/food-api";
import FoodCard from './food-card';

function Food() {
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
        } catch {
            console.warn("Something went wrong with the all food call");
        }
    }

    async function deleteFood(id, jwtToken) {
        console.log("deleting food with id:", id);
        try {
            const result = await FoodApi.deleteFood(id, jwtToken);
            console.log("Deletion result:", result);
        } catch {
            console.warn("Something went wrong with food delete");
        }
        console.log("parent deleted food");
        getAllFood();
    }

    async function updateStock(id, amount, jwtToken) {
        console.log("updating stock");
        try {
            const result = await FoodApi.updateStock(id, amount, jwtToken);
            console.log("Update result:", result.data);
        } catch {
            console.warn("Something went wrong with stock update");
        }
        console.log("parent updated stock");
        getAllFood();
    }

    useEffect(() => {
        getAllFood();
    }, []);

    const output = food.map((food, i) => {
        return (
            <FoodCard food={food} deleteFood={deleteFood} updateStock={updateStock} key={i} style={style.foodCard}></FoodCard>
        )
    })

    return (
        <div style={style.container}>
            <h2>Food</h2>
            <div style={style.list}>{ output }</div>
        </div>
    )
}

export default Food;