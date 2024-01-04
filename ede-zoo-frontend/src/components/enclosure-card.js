import { useRecoilValue } from "recoil";
import { jwtState } from "../store";
import EnclosureApi from "../api/enclosure-api";
import AnimalCard from "./animal-card";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

function EnclosureCard({ enclosure, afterApiRequest }) {
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

    async function deleteEnclosure(id) {
        console.log("deleting enclosure with id:", id);
        try {
            await EnclosureApi.deleteEnclosure(id, jwtToken);
        } catch {
            console.warn("Something went wrong with enclosure delete")
        }
        afterApiRequest();
    }

    let animalCodesP = null
    if(enclosure.animalCodes) {
        animalCodesP = enclosure.animalCodes.map((code, i) => {
            return (
                <p key={i} style={style.property}>{ code }</p>
            )
        });
    }

    let animalCards = null;
    if (enclosure.animals) {
      animalCards = enclosure.animals.map((animal, i) => (
        <AnimalCard animal={animal} key={i} afterApiRequest={afterApiRequest} />
      ));
    }

    return (
        <section className="card" style={{ backgroundColor: "lightblue", flexGrow: "1", flexBasis: "800px" }}>
            <h4 style={style.name}>{ enclosure.name }</h4>
            <div style={style.propertyDiv}>
                <p style={style.label}>Type:</p>
                <p style={style.property}>{ enclosure.type }</p>
            </div>
            <div style={style.propertyDiv}>
                <p style={style.label}>Size:</p>
                <p style={style.property}>{ enclosure.size }</p>
            </div>
            <div style={style.propertyDiv}>
                <p style={style.label}>Enclosure code:</p>
                <p style={style.property}>{ enclosure.enclosureCode }</p>
            </div>
            <div style={style.propertyDiv}>
                <p style={style.label}>Animal codes:</p>
                { animalCodesP }
            </div>
            <div style={style.propertyDiv}>
                <p style={{...style.label, marginBottom: '.2rem'}}>Animals: </p>
                <div style={{ display: 'flex', gap: '1rem' }}>
                    { animalCards }
                </div>
            </div>
            { jwtToken !== "" &&
            <div style={{ position: 'absolute', gap: '.6rem', top: '.5rem', right: '5px', display: 'flex' }}>
                <Link to={'/enclosure/' + enclosure.id}>
                    <button className='button editButton'>
                        <FontAwesomeIcon icon="pen-to-square"></FontAwesomeIcon>
                    </button>
                </Link>
                <button className='button deleteButton' onClick={() => deleteEnclosure(enclosure.id)}>
                    <FontAwesomeIcon icon="trash-can"></FontAwesomeIcon>
                </button>
            </div>
            }
        </section>
    )
}

export default EnclosureCard;