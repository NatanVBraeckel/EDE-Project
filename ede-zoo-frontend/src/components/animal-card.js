import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Link } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { jwtState } from "../store";

function AnimalCard({ animal, deleteAnimal }) {
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

    return (
        <section className="card">
            <h4 style={style.name}>{ animal.name }</h4>
            <div style={style.propertyDiv}>
                <p style={style.label}>Type:</p>
                <p style={style.property}>{ animal.type }</p>
            </div>
            <div style={style.propertyDiv}>
                <p style={style.label}>Animal code:</p>
                <p style={style.property}>{ animal.animalCode }</p>
            </div>
            <div style={style.propertyDiv}>
                <p style={style.label}>BirthDate:</p>
                <p style={style.property}>{ animal.birthDate }</p>
            </div>
            <div style={style.propertyDiv}>
                <p style={style.label}>Code preferred food:</p>
                <p style={style.property}>{ animal.codePreferredFood }</p>
            </div>
            { jwtToken !== "" &&
            <div style={{ position: 'absolute', gap: '.6rem', top: '.5rem', right: '5px', display: 'flex' }}>
                <Link to={'/animal/' + animal.id}>
                    <button className='button editButton'>
                        <FontAwesomeIcon icon="pen-to-square"></FontAwesomeIcon>
                    </button>
                </Link>
                <button className='button deleteButton' onClick={() => deleteAnimal(animal.id, jwtToken)}>
                    <FontAwesomeIcon icon="trash-can"></FontAwesomeIcon>
                </button>
            </div>
            }
        </section>
    )
}

export default AnimalCard;