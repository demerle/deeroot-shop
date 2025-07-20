import axios from "axios";
import React, {useState} from "react";
import {useAuth} from "../AuthContext.jsx";

export default function Admin(){

    const [id, setId] = useState(-1);
    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [composer, setComposer] = useState("");
    const [price, setPrice] = useState(0.00);
    const [fileName, setFileName] = useState("");
    const [fileType, setFileType] = useState("");
    const [imageFileName, setImageFileName] = useState("");
    const [imageFileType, setImageFileType] = useState("");

    const [file, setFile] = useState(null);

    const [badFile, setBadFile] = useState(false);
    const {user} = useAuth()

    function uploadAndAddItem(){

        console.log(file.type);
        if (file.type !== "application/pdf" || file.type === "audio/midi"){
            setBadFile(true)
            return
        }


        const json = {id, title, description, composer, price, fileName, fileType, imageFileName, imageFileType};

        axios.post('http://localhost:8080/upload', file, {
            headers: {
                Authorization: `Bearer ${user.token}`,
            }
        }).then(res => {
            console.log("Data:", res.data);
        }).catch(err => {
                console.error("Error:", err)
            })


        axios.post('http://localhost:8080/music-items', json, {
            headers: {
                Authorization: `Bearer ${user.token}`,
            }
        })
            .then(res => {
                console.log("Data:", res.data);
            })
            .catch(err => {
                console.error("Error:", err)
            })

        if (badFile){
            setBadFile(false);
        }
    }

    function fileChange(e){
        setFile(e.target.files[0]);
    }




    return (

        <>
            <h1>This is the admin page</h1>


            <p>Add a music item here</p>
            <form id="form" className="login-form" onSubmit={e => e.preventDefault()}>
                <label>Id:</label>
                <input value={id} onChange={(e) => setId(e.target.value)}/>

                <label>title:</label>
                <input value={title} onChange={(e) => setTitle(e.target.value)}/>

                <label>description:</label>
                <input value={description} onChange={(e) => setDescription(e.target.value)}/>

                <label>composer:</label>
                <input value={composer} onChange={(e) => setComposer(e.target.value)}/>

                <label>price:</label>
                <input value={price} onChange={(e) => setPrice(e.target.value)}/>

                <label>fileName:</label>
                <input value={fileName} onChange={(e) => setFileName(e.target.value)}/>

                <label>fileType:</label>
                <input value={fileType} onChange={(e) => setFileType(e.target.value)}/>

                <label>imageFileName:</label>
                <input value={imageFileName} onChange={(e) => setImageFileName(e.target.value)}/>

                <label>imageFileType:</label>
                <input value={imageFileType} onChange={(e) => setImageFileType(e.target.value)}/>

                <label>Upload Sheet or Midi Here: </label>
                <input id = "file" type={"file"} onChange={fileChange}/>
            </form>


            <button onClick={uploadAndAddItem}>Create</button>

            {badFile && <h2>Incorrect File Type, Please Try Again</h2>}

        </>

    )
}