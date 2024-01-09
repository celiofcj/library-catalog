async function displayWriters() {
    let writers = await getAllWriters();
    let line = "";
    let i = 1;

    writers.forEach(writer => {
        let bio = getBio(writer);
        let nOfBooks = getNumOfBooks(writer);
        line += `<tr>
                    <th>${i}</th>
                    <td><a href="#" class = "redirect" onclick="writerDetails(${writer.id})">${writer.name}</a></td>
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

function writerDetails(writerId){
    window.localStorage.setItem('writerId', writerId);
    window.location.href="writerdetails.html";
}


displayWriters();