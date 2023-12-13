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
    let name = parts[1];
    let line = document.getElementById("body-writer-select");

    line.innerHTML += `
            <tr>
                <td>${name}</td>
            </tr>`;

    let id = parts[0];
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
    let name = parts[1];
    let line = document.getElementById("body-theme-select");

    line.innerHTML += `
            <tr>
                <td>${name}</td>
            </tr>`;

    let id = parts[0];
    let themes = JSON.parse(window.localStorage.getItem('themes')) || [];
    themes.push(id);
    window.localStorage.setItem('themes', JSON.stringify(themes));
}

async function registerBook(){
    let title = document.getElementById("title").value;
    let year = document.getElementById("year").value;
    let publisher = document.getElementById("publisher").value;
    console.log('publisher' + publisher);
    let writers = adicionarLabel(JSON.parse(window.localStorage.getItem('writers')));
    let themes = adicionarLabel(JSON.parse(window.localStorage.getItem('themes')));

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

    if(response.ok) {
        window.localStorage.removeItem('writers');
        window.localStorage.removeItem('themes');
        window.location.href = "books.html";
    }
}

function adicionarLabel(arrayEmJson){
    let mapped = arrayEmJson.map(id => ({ id }))

    console.log(mapped);
    return mapped;
}


showWriterOptions();
showThemeOptions();
