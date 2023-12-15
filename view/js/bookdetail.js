async function displayBook(){
    let book = await getBook();

    document.getElementById('title').innerHTML = book.title;
    document.getElementById('year').innerHTML = book.publishYear;
    document.getElementById('publisher').innerHTML = book.publisher;

    displayWriters(book.writers);
    displayThemes(book.themes);
    let instances = await getInstances(book.id);
    displayInstances(instances);
}

function displayWriters(writers){
    let writersLine = "";
    writers.forEach(writer => {
        writersLine += `
        <tr>
        <td>${writer.name}</td>
        <td><button type="button" class = "btn btn-danger mx-auto d-block" onclick="removeWriter(${writer.id})">Remove</button></td>
</tr>
    `;
    });

    document.getElementById('body-writer-show').innerHTML = writersLine;
}

function displayThemes(themes){
    let themesLine = "";
    themes.forEach(theme => {
        themesLine += `
        <tr>
            <td>${theme.name}</td>
            <td><button type="button" class = "btn btn-danger mx-auto d-block" onclick="removeTheme(${theme.id})">Remove</button></td>
        </tr>
    `;
    });

    document.getElementById('body-theme-show').innerHTML = themesLine;
}

function displayInstances(instances){
    instances.forEach(instance => {
        displayInstance(instance);
    });
}

function displayInstance(instance){
    let instanceLine = document.getElementById('body-instance-show').innerHTML;
    let instanceStatus;
    if(instance.available){
        instanceStatus = 'Available';
    }
    else{
        instanceStatus = 'Not available';
    }
    instanceLine += `
        <tr id = "instance_${instance.id}">
            <td>${instance.id}</td>
            <td>${instanceStatus}</td>
            <td><button type="button" class="btn btn-dark mx-auto d-block" onclick="changeInstanceStatus(${instance.id}, ${instance.available})">Change Status</button></td>
            <td><button type="button" class="btn btn-danger mx-auto d-block" onclick="removeInstance(${instance.id})">Remove</button></td>
        </tr>`

    document.getElementById('body-instance-show').innerHTML = instanceLine;
}

async function getInstances(id){
    const response = await fetch(`http://localhost:8080/instance/book/${id}`,{
        method: "GET",
        headers: {
            "Content-Type": "application/json; charset=utf8"
        }
    });

    let instances = await response.json();
    window.localStorage.setItem('instances', JSON.stringify(instances));
    return instances;
}

async function updateBook(){
    let book = JSON.parse(window.localStorage.getItem('book'));
    let tittle = document.getElementById('new-title').value;
    let year = parseInt(document.getElementById('new-year').value);
    let publisher = document.getElementById('new-publisher').value;
    let writers = book.writers;
    let themes = book.themes;

    console.log(year + ' ' + publisher);
    book.title = tittle;
    book.publishYear = year;
    book.publisher = publisher;
    book.writers = filterIds(writers);
    book.themes = filterIds(themes);

    await update(book.id, JSON.stringify(book));
    displayBook();
}

async function removeWriter(writerId){
    let book = JSON.parse(window.localStorage.getItem('book'));
    let writers = book.writers;
    for(let i = 0; i < writerId; i++) {
        if (writers[i].id === writerId) {
            writers.splice(i, 1);
            break;
        }
    }


    book.writers = filterIds(writers);
    book.themes = filterIds(book.themes);
    let bookUpdated = await update(book.id, JSON.stringify(book));
    displayWriters(bookUpdated.writers);
}

async function removeTheme(themeId){
    let book = JSON.parse(window.localStorage.getItem('book'));
    let themes = book.themes;
    for(let i = 0; i < themeId; i++) {
        if (themes[i].id === themeId) {
            themes.splice(i, 1);
            break;
        }
    }


    book.themes = filterIds(themes);
    book.writers = filterIds(book.writers);
    let bookUpdated = await update(book.id, JSON.stringify(book));
    displayThemes(bookUpdated.themes);
}

async function removeInstance(instanceId){
     const response = await fetch(`http://localhost:8080/instance/${instanceId}`, {
         method: "DELETE",
         headers: new Headers({
             "Content-Type": "application/json; charset=utf8"
         })
     });

     document.getElementById(`instance_${instanceId}`).innerHTML = "";
}

async function changeInstanceStatus(instanceId, available){
    let bookId = window.localStorage.getItem('bookId');
    let availableUpdate;
    availableUpdate = !available;

    const response = await fetch(`http://localhost:8080/instance/${instanceId}`, {
        method: "PATCH",
        headers: new Headers({
            "Content-Type": "application/json; charset=utf8"
        }),
        body: JSON.stringify({
            available: availableUpdate,
            book: bookId
        })
    });

    let instance = await response.json();
    let instanceStatus;
    if(instance.available){
        instanceStatus = 'Available';
    }
    else{
        instanceStatus = 'Not available';
    }

    if(response.ok){
        document.getElementById(`instance_${instanceId}`).innerHTML = `
        <tr id = "instance_${instance.id}">
            <td>${instance.id}</td>
            <td>${instanceStatus}</td>
            <td><button type="button" class="btn btn-dark mx-auto d-block" onclick="changeInstanceStatus(${instance.id}, ${instance.available})">Change Status</button></td>
            <td><button type="button" class="btn btn-danger mx-auto d-block" onclick="removeInstance(${instance.id})">Remove</button></td>
        </tr>`;
    }
}

function filterIds(objects){
    let ids = [];
    objects.forEach(object => {
        ids.push(object.id);
    })

    return ids;
}

async function update(bookId, body){
    const response = await fetch(`http://localhost:8080/book/${bookId}`, {
        method: "PATCH",
        headers: new Headers({
            "Content-Type": "application/json; charset=utf8"
        }),
        body: body
    });

    let book = await response.json();
    window.localStorage.setItem('book', JSON.stringify(book));
    return book;
}

async function getBook(){
    const bookId = window.localStorage.getItem('bookId');
    const response = await fetch(`http://localhost:8080/book/${bookId}`, {
        method: "GET",
        headers: new Headers({
            "Content-Type": "application/json; charset=utf8"
        })
    });

    let book = await response.json();
    window.localStorage.setItem('book', JSON.stringify(book));
    return book;
}

async function addInstance(){
    const availableness = true;
    let idBook = window.localStorage.getItem('bookId');
    const response = await fetch("http://localhost:8080/instance", {
        method: "POST",
        headers: new Headers({
            "Content-Type": "application/json; charset=utf8"
        }),
        body: JSON.stringify({
            available: availableness,
            book: idBook
        })
    });

    let instance = await response.json();

    if(response.ok) {
        displayInstance(instance);
    }

}

async function deleteBook(){
    let bookId = window.localStorage.getItem('bookId');

    const response = await fetch(`http://localhost:8080/book/${bookId}`, {
        method: "DELETE",
        headers: new Headers({
            "Content-Type": "application/json; charset=utf8"
        })
    });

    if(response.ok){
        window.location.href="books.html";
    }
}


displayBook();