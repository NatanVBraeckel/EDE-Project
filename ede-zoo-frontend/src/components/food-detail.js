import { useEffect, useState } from "react";
import { useParams } from "react-router";
import FoodApi from "../api/food-api";
import { useRecoilValue } from "recoil";
import { jwtState } from "../store";

function FoodDetail() {
    const {id} = useParams();
    const jwtToken = useRecoilValue(jwtState);

    const [name, setName] = useState('');
    const [category, setCategory] = useState('');
    const [foodCode, setFoodCode] = useState('');
    const [origin, setOrigin] = useState('');
    const [servingSize, setServingSize] = useState('');
    const [stock, setStock] = useState(0);

    function handleSubmit(event) {
        event.preventDefault();

        let food = {
            name: name,
            category: category,
            foodCode: foodCode,
            origin: origin,
            servingSize: servingSize,
            stock: stock,
        }

        if(id === '0') {
            console.log("creating food")
            FoodApi.createFood(food, jwtToken)
        } else {
            console.log("updating food")
        }
    }

    const getFoodData = async () => {
        let result = await FoodApi.getFoodById(id);
        setName(result.data[0].name);
        setCategory(result.data[0].category);
        setFoodCode(result.data[0].foodCode);
        setOrigin(result.data[0].origin);
        setServingSize(result.data[0].servingSize);
        setStock(result.data[0].stock);
        console.log("Data from get by id:", result.data);
    }

    useEffect(() => {
        if(id !== '0') {
            getFoodData();
        }
    }, [])

    return (
        <div className="container">
            { id === '0' ? (
                <h2>New food</h2>
            ) : (
                <h2>Edit food</h2>
            )}
            <form onSubmit={handleSubmit}>
                <div className="form-label-group">
                    <label for="name">Name:</label>
                    <input type="text" id="name" value={name} onChange={(e) => setName(e.target.value)} />
                </div>
                <div className="form-label-group">
                    <label for="category">Category:</label>
                    <input type="text" id="category" value={category} onChange={(e) => setCategory(e.target.value)} />
                </div>
                <div className="form-label-group">
                    <label for="foodCode">Food code:</label>
                    <input type="text" id="foodCode" value={foodCode} onChange={(e) => setFoodCode(e.target.value)} />
                </div>
                <div className="form-label-group">
                    <label for="origin">Origin:</label>
                    <input type="text" id="origin" value={origin} onChange={(e) => setOrigin(e.target.value)} />
                </div>
                <div className="form-label-group">
                    <label for="servingSize">Serving size:</label>
                    <input type="text" id="servingSize" value={servingSize} onChange={(e) => setServingSize(e.target.value)} />
                </div>
                <div className="form-label-group">
                    <label for="stock">Stock:</label>
                    <input type="number" id="stock" value={stock} onChange={(e) => setStock(e.target.value)} />
                </div>
                <button type="submit">{id === '0' ? 'Create' : 'Update'}</button>
            </form>
        </div>
    )
}

export default FoodDetail;