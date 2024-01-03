import { useEffect, useState } from "react";
import { useParams } from "react-router";
import FoodApi from "../api/food-api";
import { useRecoilValue } from "recoil";
import { jwtState } from "../store";
import { useNavigate } from "react-router-dom";
import Back from "./back";

function FoodDetail() {
    const {id} = useParams();
    const jwtToken = useRecoilValue(jwtState);
    const navigate = useNavigate();

    const [name, setName] = useState('');
    const [category, setCategory] = useState('');
    const [foodCode, setFoodCode] = useState('');
    const [origin, setOrigin] = useState('');
    const [servingSize, setServingSize] = useState('');
    const [stock, setStock] = useState(0);

    const [isSubmitting, setIsSubmitting] = useState(false);

    async function handleSubmit(event) {
        setIsSubmitting(true);
        event.preventDefault();

        let food = {
            name: name,
            category: category,
            foodCode: foodCode,
            origin: origin,
            servingSize: servingSize,
            stock: stock,
        }

        try {
            if(id === '0') {
                console.log("creating food")
                await FoodApi.createFood(food, jwtToken)
            } else {
                console.log("updating food")
                await FoodApi.updateFood(id, food, jwtToken)
            }   
        } catch {
            console.warn("Something went wrong")
        } finally {
            setIsSubmitting(false);
        }
        navigate(-1);
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
            <div style={{ display: 'flex', gap: '.5rem', alignItems: 'center', marginBottom: '1rem' }}>
                <Back></Back>
                { id === '0' ? (
                    <h2 style={{ margin: '0'}}>New food</h2>
                ) : (
                    <h2 style={{ margin: '0'}}>Edit food</h2>
                )}
            </div>
            <form onSubmit={handleSubmit}>
                <div className="form-label-group">
                    <label htmlFor="name">Name:</label>
                    <input type="text" id="name" value={name} onChange={(e) => setName(e.target.value)} />
                </div>
                <div className="form-label-group">
                    <label htmlFor="category">Category:</label>
                    <input type="text" id="category" value={category} onChange={(e) => setCategory(e.target.value)} />
                </div>
                <div className="form-label-group">
                    <label htmlFor="foodCode">Food code:</label>
                    <input type="text" id="foodCode" value={foodCode} onChange={(e) => setFoodCode(e.target.value)} />
                </div>
                <div className="form-label-group">
                    <label htmlFor="origin">Origin:</label>
                    <input type="text" id="origin" value={origin} onChange={(e) => setOrigin(e.target.value)} />
                </div>
                <div className="form-label-group">
                    <label htmlFor="servingSize">Serving size:</label>
                    <input type="text" id="servingSize" value={servingSize} onChange={(e) => setServingSize(e.target.value)} />
                </div>
                <div className="form-label-group">
                    <label htmlFor="stock">Stock:</label>
                    <input type="number" id="stock" value={stock} onChange={(e) => setStock(e.target.value)} />
                </div>
                <button type="submit" disabled={isSubmitting}>{id === '0' ? 'Create' : 'Update'}</button>
            </form>
        </div>
    )
}

export default FoodDetail;