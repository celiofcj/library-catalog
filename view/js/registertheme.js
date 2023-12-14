async function registerTheme() {
    let name = document.getElementById('name').value;
    let description = document.getElementById('description').value;

    const response = await fetch("http://localhost:8080/theme", {
        method: "POST",
        headers: {
            "Content-Type": "application/json; charset=utf8"
        },
        body: JSON.stringify({
            name: name,
            description: description
        }),
    });

    if(response.ok){
        window.location.href = "themes.html";
    }
}