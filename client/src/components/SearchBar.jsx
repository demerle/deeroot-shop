import {useEffect, useState} from "react";

export default function SearchBar(props) {

    const [inputText, setInputText] = useState('');

    useEffect(() => {
        props.search(inputText)
    },[inputText])

    return (
        <input
            className="search-bar"
            type="text"
            placeholder="Looking for something specific?"
            name="searchBar"
            value={inputText}
            onChange={e => setInputText(e.target.value)}
        />
    )
}