const API_BASE_URL = 'http://localhost:8080/api';

// --- 1. Fetch and display categories ---
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
                    <div class="col-md-3 mb-4">
                        <div class="card h-100 shadow-sm border-0">
                            <img src="${cat.picture}" class="card-img-top" alt="${cat.categoryName}">
                            <div class="card-body">
                                <h5 class="card-title text-dark fw-bold">${cat.categoryName}</h5>
                                <p class="card-text small text-muted">${cat.description}</p>
                                <a href="machines.html?categoryName=${encodeURIComponent(cat.categoryName)}" class="btn btn-success">View Machines</a>
                            </div>
                        </div>
                    </div>
                `;
                if (cat.categoryType === 'Vehicle') vehicleContainer.innerHTML += cardHTML;
                else if (cat.categoryType === 'Machine') machineContainer.innerHTML += cardHTML;
            });
        })
        .catch(error => console.error('Failed to load categories!', error));
}

// --- 2. Load machines with filtering (category, manufacturer, or search) ---
function loadMachines() {
    const machineContainer = document.getElementById('machine-container');
    if (!machineContainer) return;

    machineContainer.innerHTML = '<p class="text-center">Loading machines...</p>';

    const urlParams = new URLSearchParams(window.location.search);
    const categoryName = urlParams.get('categoryName');
    const manufacturer = urlParams.get('manufacturer');
    const searchQuery = urlParams.get('search'); 
    
    let url = `${API_BASE_URL}/machines`;
    
    if (categoryName) { url += `?categoryName=${encodeURIComponent(categoryName)}`; }
    else if (manufacturer) { url += `?manufacturer=${encodeURIComponent(manufacturer)}`; }
    else if (searchQuery) { url += `/search?name=${encodeURIComponent(searchQuery)}`; }

    fetch(url)
        .then(res => res.json())
        .then(data => {
            machineContainer.innerHTML = '';
            const h3Element = document.querySelector('h3');
            if (h3Element) {
                if (manufacturer) h3Element.innerText = `Machines by ${manufacturer}`;
                else if (categoryName) h3Element.innerText = `Machines in ${categoryName}`;
                else if (searchQuery) h3Element.innerText = `Search results for: "${searchQuery}"`;
            }

            if (data.length === 0) {
                machineContainer.innerHTML = '<p class="text-muted">No machines found.</p>';
                return;
            }

            data.forEach(m => {
                machineContainer.innerHTML += `
                    <div class="col-md-3 mb-4">
                        <div class="card h-100 shadow-sm border-0">
                            <img src="${m.iconPath}" class="card-img-top" alt="${m.name}" style="height: 200px; object-fit: cover;">
                            <div class="card-body">
                                <h5 class="card-title fw-bold">${m.name}</h5>
                                <p class="card-text text-muted">${m.releaseYear || ''} • ${Number(m.price).toLocaleString('de-DE')} EUR</p>
                                <a href="details.html?machineName=${encodeURIComponent(m.name)}" class="btn btn-success">View Details</a>
                            </div>
                        </div>
                    </div>
                `;
            });
        })
        .catch(err => {
            console.error("Error:", err);
            machineContainer.innerHTML = '<p class="text-danger">Failed to load machines!</p>';
        });
}

// --- 3. Fetch machine details and initialize gallery ---
let allPictures = [];
let currentLightboxIndex = 0;

function loadMachineDetails() {
    const params = new URLSearchParams(window.location.search);
    const name = params.get('machineName'); 
    const container = document.getElementById('details-container');
    
    if (!container) return;
    
    fetch(`${API_BASE_URL}/machines/name/${encodeURIComponent(name)}`)
        .then(res => res.json())
        .then(m => {
            // Sort pictures by ID for consistent ordering
            allPictures = Array.isArray(m.pictures) ? m.pictures.sort((a, b) => a.pictureId - b.pictureId) : [];
            const specs = Array.isArray(m.specifications) ? m.specifications : [];
            
            // Pre-load images to cache for smooth slider performance
            allPictures.forEach(p => { new Image().src = p.path; });
            
            currentLightboxIndex = 0;
            const firstImg = allPictures.length > 0 ? allPictures[0].path : 'placeholder.jpg';

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
                        <div class="main-slider-container" style="position: relative; border: 3px solid #eee; border-radius: 8px; overflow: hidden;">
                            <img id="main-view" src="${firstImg}" class="img-fluid" 
                                 style="width: 100%; height: 350px; object-fit: cover; cursor: pointer;"
                                 onclick="openLightbox(allPictures[currentLightboxIndex].path)">
                            
                            <button class="btn btn-dark" style="position: absolute; left: 10px; top: 50%; transform: translateY(-50%); opacity: 0.7;" onclick="changeMainSlide(-1)">&#10094;</button>
                            <button class="btn btn-dark" style="position: absolute; right: 10px; top: 50%; transform: translateY(-50%); opacity: 0.7;" onclick="changeMainSlide(1)">&#10095;</button>
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
        .catch(err => console.error("Failed to load machine details!", err));
}

// Change the main displayed slide in the gallery
function changeMainSlide(n) {
    if (allPictures.length === 0) return;
    currentLightboxIndex = (currentLightboxIndex + n + allPictures.length) % allPictures.length;
    const mainView = document.getElementById('main-view');
    if (mainView) mainView.src = allPictures[currentLightboxIndex].path;
}

// --- 4. Load manufacturers ---
function loadManufacturers() {
    const container = document.getElementById('manufacturer-container');
    if (!container) return;

    fetch(`${API_BASE_URL}/manufacturers`)
        .then(response => response.json())
        .then(data => {
            container.innerHTML = '';
            data.forEach(m => {
                container.innerHTML += `
                    <div class="col-md-2 mb-5">
                        <div class="card h-100 shadow-sm border-0">
                            <img src="${m.logo}" class="card-img-top" alt="${m.name}" style="height: 200px; object-fit: contain; padding: 15px;">
                            <div class="card-body text-center">
                                <h5 class="card-title text-dark fw-bold">${m.name}</h5>
                                <p class="card-text small text-muted">${m.madeIn || ''}</p>
                                <a href="machines.html?manufacturer=${encodeURIComponent(m.name)}" class="btn btn-success">View Machines</a>
                            </div>
                        </div>
                    </div>
                `;
            });
        })
        .catch(error => console.error('Error fetching manufacturers:', error));
}

// --- 5. Lightbox logic ---
function openLightbox(imgSrc) {
    const lightbox = document.getElementById("myLightbox");
    const fullImg = document.getElementById("fullImg");
    currentLightboxIndex = allPictures.findIndex(p => p.path === imgSrc);
    if (currentLightboxIndex === -1) currentLightboxIndex = 0;
    if (lightbox && fullImg) {
        fullImg.src = allPictures[currentLightboxIndex].path;
        lightbox.style.display = "flex";
    }
}

function closeLightbox() {
    const lightbox = document.getElementById("myLightbox");
    if (lightbox) lightbox.style.display = "none";
}

function changeLightboxSlide(n) {
    if (allPictures.length === 0) return;
    const fullImg = document.getElementById("fullImg");
    if (!fullImg) return;
    currentLightboxIndex = (currentLightboxIndex + n + allPictures.length) % allPictures.length;
    fullImg.src = allPictures[currentLightboxIndex].path;
}

// --- 6. Search functionality ---
function setupSearch() {
    const searchForm = document.querySelector('form'); 
    if (!searchForm) return;
    searchForm.addEventListener('submit', function(e) {
        e.preventDefault(); 
        const query = searchForm.querySelector('input').value;
        if (query.trim()) window.location.href = `machines.html?search=${encodeURIComponent(query)}`;
    });
}
