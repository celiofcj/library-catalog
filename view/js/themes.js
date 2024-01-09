async function displayThemes() {
    let themes = await getAllThemes();
    let line = "";
    let i = 1;

    themes.forEach(theme => {
        let description = getDescription(theme);
        let nOfBooks = getNumOfBooks(theme);
        line += `<tr>
                    <th>${i}</th>
                    <td><a href="#" class="redirect" onclick="themeDetails(${theme.id})">${theme.name}</a></td>
                    <td>${description}</td>
                    <td>${nOfBooks}</td>
        `;
        i++;
    });
    document.getElementById("body-table-themes").innerHTML = line;
}

async function getAllThemes(){
    const response = await fetch("http://localhost:8080/theme", {
        method: "GET",
        headers: new Headers(),
    });
    return await response.json();
}

function getDescription(theme){
    const length = 60;
    let description =  theme.description.substring(0, length);
    if(theme.description.length > length){
        description+='...';
    }
    return description;
}

function getNumOfBooks(theme){
    return theme.books.length;
}

function themeDetails(themeId){
    window.localStorage.setItem('themeId', themeId);
    window.location.href="themedetails.html";
}


displayThemes();