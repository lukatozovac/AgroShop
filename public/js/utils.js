const API_BASE_URL = 'http://localhost:8080/api';

// --- 1. Učitavanje kategorija ---
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
                if (cat.categoryType === 'Vehicle') vehicleContainer.innerHTML += cardHTML;
                else if (cat.categoryType === 'Machine') machineContainer.innerHTML += cardHTML;
            });
        })
        .catch(error => console.error('Error:', error));
}

// --- 2. Učitavanje mašina ---
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

// --- 3. Učitavanje detalja mašine ---
// Globalne varijable na vrhu fajla
let allPictures = [];
let slideIndex = 0;
let currentLightboxIndex = 0;

// ... (loadCategories i loadMachines ostaju isti)

function loadMachineDetails() {
    const params = new URLSearchParams(window.location.search);
    const name = params.get('machineName'); 
    const container = document.getElementById('details-container');
    
    if (!container) return;
    
    fetch(`${API_BASE_URL}/machines/name/${encodeURIComponent(name)}`)
        .then(res => res.json())
        .then(m => {
            // OVO JE KLJUČNO: Popunjavamo globalni niz
            allPictures = Array.isArray(m.pictures) ? m.pictures : [];
            const specs = Array.isArray(m.specifications) ? m.specifications : [];
            
            container.innerHTML = `
                <div class="row mb-5">
                    <div class="col-12">
                        <h1 class="text-success fw-bold">${m.name}</h1>
                        <p class="text-muted mt-3 fs-5">${m.description || 'No description available.'}</p>
                        <h3 class="text-dark fw-bold">Price: ${Number(m.price).toLocaleString('de-DE')} EUR</h3>
                    </div>
                </div>

                <div class="row mt-4">
                    <div class="col-lg-6">
                        <h3 class="mb-3">Photo Gallery</h3>
                        <div class="slider-wrapper">
                            ${allPictures.map((p, i) => `
                                <img src="${p.path}" class="slide ${i === 0 ? 'active' : ''}" 
                                     onclick="openLightbox('${p.path}')" style="cursor: pointer;">
                            `).join('')}
                            <button class="prev" onclick="plusSlides(-1)">&#10094;</button>
                            <button class="next" onclick="plusSlides(1)">&#10095;</button>
                        </div>
                    </div>
                    <div class="col-lg-6">
                        <h3 class="mb-3">Technical Specifications</h3>
                        <ul class="list-group list-group-flush border rounded">
                            ${specs.map(s => `
                                <li class="list-group-item d-flex justify-content-between">
                                    <span class="text-secondary">${s.name}</span>
                                    <span class="fw-bold text-dark">${s.value}</span>
                                </li>
                            `).join('')}
                        </ul>
                    </div>
                </div>
            `;
        })
        .catch(err => console.error("Greška:", err));
}

// Logika za Lightbox
function openLightbox(imgSrc) {
    const lightbox = document.getElementById("myLightbox");
    const fullImg = document.getElementById("fullImg");
    
    // Pronađi indeks kliknute slike u globalnom nizu
    currentLightboxIndex = allPictures.findIndex(p => p.path === imgSrc);
    
    if (currentLightboxIndex !== -1) {
        fullImg.src = allPictures[currentLightboxIndex].path;
        lightbox.style.display = "flex";
    }
}

function closeLightbox() {
    document.getElementById("myLightbox").style.display = "none";
}

function changeLightboxSlide(n) {
    if (allPictures.length === 0) return;
    currentLightboxIndex = (currentLightboxIndex + n + allPictures.length) % allPictures.length;
    document.getElementById("fullImg").src = allPictures[currentLightboxIndex].path;
}

// Zatvaranje na klik van slike
window.onclick = function(event) {
    const lightbox = document.getElementById("myLightbox");
    if (event.target == lightbox) closeLightbox();
}

// plusSlides funkcija (za mali slajder)
function plusSlides(n) {
    let slides = document.getElementsByClassName("slide");
    if (slides.length === 0) return;
    slides[slideIndex].classList.remove("active");
    slideIndex = (slideIndex + n + slides.length) % slides.length;
    slides[slideIndex].classList.add("active");
}
