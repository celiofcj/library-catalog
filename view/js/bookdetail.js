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

function cloneBook(){
    let book = window.localStorage.getItem('book');
    window.localStorage.setItem('updateBook', book);
}

function displayWriters(writers){
    let writersLine = "";
    writers.forEach(writer => {
        writersLine += `
        <tr>
            <td>${writer.name}</td>
            <td id="writer${writer.id}-btn">
                <button type="button" class="btn btn-danger mx-auto d-block" onclick="removeWriter(${writer.id})">Remove</button>
            </td>
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
            <td id="theme${theme.id}-btn">
                <button type="button" class = "btn btn-danger mx-auto d-block" onclick="removeTheme(${theme.id})">Remove</button>
            </td>
        </tr>
    `;
    });

    document.getElementById('body-theme-show').innerHTML = themesLine;
}

function displayInstances(instances){
    document.getElementById('body-instance-show').innerHTML = '';
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
    let updateBook = JSON.parse(window.localStorage.getItem('updateBook'));
    console.log(JSON.parse(window.localStorage.getItem('updateBook')));
    let title = document.getElementById(
        'new-title').value !== '' ? document.getElementById('new-title').value : updateBook.title;
    let year = parseInt(document.getElementById(
        'new-year').value !== '' ? document.getElementById('new-year').value : updateBook.publishYear);
    let publisher = document.getElementById(
        'new-publisher').value !== '' ? document.getElementById('new-publisher').value : updateBook.publisher;
    let writers = updateBook.writers;
    let themes = updateBook.themes;
    updateBook.title = title;
    updateBook.publishYear = year;
    updateBook.publisher = publisher;
    updateBook.writers = filterIds(writers);
    updateBook.themes = filterIds(themes);

    await update(updateBook.id, JSON.stringify(updateBook));
    displayBook();
}

async function writersOptions(){
    let writers = await getAllWriters();
    window.localStorage.setItem('allWriters', JSON.stringify(writers));
    displayWritersOptions();
}

function displayWritersOptions(){
    let updateBook = JSON.parse(window.localStorage.getItem('updateBook'));
    let allWriters = JSON.parse(window.localStorage.getItem('allWriters'));

    let line = '';
    line += `<div class="selectOptions">
                <select id="writersOptions" class="form-select form-select-lg mb-3 options" aria-label=".form-select-lg example" onchange="addWriter()">
                    <option selected value="0">Select Writer...</option>
            `;
    for(let writerOption of allWriters){
        let repeated = false;
        for(let writerRegistrated of updateBook.writers) {
            if (writerOption.id === writerRegistrated.id) {
                repeated = true;
                break;
            }
        }
        if(repeated === false){
            line += `<option value="${writerOption.id};${writerOption.name}">${writerOption.name}</option>`;
        }
    }

    line +=
        `    </select>
         </div>
        `;

    document.getElementById('bd-writer-header').innerHTML = line;
    document.getElementById('btn-addWriter').innerHTML = `
        <button type="button"  class="btn btn-secondary mx-auto d-block" onclick="concludeAddWriter()">Conclude</button>`
    ;
}

function addWriter(){
    let value = document.getElementById('writersOptions').value;
    let parts = value.split(';');
    let id = parts[0];
    let name = parts[1];


    let updateBook = JSON.parse(window.localStorage.getItem('updateBook'));

    updateBook.writers.push(JSON.parse(`{"id": ${id}, "name": "${name}"}`));

    window.localStorage.setItem('updateBook', JSON.stringify(updateBook));
    document.getElementById('body-writer-show').innerHTML +=
        `
        <tr>
            <td>${name}</td>
            <td id="writer${id}-btn">
                <button type="button" class="btn btn-danger mx-auto d-block" onclick="removeWriter(${id})">Remove</button>
            </td>
        </tr>
        `;

    displayWritersOptions();
}

function concludeAddWriter(){
    document.getElementById('bd-writer-header').innerHTML = 'Name';
    document.getElementById('btn-addWriter').innerHTML =
        `
            <button type="button" class="btn btn-success mx-auto d-block" onClick="writersOptions()">Add</button>
        `;
}

function removeWriter(writerId){
    let updateBook = JSON.parse(window.localStorage.getItem('updateBook'));
    let writers = updateBook.writers;
    for(let i = 0; i < writerId; i++) {
        if (writers[i].id === writerId) {
            writers.splice(i, 1);
            break;
        }
    }

   document.getElementById(`writer${writerId}-btn`).innerHTML = `
        <td id="writer${writerId}-btn">
            <button type="button" class="btn btn-secondary mx-auto d-block"
                    onclick="undoRemoveWriter(${writerId})">Undo
            </button>
        </td>`;

    updateBook.writers = writers;
    window.localStorage.setItem('updateBook', JSON.stringify(updateBook));
}

function undoRemoveWriter(writerId){
    let updateBook = JSON.parse(window.localStorage.getItem('updateBook'));
    updateBook.writers.push(JSON.parse(`{"id": ${writerId}}`));

    document.getElementById(`writer${writerId}-btn`).innerHTML =
        `<td id="writer${writerId}-btn">
            <button type="button" class="btn btn-danger mx-auto d-block"
                    onClick="removeWriter(${writerId})">Remove
            </button>
        </td>`
    ;

    window.localStorage.setItem('updateBook', JSON.stringify(updateBook));
}

function removeTheme(themeId){
    let updateBook = JSON.parse(window.localStorage.getItem('updateBook'));
    let themes = updateBook.themes;
    for(let i = 0; i < themeId; i++) {
        if (themes[i].id === themeId) {
            themes.splice(i, 1);
            break;
        }
    }

    document.getElementById(`theme${themeId}-btn`).innerHTML = `
        <td id="theme${themeId}-btn">
            <button type="button" class="btn btn-secondary mx-auto d-block"
                    onclick="undoRemoveTheme(${themeId})">Undo
            </button>
        </td>`;

    updateBook.themes = themes;
    window.localStorage.setItem('updateBook', JSON.stringify(updateBook));
}

function undoRemoveTheme(themeId){
    let updateBook = JSON.parse(window.localStorage.getItem('updateBook'));
    updateBook.themes.push(JSON.parse(`{"id": ${themeId}}`));

    document.getElementById(`theme${themeId}-btn`).innerHTML =
        `<td id="theme${themeId}-btn">
            <button type="button" class="btn btn-danger mx-auto d-block"
                    onClick="removeTheme(${themeId})">Remove
            </button>
        </td>`
    ;

    window.localStorage.setItem('updateBook', JSON.stringify(updateBook));
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

async function getAllWriters(){
    const response = await fetch("http://localhost:8080/writer", {
        method: "GET",
        headers: new Headers(),
    });
    return await response.json();
}

displayBook();
cloneBook();