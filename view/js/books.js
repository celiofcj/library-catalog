async function displayBooks() {
    let books = await getAllBooks();
    console.log(books);
    let line = "";
    let i = 1;

    books.forEach(book => {
        let themes = getThemeNames(book);
        let authors = getWritersNames(book);
        line += `<tr>
                    <th>${i}</th>
                    <td>${book.title}</td>
                    <td>${book.publishYear}</td>
                    <td>${book.publisher}</td>
                    <td>${themes}</td>
                    <td>${authors}</td>
        `;
        i++;
    });

    document.getElementById("body-table-books").innerHTML = line;
}

async function getAllBooks(){
    const response = await fetch("http://localhost:8080/book", {
        method: "GET",
        headers: new Headers(),
    });

    return await response.json();
}

function getThemeNames(book){
    let themes = book.themes;
    let themeNames = "";
    const maximo = 4;
    for(let i = 0; i < maximo && themes[i] != null; i++){
        if(i > 0){
            themeNames += ", ";
        }
        themeNames += themes[i].name;
    }

        if(themes[maximo] != null){
            themeNames += "...";
        }
        return themeNames;
}

function getWritersNames(book){
    let writers = book.writers;
    let writerNames = "";
    const maximo = 4;
    for(let i = 0; i < maximo && writers[i] != null; i++){
            if(i > 0){
                writerNames += ", ";
            }
            writerNames += writers[i].name;

        if(writers[maximo] != null){
            writerNames += "...";
        }
    }
    return writerNames;
}

displayBooks();