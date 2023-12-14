async function showWriterOptions() {
    let writers = await getAllWriters();
    let line = `<option selected value ="0">Select Writer...</option>`;
    writers.forEach(writers => {
        line += `
            <option value="${writers.id};${writers.name}">${writers.name}</option>
        `;

    });

    document.getElementById("writersOptions").innerHTML = line;
}

async function getAllWriters(){
    const response = await fetch("http://localhost:8080/writer", {
        METHOD: "GET",
        HEADER: new Headers({
            "Content-Type": "application/json; charset=utf8",
        })
    });

    return await response.json();
}

function registerWriter() {
    let parts = document.getElementById('writersOptions').value.split(';');
    let id = parts[0];
    let name = parts[1];
    let line = document.getElementById("body-writer-select");

    line.innerHTML += `
            <tr id="writers_${id}">
                <td>${name}</td>
                <td>
                    <button class="btn btn-primary mx-auto d-block"
                     type="button" onclick="remove('writers', ${id})">
                     Remove
                    </button>
                </td>
            </tr>`;
    let writers = JSON.parse(window.localStorage.getItem('writers')) || [];
    writers.push(id);
    window.localStorage.setItem('writers', JSON.stringify(writers));
}

async function showThemeOptions() {
    let themes = await getAllThemes();
    let line = `<option selected value ="0">Select Theme...</option>`;
    themes.forEach(theme => {
        line += `
            <option value="${theme.id};${theme.name}">${theme.name}</option>
        `;

    });

    document.getElementById("themesOptions").innerHTML = line;
}

async function getAllThemes(){
    const response = await fetch("http://localhost:8080/theme", {
        METHOD: "GET",
        HEADER: new Headers({
            "Content-Type": "application/json; charset=utf8",
        })
    });

    return await response.json();
}

function registerTheme() {
    let parts = document.getElementById('themesOptions').value.split(';');
    let id = parts[0];
    let name = parts[1];
    let line = document.getElementById("body-theme-select");

    line.innerHTML += `
                <tr id="themes_${id}">  
                    <td>${name}</td>
                    <td class="remove-button">
                        <button class="btn btn-primary mx-auto d-block"
                         type="button" onclick="remove('themes', ${id})">
                         Remove
                        </button>
                    </td>
                </tr>`;
    let themes = JSON.parse(window.localStorage.getItem('themes')) || [];
    themes.push(id);
    window.localStorage.setItem('themes', JSON.stringify(themes));
}

async function registerBook(){
    let title = document.getElementById("title").value;
    let year = document.getElementById("year").value;
    let publisher = document.getElementById("publisher").value;
    let writers = JSON.parse(window.localStorage.getItem('writers')).map(function(numString){
        return parseInt(numString);
    });
    let themes = JSON.parse(window.localStorage.getItem('themes')).map(function (numString){
        return parseInt(numString);
    });

    const response = await fetch("http://localhost:8080/book", {
        method: "POST",
        headers: new Headers({
            "Content-Type": "application/json; charset=utf8"
        }),
        body: JSON.stringify({
            title: title,
            year: parseInt(year),
            publisher: publisher,
            publishYear: year,
            writers: writers,
            themes: themes
        })
    });

    console.log(response);

    if(response.ok) {
        window.localStorage.removeItem('writers');
        window.localStorage.removeItem('themes');
        window.location.href = "books.html";
    }
}

function remove(attribute, id){
    console.log(attribute + ' ' + id);
    document.getElementById(`${attribute}_${id}`).remove();
    let data = JSON.parse(window.localStorage.getItem(`${attribute}`));
    data = data.filter(item => item !== id.toString())
    window.localStorage.setItem(`${attribute}`, JSON.stringify(data));
}


showWriterOptions();
showThemeOptions();
