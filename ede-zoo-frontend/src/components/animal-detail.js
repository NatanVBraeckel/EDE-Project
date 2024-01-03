import { useEffect, useState } from "react";
import AnimalApi from "../api/animal-api";
import { useNavigate, useParams } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { jwtState } from "../store";
import Back from "./back";

function AnimalDetail() {
    const {id} = useParams();
    const navigate = useNavigate();
    const jwtToken = useRecoilValue(jwtState);

    const [name, setName] = useState('');
    const [type, setType] = useState('');
    const [animalCode, setAnimalCode] = useState('');
    const [birthDate, setBirthDate] = useState('');
    const [codePreferredFood, setCodePreferredFood] = useState('');

    const [isSubmitting, setIsSubmitting] = useState(false);
    
    async function handleSubmit(event) {
        setIsSubmitting(true);
        event.preventDefault();

        let animal = {
            name: name,
            type: type,
            animalCode: animalCode,
            birthDate: birthDate,
            codePreferredFood: codePreferredFood
        }

        try {
            if(id === '0') {
                console.log("creating animal")
                await AnimalApi.createAnimal(animal, jwtToken)
            } else {
                console.log("updating animal")
                await AnimalApi.updateAnimal(id, animal, jwtToken)
            }   
        } catch {
            console.warn("Something went wrong")
        } finally {
            setIsSubmitting(false);
        }
        navigate('/animal');
    }

    const getAnimalData = async () => {
        let result = await AnimalApi.getAnimalById(id);
        console.log("result get by id ", result.data);
        setName(result.data[0].name);
        setType(result.data[0].type);
        setAnimalCode(result.data[0].animalCode);
        if (result.data[0].birthDate === null) {
            setBirthDate('');
          } else {
            setBirthDate(result.data[0].birthDate);
          }
        setCodePreferredFood(result.data[0].codePreferredFood);
    }

    useEffect(() => {
        if(id !== '0') {
            getAnimalData();
        }
    }, []);

    return (
        <div className="container">
            <div style={{ display: 'flex', gap: '.5rem', alignItems: 'center', marginBottom: '1rem' }}>
                <Back></Back>
                { id === '0' ? (
                    <h2 style={{ margin: '0'}}>New animal</h2>
                ) : (
                    <h2 style={{ margin: '0'}}>Edit animal</h2>
                )}
            </div>
            <form onSubmit={handleSubmit}>
                <div className="form-label-group">
                    <label htmlFor="name">Name:</label>
                    <input type="text" id="name" value={name} onChange={(e) => setName(e.target.value)} />
                </div>
                <div className="form-label-group">
                    <label htmlFor="type">Type:</label>
                    <input type="text" id="type" value={type} onChange={(e) => setType(e.target.value)} />
                </div>
                <div className="form-label-group">
                    <label htmlFor="animalCode">Animal code:</label>
                    <input type="text" id="animalCode" value={animalCode} onChange={(e) => setAnimalCode(e.target.value)} />
                </div>
                <div className="form-label-group">
                    <label htmlFor="birthDate">BirthDate:</label>
                    <input type="date" id="birthDate" value={birthDate} onChange={(e) => setBirthDate(e.target.value)} />
                </div>
                <div className="form-label-group">
                    <label htmlFor="codePreferredFood">Preferred food code:</label>
                    <input type="text" id="codePreferredFood" value={codePreferredFood} onChange={(e) => setCodePreferredFood(e.target.value)} />
                </div>
                <button type="submit" disabled={isSubmitting}>{id === '0' ? 'Create' : 'Update'}</button>
            </form>
        </div>
    )
}

export default AnimalDetail;