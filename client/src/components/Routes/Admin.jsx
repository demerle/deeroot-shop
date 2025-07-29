import axios from "axios";
import React, {useState} from "react";
import {useAuth} from "../AuthContext.jsx";

export default function Admin(){

    const [title, setTitle] = useState("title");
    const [description, setDescription] = useState("description");
    const [composer, setComposer] = useState("composer");
    const [price, setPrice] = useState(10.00);
    const [numPages, setNumPages] = useState(1);
    const [file, setFile] = useState(null);

    const [badFile, setBadFile] = useState(false);
    const {user} = useAuth()

    function uploadAndAddItem(){

        if (file.type !== "application/pdf" && file.type !== "audio/mid"){
            console.log(file.type)
            setBadFile(true)
            alert("Incorrect File Type, Please Try Again")
            return
        }


        const formData = new FormData();
        formData.append("file", file);

        axios.post('http://localhost:8080/music-items/upload', formData, {
            headers: {
                Authorization: `Bearer ${user.token}`
            }
        }).then(res => {

            const s3PreviewUrl = res.data

            const fileType = file.type
            const fileName = file.name

            const json = { title, description, composer, price, fileName, fileType, s3PreviewUrl, numPages};

            //API create call upon successful file upload
            axios.post('http://localhost:8080/music-items', json, {
                headers: {
                    Authorization: `Bearer ${user.token}`,
                }
            })
                .then(res => {
                    alert("Item Created")
                })
                .catch(err => {
                    if (err.response.status === 409){
                        alert("Error: File with that name already exists in the DB")
                    }
                    console.error("Error:", err)
                })

        }).catch(err => {
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
                <label>title:</label>
                <input type="text" value={title} onChange={(e) => setTitle(e.target.value)}/>

                <label>description:</label>
                <input type="text" value={description} onChange={(e) => setDescription(e.target.value)}/>

                <label>composer:</label>
                <input type="text" value={composer} onChange={(e) => setComposer(e.target.value)}/>

                <label>price:</label>
                <input type="text" value={price} onChange={(e) => setPrice(e.target.value)}/>

                <label>numPages:</label>
                <input type="text" value={numPages} onChange={(e) => setNumPages(e.target.value)}/>

                <label>Upload Sheet or Midi Here: </label>
                <input id = "file" type="file" onChange={fileChange}/>


            </form>


            <button className ="button" onClick={uploadAndAddItem}>Create</button>

            {badFile && <h2>Incorrect File Type, Please Try Again</h2>}

        </>

    )
}