function displayWriter(writer){
    document.getElementById('writer-name').innerHTML = writer.name;
    document.getElementById('bio').innerHTML = writer.bio;
}

async function writerDetails(){
    let writer = await getWriter();
    let books = await getBooks(writer.id);
    displayWriter(writer);
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


async function updateWriter(){
    let writer = JSON.parse(window.localStorage.getItem("writer"));

    let name;
    if(document.getElementById("new-writer-name").value !== ''){
        name = document.getElementById("new-writer-name").value;
    }
    else{
        name = writer.name;
    }
    let bio;
    if(document.getElementById("new-bio").value !== ''){
        bio = document.getElementById("new-bio").value;
    }else {
        bio = writer.bio;
    }

    let updatedWriter = await update(writer.id, JSON.stringify({
        name: name,
        bio: bio
    }));

    window.localStorage.setItem('writer', JSON.stringify(updatedWriter));
    displayWriter(updatedWriter);
    let books = await getBooks(updatedWriter.id);
    displayBooks(books);
}

async function deleteWriter(){
    let writer = JSON.parse(window.localStorage.getItem('writer'));
    deleteRequest(writer.id);
    window.location.href="writers.html";
}

async function getBooks(bookId){
    const response = await fetch(`http://localhost:8080/book/writer/${bookId}`, {
        method: "GET",
        headers: new Headers({
            "Content-Type": "application/json; charset=utf8"
        }),
    });

    return await response.json();
}

async function getWriter(){
    let writerId = window.localStorage.getItem('writerId');
    const response = await fetch(`http://localhost:8080/writer/${writerId}`, {
        method: "GET",
        headers: new Headers({
                "Content-Type": "application/json; charset=utf8"
            }
        ),
    });

    let writer = await response.json();
    window.localStorage.setItem('writer', JSON.stringify(writer));
    return writer;
}

async function update(writerId, body){
    console.log(body);
    const response = await fetch(`http://localhost:8080/writer/${writerId}`, {
        method: "PATCH",
        headers: new Headers({
            "Content-Type": "application/json; charset=utf8"
        }),
        body:body
    });

    let writer = await response.json();
    console.log(writer);
    window.localStorage.setItem('writer', JSON.stringify(writer));
    return writer;
}

async function deleteRequest(writerId){
    const response = await fetch(`http://localhost:8080/writer/${writerId}`, {
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

writerDetails();