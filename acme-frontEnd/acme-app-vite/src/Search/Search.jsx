import React, { useState } from 'react';
import axios from 'axios';
import './Search.css'
import swal from 'sweetalert'
const Search = () => {
    //declaracion de variable
    const [countryName, setCountryName] = useState('');
    const [countryInfo, setCountryInfo] = useState(null);
    
    const handleInputChange = (event) => {
        setCountryName(event.target.value);
    };

    const mostrarAlerta =(msj, code)=>{
        swal({
            title:code,
            text:msj,
            icon:"error",
            button: "Aceptar",
            timer:2000
        })
    }

    const handleSearch = () => {
        axios.get(`http://localhost:8082/pais/${countryName}`)
            .then(response => {
                
                if(typeof response?.data ==="string"){
                    const [errorMessageText, errorCode] = response?.data.split(":");
                    mostrarAlerta(errorMessageText , errorCode)
                }  
                
                setCountryInfo(response?.data);
                
            })
            .catch(error => {
                console.error(error);
            });
    };
      //let d = JSON.parse(JSON.stringify({...countryInfo?.languages})) 
      let languageslist 
      if(countryInfo?.languages !== undefined){
        languageslist = Object.values(countryInfo?.languages); 
      }    

      let NameMoney
      let SymbolMoney
      if(countryInfo?.currencies !== undefined){
        const currencies = Object.values(countryInfo?.currencies); 
         NameMoney = currencies[0]?.name
         SymbolMoney = currencies[0]?.symbol
      }    
          
     
     
    return (
        <>

              <section className="hero__main container container--hero">
                <article className="hero__texts">
                    <h1 className="hero__title">¡Acme! Country Information </h1>
                    <div className="container-search">
                        <input className="input-tyext" type="text" value={countryName} onChange={handleInputChange} placeholder="Buscar ..."></input>
                        <button onClick={handleSearch}  className="button-search"><img src="./src/assets/icon.png" className="img-lupa" alt="Ilustración" /></button>
                    </div>

                    
                    <h1>Official Name: {countryInfo?.officialName}</h1>
                    <h1>Region: {countryInfo?.region}</h1>
                    <h1>Subregion: {countryInfo?.subregion}</h1>
                    <h1>Currencies:</h1>
                    <p>{NameMoney}</p>
                    <p>{SymbolMoney}</p>
                    <h1>Languages:</h1>
                    <ul id="id_languages">
                        {
                            languageslist?.map(item => (
                                <li key={item}>{item}</li>
                            ))
                        }
                    </ul>
  
                </article>
                <figure className="hero__figure">
                    <img src={countryInfo?.coatOfArms?.png} className="hero__img" alt="Ilustración" />
                </figure>
                
            </section>



           
        </>
    )
}
export default Search