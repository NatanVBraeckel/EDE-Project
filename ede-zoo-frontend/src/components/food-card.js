import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Link } from 'react-router-dom';
import './food-card.css'
import { useRecoilValue } from 'recoil';
import { jwtState } from '../store';

function FoodCard({ food, deleteFood, updateStock }) {
    const jwtToken = useRecoilValue(jwtState);

    function handleStock() {
        const input = window.prompt("With how much would you like to increase/decrease stock?", "")
        if(input !== null) {
            console.log("user entered", input);
            updateStock(food.id, input, jwtToken)
        } else {
            console.log("user cancelled prompt");
        }
    }

    return (
        <section className="card">
            <h4>{ food.name }</h4>
            <p>{ food.category }</p>
            <p>{ food.foodCode }</p>
            <p>{ food.origin }</p>
            <p>{ food.servingSize }</p>
            <p>{ food.stock }</p>
            { jwtToken !== "" &&
            <div>
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