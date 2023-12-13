async function registerWriter() {
    let name = document.getElementById('name').value;
    let bio = document.getElementById('bio').value;

    const response = await fetch("http://localhost:8080/writer", {
        method: "POST",
        headers: {
            "Content-Type": "application/json; charset=utf8"
        },
        body: JSON.stringify({
            name: name,
            bio: bio
        }),
    });

    if(response.ok){
        window.location.href = "writers.html";
    }
}