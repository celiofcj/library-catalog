function displayTheme(theme){
    document.getElementById('theme-name').innerHTML = theme.name;
    document.getElementById('description').innerHTML = theme.description;
}

async function themeDetails(){
    let theme = await getTheme();
    let books = await getBooks(theme.id);
    displayTheme(theme);
    displayBooks(books);
}

function displayBooks(books){
    let booksLine = "";
    books.forEach(book => {
        booksLine += `
        <tr>
            <td ><a href="#" class="redirect" onclick="bookDetails(${book.id})">${book.title}</a></td>
        </tr>
        `
    });

    document.getElementById('body-book-show').innerHTML = booksLine;
}


async function updateTheme(){
    let theme = JSON.parse(window.localStorage.getItem("theme"));

    let name;
    if(document.getElementById("new-theme-name").value !== ''){
        name = document.getElementById("new-theme-name").value;
    }
    else{
        name = theme.name;
    }
    let description;
    if(document.getElementById("new-description").value !== ''){
        description = document.getElementById("new-description").value;
    }else {
        description = theme.description;
    }

    let updatedTheme = await update(theme.id, JSON.stringify({
        name: name,
        description: description
    }));

    window.localStorage.setItem('theme', JSON.stringify(updatedTheme));
    displayTheme(updatedTheme);
    let books = await getBooks(updatedTheme.id);
    displayBooks(books);
}

async function deleteTheme(){
    let theme = JSON.parse(window.localStorage.getItem('theme'));
    deleteRequest(theme.id);
    window.location.href="themes.html";
}

async function getBooks(bookId){
    const response = await fetch(`http://localhost:8080/book/theme/${bookId}`, {
        method: "GET",
        headers: new Headers({
            "Content-Type": "application/json; charset=utf8"
        }),
    });

    return await response.json();
}

async function getTheme(){
    let themeId = window.localStorage.getItem('themeId');
    const response = await fetch(`http://localhost:8080/theme/${themeId}`, {
        method: "GET",
        headers: new Headers({
                "Content-Type": "application/json; charset=utf8"
            }
        ),
    });

    let theme = await response.json();
    window.localStorage.setItem('theme', JSON.stringify(theme));
    return theme;
}

async function update(themeId, body){
    console.log(body);
    const response = await fetch(`http://localhost:8080/theme/${themeId}`, {
        method: "PATCH",
        headers: new Headers({
            "Content-Type": "application/json; charset=utf8"
        }),
        body:body
    });

    let theme = await response.json();
    console.log(theme);
    window.localStorage.setItem('theme', JSON.stringify(theme));
    return theme;
}

async function deleteRequest(themeId){
    const response = await fetch(`http://localhost:8080/theme/${themeId}`, {
        method: "DELETE",
        headers: new Headers({
            "Content-Type": "application/json; charset=utf8"
        }),
    });
}

function bookDetails(bookId){
    window.localStorage.setItem('bookId', bookId);
    window.location.href="bookdetail.html";
}

themeDetails();