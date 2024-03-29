import { useNavigate, useParams } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { jwtState } from "../store";
import { useEffect, useState } from "react";
import EnclosureApi from "../api/enclosure-api";
import Back from "./back";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

function EnclosureDetail() {
    const {id} = useParams();
    const navigate = useNavigate();
    const jwtToken = useRecoilValue(jwtState);
    const enclosureTypes = [
        "Savannah",
        "Jungle",
        "Forest",
        "Desert",
        "Arctic",
        "Grassland",
        "Tundra",
        "Aviary"
    ];
    const enclosureSizes = [
        "ExtraSmall",
        "Small",
        "Medium",
        "Large",
        "ExtraLarge",
    ];

    const [name, setName] = useState('');
    const [type, setType] = useState('');
    const [size, setSize] = useState('');
    const [enclosureCode, setEnclosureCode] = useState('');
    const [animalCodes, setAnimalCodes] = useState(['']);

    const [isSubmitting, setIsSubmitting] = useState(false);

    async function handleSubmit(event) {
        setIsSubmitting(true);
        event.preventDefault();

        let enclosure = {
            name: name,
            type: type,
            size: size,
            enclosureCode: enclosureCode,
            animalCodes: animalCodes,
        }

        try {
            if(id === '0') {
                console.log("creating enclosure")
                await EnclosureApi.createEnclosure(enclosure, jwtToken)
            } else {
                console.log("updating enclosure")
                await EnclosureApi.updateEnclosure(id, enclosure, jwtToken)
            }   
        } catch {
            console.warn("Something went wrong")
        } finally {
            setIsSubmitting(false);
        }
        navigate(-1);
    }

    const handleInputChange = (index, event) => {
        const updatedCodes = [...animalCodes];
        updatedCodes[index] = event.target.value;
        setAnimalCodes(updatedCodes);
      };
    
      const handleAddInput = () => {
        setAnimalCodes([...animalCodes, '']); // Add a new empty string input
      };
    
      const handleRemoveInput = (index) => {
        const updatedCodes = [...animalCodes];
        updatedCodes.splice(index, 1); // Remove input at the given index
        setAnimalCodes(updatedCodes);
      };

    const getEnclosureData = async () => {
        let result = await EnclosureApi.getEnclosureById(id);
        console.log("result get by id ", result.data);
        setName(result.data[0].name);
        setType(result.data[0].type);
        setSize(result.data[0].size);
        setEnclosureCode(result.data[0].enclosureCode);
        if (result.data[0].animalCodes !== null) {
            setAnimalCodes(result.data[0].animalCodes);
        }
    }

    useEffect(() => {
        if(id !== '0') {
            document.title = "Edit enclosure";
            getEnclosureData();
        } else {
            document.title = "Create enclosure";
        }
    }, []);

    return (
        <div className="container">
            <div style={{ display: 'flex', gap: '.5rem', alignItems: 'center', marginBottom: '1rem' }}>
                <Back></Back>
                { id === '0' ? (
                    <h2 style={{ margin: '0'}}>New enclosure</h2>
                ) : (
                    <h2 style={{ margin: '0'}}>Edit enclosure</h2>
                )}
            </div>
            <form onSubmit={handleSubmit}>
                <div className="form-label-group">
                    <label htmlFor="name">Name:</label>
                    <input type="text" id="name" value={name} onChange={(e) => setName(e.target.value)} />
                </div>
                {/* <div className="form-label-group">
                    <label htmlFor="type">Enclosure type:</label>
                    <input type="text" id="type" value={type} onChange={(e) => setType(e.target.value)} />
                </div> */}
                <div className="form-label-group">
                    <label htmlFor="type">Enclosure type:</label>
                    <select id="type" value={type} onChange={(e) => setType(e.target.value)}>
                        <option value="">Select an Enclosure Type</option>
                        {enclosureTypes.map((type, index) => (
                        <option key={index} value={type}>
                            {type}
                        </option>
                        ))}
                    </select>
                </div>
                {/* <div className="form-label-group">
                    <label htmlFor="size">Enclosure size:</label>
                    <input type="text" id="size" value={size} onChange={(e) => setSize(e.target.value)} />
                </div> */}
                <div className="form-label-group">
                    <label htmlFor="size">Enclosure size:</label>
                    <select id="size" value={size} onChange={(e) => setSize(e.target.value)}>
                        <option value="">Select an Enclosure Size</option>
                        {enclosureSizes.map((size, index) => (
                        <option key={index} value={size}>
                            {size}
                        </option>
                        ))}
                    </select>
                </div>
                <div className="form-label-group">
                    <label htmlFor="enclosureCode">Enclosure code:</label>
                    <input type="text" id="enclosureCode" value={enclosureCode} onChange={(e) => setEnclosureCode(e.target.value)} />
                </div>
                {/* <div className="form-label-group">
                    <label htmlFor="animalCodes">Animal codes:</label>
                    <input type="text" id="animalCodes" value={animalCodes} onChange={(e) => setAnimalCodes(e.target.value)} />
                </div> */}
                <label>Animal codes: </label>
                {animalCodes.map((code, index) => (
                <div key={index}>
                    <input
                        style={{ marginBlock: '.2rem'}}
                        type="text"
                        value={code}
                        onChange={(event) => handleInputChange(index, event)}
                    />
                    <button type="button" onClick={() => handleRemoveInput(index)} style={{ marginLeft: '.2rem' }}>
                        <FontAwesomeIcon icon="xmark"></FontAwesomeIcon>
                    </button>
                    </div>
                ))}
                <button type="button" onClick={handleAddInput} style={{ marginTop: '.5rem',marginBottom: '1rem', display: 'block'}}>Add Code</button>
                <button type="submit" disabled={isSubmitting}>{id === '0' ? 'Create' : 'Update'}</button>
            </form>
        </div>
    )
}

export default EnclosureDetail;