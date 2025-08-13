import axios from "axios";

export function convertPrice(price){
    return "$" + price + ".00"
}


export function download(fileName){

    const token = localStorage.getItem("token");
    axios.get(`${import.meta.env.VITE_API_URL}/music-items/download/` + fileName, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
        responseType: 'blob'
    })
        .then(res => {

            const blob = new Blob([res.data])
            const url = window.URL.createObjectURL(blob);

            const a = document.createElement("a");
            a.href = url
            a.download = fileName
            document.body.appendChild(a)
            a.click()
            a.remove()
            window.URL.revokeObjectURL(url)

        })
        .catch(err => {
            if (err.response && err.response.status === 403) {
                (console.log("403 when download attempted"))
            }
            if (err.response && err.response.status === 404) {
                alert("No such file was found for download")
            }
            else{
                console.error("Error in Download:", err);
            }
        });
}