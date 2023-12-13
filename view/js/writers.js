async function displayWriters() {
    let writers = await getAllWriters();
    let line = "";
    let i = 1;

    writers.forEach(writer => {
        let bio = getBio(writer);
        let nOfBooks = getNumOfBooks(writer);
        line += `<tr>
                    <th>${i}</th>
                    <td>${writer.name}</td>
                    <td>${bio}</td>
                    <td>${nOfBooks}</td>
        `;
        i++;
    });
    document.getElementById("body-table-writers").innerHTML = line;
}

async function getAllWriters(){
    const response = await fetch("http://localhost:8080/writer", {
        method: "GET",
        headers: new Headers(),
    });
    return await response.json();
}

function getBio(writer){
    const length = 60;
    let bio =  writer.bio.substring(0, length);
    if(writer.bio.length > length){
        bio+='...';
    }
    return bio;
}

function getNumOfBooks(writer){
    return writer.books.length;
}


displayWriters();