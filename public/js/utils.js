const API_BASE_URL = 'http://localhost:8080/api';

// 1. Funkcija za učitavanje kategorija podeljenih na Vozila i Mašine (za index.html)
function loadCategories() {
    const vehicleContainer = document.getElementById('vehicle-container');
    const machineContainer = document.getElementById('machine-container');
    
    if (!vehicleContainer || !machineContainer) return;

    fetch(`${API_BASE_URL}/categories`)
        .then(response => response.json())
        .then(data => {
            vehicleContainer.innerHTML = '';
            machineContainer.innerHTML = '';

            data.forEach(cat => {
                const cardHTML = `
                    <div class="col-md-4">
                        <div class="card h-100 shadow-sm border-0">
                            <img src="${cat.picture}" class="card-img-top" alt="${cat.categoryName}">
                            <div class="card-body">
                                <h5 class="card-title text-dark fw-bold">${cat.categoryName}</h5>
                                <p class="card-text small text-muted">${cat.description}</p>
                                <a href="machines.html?categoryName=${encodeURIComponent(cat.categoryName)}" class="btn btn-outline-success">View Machines</a>
                            </div>
                        </div>
                    </div>
                `;

                if (cat.categoryType === 'Vehicle') {
                    vehicleContainer.innerHTML += cardHTML;
                } else if (cat.categoryType === 'Machine') {
                    machineContainer.innerHTML += cardHTML;
                }
            });
        })
        .catch(error => console.error('Error:', error));
}

// 2. Funkcija za učitavanje mašina (OVO TI JE FALILO!)
function loadMachines() {
    const params = new URLSearchParams(window.location.search);
    const categoryName = params.get('categoryName');
    const container = document.getElementById('machine-container');
    if (!container) return;

    fetch(`${API_BASE_URL}/machines?categoryName=${encodeURIComponent(categoryName)}`)
        .then(res => res.json())
        .then(data => {
            container.innerHTML = '';
            data.forEach(m => {
                container.innerHTML += `
                    <div class="col-md-4">
                        <div class="card mb-4">
                            <img src="${m.iconPath}" class="card-img-top" alt="${m.name}">
                            <div class="card-body">
                                <h5>${m.name}</h5>
                                <p>${Number(m.price).toLocaleString('de-DE')} EUR</p>
                                <a href="details.html?machineName=${encodeURIComponent(m.name)}" class="btn btn-success">View Details</a>
                            </div>
                        </div>
                    </div>
                `;
            });
        })
        .catch(err => console.error("Greška pri učitavanju mašina:", err));
}

// 3. Funkcija za detalje mašine
function loadMachineDetails() {
    const params = new URLSearchParams(window.location.search);
    const name = params.get('machineName'); 
    
    const container = document.getElementById('details-container');
    if (!container) return;

    if (!name) return;

    fetch(`${API_BASE_URL}/machines/name/${encodeURIComponent(name)}`)
        .then(res => res.json())
        .then(m => {
            container.innerHTML = `
                <h1>${m.name}</h1>
                <p>${m.description}</p>
                <h3>Price: ${Number(m.price).toLocaleString('de-DE')} EUR</h3>
                <button class="btn btn-warning">Add to Cart</button>
            `;
        })
        .catch(err => console.error("Greška:", err));
}
