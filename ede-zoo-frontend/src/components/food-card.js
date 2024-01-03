import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Link } from 'react-router-dom';
import './food-card.css'
import { useRecoilValue } from 'recoil';
import { jwtState } from '../store';
import FoodApi from '../api/food-api';

function FoodCard({ food, afterApiRequest }) {
    const jwtToken = useRecoilValue(jwtState);

    const style = {
        name: {
            fontSize: "1.5rem"
        },
        propertyDiv: {
            marginBlock: '.8rem',
        },
        property: {
            fontSize: "1.2rem"
        },
        label: {
            fontWeight: 'bold',
        },
    }

    function handleStock() {
        const input = window.prompt("With how much would you like to increase/decrease stock?", "")
        if(input !== null) {
            console.log("user entered", input);
            updateStock(food.id, input, jwtToken)
        } else {
            console.log("user cancelled prompt");
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
        afterApiRequest();
    }

    async function updateStock(id, amount, jwtToken) {
        console.log("updating stock");
        try {
            const result = await FoodApi.updateStock(id, amount, jwtToken);
            console.log("Update result:", result.data);
        } catch {
            console.warn("Something went wrong with stock update");
        }
        afterApiRequest();
    }

    return (
        <section className="card">
            <h4 style={style.name}>{ food.name }</h4>
            <div style={style.propertyDiv}>
                <p style={style.label}>Category: </p>
                <p style={style.property}>{ food.category }</p>
            </div>
            <div style={style.propertyDiv}>
                <p style={style.label}>Food code:</p>
                <p style={style.property}>{ food.foodCode }</p>
            </div>
            <div style={style.propertyDiv}>
                <p style={style.label}>Food origin:</p>
                <p style={style.property}>{ food.origin }</p>
            </div>
            <div style={style.propertyDiv}>
                <p style={style.label}>Serving size:</p>
                <p style={style.property}>{ food.servingSize }</p>
            </div>
            <div style={{...style.propertyDiv, position: 'absolute', bottom: '1rem', right: '1rem'}}>
                <p style={style.label}>Stock:</p>
                <p style={style.property}>{ food.stock } units in stock</p>
            </div>
            { jwtToken !== "" &&
            <div style={{ position: 'absolute', gap: '.6rem', top: '.5rem', right: '5px', display: 'flex' }}>
                <button className='button stockButton' onClick={() => handleStock()}>
                    <FontAwesomeIcon icon="cubes-stacked"></FontAwesomeIcon>
                </button>
                <Link to={'/food/' + food.id}>
                    <button className='button editButton'>
                        <FontAwesomeIcon icon="pen-to-square"></FontAwesomeIcon>
                    </button>
                </Link>
                <button className='button deleteButton' onClick={() => deleteFood(food.id, jwtToken)}>
                    <FontAwesomeIcon icon="trash-can"></FontAwesomeIcon>
                </button>
            </div>
            }
        </section>
    )
}

export default FoodCard;